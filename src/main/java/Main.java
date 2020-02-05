import bean.*;
import interfaces.*;

import java.util.ArrayList;

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
            public void onAddDevice(String deviceId) {
                // do something
            }

            // client通信丢失
            @Override
            public void onLostConnectionDevice(int deviceId) {
                // do something
            }
        });
        // 命令执行器
        ICommandProcessor commandExecutor = server.getCommandProcessor();

        int deviceId = 9999;
        commandExecutor.requestDeviceBaseInfo(deviceId, new ICommandCallback<DeviceBaseInfo>() {
            @Override
            public void process(DeviceBaseInfo response) {
                // 做业务处理
            }
        });

        commandExecutor.requestDeviceState(deviceId, new ICommandCallback<DeviceStateInfo>() {

            @Override
            public void process(DeviceStateInfo response) {

            }
        });

        String alias = "";
        commandExecutor.requestSetDeviceAlias(deviceId, alias, new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        });

        commandExecutor.requestSetSystemSingleConfiguration(deviceId,
                new SystemConfiguration.ByteConfiguration(SystemConfiguration.ConfigAddress.AUTOMATIC_SCREEN_SWITCH),
                new ICommandCallback<ICommandCallback.ResultCode>() {

                    @Override
                    public void process(ResultCode response) {

                    }
                });

        commandExecutor.requestSetSystemSingleConfiguration(deviceId,
                new SystemConfiguration.IntegerConfiguration(SystemConfiguration.ConfigAddress.STRANGER_RECORD_UPLOAD_INTERVAL),
                new ICommandCallback<ICommandCallback.ResultCode>() {

                    @Override
                    public void process(ResultCode response) {

                    }
                });

        commandExecutor.requestSetSystemAllConfiguration(deviceId,
                new SystemConfiguration.AllConfiguration(), new ICommandCallback<ICommandCallback.ResultCode>() {

                    @Override
                    public void process(ResultCode response) {

                    }
                });

        commandExecutor.requestReadSystemAllConfiguration(deviceId, new ICommandCallback<SystemConfiguration.AllConfiguration>() {
            @Override
            public void process(SystemConfiguration.AllConfiguration response) {

            }
        });

        int seconds = 0;
        commandExecutor.requestSetSystemTime(deviceId, seconds, new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        } );

        boolean forceUpdate = true;
        short version = 20;
        String md5 = "ababu899ania36694";
        String url = "http:///************";
        commandExecutor.requestUpdateApplication(deviceId, forceUpdate, version, md5, url, new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        });


        String address = "http:///";
        commandExecutor.requestSetOrCodeUrl(deviceId, address, new ICommandCallback<ICommandCallback.ResultCode>(){

            @Override
            public void process(ResultCode response) {

            }
        });


        commandExecutor.requestGetApplicationKey(deviceId, (byte) 0, new ICommandCallback<ArrayList<ApplicationKey>>() {

            @Override
            public void process(ArrayList<ApplicationKey> response) {

            }
        });

        String key = "";
        commandExecutor.requestSetApplicationKey(deviceId, (byte) 0, key, new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        });

        commandExecutor.requestSetUploadAddress(deviceId, (byte) 0, "http:///", new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        });

        commandExecutor.requestGetUploadAddress(deviceId, (byte) 0, new ICommandCallback<UploadAddress>() {

            @Override
            public void process(UploadAddress response) {

            }

        });

        commandExecutor.requestControlRemoteByRelay(deviceId, (byte) 0, new ICommandCallback<ICommandCallback.ResultCode>() {

            @Override
            public void process(ResultCode response) {

            }
        });



    }


}
