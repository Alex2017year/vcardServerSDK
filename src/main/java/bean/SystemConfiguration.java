package bean;

public class SystemConfiguration {

    private static final int WHITE_LIGHT_WIDTH_RATIO_MAX_VALUE          = 50;
    private static final int RED_LIGHT_WIDTH_RATIO_MAX_VALUE            = 20;
    private static final int AUTO_REBOOT_MAX_VALUE                      = 23;
    private static final int SCREENSAVER_TIME_RANGE_FLOOR               = 5;
    private static final int SCREENSAVER_TIME_RANGE_UPPER               = 60;

    private static final int DEFAULT_UPLOAD_SECOND                      = 10;
    private static final int DEFAULT_PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD = 110;
    private static final int DEFAULT_PHOTOSENSITIVE_LIGHT_ON_THRESHOLD  = 75;
    private static final int DEFAULT_PASSWORD                           = 888888;

    // private static final byte AUTOMATIC_SCREEN_SWITCH                   = 0x01;
    // private static final byte SCREENSAVER_FACE_IDENTIFICATION_SWITCH    = 0x02;
    // private static final byte STRANGER_RECORD_SWITCH                    = 0x03;
    // private static final byte STRANGER_RECORD_UPLOAD_INTERVAL           = 0x04;
    // private static final byte STRANGER_RECORD_UPLOAD_MODEL              = 0x05;
    // private static final byte DOORBELL_SWITCH                           = 0x06;
    // private static final byte PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD        = 0x07;
    // private static final byte PHOTOSENSITIVE_LIGHT_ON_THRESHOLD         = 0x08;
    // private static final byte SET_PASSWORD                              = 0x09;
    // private static final byte POPUP_DIALOG_DURATION                     = 0x0A;
    // private static final byte WHITE_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE     = 0x0B;
    // private static final byte WHITE_LIGHT_LOWLIGHT_WIDE_PERCENTAGE      = 0x0C;
    // private static final byte RED_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE       = 0x0D;
    // private static final byte RED_LIGHT_LOWLIGHT_WIDE_PERCENTAGE        = 0x0E;
    // private static final byte STRANGE_FACE_HINT_SWITCH                  = 0x0F;
    // private static final byte AUTO_REBOOT_SWITCH                        = 0x10;
    // private static final byte AUTO_REBOOT_TIME                          = 0x11;
    // private static final byte AUTO_SCREENSAVER_ANIMATION_SWITCH         = 0x12;
    // private static final byte AUTO_ENTERING_SCREENSAVER_TIME            = 0x13;
    // private static final byte VOICE_SWITCH                              = 0x14;
    // private static final byte WIPING_CARD_AND_FACE_SWITCH               = 0x15;
    // private static final byte WHETHER_ADD_CARD_AUTHORIZATION            = 0x16;

    private static final byte SWITCH_OPEN                               = 0x25;
    private static final byte SWITCH_CLOSE                              = 0x19;
    private static final byte CONTINUE_SHIELD                           = 0x00;
    private static final byte FIXED_TIME_INTERVAL                       = 0x01;

    public enum ConfigAddress {
        AUTOMATIC_SCREEN_SWITCH((byte)0x01),
        SCREENSAVER_FACE_IDENTIFICATION_SWITCH((byte)0x02),
        STRANGER_RECORD_SWITCH((byte)0x03),
        STRANGER_RECORD_UPLOAD_INTERVAL((byte)0x04),
        STRANGER_RECORD_UPLOAD_MODEL((byte)0x05),
        DOORBELL_SWITCH((byte)0x06),
        PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD((byte)0x07),
        PHOTOSENSITIVE_LIGHT_ON_THRESHOLD((byte)0x08),
        SET_PASSWORD((byte)0x09),
        POPUP_DIALOG_DURATION((byte)0x0A),
        WHITE_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE((byte)0x0B),
        WHITE_LIGHT_LOWLIGHT_WIDE_PERCENTAGE((byte)0x0C),
        RED_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE((byte)0x0D),
        RED_LIGHT_LOWLIGHT_WIDE_PERCENTAGE((byte)0x0E),
        STRANGE_FACE_HINT_SWITCH((byte)0x0F),
        AUTO_REBOOT_SWITCH((byte)0x10),
        AUTO_REBOOT_TIME((byte)0x11),
        AUTO_SCREENSAVER_ANIMATION_SWITCH((byte)0x12),
        AUTO_ENTERING_SCREENSAVER_TIME((byte)0x13),
        VOICE_SWITCH((byte)0x14),
        WIPING_CARD_AND_FACE_SWITCH((byte)0x15),
        WHETHER_ADD_CARD_AUTHORIZATION((byte)0x16);

