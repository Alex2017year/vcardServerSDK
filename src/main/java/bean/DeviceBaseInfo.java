package bean;

public class DeviceBaseInfo {
    public DeviceBaseInfo() {}

    public DeviceBaseInfo(int deviceType, String version, short flashVolume,
                          String deviceAlias, short agentCode) {
        this.deviceType = deviceType;
        this.version = version;
        this.flashVolume = flashVolume;
        this.deviceAlias = deviceAlias;
        this.agentCode = agentCode;
    }

    private int deviceType;
    private String version;
    private short flashVolume;
    private String deviceAlias; // UTF-8
    private short agentCode;


    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public short getFlashVolume() {
        return flashVolume;
    }

    public void setFlashVolume(short flashVolume) {
        this.flashVolume = flashVolume;
    }

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }

    public short getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(short agentCode) {
        this.agentCode = agentCode;
    }
}