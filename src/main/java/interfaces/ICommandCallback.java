package interfaces;

public interface ICommandCallback {

    /**
     * 异步回调接口
     */
    void process(int resultCode, Object response);

    // void process(int resultCode, );

}