        private byte value;
        public byte getValue() {
            return value;
        }

        ConfigAddress(byte value) {
            this.value = value;
        }
    }

    public static class ByteConfiguration {
        private byte address;
        private byte configurationValue;

        public ByteConfiguration(ConfigAddress configAddress) {
            switch(configAddress) {
                case AUTOMATIC_SCREEN_SWITCH:
                case SCREENSAVER_FACE_IDENTIFICATION_SWITCH:
                case STRANGE_FACE_HINT_SWITCH:
                case AUTO_REBOOT_SWITCH:
                    this.configurationValue = SWITCH_OPEN;
                    break;
                case STRANGER_RECORD_SWITCH:
                    this.configurationValue = SWITCH_CLOSE;
                    break;
                case POPUP_DIALOG_DURATION:
                    this.configurationValue = 1; // 缺省值
                    break;
            }

            this.address = configAddress.getValue();
        }

        public ByteConfiguration(ConfigAddress configAddress, byte configurationValue) {
            this.address = configAddress.getValue();
            this.configurationValue = configurationValue;
        }

        public byte getAddress() {
            return address;
        }

        public byte setAddress() {
            return address;
        }

        public byte getConfigurationValue() {
            return configurationValue;
        }

        public void setConfigurationValue(ConfigAddress configAddress, byte configurationValue) {
            byte newConfigValue = configurationValue;

            switch(configAddress) {
                case STRANGER_RECORD_UPLOAD_MODEL:
                    if (configurationValue != CONTINUE_SHIELD && configurationValue != FIXED_TIME_INTERVAL) {
                        if (configurationValue <= 0) newConfigValue = CONTINUE_SHIELD;
                        if (configurationValue > 0) newConfigValue = FIXED_TIME_INTERVAL;
                    }
                    break;
                case WHITE_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE:
                case WHITE_LIGHT_LOWLIGHT_WIDE_PERCENTAGE:
                    if (configurationValue > WHITE_LIGHT_WIDTH_RATIO_MAX_VALUE) {
                        newConfigValue = WHITE_LIGHT_WIDTH_RATIO_MAX_VALUE;
                    }
                    break;
                case RED_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE:
                case RED_LIGHT_LOWLIGHT_WIDE_PERCENTAGE:
                    if (configurationValue > RED_LIGHT_WIDTH_RATIO_MAX_VALUE) {
                        newConfigValue = RED_LIGHT_WIDTH_RATIO_MAX_VALUE;
                    }
                    break;
                case AUTO_REBOOT_TIME:
                    if (configurationValue > AUTO_REBOOT_MAX_VALUE) {
                        newConfigValue = AUTO_REBOOT_MAX_VALUE;
                    } else if (configurationValue < 0) {
                        newConfigValue = 0;
                    }
                    break;
                case AUTO_ENTERING_SCREENSAVER_TIME:
                    if (configurationValue > SCREENSAVER_TIME_RANGE_UPPER) {
                        newConfigValue = SCREENSAVER_TIME_RANGE_UPPER;
                    } else if (configurationValue < SCREENSAVER_TIME_RANGE_FLOOR) {
                        newConfigValue = SCREENSAVER_TIME_RANGE_FLOOR;
                    }
                    break;
            }
            this.configurationValue = newConfigValue;
        }
    }

    public static class IntegerConfiguration {
        private byte address;
        private int configurationValue;

        public IntegerConfiguration(ConfigAddress configAddress) {
            switch(configAddress) {
                case STRANGER_RECORD_UPLOAD_INTERVAL:
                    this.configurationValue = DEFAULT_UPLOAD_SECOND;
                    break;
                case PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD:
                    this.configurationValue = DEFAULT_PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD;
                    break;
                case PHOTOSENSITIVE_LIGHT_ON_THRESHOLD:
                    this.configurationValue = DEFAULT_PHOTOSENSITIVE_LIGHT_ON_THRESHOLD;
                    break;
                case SET_PASSWORD:
                    this.configurationValue = DEFAULT_PASSWORD;
                    break;
            }
        }

