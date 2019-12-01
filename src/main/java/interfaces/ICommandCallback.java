package interfaces;

public interface ICommandCallback<E> {

    enum ResultCode {
        OK(0x0), // ok
        ECC_ERROR(0x1), // 校验错误
        PARAMETER_ERROR(0x2), // 参数错误
        BUFFER_OVERFLOW_ERROR(0x3), // 缓冲区越界
        REINVENT_ERROR(0x4), // 重复设置
        CONFIG_OR_HARDWARE_NOT_SUPPORT_ERROR(0xFF); // 配置错误或硬件不支持

        private int value;
        ResultCode(int value) {
            this.value = value;
        }
    }

    /**
     * 异步回调接口
     */
    void process(E response);
}
