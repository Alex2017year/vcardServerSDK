package handler;

import global.Commands;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.VCardMessage;

public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        VCardMessage message = (VCardMessage) msg;
        if (message == null || message.getHeader() == null) {
            return;
        }

        if (message.getHeader().getControlCode() == Commands.HEARTBEAT_REQ) {

            // 提取相应的客户端端数据，查看当前时间与服务器端时间一致
            VCardMessage heartBeat = null;

            // 需要构建一个response会给客户端
            // TODO !!!!!

            ctx.writeAndFlush(heartBeat);
        }

    }
}