        public IntegerConfiguration(ConfigAddress configAddress, int configurationValue) {
            this.address = configAddress.getValue();
            this.configurationValue = configurationValue;
        }

        public byte getAddress() {
            return address;
        }

        public void setAddress(byte address) {
            this.address = address;
        }

        public int getConfigurationValue() {
            return configurationValue;
        }

        public void setConfigurationValue(ConfigAddress configAddress, int configurationValue) {
            if (configAddress == ConfigAddress.STRANGER_RECORD_UPLOAD_INTERVAL && configurationValue < 1) {
                this.configurationValue = DEFAULT_UPLOAD_SECOND;
                return;
            }
            this.configurationValue = configurationValue;
        }
    }

    public static class AllConfiguration {

        public AllConfiguration() {}

        private ByteConfiguration automaticScreenSwitchConfig;
        private ByteConfiguration screensaverFaceIdentificationSwitchConfig;
        private ByteConfiguration strangerRecordSwitchConfig;
        private IntegerConfiguration strangerRecordUploadIntervalConfig;
        private ByteConfiguration strangerRecordUploadModelConfig;
        private ByteConfiguration doorbellSwitchConfig;
        private IntegerConfiguration photosensitiveLightOffThresholdConfig;
        private IntegerConfiguration photosensitiveLightOnThresholdConfig;
        private IntegerConfiguration setPasswordConfig;
        private ByteConfiguration popupDialogDurationConfig;
        private ByteConfiguration whiteLightHighlightWidePercentageConfig;
        private ByteConfiguration whiteLightLowlightWidePercentageConfig;
        private ByteConfiguration redLightHighlightWidePercentageConfig;
        private ByteConfiguration redLightLowlightWidePercentageConfig;
        private ByteConfiguration strangeFaceHintSwitchConfig;
        private ByteConfiguration autoRebootSwitchConfig;
        private ByteConfiguration autoRebootTimeConfig;
        private ByteConfiguration autoScreensaverAnimationSwitchConfig;
        private ByteConfiguration autoEnteringScreensaverTimeConfig;
        private ByteConfiguration voiceSwitchConfig;
        private ByteConfiguration wipingCardAndFaceSwitchConfig;
        private ByteConfiguration whetherAddCardAuthorizationConfig;

        public AllConfiguration(ByteConfiguration automaticScreenSwitchConfig,
                                ByteConfiguration screensaverFaceIdentificationSwitchConfig,
                                ByteConfiguration strangerRecordSwitchConfig,
                                IntegerConfiguration strangerRecordUploadIntervalConfig,
                                ByteConfiguration strangerRecordUploadModelConfig,
                                ByteConfiguration doorbellSwitchConfig,
                                IntegerConfiguration photosensitiveLightOffThresholdConfig,
                                IntegerConfiguration photosensitiveLightOnThresholdConfig,
                                IntegerConfiguration setPasswordConfig,
                                ByteConfiguration popupDialogDurationConfig,
                                ByteConfiguration whiteLightHighlightWidePercentageConfig,
                                ByteConfiguration whiteLightLowlightWidePercentageConfig,
                                ByteConfiguration redLightHighlightWidePercentageConfig,
                                ByteConfiguration redLightLowlightWidePercentageConfig,
                                ByteConfiguration strangeFaceHintSwitchConfig,
                                ByteConfiguration autoRebootSwitchConfig,
                                ByteConfiguration autoRebootTimeConfig,
                                ByteConfiguration autoScreensaverAnimationSwitchConfig,
                                ByteConfiguration autoEnteringScreensaverTimeConfig,
                                ByteConfiguration voiceSwitchConfig,
                                ByteConfiguration wipingCardAndFaceSwitchConfig,
                                ByteConfiguration whetherAddCardAuthorizationConfig) {

            setAllConfiguration(automaticScreenSwitchConfig,
                    screensaverFaceIdentificationSwitchConfig,
                    strangerRecordSwitchConfig,
                    strangerRecordUploadIntervalConfig,
                    strangerRecordUploadModelConfig,
                    doorbellSwitchConfig,
                    photosensitiveLightOffThresholdConfig,
                    photosensitiveLightOnThresholdConfig,
                    setPasswordConfig,
                    popupDialogDurationConfig,
                    whiteLightHighlightWidePercentageConfig,
                    whiteLightLowlightWidePercentageConfig,
                    redLightHighlightWidePercentageConfig,
                    redLightLowlightWidePercentageConfig,
                    strangeFaceHintSwitchConfig,
                    autoRebootSwitchConfig,
                    autoRebootTimeConfig,
                    autoScreensaverAnimationSwitchConfig,
                    autoEnteringScreensaverTimeConfig,
                    voiceSwitchConfig,
                    wipingCardAndFaceSwitchConfig,
                    whetherAddCardAuthorizationConfig);
        }

