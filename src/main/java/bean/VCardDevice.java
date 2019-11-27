package bean;

import global.Constants;
import handler.IProtocolHandler;
import handler.ProtocolHandler;
import interfaces.ICommandCallback;
import io.netty.channel.Channel;
import protocol.VCardMessage;

import java.net.InetSocketAddress;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;

/**
 * 该类对整个client做的抽象，提供
 */
public class VCardDevice {

    // 首次设置KeyIV时，不需要知道，其他信息
    public VCardDevice() { }

    public VCardDevice(int deviceId) {
        this.deviceId = deviceId;
        this.status = Constants.ConnectionStatus.COM_FAILED;
    }

    private int deviceId; // 设备唯一标识
    private VCardMessage pollTelegram; // 轮询响应，客户端发送了一个polling，服务器就回复相应的pollData
    private Channel channel; // 设备连接通道信息
    private IPAddressPair curIPAddress; // 可以向外界提供一个调试的方法；
    private Constants.ConnectionStatus status; // 设备的通信状态
    private byte[] keyIV; // 16个字节的密钥向量
    private ICommandCallback commandResponseCallback; // 回调给命令调用者
    private short cmdSequence; // 命令序列号

    // 外界发送命令与其回调
    private Map<VCardMessage, ICommandCallback> cmdMapToCallback;

    private Deque<VCardMessage> commandList; // 命令队列，接收外界的命令请求的缓存队列
    VCardMessage currentCommand; // 当前的命令，还未进行确认的命令

    // 对外提供的一个接口：发送请求命令
    public void sendCmd(VCardMessage cmd) {
        ProtocolHandler.getInstance().sendData(cmd);
    }

    // 解析应用数据，向外提供，接收
    public void decodeMessage(VCardMessage msg) {

    }

    // 收到device client response的命令进行确认，并发送下一个命令
    public void acknowledgeCmd(VCardMessage msg) {

    }

    // 内部完成其他逻辑处理
    public void updateStatus(Constants.ConnectionStatus status) {
        setStatus(status);

    }

    // 返回下一个命令序列号
    public short nextCmdSequence() {
        cmdSequence++;
        if (cmdSequence == Short.MAX_VALUE) cmdSequence = 0;
        return cmdSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VCardDevice)) return false;
        VCardDevice that = (VCardDevice) o;
        return getDeviceId() == that.getDeviceId() &&
                Objects.equals(getPollTelegram(), that.getPollTelegram()) &&
                Objects.equals(getChannel(), that.getChannel()) &&
                Objects.equals(getCurIPAddress(), that.getCurIPAddress()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getKeyIV(), that.getKeyIV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getPollTelegram(), getChannel(), getCurIPAddress(), getStatus(), getKeyIV());
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public VCardMessage getPollTelegram() {
        return pollTelegram;
    }

    public void setPollTelegram(VCardMessage pollTelegram) {
        this.pollTelegram = pollTelegram;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        InetSocketAddress address = (InetSocketAddress)channel.remoteAddress();
        curIPAddress = new IPAddressPair(address.getHostString(), address.getPort());
    }

    public IPAddressPair getCurIPAddress() {
        return curIPAddress;
    }

    public void setCurIPAddress(IPAddressPair curIPAddress) {
        this.curIPAddress = curIPAddress;
    }

    public Constants.ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(Constants.ConnectionStatus status) {
        this.status = status;
    }

    public byte[] getKeyIV() {
        return keyIV;
    }

    public void setKeyIV(byte[] keyIV) {
        this.keyIV = keyIV;
    }

    public void setCommandResponseCallback(ICommandCallback commandResponseCallback) {
        this.commandResponseCallback = commandResponseCallback;
    }

    public ICommandCallback getCommandResponseCallback() {
        return this.commandResponseCallback;
    }

    // 这样做会出现问题的
    public boolean putCmd(VCardMessage cmd, ICommandCallback callback) {
        if (cmdMapToCallback.get(cmd) != null) {
            cmdMapToCallback.put(cmd, callback);
            return true;
        }

        return false;
    }
}
