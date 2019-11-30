package handler;


import bean.ServerBaseInfo;
import bean.VCardDevice;
import bean.IPAddressPair;
import global.Constants;
import interfaces.IClientStatusListener;
import interfaces.ICommandCallback;
import io.netty.channel.Channel;
import protocol.VCardMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtocolHandler implements IProtocolHandler {

    // 协议处理器
    private ProtocolHandler() {}
    private static class ProtocolHandlerInstance {
        private static final IProtocolHandler INSTANCE = new ProtocolHandler();
    }

    // 单例模式
    public static IProtocolHandler getInstance() {
        return ProtocolHandlerInstance.INSTANCE;
    }

    // deviceId <---> VCardDevice
    private Map<Integer, VCardDevice> deviceTable = new ConcurrentHashMap<>();

    // deviceId <---> channel
    private Map<Integer, Channel> channelMap = new ConcurrentHashMap<>();

    // channel <---> VCardDevice
    private Map<Channel, VCardDevice> channelDeviceIdTable = new ConcurrentHashMap<>();

    // SDK提供的回调，用于报告client的建立连接/连接丢失/其他信息等
    private IClientStatusListener mListener;

    private ServerBaseInfo serverBaseInfo = new ServerBaseInfo(); // 服务器端基本信息

    @Override
    public boolean initialize(IClientStatusListener listener) {
        if (listener == null)  return false;
        mListener = listener;
        return true;
    }

    @Override
    public void reportClientMsg(Channel channel, String msg) {
        mListener.onMessage(channelDeviceIdTable.get(channel).getDeviceId(), msg);
    }

    @Override
    public void removeBadDevice(Channel channel) {
        if (channelDeviceIdTable.containsKey(channel)) {
            VCardDevice dd = channelDeviceIdTable.remove(channel);
            if (deviceTable.containsKey(dd.getDeviceId())) {
                deviceTable.remove(dd.getDeviceId());
            }
        }
    }

    @Override
    public VCardDevice find(Channel channel) {
        if (channelDeviceIdTable.containsKey(channel)) {
            return channelDeviceIdTable.get(channel);
        }
        return null;
    }

    @Override
    public VCardDevice find(int deviceId) {
        return deviceTable.get(deviceId);
    }

    @Override
    public void addNewDevice(Channel channel, VCardMessage message) {
        if (!channelDeviceIdTable.containsKey(channel)) {
            // note: here, maye be deviceId=0!
            VCardDevice vcardDevice = new VCardDevice(message.getHeader().getDeviceId());
            vcardDevice.setStatus(Constants.ConnectionStatus.HEALTHY);
            vcardDevice.setChannel(channel);
            InetSocketAddress address = (InetSocketAddress) channel.localAddress();
            vcardDevice.setCurIPAddress(new IPAddressPair(address.getHostName(), address.getPort()));

            channelDeviceIdTable.put(channel, vcardDevice);

            // handle message body
            vcardDevice.updateDeviceLoginInfo(message);

            // report to API caller
            if (message.getHeader().getDeviceId() == 0) {
                mListener.onAddDevice(vcardDevice.getCurIPAddress().toString());

                // send request to get deviceId
                VCardMessage command = vcardDevice.buildGetDeviceIdCmd();
                channel.writeAndFlush(command);

            } else {
                mListener.onAddDevice(String.valueOf(message.getHeader().getDeviceId()));
            }
        }
    }

    // 仅仅处理已经正常通信的数据
    @Override
    public void dealWithDeviceData(Channel channel, VCardMessage message) {
        int deviceId = message.getHeader().getDeviceId();
        if (deviceTable.containsKey(deviceId)) {
            VCardDevice vcardDevice = deviceTable.get(deviceId);
            if (vcardDevice.getStatus() == Constants.ConnectionStatus.COM_FAILED) {
                // 设备的上一次状态是com_failed, 所以这次必须触发一个登陆请求
                return;
            }

            vcardDevice.setStatus(Constants.ConnectionStatus.HEALTHY);
            if (vcardDevice.getChannel() != channel) {
                // log("");
                // 某个设备通道变化了，说明IP和port变化，需要及时更新
                vcardDevice.setChannel(channel);
                InetSocketAddress address = (InetSocketAddress) channel.localAddress();
                vcardDevice.setCurIPAddress(new IPAddressPair(address.getHostName(), address.getPort()));
            }

            vcardDevice.acknowledgeCmd(message);

            // 更新设备状态
            // setData(new VCardEvent(deviceId, Constants.ConnectionStatus.HEALTHY));

            // 发送数据报文
            // setData(new VCardEvent(deviceId, message));
        }
    }

    @Override
    public boolean sendData(VCardMessage telegram) {
        if (telegram == null || telegram.getHeader() == null)
            return false;

        int deviceId = telegram.getHeader().getDeviceId();

        // 当前设备ID，并不在设备属性表中
        if (!deviceTable.containsKey(deviceId))
            return false;

        // 当前设备断开连接
        if (deviceTable.get(deviceId).getStatus() == Constants.ConnectionStatus.COM_FAILED)
            return false;

        // 开始发送数据
        deviceTable.get(deviceId).getChannel().writeAndFlush(telegram);
        return true;
    }

    @Override
    public boolean sendCommand(VCardMessage cmd, ICommandCallback callback) {
        if (cmd == null || cmd.getHeader() == null)
            return false;

        VCardDevice cardDevice = deviceTable.get(cmd.getHeader().getDeviceId());
        if (cardDevice == null || cardDevice.getStatus() == Constants.ConnectionStatus.COM_FAILED)
            return false;

        // 将数据保存到队列中
        cardDevice.putCmd(cmd, callback);
        return true;
    }

    @Override
    public ServerBaseInfo getServerBaseInfo() {
        return serverBaseInfo;
    }

    @Override
    public void updateServerBaseInfo(short serverNo) {
        serverBaseInfo.setServerNo(serverNo);
    }

    @Override
    public void updateServerBaseInfoWithOEMCode(String oemCode) {
        // 这里重新构造签名
        serverBaseInfo.setOemCodeServer(oemCode);
    }
}
