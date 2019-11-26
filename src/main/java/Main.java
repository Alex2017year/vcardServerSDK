import interfaces.*;

public class Main {


    public static void main(String[] args) {

        INettyServer server = IServerBootstrap.getInstance();

        String host = "127.0.0.1";
        int port = 6666;
        server.init(host, port, new IClientStatusListener() {

            // 提示信息
            @Override
            public void onMessage(int deviceId, String message) {

            }

            // client建立起通信
            @Override
            public void onAddDevice(int deviceId) {
                // do something
            }

            // client通信丢失
            @Override
            public void onLostConnectionDevice(int deviceId) {
                // do something
            }
        });
        // 命令执行器
        ICommandProcessor commandExecutor= server.getCommandProcessor();

        int deviceId = 9999;
        commandExecutor.requestDeviceBaseInfo(deviceId, new CommandCallbackAdapter() {
            @Override
            public void process(int resultCode, Object response) {
                // 做业务处理
            }
        });
    }
}
