package handler;

import bean.VCardDevice;
import global.Commands;
import interfaces.CommandCallbackAdapter;
import interfaces.ICommandProcessor;
import protocol.MessageHeader;
import protocol.VCardMessage;

public class CommandProcessorHandler implements ICommandProcessor {

    // command type or client request type
    enum CommandType {
        // GET_DEVICE_ID_CMD(1),
        GET_DEVICE_BASE_INFO_CMD(2),
        SET_DEVICE_ALIAS_CMD(3),
        GET_DEVICE_STATUS_CMD(4),
        SET_SYSTEM_CONFIGURATION_CMD(5),
        GET_SYSTEM_CONFIGURATION(6),
        SET_SYSTEM_TIME(7),
        INITIALIZATION_DEVICE(8),
        DEVICE_HEARTBEAT_COMMUNICATION(9),
        UPDATE_APPLICATION(10),
        SET_QRCODE_URL(11),
        SET_APPLICATION_SECRET_KEY(12),
        GET_APPLICATION_SECRET_KEY(13),
        SET_UPLOAD_ADDRESS(14),
        GET_UPLOAD_ADDRESS(15),
        RELAY_CONTROL_REMOTE_DOOR(16),
        DEVICE_LOCK(17),
        RESTART_DEVICE(18),
        LOCAL_SET_LOCK(19),
        RELAY_CONTROL_REMOTE_DOOR_WITH_ORCODE(20),
        RELAY_CONTROL_REMOTE_DOOR_WITH_USER(21),
        CLEAR_ALL_AUTHORIZATION_CARD(22),
        READ_SINGLE_AUTHORIZATION_CARD(23),
        ADD_AUTHORIZATION_CARD(24),
        DELETE_AUTHORIZATION_CARD(25),
        PUSH_TRAFFIC_RECORD(26),
        PUSH_EVENT(27),
        REPUBLISH_TRAFFIC_RECORD(28),
        REPUBLISH_EVENT(29),
        GET_SDK_INFO(30),
        SET_FACE_IDENTIFICATION_CONFIGURATION(31),
        GET_FACE_IDENTIFICATION_CONFIGURATION(32),
        DELETE_FACE_INFO(33),
        DOWNLOAD_SDK_AUTHORIZATION_INFO(34),
        ADD_BACKLIST_SINGLE_FACE(35),
        ADD_AUTHORIZATION_FACE(36),
        SEND_TEXT_DATA(37),
        DELETE_TEXT_DATA(38),
        DOWNLOAD_MULTIMEDIA_DATA(39),
        DELETE_MULTIMEDIA_DATA(40),
        SCREENSAVER_SWITCH(41),
        SET_SCREENSAVER_CAROUSEL(42),
        SEND_MULTIMEDIA_DATA(43),
        SEND_TRAFFICE_SNAPSHOT_(44),
        SEND_BACKLIST_SNAPSHOT(45),
        SEND_RECORD_TEXT(46),
        SEND_STRANGER_FACE_SNAPSHOT(47),
        SEND_DOORBELL_SNAPSHOT(48),
        SEND_AUTHORIZED_OFFICER_PASSWORD(49),
        DELETE_AUTHORIZED_OFFICER_PASSWORD(50),
        GET_RANDOM_OPEN_PASSWORD(51),
        OPEN_TOKEN_PASSWORD(52),
        CLOSE_TOKEN_PASSWORD(53),
        SET_UI_CONFIGURATION(54),
        GET_UI_CONFIGURATION(55),
        SCENE_RESOURCE_CONFIGGURATION(56),
        SCENE_STATUS_CONTROL(57),
        SET_DEVICE_TITLE(58);

        private int value;
        CommandType(int value) { this.value = value; }
    }

    public CommandProcessorHandler() {
    }

    /*
    @Override
    public CommandParameterCheck requestDeviceId(int deviceId, CommandCallbackAdapter callback) {
        if(deviceId < 0) return CommandParameterCheck.PARATER_ERROR;

        // 直接在这里进行数据的封装
        VCardMessage cmd;


        return CommandParameterCheck.NORMAL;
    }*/

    @Override
    public CommandParameterCheck requestDeviceBaseInfo(int deviceId, CommandCallbackAdapter callback) {
        if (deviceId <= 0) return CommandParameterCheck.PARATER_ERROR;

        VCardDevice cardDevice = ProtocolHandler.getInstance().find(deviceId);
        if (cardDevice == null) return CommandParameterCheck.DEVICE_NOT_FOUND;

        VCardMessage cmd = new VCardMessage();
        MessageHeader header = new MessageHeader();
        header.setControlCode(Commands.GET_DEVICE_BASE_INFO_CMD);
        header.setDeviceId(deviceId);
        header.setInfoCode(MessageHeader.MessageType.SERVER_SENDER, cardDevice.nextCmdSequence());

        cmd.setAppData(null);
        cmd.setHeader(header);

        return CommandParameterCheck.NORMAL;
    }

    @Override
    public CommandParameterCheck requestSetDeviceAlias(int deviceId, String deviceAlias, CommandCallbackAdapter callback) {
        return CommandParameterCheck.NORMAL;
    }

    @Override
    public CommandParameterCheck requestDeviceStatus(int deviceId, CommandCallbackAdapter callback) {
        return CommandParameterCheck.NORMAL;
    }

    @Override
    public CommandParameterCheck requestSystemConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestSystemAllConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestReadSystemAllConfiguration(int deviceId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestSetSystemTime(int deviceId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestInitializeDevice(int deviceId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestUpdateApplication(int deviceId, boolean forceUpdate, short version, String md5, String url, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestSetOrCodeUrl(int deviceId, String url, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestGetApplicationKey(int deviceId, short funId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestSetApplicationKey(int deviceId, short funId, String key, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestSetUploadAddress(int deviceId, short funId, String url, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

    @Override
    public CommandParameterCheck requestGetUploadAddress(int deviceId, short funId, CommandCallbackAdapter callbackAdapter) {
        return null;
    }

}
