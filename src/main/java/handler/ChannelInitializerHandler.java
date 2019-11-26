package handler;


import codec.VCardMessageDecoder;
import codec.VCardMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.nio.ByteOrder;

import static codec.VCardMessageDecoder.*;


public class ChannelInitializerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encoder", new VCardMessageDecoder(ByteOrder.BIG_ENDIAN, MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET,
                LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, true));
        pipeline.addLast("decoder", new VCardMessageEncoder());
        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
        // 握手认证消息处理handler
        pipeline.addLast(new LoginAuthRespHandler());
        // 心跳消息响应处理handler
        pipeline.addLast("heartBeatHandler", new HeartBeatRespHandler());
        // 接收消息处理handler
        pipeline.addLast(new NettyServerHandler());
    }
}
