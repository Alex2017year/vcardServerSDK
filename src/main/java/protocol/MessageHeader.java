package protocol;

import java.util.Objects;

public class MessageHeader {

    public static final int MAX_CMD_SEQUENCE = 0x7FFF; // 最大的命令序列号
    private static final int HIGHEST_BIT_INDEX = 15; // short 最高位置索引

    private int deviceId; // 设备ID
    private int length; // 加密后数据长度
    private short infoCode; // 信息代码
    private ControlCode controlCode; // 控制码
    private MessageType msgType; // 消息类型
    private short cmdSequence; // 命令序列号

    public MessageHeader() { }

    public MessageHeader(int deviceId, int length, short infoCode, ControlCode controlCode) {
        this.deviceId = deviceId;
        this.length = length;
        this.infoCode = infoCode;
        this.controlCode = controlCode;
    }

    public void setInfoCode(MessageType msgType, short cmdSequence) {
        if (cmdSequence > MAX_CMD_SEQUENCE)
            cmdSequence = 0;

        this.msgType = msgType;
        this.cmdSequence = cmdSequence;
        this.infoCode = (short) (msgType.getValue() << HIGHEST_BIT_INDEX | cmdSequence);
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public short getCmdSequence() {
        return cmdSequence;
    }

    public enum MessageType {
        SERVER_SENDER((byte) 0),
        DEVICE_SENDER((byte) 1);

        public byte getValue() {
            return value;
        }

        private byte value;

        MessageType(byte value) {
            this.value = value;
        }
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(short infoCode) {
        this.infoCode = infoCode;
        this.msgType = (this.infoCode >>> HIGHEST_BIT_INDEX) == 0 ? MessageType.SERVER_SENDER : MessageType.DEVICE_SENDER;
        this.cmdSequence = (short) (this.infoCode & MAX_CMD_SEQUENCE);
    }

    public ControlCode getControlCode() {
        return controlCode;
    }

    public void setControlCode(ControlCode controlCode) {
        this.controlCode = controlCode;
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "deviceId=" + deviceId +
                ", length=" + length +
                ", infoCode=" + infoCode +
                ", controlCode=" + controlCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageHeader)) return false;
        MessageHeader that = (MessageHeader) o;
        return getDeviceId() == that.getDeviceId() &&
                getLength() == that.getLength() &&
                getInfoCode() == that.getInfoCode() &&
                getCmdSequence() == that.getCmdSequence() &&
                Objects.equals(getControlCode(), that.getControlCode()) &&
                getMsgType() == that.getMsgType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getLength(), getInfoCode(), getControlCode(), getMsgType(), getCmdSequence());
    }
}
