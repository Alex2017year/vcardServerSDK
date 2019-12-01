package bean;

public class ApplicationKey {
    public static final int KEY_SIZE = 16;
    public static final int OEMCODE_SIZE = 8;

    public enum KeyType {
        CARD_READER_KEY((byte)0),
        QR_CODE_KEY((byte) 1),
        OEMCODE_THIRDPARTY_DEVICE((byte)2);

        private byte value;
        public byte getValue() {
            return value;
        }
        KeyType(byte value) { this.value = value;}
    }

    private KeyType type;
    private String keyCode;

    public ApplicationKey() {
    }

    public ApplicationKey(KeyType type, String keyCode) {
        this.type = type;
        this.keyCode = keyCode;
    }

    public KeyType getType() {
        return type;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyType type, String keyCode) {
        this.type = type;
        this.keyCode = keyCode;
    }
}
