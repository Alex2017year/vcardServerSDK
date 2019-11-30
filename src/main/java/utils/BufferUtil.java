package utils;

import java.nio.ByteBuffer;

import static java.lang.System.arraycopy;

public class BufferUtil {

    public static byte[] subBytes(byte[] src, int startIdx, int count) {
        if (src == null)
            return null;

        if (src.length < startIdx + count + 1)
            return null;

        byte[] result = new byte[count];
        System.arraycopy(src, startIdx, result, 0, count);

        return result;
    }

    public static byte[] lastBytes(byte[] src, int count) {
        if (src == null || src.length < count)
            return null;

        byte[] result = new byte[count];
        System.arraycopy(src, src.length - count, result, 0, count);
        return result;
    }

    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 3] & 0xff) << 24)
                | ((bb[index + 2] & 0xff) << 16)
                | ((bb[index + 1] & 0xff) << 8)
                | ((bb[index + 0] & 0xff) << 0)));
    }

    public static int getInt(byte[] bytes) {
        return getInt(bytes, 0);
    }


    public static int calcuatePacketLength(int dataLength) {
        int totalPacketLenght = 0;
        if ((dataLength + 6) % 16 == 0) {
            totalPacketLenght = dataLength + 6 + 16;
        } else {
            totalPacketLenght = ((dataLength + 6) / 16 + 1) * 16;
        }

        totalPacketLenght += 8;
        return totalPacketLenght;
    }

    public static byte[] long2Bytes(long srcNum) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(srcNum);
        return buffer.array();
    }

    public static byte[] int2Bytes(int srcNum) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(srcNum);
        return buffer.array();
    }

    public static byte[] short2Bytes(short srcNum) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(srcNum);
        return buffer.array();
    }

    public static byte[] byte2Bytes(byte srcNum) {
        ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES);
        buffer.put(srcNum);
        return buffer.array();
    }

    public static void putShort(byte[] b, short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    public static void putInt(byte[] b, int x, int index) {
        b[index + 3] = (byte) (x >> 24);
        b[index + 2] = (byte) (x >> 16);
        b[index + 1] = (byte) (x >> 8);
        b[index + 0] = (byte) (x >> 0);
    }

    public static void putString(byte[] src, String str, int index) {
        byte[] strBytes = str.getBytes();
        arraycopy(src, index, strBytes, 0, strBytes.length);
    }

}
