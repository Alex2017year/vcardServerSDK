package interfaces;

public interface ICommandProcessor {

    enum CommandParameterCheck {
        NORMAL(0),
        DEVICE_NOT_FOUND(1),
        PARATER_ERROR(2);

        private int value;
        CommandParameterCheck(int value) { this.value = value; }
    }

    enum KeyFunId {
        CARD_READER_KEY(0), // 读卡器密钥
        QR_CODE_KEY(1), // 二维码密钥
        OEMCODE_THIRDPARTY_DEVICE(2); // OEMCode_Thirdparty_Device

        private int value;
        public int getValue() {
            return this.value;
        }

        KeyFunId(int value) {
            this.value = value;
        }
    }

    enum UploadAddressType {
        GOTHROUGH_SNAPSHOT_UPLOAD_ADDRESS(0),
        STRANGER_SNAPSHOT_UPLOAD_ADDRESS(1),
        BACKLIST_SNAPSHOT_UPLOAD_ADDRESS(2),
        RECORD_UPLOAD_ADDRESS(3),
        DOORBELL_SNAPSHOT_ADDRESS(4);

        private int value;
        int getValue() { return this.value; }
        UploadAddressType(int value) {
            this.value = value;
        }
    }

    enum LockMode {
        CLOSE_DOOR_LOCK(0),
        OPEN_DOOR_LOCK(1),
        CANCEL_LOCK(2);

        int value;
        public int getValue() { return this.value; }
        LockMode(int value) {
            this.value = value;
        }
    }

    enum LocalLockType {
        SETTABLE(0),
        FORBID_ENTER_LOCAL_SETTING(1),
        UNABLE_SET(2),
        TOUCH_INVALID(3);

        int value;
        public int getValue() { return this.value; }
        LocalLockType(int value) {
            this.value = value;
        }
    }

    // 设备参数设置
    // 确定对外接口
    // CommandParameterCheck requestDeviceId(int deviceId, CommandCallbackAdapter callbackAdapter); // 这个命令不对外开发，由服务器内部触发


    CommandParameterCheck requestDeviceBaseInfo(int deviceId, CommandCallbackAdapter callbackAdapter);
    CommandParameterCheck requestSetDeviceAlias(int deviceId, String deviceAlias, CommandCallbackAdapter callbackAdapter);
    CommandParameterCheck requestDeviceStatus(int deviceId, CommandCallbackAdapter callbackAdapter);

    // set different system configurations
    CommandParameterCheck requestSystemConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter);
    CommandParameterCheck requestSystemAllConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter);

    // 读取系统配置
    CommandParameterCheck requestReadSystemAllConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter);
    // 设置系统时间
    CommandParameterCheck requestSetSystemTime(int deviceId, CommandCallbackAdapter callbackAdapter);
    // 初始化设备
    CommandParameterCheck requestInitializeDevice(int deviceId, CommandCallbackAdapter callbackAdapter);

    // 设备通信心跳 -- 这个设备主动发起，服务端被动响应（不向外界提供接口）

    CommandParameterCheck requestUpdateApplication(int deviceId, boolean forceUpdate, short version, String md5, String url,CommandCallbackAdapter callbackAdapter);
    CommandParameterCheck requestSetOrCodeUrl(int deviceId, String url, CommandCallbackAdapter callbackAdapter);

    // 获取应用密钥
    CommandParameterCheck requestGetApplicationKey(int deviceId, short funId, CommandCallbackAdapter callbackAdapter);

    // 设置应用密钥
    CommandParameterCheck requestSetApplicationKey(int deviceId, short funId, String key, CommandCallbackAdapter callbackAdapter);

    // 设置上传地址
    CommandParameterCheck requestSetUploadAddress(int deviceId, short funId, String url, CommandCallbackAdapter callbackAdapter);

    // 获取上传地址
    CommandParameterCheck requestGetUploadAddress(int deviceId, short funId, CommandCallbackAdapter callbackAdapter);
    
}
