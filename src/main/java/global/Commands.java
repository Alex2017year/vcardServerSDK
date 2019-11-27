package global;


import protocol.ControlCode;

public class Commands {
    // 设备主动发起的请求
    public static final ControlCode REGISTER_REQ = new ControlCode(Constants.UNIVERSAL_CATEGORY, Constants.COMMAND_00);
    public static final ControlCode HEARTBEAT_REQ = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0A);

    public static final ControlCode GET_DEVICE_ID_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_02);

    // 服务器主动发起的命令（请求）
    public static final ControlCode GET_DEVICE_BASE_INFO_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_03);
    public static final ControlCode SET_DEVICE_ALIAS_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_04);
    public static final ControlCode GET_DEVICE_STATUS_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_05);

}
