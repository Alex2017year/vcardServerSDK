package server;

import bean.IPAddressPair;
import handler.ChannelInitializerHandler;
import handler.CommandProcessorHandler;
import handler.IProtocolHandler;
import handler.ProtocolHandler;
import interfaces.IClientStatusListener;
import interfaces.ICommandProcessor;
import interfaces.INettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import protocol.ControlCode;

import java.util.ArrayList;


public class NettyServer implements INettyServer, IServerHandler {

    private static volatile NettyServer instance;
    private String mHost;
    private int mPort;

    private EventLoopGroup mBossGroup;
    private EventLoopGroup mWorkGroup;
    private Channel mServerChannel;
    private IClientStatusListener mListener;

    private static final Log log = LogFactory.getLog(NettyServer.class);

    private ICommandProcessor mCommandProcessor;

    public static NettyServer getInstance() {
        if (null == instance) {
            synchronized (NettyServer.class) {
                if (null == instance) {
                    instance = new NettyServer();
                }
            }
        }
        return instance;
    }

    private NettyServer() {}

    private void init() throws InterruptedException {
        log.info("Starting a server...");

        mBossGroup = new NioEventLoopGroup();
        mWorkGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(mWorkGroup, mWorkGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializerHandler())
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        mServerChannel = bootstrap.bind(mHost, mPort).sync().channel();
    }

    private void shutdown() throws InterruptedException {
        log.info("Stopping server...");

        try {
            mServerChannel.close().sync();
        } finally {
            mWorkGroup.shutdownGracefully();
            mBossGroup.shutdownGracefully();
        }

        log.info("Success to stop server..");
    }


    // 提取一个公共接口，给外界发送数据
    // TODO: 在方法内部定义一个发送数据的方法
    @Override
    public boolean sendMessage(int deviceId, ControlCode controlCode) {

        return true;
    }


    @Override
    public boolean init(String hostname, int port, IClientStatusListener listener) {
        this.mHost = hostname;
        this.mPort = port;

        if(listener == null) {
            this.mListener = new IClientStatusListener() {
                @Override
                public void onMessage(int deviceId, String message) {
                }

                @Override
                public void onAddDevice(int deviceId) {
                }

                @Override
                public void onLostConnectionDevice(int deviceId) {
                }
            };
        } else {
            this.mListener = listener;
        }
        // 初始化请求处理器
        ProtocolHandler.getInstance().initialize(listener);
        // 初始化命令处理器
        mCommandProcessor = new CommandProcessorHandler(this);

        try {
            init();
        } catch (InterruptedException e) {
            log.error("server failed to start. reason: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<IPAddressPair> getAllCurrentClients() {
        return null;
    }

    @Override
    public ICommandProcessor getCommandProcessor() {
        return null;
    }

    @Override
    public void close() {

    }
}