        public void setConfiguration(ConfigAddress address, int configurationValue) {
            switch(address) {
                case AUTOMATIC_SCREEN_SWITCH:
                    automaticScreenSwitchConfig = new ByteConfiguration(ConfigAddress.AUTOMATIC_SCREEN_SWITCH,
                            (byte) configurationValue);
                    break;
                case SCREENSAVER_FACE_IDENTIFICATION_SWITCH:
                    screensaverFaceIdentificationSwitchConfig = new ByteConfiguration(ConfigAddress.SCREENSAVER_FACE_IDENTIFICATION_SWITCH,
                            (byte) configurationValue);
                    break;
                case STRANGER_RECORD_SWITCH:
                    strangerRecordSwitchConfig = new ByteConfiguration(ConfigAddress.STRANGER_RECORD_SWITCH,
                            (byte) configurationValue);
                    break;
                case STRANGER_RECORD_UPLOAD_INTERVAL:
                    strangerRecordUploadIntervalConfig = new IntegerConfiguration(ConfigAddress.STRANGER_RECORD_UPLOAD_INTERVAL,
                            configurationValue);
                    break;
                case STRANGER_RECORD_UPLOAD_MODEL:
                    strangerRecordUploadModelConfig = new ByteConfiguration(ConfigAddress.STRANGER_RECORD_UPLOAD_MODEL,
                            (byte) configurationValue);
                    break;
                case DOORBELL_SWITCH:
                    doorbellSwitchConfig = new ByteConfiguration(ConfigAddress.DOORBELL_SWITCH,
                            (byte) configurationValue);
                    break;
                case PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD:
                    photosensitiveLightOffThresholdConfig = new IntegerConfiguration(ConfigAddress.PHOTOSENSITIVE_LIGHT_OFF_THRESHOLD,
                            configurationValue);
                    break;
                case PHOTOSENSITIVE_LIGHT_ON_THRESHOLD:
                    photosensitiveLightOnThresholdConfig = new IntegerConfiguration(ConfigAddress.PHOTOSENSITIVE_LIGHT_ON_THRESHOLD,
                            configurationValue);
                    break;
                case SET_PASSWORD:
                    setPasswordConfig = new IntegerConfiguration(ConfigAddress.SET_PASSWORD, configurationValue);
                    break;
                case POPUP_DIALOG_DURATION:
                    popupDialogDurationConfig = new ByteConfiguration(ConfigAddress.POPUP_DIALOG_DURATION,
                            (byte) configurationValue);
                    break;
                case WHITE_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE:
                    whiteLightHighlightWidePercentageConfig = new ByteConfiguration(ConfigAddress.WHITE_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE,
                            (byte) configurationValue);
                    break;
                case WHITE_LIGHT_LOWLIGHT_WIDE_PERCENTAGE:
                    whiteLightLowlightWidePercentageConfig = new ByteConfiguration(ConfigAddress.WHITE_LIGHT_LOWLIGHT_WIDE_PERCENTAGE,
                            (byte) configurationValue);
                    break;
                case RED_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE:
                    redLightHighlightWidePercentageConfig = new ByteConfiguration(ConfigAddress.RED_LIGHT_HIGHLIGHT_WIDE_PERCENTAGE,
                            (byte) configurationValue);
                    break;
                case RED_LIGHT_LOWLIGHT_WIDE_PERCENTAGE:
                    redLightLowlightWidePercentageConfig = new ByteConfiguration(ConfigAddress.RED_LIGHT_LOWLIGHT_WIDE_PERCENTAGE,
                            (byte) configurationValue);
                    break;
                case STRANGE_FACE_HINT_SWITCH:
                    strangeFaceHintSwitchConfig = new ByteConfiguration(ConfigAddress.STRANGE_FACE_HINT_SWITCH,
                            (byte) configurationValue);
                    break;
                case AUTO_REBOOT_SWITCH:
                    autoRebootSwitchConfig = new ByteConfiguration(ConfigAddress.AUTO_REBOOT_SWITCH,
                            (byte) configurationValue);
                    break;
                case AUTO_REBOOT_TIME:
                    autoRebootTimeConfig = new ByteConfiguration(ConfigAddress.AUTO_REBOOT_TIME,
                            (byte) configurationValue);
                    break;
                case AUTO_SCREENSAVER_ANIMATION_SWITCH:
                    autoScreensaverAnimationSwitchConfig = new ByteConfiguration(ConfigAddress.AUTO_SCREENSAVER_ANIMATION_SWITCH,
                            (byte) configurationValue);
                    break;
                case AUTO_ENTERING_SCREENSAVER_TIME:
                    autoEnteringScreensaverTimeConfig = new ByteConfiguration(ConfigAddress.AUTO_ENTERING_SCREENSAVER_TIME,
                            (byte) configurationValue);
                    break;
                case VOICE_SWITCH:
                    voiceSwitchConfig = new ByteConfiguration(ConfigAddress.VOICE_SWITCH, (byte) configurationValue);
                    break;
                case WIPING_CARD_AND_FACE_SWITCH:
                    wipingCardAndFaceSwitchConfig = new ByteConfiguration(ConfigAddress.WIPING_CARD_AND_FACE_SWITCH,
                            (byte) configurationValue);
                    break;
                case WHETHER_ADD_CARD_AUTHORIZATION:
                    whetherAddCardAuthorizationConfig = new ByteConfiguration(ConfigAddress.WHETHER_ADD_CARD_AUTHORIZATION,
                            (byte) configurationValue);
                    break;
            }
        }

