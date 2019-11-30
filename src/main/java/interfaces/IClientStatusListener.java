package interfaces;

public interface IClientStatusListener {
    void onMessage(int deviceId, String message);
    void onAddDevice(String deviceId);
    void onLostConnectionDevice(int deviceId);
}
