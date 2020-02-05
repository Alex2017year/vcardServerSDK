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
    public static final ControlCode SET_SINGLE_SYSTEM_CONFIGURATION_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_06);
    public static final ControlCode SET_ALL_SYSTEM_CONFIGURATION_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0B);
    public static final ControlCode GET_ALL_SYSTEM_CONFIGURATION_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_07);
    public static final ControlCode SET_SYSTEM_TIME_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_08);
    public static final ControlCode INITIALIZE_DEVICE_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_09);
    public static final ControlCode APPLICATION_UPDATE_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0B);
    public static final ControlCode SET_OR_CODE_URL_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0C);
    public static final ControlCode SET_APPLICATION_KEY_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0D);
    public static final ControlCode GET_APPLICATION_KEY_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0E);
    public static final ControlCode SET_UPLOAD_ADDRESS_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_0F);
    public static final ControlCode GET_UPLOAD_ADDRESS_CMD = new ControlCode(Constants.DEVICES_PARAMETER_CATEGORY, Constants.COMMAND_10);
    public static final ControlCode CONTROL_RELAY_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_05);
    public static final ControlCode CONTROL_RELAY_DELAY_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_06);
    public static final ControlCode CONTROL_DEVICE_LOCK_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_07);
    public static final ControlCode REBOOT_DEVICE_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_08);
    public static final ControlCode SET_LOCAL_LOCK_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_09);
    public static final ControlCode CONTROL_RELAY_BY_ORCODE_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_0A);
    public static final ControlCode CONTROL_RELAY_BY_REMOTE_USER_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_0B);
    public static final ControlCode CONTROL_RELAY_BY_REMOTE_USER_WITH_DELAY_CMD = new ControlCode(Constants.DEVICES_CONTROL_CATEGORY, Constants.COMMAND_0C);

}
