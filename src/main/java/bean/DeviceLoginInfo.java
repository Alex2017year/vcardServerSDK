package bean;

public class DeviceLoginInfo {

    public DeviceLoginInfo() { }

    public enum LoginReason {
        STARTUP_REG((byte) 0x00),
        TIMER_REG((byte) 0x01),
        REGISTER_DECRYPTION_FAILURE_REG((byte) 0x02),
        REGISTER_AUTHENTICATION_FAILURE_REG((byte) 0x03),
        DECRYPTION_FAILURE_REG((byte) 0x04),
        NEGOTIATE_FAILURE_REG((byte) 0x05),
        TIMEOUT_NET((byte) 0x06),
        NET_ANOMALY((byte) 0x07),
        CHECK_FAILURE((byte) 0x08),
        SHORT_DATA((byte) 0x09);

        public byte value;
        private LoginReason(byte value) {
            this.value = value;
        }
    }

    private int deviceId;
    private LoginReason loginReason;
    private byte protocolVerion;
    private int timestamp;
    private short randomNum;
    private String signature;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public LoginReason getLoginReason() {
        return loginReason;
    }

    public void setLoginReason(LoginReason loginReason) {
        this.loginReason = loginReason;
    }

    public byte getProtocolVerion() {
        return protocolVerion;
    }

    public void setProtocolVerion(byte protocolVerion) {
        this.protocolVerion = protocolVerion;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public short getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(short randomNum) {
        this.randomNum = randomNum;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
