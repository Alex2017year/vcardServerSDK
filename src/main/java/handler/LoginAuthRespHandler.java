package handler;

import global.Command;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.VCardMessage;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        VCardMessage message = (VCardMessage) msg;
        if (message == null || message.getHeader() == null) {
            return;
        }

        // 仅仅考虑注册请求
        VCardMessage loginResp = null;
        if (message.getHeader().getControlCode() == Command.REGISTER_REQ) {
            if (ProtocolHandler.getInstance().find(ctx.channel()) != null) {
                // 重复登陆，需要把信息上报给应用层，告知，并且告知客户端
                ProtocolHandler.getInstance().reportClientMsg(ctx.channel(), "Error: Repeated Login!");

                // 重复登陆
                loginResp = buildResponse();
            } else {
                // 新增设备
                ProtocolHandler.getInstance().addNewDevice(ctx.channel(), message);
                // 此处需要统一进行处理比较稳妥
                // 统一构建一个response处理方法
                //  TODO: 统一写一个response的响应
                loginResp = buildResponse();
            }
        }

        // 最后把响应部分传递出去
        ctx.writeAndFlush(loginResp);
    }

    private VCardMessage buildResponse() {

        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ProtocolHandler.getInstance().removeBadDevice(ctx.channel()); // 移除异常的客户端链接
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
