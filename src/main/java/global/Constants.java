package global;

public class Constants {

    // 设备通信状态
    public enum ConnectionStatus {
        COM_FAILED((byte) 0),
        HEALTHY((byte) 1),
        REGISTRATION_REQUEST((byte) 2);

        public byte value;
        ConnectionStatus(byte value) {
            this.value = value;
        }
    }

    public static final byte FORCE_UPDATE = 0x25;
    public static final int TIME_DIFFERENCE = 5*60; // 设备端时间与服务器端时间差5min，立马向设备端更新时间

    public final static byte DEVICES_PARAMETER_CATEGORY = (byte) 0x01;
    public final static byte DEVICES_CONTROL_CATEGORY = (byte) 0x02;
    public final static byte DEVICES_AUTHORIZATION_CATEGORY = (byte) 0x03;
    public final static byte ACCESS_RECORD_EVENT_CATEGORY = (byte) 0x04;
    public final static byte AUTHORIZED_FACE_RECOGNITION_CATEGORY = (byte) 0x05;
    public final static byte COMPOSITE_DATA_CATEGORY = (byte) 0x06;
    public final static byte FILE_UPLOAD_CATEGORY = (byte) 0x07;
    public final static byte AUTHORIZATION_CODE_CATEGORY = (byte) 0x08;
    public final static byte UI_SET_CATEGORY = (byte) 0x09;
    public final static byte UNIVERSAL_CATEGORY = (byte) 0xFF; // 通用接口分类


    public final static byte COMMAND_00 = (byte) 0x00;
    public final static byte COMMAND_02 = (byte) 0x02;
    public final static byte COMMAND_03 = (byte) 0x03;
    public final static byte COMMAND_04 = (byte) 0x04;
    public final static byte COMMAND_05 = (byte) 0x05;
    public final static byte COMMAND_06 = (byte) 0x06;
    public final static byte COMMAND_07 = (byte) 0x07;
    public final static byte COMMAND_08 = (byte) 0x08;
    public final static byte COMMAND_09 = (byte) 0x09;
    public final static byte COMMAND_0A = (byte) 0x0A;
    public final static byte COMMAND_0B = (byte) 0x0B;
    public final static byte COMMAND_0C = (byte) 0x0C;
    public final static byte COMMAND_0D = (byte) 0x0D;
    public final static byte COMMAND_0E = (byte) 0x0E;
    public final static byte COMMAND_0F = (byte) 0x0F;
    public final static byte COMMAND_10 = (byte) 0x10;

}
