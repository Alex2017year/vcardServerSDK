package handler;

import bean.SystemConfiguration;
import bean.VCardDevice;
import global.Commands;
import global.Constants;
import interfaces.ICommandCallback;
import interfaces.ICommandProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocol.ControlCode;
import protocol.MessageHeader;
import protocol.VCardMessage;

public class CommandProcessorHandler implements ICommandProcessor {
    public CommandProcessorHandler() {
    }

    /*
    @Override
    public CommandParameterCheck requestDeviceId(int deviceId, ICommandCallback callback) {
        if(deviceId < 0) return CommandParameterCheck.PARATER_ERROR;
        // 直接在这里进行数据的封装
        VCardMessage cmd;
        return CommandParameterCheck.NORMAL;
    }*/

    private CommandParameterCheck commandTemplate(int deviceId, ControlCode cmdType, Object appData, ICommandCallback callback) {
        if (deviceId <= 0) return CommandParameterCheck.PARATER_ERROR;

        VCardDevice cardDevice = ProtocolHandler.getInstance().find(deviceId);
        if (cardDevice == null) return CommandParameterCheck.DEVICE_NOT_FOUND;

        VCardMessage cmd = new VCardMessage();
        MessageHeader header = new MessageHeader();
        header.setControlCode(cmdType);
        header.setDeviceId(deviceId);
        header.setInfoCode(MessageHeader.MessageType.SERVER_SENDER, cardDevice.nextCmdSequence());

        cmd.setAppData(appData);
        cmd.setHeader(header);

        if (!ProtocolHandler.getInstance().sendCommand(cmd, callback)) {
            return CommandParameterCheck.FAILED_CONNECTION;
        }

        return CommandParameterCheck.NORMAL;
    }

    @Override
    public CommandParameterCheck requestDeviceBaseInfo(int deviceId, ICommandCallback callback) {
        return commandTemplate(deviceId, Commands.GET_DEVICE_BASE_INFO_CMD, null, callback);
    }

