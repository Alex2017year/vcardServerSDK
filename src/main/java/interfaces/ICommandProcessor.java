package interfaces;

public interface ICommandProcessor {
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
    void requestDeviceId(int deviceId, CommandCallbackAdapter callback);
    void requestDeviceBaseInfo(int deviceId, CommandCallbackAdapter callback);
    void requestSetDeviceAlias(int deviceId, String deviceAlias, CommandCallbackAdapter callback);
    void requestDeviceStatus(int deviceId, CommandCallbackAdapter callback);
}
