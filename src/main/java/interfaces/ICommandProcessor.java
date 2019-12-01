package interfaces;

import bean.SystemConfiguration;

public interface ICommandProcessor {

    enum CommandParameterCheck {
        NORMAL(0),
        DEVICE_NOT_FOUND(1),
        PARATER_ERROR(2),
        FAILED_CONNECTION(3),
        NOT_IMPLEMENT(4);

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
    // CommandParameterCheck requestDeviceId(int deviceId, ICommandCallback callbackAdapter); // 这个命令不对外开发，由服务器内部触发

    CommandParameterCheck requestDeviceBaseInfo(int deviceId, ICommandCallback callbackAdapter);
    CommandParameterCheck requestSetDeviceAlias(int deviceId, String deviceAlias, ICommandCallback callbackAdapter);
    CommandParameterCheck requestDeviceState(int deviceId, ICommandCallback callbackAdapter);

    // set different system configurations
    CommandParameterCheck requestSystemSingleConfiguration(int deviceId, SystemConfiguration.ByteConfiguration config,
                                                           ICommandCallback callbackAdapter);
    CommandParameterCheck requestSystemSingleConfiguration(int deviceId, SystemConfiguration.IntegerConfiguration config,
                                                           ICommandCallback callbackAdapter);

    CommandParameterCheck requestSystemAllConfiguration(int deviceId, SystemConfiguration.AllConfiguration config,ICommandCallback callbackAdapter);

    // 读取系统配置
    CommandParameterCheck requestReadSystemAllConfiguration(int deviceId, ICommandCallback callbackAdapter);
    // 设置系统时间
    CommandParameterCheck requestSetSystemTime(int deviceId, int seconds, ICommandCallback callbackAdapter);
    // 初始化设备(未实现)
    CommandParameterCheck requestInitializeDevice(int deviceId, ICommandCallback callbackAdapter);

    // 设备通信心跳 -- 这个设备主动发起，服务端被动响应（不向外界提供接口）

    CommandParameterCheck requestUpdateApplication(int deviceId, boolean forceUpdate, short version, String md5, String url,
                                                   ICommandCallback callbackAdapter);
    CommandParameterCheck requestSetOrCodeUrl(int deviceId, String url, ICommandCallback callbackAdapter);

    // 获取应用密钥
    CommandParameterCheck requestGetApplicationKey(int deviceId, byte funId, ICommandCallback callbackAdapter);

    // 设置应用密钥
    CommandParameterCheck requestSetApplicationKey(int deviceId, byte funId, String key, ICommandCallback callbackAdapter);

    // 设置上传地址
    CommandParameterCheck requestSetUploadAddress(int deviceId, byte funId, String url, ICommandCallback callbackAdapter);

    // 获取上传地址
    CommandParameterCheck requestGetUploadAddress(int deviceId, byte funId, ICommandCallback callbackAdapter);
    
}
