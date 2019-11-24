package interfaces;

import bean.IPAddressPair;

import java.util.ArrayList;

public interface INettyServer {

    // 初始化方法
    boolean init(String hostname, int port, IClientStatusListener listener);

    // 返回客户端当前在线的所有地址
    ArrayList<IPAddressPair> getAllCurrentClients();

    // 返回一个命令处理器件，用于给客户端调用
    ICommandProcessor getCommandProcessor();

    // 关闭
    void close();
}
