package codec;

import bean.VCardDevice;
import global.config.ConfigInfo;
import handler.ProtocolHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.ControlCode;
import protocol.MessageHeader;
import protocol.VCardMessage;
import utils.BufferUtil;
import utils.cryption.Pkcs7Encoder;

import java.nio.ByteOrder;

import static utils.BufferUtil.getInt;
import static utils.cryption.CRC16Util.calcCRC16;


public class VCardMessageDecoder extends LengthFieldBasedFrameDecoder {

    public static final int MAX_FRAME_LENGTH = 1024 * 1024;
    public static final int LENGTH_FIELD_LENGTH = 4;
    public static final int LENGTH_FIELD_OFFSET = 4;
    public static final int LENGTH_ADJUSTMENT = 0;
    public static final int INITIAL_BYTES_TO_STRIP = 0;

    private static final int MIX_SIZE = 14; // 数据最小大小
    private static final int HEADER_SIZE = 12; //固定协议头大小
    private static final int CRC_CODE_SIZE = 2; // 校验码大小

    public VCardMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public VCardMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public VCardMessageDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null || in.readableBytes() < MIX_SIZE)
            return null;
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);

        // 首先，需要进行解密
        byte[] bytes = new byte[frame.readableBytes()];
        frame.readBytes(bytes);

        // 查看是否相应的ivs
        byte[] ivs = null;
        VCardDevice dd = ProtocolHandler.getInstance().find(ctx.channel());
        if (dd == null) {
            ivs = BufferUtil.lastBytes(bytes, ConfigInfo.IVS_COUNT);
            dd = new VCardDevice();
            dd.setKeyIV(ivs);
        } else {
            ivs = dd.getKeyIV();
        }

        byte[] plaintext = decrypt(bytes, ivs);
        frame = Unpooled.copiedBuffer(plaintext);
        frame.markReaderIndex();

        // 然后，进行校验
        bytes = new byte[frame.readableBytes()];
        frame.readBytes(bytes);
        if (!verifyCrc16(bytes))
            return null;

        // 最后，开始解析数据
        // 此处需要把应用数据部分转化为 ByteBuf 设置到Body里面
        frame = frame.resetReaderIndex();

        VCardMessage message = new VCardMessage();
        MessageHeader header = new MessageHeader();
        ControlCode controlCode = new ControlCode();

        header.setDeviceId(frame.readInt());
        header.setLength(frame.readInt());
        header.setInfoCode(frame.readShort());

        controlCode.setCategoryCode(frame.readByte());
        controlCode.setCommandCode(frame.readByte());

        header.setControlCode(controlCode);
        message.setHeader(header);

        // 获取应用数据部分
        int len = frame.readableBytes() - (HEADER_SIZE + CRC_CODE_SIZE);
        if (len > 0) {
            ByteBuf body = frame.copy(HEADER_SIZE, len);
            message.setAppData(body);
        } else {
            message.setAppData(null);
        }

        // 忽略response中的crc16校验码
        return message;
    }

    private static boolean verifyCrc16(byte[] data) {
        // 获取数据包中的crc16
        byte[] originCrc16Code = BufferUtil.lastBytes(data, CRC_CODE_SIZE);
        if (originCrc16Code == null || data.length < MIX_SIZE)
            return false;
        int originCrc16CodeInt = getInt(originCrc16Code);

        // 计算收到数据的crc169
        byte[] allDataBytes = BufferUtil.subBytes(data, 0, data.length - CRC_CODE_SIZE);
        int genCrc16code = calcCRC16(allDataBytes);

        // 根据两个crc16进行判断是否相等
        if (genCrc16code != originCrc16CodeInt)
            return false;
        return true;
    }

    private static byte[] decrypt(byte[] ciphertext, byte[] ivs) {
        if (ivs == null)
            return null;

        byte[] plaintext = Pkcs7Encoder.decryptOfDiyIV(ciphertext, ConfigInfo.getCommKey().getBytes(), ivs);
        return plaintext;
    }

}