    @Override
    public CommandParameterCheck requestSetDeviceAlias(int deviceId, String deviceAlias, ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeBytes(deviceAlias.getBytes());
        return commandTemplate(deviceId, Commands.SET_DEVICE_ALIAS_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestDeviceState(int deviceId, ICommandCallback callback) {
        return commandTemplate(deviceId, Commands.GET_DEVICE_STATUS_CMD, null, callback);
    }

    @Override
    public CommandParameterCheck requestSetSystemSingleConfiguration(int deviceId, SystemConfiguration.ByteConfiguration config,
                                                                     ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(config.getAddress());
        appData.writeByte(config.getConfigurationValue());
        return commandTemplate(deviceId, Commands.SET_SINGLE_SYSTEM_CONFIGURATION_CMD, appData, callback);
    }


    @Override
    public CommandParameterCheck requestSetSystemSingleConfiguration(int deviceId, SystemConfiguration.IntegerConfiguration config,
                                                                     ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(config.getAddress());
        appData.writeInt(config.getConfigurationValue());
        return commandTemplate(deviceId, Commands.SET_SINGLE_SYSTEM_CONFIGURATION_CMD, null, callback);
    }

    @Override
    public CommandParameterCheck requestSetSystemAllConfiguration(int deviceId, SystemConfiguration.AllConfiguration config, ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(config.getAutomaticScreenSwitchConfig().getAddress());
        appData.writeByte(config.getAutomaticScreenSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getScreensaverFaceIdentificationSwitchConfig().getAddress());
        appData.writeByte(config.getScreensaverFaceIdentificationSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getScreensaverFaceIdentificationSwitchConfig().getAddress());
        appData.writeByte(config.getScreensaverFaceIdentificationSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getStrangerRecordUploadIntervalConfig().getAddress());
        appData.writeInt(config.getStrangerRecordUploadIntervalConfig().getConfigurationValue());

        appData.writeByte(config.getStrangerRecordUploadModelConfig().getAddress());
        appData.writeByte(config.getStrangerRecordUploadModelConfig().getConfigurationValue());

        appData.writeByte(config.getDoorbellSwitchConfig().getAddress());
        appData.writeByte(config.getDoorbellSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getPhotosensitiveLightOffThresholdConfig().getAddress());
        appData.writeInt(config.getPhotosensitiveLightOffThresholdConfig().getConfigurationValue());

        appData.writeByte(config.getPhotosensitiveLightOnThresholdConfig().getAddress());
        appData.writeInt(config.getPhotosensitiveLightOnThresholdConfig().getConfigurationValue());

        appData.writeByte(config.getSetPasswordConfig().getAddress());
        appData.writeInt(config.getSetPasswordConfig().getConfigurationValue());

        appData.writeByte(config.getPopupDialogDurationConfig().getAddress());
        appData.writeByte(config.getPopupDialogDurationConfig().getConfigurationValue());

        appData.writeByte(config.getWhiteLightHighlightWidePercentageConfig().getAddress());
        appData.writeByte(config.getWhiteLightHighlightWidePercentageConfig().getConfigurationValue());

        appData.writeByte(config.getWhiteLightLowlightWidePercentageConfig().getAddress());
        appData.writeByte(config.getWhiteLightLowlightWidePercentageConfig().getConfigurationValue());

        appData.writeByte(config.getRedLightHighlightWidePercentageConfig().getAddress());
        appData.writeByte(config.getRedLightHighlightWidePercentageConfig().getConfigurationValue());

        appData.writeByte(config.getRedLightLowlightWidePercentageConfig().getAddress());
        appData.writeByte(config.getRedLightLowlightWidePercentageConfig().getConfigurationValue());

        appData.writeByte(config.getStrangeFaceHintSwitchConfig().getAddress());
        appData.writeByte(config.getStrangeFaceHintSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getAutoRebootSwitchConfig().getAddress());
        appData.writeByte(config.getAutoRebootSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getAutoRebootTimeConfig().getAddress());
        appData.writeByte(config.getAutoRebootTimeConfig().getConfigurationValue());

        appData.writeByte(config.getAutoScreensaverAnimationSwitchConfig().getAddress());
        appData.writeByte(config.getAutoScreensaverAnimationSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getAutoEnteringScreensaverTimeConfig().getAddress());
        appData.writeByte(config.getAutoEnteringScreensaverTimeConfig().getConfigurationValue());

        appData.writeByte(config.getVoiceSwitchConfig().getAddress());
        appData.writeByte(config.getVoiceSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getWipingCardAndFaceSwitchConfig().getAddress());
        appData.writeByte(config.getWipingCardAndFaceSwitchConfig().getConfigurationValue());

        appData.writeByte(config.getWhetherAddCardAuthorizationConfig().getAddress());
        appData.writeByte(config.getWhetherAddCardAuthorizationConfig().getConfigurationValue());

        return commandTemplate(deviceId, Commands.SET_ALL_SYSTEM_CONFIGURATION_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestReadSystemAllConfiguration(int deviceId, ICommandCallback callback) {
        return commandTemplate(deviceId, Commands.GET_ALL_SYSTEM_CONFIGURATION_CMD, null, callback);
    }

    @Override
    public CommandParameterCheck requestSetSystemTime(int deviceId, int seconds, ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeInt(seconds);
        return commandTemplate(deviceId, Commands.SET_SYSTEM_TIME_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestInitializeDevice(int deviceId, ICommandCallback callback) {
        return CommandParameterCheck.NOT_IMPLEMENT;
    }

    @Override
    public CommandParameterCheck requestUpdateApplication(int deviceId, boolean forceUpdate, short version, String md5, String url, ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        if (forceUpdate) {
            appData.writeByte(Constants.FORCE_UPDATE);
        } else {
            appData.writeByte(0);
        }
        appData.writeShort(version);
        appData.writeBytes(md5.getBytes());
        appData.writeShort(url.length());
        appData.writeBytes(url.getBytes());

        return commandTemplate(deviceId, Commands.APPLICATION_UPDATE_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestSetOrCodeUrl(int deviceId, String url, ICommandCallback callback) {
        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(url.length());
        appData.writeBytes(url.getBytes());
        return commandTemplate(deviceId, Commands.APPLICATION_UPDATE_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestGetApplicationKey(int deviceId, byte funId, ICommandCallback callback) {
        if (funId < 0 || funId > 3) return CommandParameterCheck.PARATER_ERROR;

        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(funId);
        return commandTemplate(deviceId, Commands.GET_APPLICATION_KEY_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestSetApplicationKey(int deviceId, byte funId, String key, ICommandCallback callback) {
        if (funId < 0 || funId > 3) return CommandParameterCheck.PARATER_ERROR;

        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(funId);
        appData.writeShort(key.length());
        appData.writeBytes(key.getBytes());
        return commandTemplate(deviceId, Commands.SET_APPLICATION_KEY_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestSetUploadAddress(int deviceId, byte funId, String url, ICommandCallback callback) {
        if (funId < 0 || funId > 5 || url == null || url.equals("")) return CommandParameterCheck.PARATER_ERROR;

        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(funId);
        appData.writeShort(url.length());
        appData.writeBytes(url.getBytes());
        return commandTemplate(deviceId, Commands.SET_UPLOAD_ADDRESS_CMD, appData, callback);
    }

    @Override
    public CommandParameterCheck requestGetUploadAddress(int deviceId, byte funId, ICommandCallback callback) {
        if (funId < 0 || funId > 5) return CommandParameterCheck.PARATER_ERROR;
        ByteBuf appData = Unpooled.buffer();
        appData.writeByte(funId);

        return commandTemplate(deviceId, Commands.GET_UPLOAD_ADDRESS_CMD, appData, callback);
    }

}
