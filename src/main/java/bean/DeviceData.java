package bean;

import global.Constants;
import io.netty.channel.Channel;
import protocol.VCardMessage;

import java.net.InetSocketAddress;
import java.util.Objects;

public class DeviceData {

    // 首次设置KeyIV时，不需要知道，其他信息
    public DeviceData() { }

    public DeviceData(int deviceId) {
        this.deviceId = deviceId;
        this.status = Constants.ConnectionStatus.COM_FAILED;
    }

    private int deviceId; // 设备唯一标识
    private VCardMessage pollTelegram; // 轮询响应，客户端发送了一个polling，服务器就回复相应的pollData
    private Channel channel; // 设备连接通道信息
    private IPAddressPair curIPAddress; // 可以向外界提供一个调试的方法；
    private Constants.ConnectionStatus status; // 设备的通信状态
    private byte[] keyIV; // 16个字节的密钥向量

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceData)) return false;
        DeviceData that = (DeviceData) o;
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
}
