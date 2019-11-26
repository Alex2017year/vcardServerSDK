package server;

import protocol.ControlCode;

public interface IServerHandler {
    boolean sendMessage(int deviceId, ControlCode controlCode);
}
