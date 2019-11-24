package global.config;

public class ConfigInfo {
    private static String KEY = "&AccessDoor@2016"; // 客户端通信密钥
    public static int IVS_COUNT = 16; // 加密/解密向量指定,

    private static String OEMCodeServer = "xk1FFriW";
    private static String OEMCodeDevice = "ShAvx9zt";

    public static String getOEMCodeServer() {
        return OEMCodeServer;
    }

    public static void setOEMCodeServer(String OEMCodeServer) {
        ConfigInfo.OEMCodeServer = OEMCodeServer;
    }

    public static String getOEMCodeDevice() {
        return OEMCodeDevice;
    }

    public static void setOEMCodeDevice(String OEMCodeDevice) {
        ConfigInfo.OEMCodeDevice = OEMCodeDevice;
    }

    public static String getCommKey() {
        return KEY;
    }
    public static void setCommKey(String key) {
        KEY = key;
    }
}
