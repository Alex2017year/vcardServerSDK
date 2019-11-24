package utils;

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

}
