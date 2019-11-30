package codec;


import bean.VCardDevice;
import global.config.ConfigInfo;
import handler.ProtocolHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocol.MessageHeader;
import protocol.VCardMessage;
import utils.BufferUtil;
import utils.cryption.CRC16Util;
import utils.cryption.Pkcs7Encoder;

public class VCardMessageEncoder extends MessageToByteEncoder<VCardMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, VCardMessage vCardMessage, ByteBuf sendBuf) throws Exception {
        if (vCardMessage == null || vCardMessage.getHeader() == null)
            throw new Exception("The encode message is null");

        VCardDevice dd = ProtocolHandler.getInstance().find(channelHandlerContext.channel());
        if (dd == null || dd.getKeyIV() == null) {
            return;
        }

        MessageHeader header = vCardMessage.getHeader();
        Object appData = vCardMessage.getAppData();

        int len = 0;

        // 生成crc16检验码
        ByteBuf crcBuf = channelHandlerContext.channel().alloc().heapBuffer();
        crcBuf.writeInt(header.getDeviceId());
        crcBuf.writeInt(header.getLength());
        crcBuf.writeShort(header.getInfoCode());
        crcBuf.writeByte(header.getControlCode().getCategoryCode());
        crcBuf.writeByte(header.getControlCode().getCommandCode());


        // 开始加密
        ByteBuf plaintextBuf = channelHandlerContext.channel().alloc().heapBuffer();
        plaintextBuf.writeShort(header.getInfoCode());
        plaintextBuf.writeByte(header.getControlCode().getCategoryCode());
        plaintextBuf.writeByte(header.getControlCode().getCommandCode());

        if (appData != null) {
            ByteBuf body = (ByteBuf) appData;

            // 开始计算长度
            len = BufferUtil.calcuatePacketLength(body.readableBytes());
            header.setLength(len);
            crcBuf.writeBytes(body);

            byte[] buf = new byte[crcBuf.readableBytes()];
            crcBuf.readBytes(buf);
            short crc16 = genCrc16Code(buf);
            if (crc16 == 0) {
                return;
            }

            plaintextBuf.writeBytes(body);
            plaintextBuf.writeShort(crc16);

            buf = new byte[plaintextBuf.readableBytes()];
            plaintextBuf.readBytes(buf);
            byte[] totalBuf = encrypt(buf, dd.getKeyIV());

            // 将加密后数据写入ByteBuf
            sendBuf.writeBytes(totalBuf);

        } else {
            len = BufferUtil.calcuatePacketLength(0);
            header.setLength(len);

            byte[] buf = new byte[crcBuf.readableBytes()];
            crcBuf.readBytes(buf);
            short crc16 = genCrc16Code(buf);
            if (crc16 == 0) {
                return;
            }

            plaintextBuf.writeShort(crc16);

            buf = new byte[plaintextBuf.readableBytes()];
            plaintextBuf.readBytes(buf);
            byte[] totalBuf = encrypt(buf, dd.getKeyIV());

            // 将加密后数据写入ByteBuf
            sendBuf.writeBytes(totalBuf);
        }
    }

    private static byte[] encrypt(byte[] plaintext, byte[] ivs) {
        if (plaintext == null || ivs == null) {
            return null;
        }
        return Pkcs7Encoder.decryptOfDiyIV(plaintext, ConfigInfo.getCommKey().getBytes(), ivs);
    }

    private static short genCrc16Code(byte[] data) {
        if (data == null) {
            return 0;
        }
        return (short) CRC16Util.calcCRC16(data);
    }



}
