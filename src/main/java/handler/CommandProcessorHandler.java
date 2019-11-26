package handler;

import interfaces.CommandCallbackAdapter;
import interfaces.ICommandProcessor;
import server.IServerHandler;

public class CommandProcessorHandler implements ICommandProcessor {

    // 用于发送命令请求信息
    private IServerHandler mHandler;

    public CommandProcessorHandler(IServerHandler handler) {
        mHandler = handler;
    }

    @Override
    public void requestDeviceId(int deviceId, CommandCallbackAdapter callback) {

    }

    @Override
    public void requestDeviceBaseInfo(int deviceId, CommandCallbackAdapter callback) {

    }

    @Override
    public void requestSetDeviceAlias(int deviceId, String deviceAlias, CommandCallbackAdapter callback) {

    }

    @Override
    public void requestDeviceStatus(int deviceId, CommandCallbackAdapter callback) {

    }
}
