package interfaces;

public interface IClientStatusListener {
    void onMessage(int deviceId, String message);
    void onAddDevice(int deviceId);
    void onLostConnectionDevice(int deviceId);
}
