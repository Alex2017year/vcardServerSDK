package bean;

import java.util.BitSet;

public class DeviceStateInfo {
    public enum PowerEnum {
        POWER_SOURCE((byte)0), // 电源取电
        UPS((byte)1); // UPS 供电

        public byte value;
        private PowerEnum(byte value) {
            this.value = value;
        }
    }

    public DeviceStateInfo() {
    }

    private int runningTime;
    private int localTime;
    private byte systemLoad;
    private short totalMemory; // 内存总量
    private short availableMemory; // 可使用内存量
    private short sharedMemory; // 共享内存使用量
    private short cacheMemory; // 缓冲内存使用量
    private PowerEnum powerType; // 电源类型
    private byte power; // 电量百分比
    private byte temperature;


// setters and getters
    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getLocalTime() {
        return localTime;
    }

    public void setLocalTime(int localTime) {
        this.localTime = localTime;
    }

    public byte getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(byte systemLoad) {
        this.systemLoad = systemLoad;
    }

    public short getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(short totalMemory) {
        this.totalMemory = totalMemory;
    }

    public short getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(short availableMemory) {
        this.availableMemory = availableMemory;
    }

    public short getSharedMemory() {
        return sharedMemory;
    }

    public void setSharedMemory(short sharedMemory) {
        this.sharedMemory = sharedMemory;
    }

    public short getCacheMemory() {
        return cacheMemory;
    }

    public void setCacheMemory(short cacheMemory) {
        this.cacheMemory = cacheMemory;
    }

    public PowerEnum getPowerType() {
        return powerType;
    }

    public byte getPowerPercent() {
        return power;
    }

    public void setPower(byte value) {
        int enumValue = value & 0x80; // get high bit
        switch(enumValue) {
            case 0:
                powerType = PowerEnum.POWER_SOURCE;
                break;
            case 1:
                powerType = PowerEnum.UPS;
                break;
            default:
                break;
        }

        this.power = (byte) (value & 0x3F);
    }

    public byte getTemperature() {
        return temperature;
    }

    public void setTemperature(byte temperature) {
        this.temperature = temperature;
    }
}
