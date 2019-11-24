package codec;

import com.lingyan.bean.DeviceData;
import com.lingyan.global.config.ConfigInfo;
import com.lingyan.handler.ProtocolHandler;
import com.lingyan.protocol.MessageHeader;
import com.lingyan.protocol.VCardMessage;
import com.lingyan.utils.BufferUtil;
import com.lingyan.utils.cryption.CRC16Util;
import com.lingyan.utils.cryption.Pkcs7Encoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class VCardMessageEncoder extends MessageToByteEncoder<VCardMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, VCardMessage vCardMessage, ByteBuf sendBuf) throws Exception {
        if (vCardMessage == null || vCardMessage.getHeader() == null)
            throw new Exception("The encode message is null");

        DeviceData dd = ProtocolHandler.getInstance().find(channelHandlerContext.channel());
        if (dd == null || dd.getKeyIV() == null) {
            return;
        }

        MessageHeader header = vCardMessage.getHeader();
        ByteBuf body = (ByteBuf) vCardMessage.getAppData();

        // 开始计算长度
        int len = BufferUtil.calcuatePacketLength(body.readableBytes());
        header.setLength(len);

        // 生成crc16检验码
        ByteBuf crcBuf = channelHandlerContext.channel().alloc().heapBuffer();
        crcBuf.writeInt(header.getDeviceId());
        crcBuf.writeInt(header.getLength());
        crcBuf.writeShort(header.getInfoCode());
        crcBuf.writeByte(header.getControlCode().getCategoryCode());
        crcBuf.writeByte(header.getControlCode().getCommandCode());
        crcBuf.writeBytes(body);

        byte[] buf = new byte[crcBuf.readableBytes()];
        crcBuf.readBytes(buf);
        short crc16 = genCrc16Code(buf);
        if (crc16 == 0) {
            return;
        }

        // 开始加密
        ByteBuf plaintextBuf = channelHandlerContext.channel().alloc().heapBuffer();
        plaintextBuf.writeShort(header.getInfoCode());
        plaintextBuf.writeByte(header.getControlCode().getCategoryCode());
        plaintextBuf.writeByte(header.getControlCode().getCommandCode());
        plaintextBuf.writeBytes(body);
        plaintextBuf.writeShort(crc16);

        buf = new byte[plaintextBuf.readableBytes()];
        plaintextBuf.readBytes(buf);
        byte[] totalBuf = encrypt(buf, dd.getKeyIV());

        // 将加密后数据写入ByteBuf
        sendBuf.writeBytes(totalBuf);
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