        public ByteConfiguration getAutomaticScreenSwitchConfig() {
            return automaticScreenSwitchConfig;
        }

        public void setAutomaticScreenSwitchConfig(ByteConfiguration automaticScreenSwitchConfig) {
            this.automaticScreenSwitchConfig = automaticScreenSwitchConfig;
        }

        public ByteConfiguration getScreensaverFaceIdentificationSwitchConfig() {
            return screensaverFaceIdentificationSwitchConfig;
        }

        public void setScreensaverFaceIdentificationSwitchConfig(ByteConfiguration screensaverFaceIdentificationSwitchConfig) {
            this.screensaverFaceIdentificationSwitchConfig = screensaverFaceIdentificationSwitchConfig;
        }

        public ByteConfiguration getStrangerRecordSwitchConfig() {
            return strangerRecordSwitchConfig;
        }

        public void setStrangerRecordSwitchConfig(ByteConfiguration strangerRecordSwitchConfig) {
            this.strangerRecordSwitchConfig = strangerRecordSwitchConfig;
        }

        public IntegerConfiguration getStrangerRecordUploadIntervalConfig() {
            return strangerRecordUploadIntervalConfig;
        }

        public void setStrangerRecordUploadIntervalConfig(IntegerConfiguration strangerRecordUploadIntervalConfig) {
            this.strangerRecordUploadIntervalConfig = strangerRecordUploadIntervalConfig;
        }

        public ByteConfiguration getStrangerRecordUploadModelConfig() {
            return strangerRecordUploadModelConfig;
        }

        public void setStrangerRecordUploadModelConfig(ByteConfiguration strangerRecordUploadModelConfig) {
            this.strangerRecordUploadModelConfig = strangerRecordUploadModelConfig;
        }

        public ByteConfiguration getDoorbellSwitchConfig() {
            return doorbellSwitchConfig;
        }

        public void setDoorbellSwitchConfig(ByteConfiguration doorbellSwitchConfig) {
            this.doorbellSwitchConfig = doorbellSwitchConfig;
        }

        public IntegerConfiguration getPhotosensitiveLightOffThresholdConfig() {
            return photosensitiveLightOffThresholdConfig;
        }

        public void setPhotosensitiveLightOffThresholdConfig(IntegerConfiguration photosensitiveLightOffThresholdConfig) {
            this.photosensitiveLightOffThresholdConfig = photosensitiveLightOffThresholdConfig;
        }

