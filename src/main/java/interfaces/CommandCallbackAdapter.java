package interfaces;

public abstract class CommandCallbackAdapter implements ICommandCallback {

    @Override
    public void process(Object response) {
    }

    @Override
    public void process(DeviceBaseInfo response) {
    }

    @Override
    public void process(ResultCode resultCode) {
    }


}
