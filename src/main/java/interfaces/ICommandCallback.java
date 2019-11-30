package interfaces;

public interface ICommandCallback {

    enum ResultCode {
        OK(0x0), // ok
        ECC_ERROR(0x1), // 校验错误
        PARAMETER_ERROR(0x2), // 参数错误
        BUFFER_OVERFLOW_ERROR(0x3), // 缓冲区越界
        REINVENT_ERROR(0x4), // 重复设置
        CONFIG_OR_HARDWARE_NOT_SUPPORT_ERROR(0xFF); // 配置错误或硬件不支持

        private int value;
        ResultCode(int value) {
            this.value = value;
        }
    }

    // 设备基本信息数据结构
    class DeviceBaseInfo {
        public DeviceBaseInfo() {}

        public DeviceBaseInfo(int deviceType, String version, short flashVolume,
                              String deviceAlias, short agentCode) {
            this.deviceType = deviceType;
            this.version = version;
            this.flashVolume = flashVolume;
            this.deviceAlias = deviceAlias;
            this.agentCode = agentCode;
        }

        public int deviceType;
        public String version;
        public short flashVolume;
        public String deviceAlias; // UTF-8
        public short agentCode; //
    }

    /**
     * 异步回调接口
     */
    void process(Object response);

    void process(DeviceBaseInfo response);

    void process(ResultCode resultCode);
}