        public IntegerConfiguration getPhotosensitiveLightOnThresholdConfig() {
            return photosensitiveLightOnThresholdConfig;
        }

        public void setPhotosensitiveLightOnThresholdConfig(IntegerConfiguration photosensitiveLightOnThresholdConfig) {
            this.photosensitiveLightOnThresholdConfig = photosensitiveLightOnThresholdConfig;
        }

        public IntegerConfiguration getSetPasswordConfig() {
            return setPasswordConfig;
        }

        public void setSetPasswordConfig(IntegerConfiguration setPasswordConfig) {
            this.setPasswordConfig = setPasswordConfig;
        }

        public ByteConfiguration getPopupDialogDurationConfig() {
            return popupDialogDurationConfig;
        }

        public void setPopupDialogDurationConfig(ByteConfiguration popupDialogDurationConfig) {
            this.popupDialogDurationConfig = popupDialogDurationConfig;
        }

        public ByteConfiguration getWhiteLightHighlightWidePercentageConfig() {
            return whiteLightHighlightWidePercentageConfig;
        }

        public void setWhiteLightHighlightWidePercentageConfig(ByteConfiguration whiteLightHighlightWidePercentageConfig) {
            this.whiteLightHighlightWidePercentageConfig = whiteLightHighlightWidePercentageConfig;
        }

        public ByteConfiguration getWhiteLightLowlightWidePercentageConfig() {
            return whiteLightLowlightWidePercentageConfig;
        }

        public void setWhiteLightLowlightWidePercentageConfig(ByteConfiguration whiteLightLowlightWidePercentageConfig) {
            this.whiteLightLowlightWidePercentageConfig = whiteLightLowlightWidePercentageConfig;
        }

        public ByteConfiguration getRedLightHighlightWidePercentageConfig() {
            return redLightHighlightWidePercentageConfig;
        }

        public void setRedLightHighlightWidePercentageConfig(ByteConfiguration redLightHighlightWidePercentageConfig) {
            this.redLightHighlightWidePercentageConfig = redLightHighlightWidePercentageConfig;
        }

        public ByteConfiguration getRedLightLowlightWidePercentageConfig() {
            return redLightLowlightWidePercentageConfig;
        }

        public void setRedLightLowlightWidePercentageConfig(ByteConfiguration redLightLowlightWidePercentageConfig) {
            this.redLightLowlightWidePercentageConfig = redLightLowlightWidePercentageConfig;
        }

        public ByteConfiguration getStrangeFaceHintSwitchConfig() {
            return strangeFaceHintSwitchConfig;
        }

        public void setStrangeFaceHintSwitchConfig(ByteConfiguration strangeFaceHintSwitchConfig) {
            this.strangeFaceHintSwitchConfig = strangeFaceHintSwitchConfig;
        }

        public ByteConfiguration getAutoRebootSwitchConfig() {
            return autoRebootSwitchConfig;
        }

        public void setAutoRebootSwitchConfig(ByteConfiguration autoRebootSwitchConfig) {
            this.autoRebootSwitchConfig = autoRebootSwitchConfig;
        }

        public ByteConfiguration getAutoRebootTimeConfig() {
            return autoRebootTimeConfig;
        }

        public void setAutoRebootTimeConfig(ByteConfiguration autoRebootTimeConfig) {
            this.autoRebootTimeConfig = autoRebootTimeConfig;
        }

        public ByteConfiguration getAutoScreensaverAnimationSwitchConfig() {
            return autoScreensaverAnimationSwitchConfig;
        }

        public void setAutoScreensaverAnimationSwitchConfig(ByteConfiguration autoScreensaverAnimationSwitchConfig) {
            this.autoScreensaverAnimationSwitchConfig = autoScreensaverAnimationSwitchConfig;
        }

        public ByteConfiguration getAutoEnteringScreensaverTimeConfig() {
            return autoEnteringScreensaverTimeConfig;
        }

        public void setAutoEnteringScreensaverTimeConfig(ByteConfiguration autoEnteringScreensaverTimeConfig) {
            this.autoEnteringScreensaverTimeConfig = autoEnteringScreensaverTimeConfig;
        }

        public ByteConfiguration getVoiceSwitchConfig() {
            return voiceSwitchConfig;
        }

        public void setVoiceSwitchConfig(ByteConfiguration voiceSwitchConfig) {
            this.voiceSwitchConfig = voiceSwitchConfig;
        }

