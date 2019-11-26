package interfaces;

import server.NettyServer;

// 向外提供一个接口
public class IServerBootstrap {
    public static INettyServer getInstance() {
        return NettyServer.getInstance();
    }
}
