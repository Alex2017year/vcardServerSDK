package bean;

import global.Commands;
import global.Constants;
import handler.ProtocolHandler;
import interfaces.ICommandCallback;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import protocol.ControlCode;
import protocol.MessageHeader;
import protocol.VCardMessage;
import utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;

import static bean.DeviceLoginInfo.LoginReason.*;
import static global.Commands.*;

/**
 * 实现对远程连接的设备进行抽象，该类实现对远程设备进行命令发送
 * 以及异步响应。
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
    private short cmdSequence; // 命令序列号
    private DeviceLoginInfo deviceLoginInfo = new DeviceLoginInfo();// 设备注册信息结构
    private String hardWareId; // 硬件ID

    // 外界发送命令与其回调
    // 是不是可以使用命令的序列号作为一种标识
    private Map<VCardMessage, ICommandCallback> cmdMapToCallback;
    private Deque<VCardMessage> commandList; // 命令队列，接收外界的命令请求的缓存队列
    VCardMessage currentCommand; // 当前的命令，还未进行确认的命令

    /**
     * 收到device client response的命令进行确认，并发送下一个命令
     * @param msg 这个从client返回的message，通过对比发送请求中的命令序列号与当前命令信息的序列号，接着再调用callback返回API调用者
     */
    public void acknowledgeCmd(VCardMessage msg) {
        if (msg.getHeader().getDeviceId() == currentCommand.getHeader().getDeviceId() &&
            msg.getHeader().getCmdSequence() == currentCommand.getHeader().getCmdSequence()) {
            decodeMessage(msg);
        }

        if (commandList.size() > 0) {
            currentCommand = commandList.pollFirst();
            if (!ProtocolHandler.getInstance().sendData(currentCommand)) {
                // log("failed to send command:" + deviceId + currentCommand.getHeader().getControlCode());
            }
        }
    }

    // 解析应用数据
    private void decodeMessage(VCardMessage msg) {
        ByteBuf appData = (ByteBuf) msg.getAppData();
        ControlCode codeTag = msg.getHeader().getControlCode(); // 用于标识每种命令请求

        if (codeTag == REGISTER_REQ) {
            deviceLoginInfo.setDeviceId(appData.readInt());
            switch(appData.readByte()) {
                case 0:
                    deviceLoginInfo.setLoginReason(STARTUP_REG);
                    break;
                case 1:
                    deviceLoginInfo.setLoginReason(TIMER_REG);
                    break;
                case 2:
                    deviceLoginInfo.setLoginReason(REGISTER_DECRYPTION_FAILURE_REG);
                    break;
                case 3:
                    deviceLoginInfo.setLoginReason(REGISTER_AUTHENTICATION_FAILURE_REG);
                    break;
                case 4:
                    deviceLoginInfo.setLoginReason(DECRYPTION_FAILURE_REG);
                    break;
                case 5:
                    deviceLoginInfo.setLoginReason(NEGOTIATE_FAILURE_REG);
                    break;
                case 6:
                    deviceLoginInfo.setLoginReason(TIMEOUT_NET);
                    break;
                case 7:
                    deviceLoginInfo.setLoginReason(NET_ANOMALY);
                    break;
                case 8:
                    deviceLoginInfo.setLoginReason(CHECK_FAILURE);
                    break;
                case 9:
                    deviceLoginInfo.setLoginReason(SHORT_DATA);
                    break;
                default:
                    // log("未知注册原因");
                    break;
            }

            deviceLoginInfo.setProtocolVerion(appData.readByte());
            deviceLoginInfo.setTimestamp(appData.readInt());
            deviceLoginInfo.setRandomNum(appData.readShort());
            byte[] byteArr = new byte[appData.readableBytes()];
            appData.readBytes(byteArr);
            deviceLoginInfo.setSignature(new String(byteArr));

        } else if (codeTag == GET_DEVICE_ID_CMD) {
            if (this.deviceId == 0) {
                this.deviceId = msg.getHeader().getDeviceId();
                byte[] byteArr = new byte[appData.readableBytes()];
                appData.readBytes(byteArr);
                this.hardWareId = new String(byteArr);
            } else {
                if (this.deviceId != msg.getHeader().getDeviceId()) {
                    // log("未知原因导致设备编号不一致！");
                }
            }

        } else if (codeTag == GET_DEVICE_BASE_INFO_CMD) {
            ICommandCallback.DeviceBaseInfo deviceBaseInfo = new ICommandCallback.DeviceBaseInfo();
            deviceBaseInfo.deviceType = appData.readShort();
            deviceBaseInfo.version = StringUtils.changeToVersion(appData.readShort());
            deviceBaseInfo.flashVolume = appData.readShort();

            byte[] byteArr = new byte[32];
            appData.readBytes(byteArr);
            try {
                deviceBaseInfo.deviceAlias = new String(byteArr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                deviceBaseInfo.deviceAlias = "";
                // e.printStackTrace();
            }

            deviceBaseInfo.agentCode = appData.readShort();
            cmdMapToCallback.get(currentCommand).process(deviceBaseInfo);
        } else if (codeTag == SET_DEVICE_ALIAS_CMD) {
            byte response = appData.readByte();

            ICommandCallback.ResultCode resultCode;
            switch(response) {
                case 0:
                    resultCode = ICommandCallback.ResultCode.OK;
                    break;
                case 1:
                    resultCode = ICommandCallback.ResultCode.ECC_ERROR;
                    break;
                case 2:
                    resultCode = ICommandCallback.ResultCode.PARAMETER_ERROR;
                    break;
                case 3:
                    resultCode = ICommandCallback.ResultCode.BUFFER_OVERFLOW_ERROR;
                    break;
                case 4:
                    resultCode = ICommandCallback.ResultCode.REINVENT_ERROR;
                    break;
                default:
                    resultCode = ICommandCallback.ResultCode.CONFIG_OR_HARDWARE_NOT_SUPPORT_ERROR;
                    break;
            }

            cmdMapToCallback.get(currentCommand).process(resultCode);
        } else if (codeTag == GET_DEVICE_STATUS_CMD) {


        }

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

    public void putCmd(VCardMessage cmd, ICommandCallback callback) {
        cmdMapToCallback.put(cmd, callback);
        commandList.addLast(cmd);
    }


    public VCardMessage buildGetDeviceIdCmd() {
        VCardMessage request = new VCardMessage();
        MessageHeader header = new MessageHeader();
        header.setDeviceId(0);
        header.setControlCode(GET_DEVICE_ID_CMD);
        header.setInfoCode(MessageHeader.MessageType.SERVER_SENDER, nextCmdSequence());
        request.setAppData(header);
        request.setAppData(null);

        // set the current command
        currentCommand = request;
        return request;
    }

    public void updateDeviceLoginInfo(VCardMessage msg) {
        decodeMessage(msg);
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
}