        public ByteConfiguration getWipingCardAndFaceSwitchConfig() {
            return wipingCardAndFaceSwitchConfig;
        }

        public void setWipingCardAndFaceSwitchConfig(ByteConfiguration wipingCardAndFaceSwitchConfig) {
            this.wipingCardAndFaceSwitchConfig = wipingCardAndFaceSwitchConfig;
        }

        public ByteConfiguration getWhetherAddCardAuthorizationConfig() {
            return whetherAddCardAuthorizationConfig;
        }

        public void setWhetherAddCardAuthorizationConfig(ByteConfiguration whetherAddCardAuthorizationConfig) {
            this.whetherAddCardAuthorizationConfig = whetherAddCardAuthorizationConfig;
        }


        public void setAllConfiguration(ByteConfiguration automaticScreenSwitchConfig,
                                        ByteConfiguration screensaverFaceIdentificationSwitchConfig,
                                        ByteConfiguration strangerRecordSwitchConfig,
                                        IntegerConfiguration strangerRecordUploadIntervalConfig,
                                        ByteConfiguration strangerRecordUploadModelConfig,
                                        ByteConfiguration doorbellSwitchConfig,
                                        IntegerConfiguration photosensitiveLightOffThresholdConfig,
                                        IntegerConfiguration photosensitiveLightOnThresholdConfig,
                                        IntegerConfiguration setPasswordConfig,
                                        ByteConfiguration popupDialogDurationConfig,
                                        ByteConfiguration whiteLightHighlightWidePercentageConfig,
                                        ByteConfiguration whiteLightLowlightWidePercentageConfig,
                                        ByteConfiguration redLightHighlightWidePercentageConfig,
                                        ByteConfiguration redLightLowlightWidePercentageConfig,
                                        ByteConfiguration strangeFaceHintSwitchConfig,
                                        ByteConfiguration autoRebootSwitchConfig,
                                        ByteConfiguration autoRebootTimeConfig,
                                        ByteConfiguration autoScreensaverAnimationSwitchConfig,
                                        ByteConfiguration autoEnteringScreensaverTimeConfig,
                                        ByteConfiguration voiceSwitchConfig,
                                        ByteConfiguration wipingCardAndFaceSwitchConfig,
                                        ByteConfiguration whetherAddCardAuthorizationConfig) {
            this.automaticScreenSwitchConfig = automaticScreenSwitchConfig;
            this.screensaverFaceIdentificationSwitchConfig = screensaverFaceIdentificationSwitchConfig;
            this.strangerRecordSwitchConfig = strangerRecordSwitchConfig;
            this.strangerRecordUploadIntervalConfig = strangerRecordUploadIntervalConfig;
            this.strangerRecordUploadModelConfig = strangerRecordUploadModelConfig;
            this.doorbellSwitchConfig = doorbellSwitchConfig;
            this.photosensitiveLightOffThresholdConfig = photosensitiveLightOffThresholdConfig;
            this.photosensitiveLightOnThresholdConfig = photosensitiveLightOnThresholdConfig;
            this.setPasswordConfig = setPasswordConfig;
            this.popupDialogDurationConfig = popupDialogDurationConfig;
            this.whiteLightHighlightWidePercentageConfig = whiteLightHighlightWidePercentageConfig;
            this.whiteLightLowlightWidePercentageConfig = whiteLightLowlightWidePercentageConfig;
            this.redLightHighlightWidePercentageConfig = redLightHighlightWidePercentageConfig;
            this.redLightLowlightWidePercentageConfig = redLightLowlightWidePercentageConfig;
            this.strangeFaceHintSwitchConfig = strangeFaceHintSwitchConfig;
            this.autoRebootSwitchConfig = autoRebootSwitchConfig;
            this.autoRebootTimeConfig = autoRebootTimeConfig;
            this.autoScreensaverAnimationSwitchConfig = autoScreensaverAnimationSwitchConfig;
            this.autoEnteringScreensaverTimeConfig = autoEnteringScreensaverTimeConfig;
            this.voiceSwitchConfig = voiceSwitchConfig;
            this.wipingCardAndFaceSwitchConfig = wipingCardAndFaceSwitchConfig;
            this.whetherAddCardAuthorizationConfig = whetherAddCardAuthorizationConfig;
        }
    }
}
