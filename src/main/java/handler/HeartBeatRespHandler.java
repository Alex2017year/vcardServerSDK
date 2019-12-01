package handler;

import bean.VCardDevice;
import global.Commands;
import global.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.MessageHeader;
import protocol.VCardMessage;
import utils.TimeUtil;

public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        VCardMessage message = (VCardMessage) msg;
        if (message == null || message.getHeader() == null) {
            return;
        }

        if (message.getHeader().getControlCode() == Commands.HEARTBEAT_REQ) {
            heartBeatResponse(message, ctx.channel());
            // ctx.writeAndFlush(heartBeat);
        }
    }

    private void heartBeatResponse(VCardMessage msg, Channel channel) {
        ByteBuf appData = (ByteBuf) msg.getAppData();
        int seconds = appData.readInt() - TimeUtil.getCurrentSec();
        if (Math.abs(seconds) >= Constants.TIME_DIFFERENCE) {
            // send setTime command
            VCardDevice vcardDevice = ProtocolHandler.getInstance().find(msg.getHeader().getDeviceId());
            if (vcardDevice != null) {
                buildCommand(msg, vcardDevice);
                return;
            }

            vcardDevice = ProtocolHandler.getInstance().find(channel);
            if (vcardDevice != null) {
                buildCommand(msg, vcardDevice);
            }
        }
    }

    private void buildCommand(VCardMessage msg, VCardDevice vcardDevice) {
        VCardMessage cmd = new VCardMessage();
        MessageHeader header = new MessageHeader();
        header.setInfoCode(MessageHeader.MessageType.SERVER_SENDER, vcardDevice.nextCmdSequence());
        header.setControlCode(Commands.SET_SYSTEM_TIME_CMD);
        header.setDeviceId(msg.getHeader().getDeviceId());
        cmd.setHeader(header);

        ByteBuf body = Unpooled.buffer();
        body.writeInt(TimeUtil.getCurrentSec());
        cmd.setAppData(body);

        vcardDevice.putCmd(cmd, null);
    }
}
