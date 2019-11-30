package handler;

import bean.ServerBaseInfo;
import global.Commands;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.MessageHeader;
import protocol.VCardMessage;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        VCardMessage message = (VCardMessage) msg;
        if (message == null || message.getHeader() == null) {
            return;
        }

        if (message.getHeader().getControlCode() == Commands.REGISTER_REQ) {

            // respond to device client.
            VCardMessage loginResp = null;
            loginResp = buildResponse(message);
            ctx.writeAndFlush(loginResp);

            // add device to ProtocolHandler object
            if (ProtocolHandler.getInstance().find(ctx.channel()) != null) {
                // 重复登陆，需要把信息上报给应用层，告知，并且告知客户端
                ProtocolHandler.getInstance().reportClientMsg(ctx.channel(), "Error: Repeated Login!");
            } else {
                ProtocolHandler.getInstance().addNewDevice(ctx.channel(), message);
            }
        }
    }

    private VCardMessage buildResponse(VCardMessage msg) {

        VCardMessage response = new VCardMessage();
        MessageHeader header = new MessageHeader();

        header.setDeviceId(msg.getHeader().getDeviceId());
        header.setInfoCode(MessageHeader.MessageType.SERVER_SENDER, msg.getHeader().getCmdSequence());
        header.setControlCode(msg.getHeader().getControlCode());
        response.setHeader(header);

        ServerBaseInfo serverBaseInfo = ProtocolHandler.getInstance().getServerBaseInfo();
        serverBaseInfo.updateTimestamp();
        serverBaseInfo.updateRandom();
        ByteBuf appData = serverBaseInfo.toByteBuf();
        response.setAppData(appData);

        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ProtocolHandler.getInstance().removeBadDevice(ctx.channel()); // 移除异常的客户端链接
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
