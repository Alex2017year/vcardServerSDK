package global;


import protocol.ControlCode;

public class Command {
    // 设备主动发起的请求
    public static final ControlCode REGISTER_REQ = new ControlCode(Constants.UNIVERSAL_CATEGORY, Constants.COMMAND_00);
    public static final ControlCode HEARTBEAT_REQ = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0A);

    // 服务器主动发起的命令（请求）
    public static final ControlCode READ_DEVICE_ID_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_02);

}
