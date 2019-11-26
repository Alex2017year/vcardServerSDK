package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.VCardMessage;


public class NettyServerHandler extends SimpleChannelInboundHandler<VCardMessage> {

    // data for all known remote devices
    // private Map<Integer, DeviceData> devices = new ConcurrentHashMap<>();

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, VCardMessage vcardMessage) throws Exception {
        Channel channel = channelHandlerContext.channel();

        // add device data to handler
        // 把所有数据处理交给 ProtocolHandler 类进行处理
        ProtocolHandler.getInstance().dealWithDeviceData(channel, vcardMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
