package com.seahung.handrollmachine.manager;

import com.seahung.handrollmachine.constant.ConfigConstant;
import com.unengchan.sdk.util.SPUtils;
import com.unengchan.sdk.util.StringUtils;

/**
 * Created by unengchan on 2018/5/4
 * 配置管理器
 */
public class ConfigManager {

    // 学校id
    private String schoolId;

    public String getSchoolId() {
        if (StringUtils.isEmpty(schoolId)) {
            schoolId = ((String) SPUtils.getValue(ConfigConstant.SCHOOL_ID, ConfigConstant.SCHOOL_ID_DEFAULT));
        }
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
        SPUtils.putValue(ConfigConstant.SCHOOL_ID, schoolId);
    }

    // 终端id
    private String terminalId;

    public String getTerminalId() {
        if (StringUtils.isEmpty(terminalId)) {
            terminalId = ((String) SPUtils.getValue(ConfigConstant.TERMINAL_ID, ""));
        }
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        SPUtils.putValue(ConfigConstant.TERMINAL_ID, terminalId);
    }

    // 平台编码
    private String platformId;

    public String getPlatformId() {
        if (StringUtils.isEmpty(platformId)) {
            platformId = (String) SPUtils.getValue(ConfigConstant.PLATFORM_ID, ConfigConstant.PLATFORM_ID_DEFAULT);
        }
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
        SPUtils.putValue(ConfigConstant.PLATFORM_ID, platformId);
    }

    // 串口
    private String serialPort;

    public String getSerialPort() {
        if (StringUtils.isEmpty(serialPort)) {
            serialPort = ((String) SPUtils.getValue(ConfigConstant.SERIAL_PORT, ""));
        }
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
        SPUtils.putValue(ConfigConstant.SERIAL_PORT, serialPort);
    }

    // 波特率
    private int baudRate;

    public int getBaudRate() {
        if (baudRate == 0) {
            baudRate = (int) SPUtils.getValue(ConfigConstant.BAUD_RATE, 0);
        }
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
        SPUtils.putValue(ConfigConstant.BAUD_RATE, baudRate);
    }

    // 考勤方式
    private String attendType;

    public String getAttendType() {
        if (StringUtils.isEmpty(attendType)) {
            attendType = ((String) SPUtils.getValue(ConfigConstant.ATTEND_TYPE, ConfigConstant.ATTEND_TYPE_FACE));
        }
        return attendType;
    }

    public void setAttendType(String attendType) {
        this.attendType = attendType;
        SPUtils.putValue(ConfigConstant.ATTEND_TYPE, attendType);
    }

    // 人脸置信度
    private int faceSemblance;

    public int getFaceSemblance() {
        if (faceSemblance == 0) {
            faceSemblance = ((int) SPUtils.getValue(ConfigConstant.FACE_SEMBLANCE, ConfigConstant.FACE_SEMBLANCE_DEFAULT));
        }
        return faceSemblance;
    }

    public void setFaceSemblance(int faceSemblance) {
        this.faceSemblance = faceSemblance;
        SPUtils.putValue(ConfigConstant.FACE_SEMBLANCE, faceSemblance);
    }

    // 拍照模式
    private String photoMode;

    public String getPhotoMode() {
        if (StringUtils.isEmpty(photoMode)) {
            photoMode = (String) SPUtils.getValue(ConfigConstant.PHOTO_MODE, ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO);
        }
        return photoMode;
    }

    public void setPhotoMode(String photoMode) {
        this.photoMode = photoMode;
        SPUtils.putValue(ConfigConstant.PHOTO_MODE, photoMode);
    }

    // 教师/学生
    private String userType;

    public String getUserType() {
        if (StringUtils.isEmpty(userType)) {
            userType = (String) SPUtils.getValue(ConfigConstant.USER_TYPE, ConfigConstant.USER_TYPE_GENERAL);
        }
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        SPUtils.putValue(ConfigConstant.USER_TYPE, userType);
    }

    // 出校/入校
    private String inoutSchool;

    public String getInoutSchool() {
        if (StringUtils.isEmpty(inoutSchool)) {
            inoutSchool = (String) SPUtils.getValue(ConfigConstant.INOUT_SCHOOL, ConfigConstant.INOUT_SCHOOL_GENERAL);
        }
        return inoutSchool;
    }

    public void setInoutSchool(String inoutSchool) {
        this.inoutSchool = inoutSchool;
        SPUtils.putValue(ConfigConstant.INOUT_SCHOOL, inoutSchool);
    }

    // 屏保公告
    private String announceBoard;

    public String getAnnounceBoard() {
        if (StringUtils.isEmpty(announceBoard)) {
            announceBoard = (String) SPUtils.getValue(ConfigConstant.ANNOUNCE_BOARD, ConfigConstant.ANNOUNCE_BOARD_OPEN);
        }
        return announceBoard;
    }

    public void setAnnounceBoard(String announceBoard) {
        this.announceBoard = announceBoard;
        SPUtils.putValue(ConfigConstant.ANNOUNCE_BOARD, announceBoard);
    }

    // 语音播报
    private String playVoice;

    public String getPlayVoice() {
        if (StringUtils.isEmpty(playVoice)) {
            playVoice = (String) SPUtils.getValue(ConfigConstant.PLAY_VOICE, ConfigConstant.PLAY_VOICE_OPEN);
        }
        return playVoice;
    }

    public void setPlayVoice(String playVoice) {
        this.playVoice = playVoice;
        SPUtils.putValue(ConfigConstant.PLAY_VOICE, playVoice);
    }

    // 播报方式
    private String playVoiceType;

    public String getPlayVoiceType() {
        if (StringUtils.isEmpty(playVoiceType)) {
            playVoiceType = (String) SPUtils.getValue(ConfigConstant.PLAY_VOICE_TYPE, ConfigConstant.PLAY_VOICE_TYPE_NAME);
        }
        return playVoiceType;
    }

    public void setPlayVoiceType(String playVoiceType) {
        this.playVoiceType = playVoiceType;
        SPUtils.putValue(ConfigConstant.PLAY_VOICE_TYPE, playVoiceType);
    }

    // 皮肤设置
    private String skinColors;

    public String getSkinColors() {
        if (StringUtils.isEmpty(skinColors)) {
            skinColors = (String) SPUtils.getValue(ConfigConstant.SKIN_COLOR, ConfigConstant.SKIN_WATHET_BLUE);
        }
        return skinColors;
    }

    public void setSkinColors(String skinColors) {
        this.skinColors = skinColors;
        SPUtils.putValue(ConfigConstant.SKIN_COLOR, skinColors);
    }

    // 定位设置
    private String gpsLocation;

    public String getGpsLocation() {
        if (StringUtils.isEmpty(gpsLocation)) {
            gpsLocation = (String) SPUtils.getValue(ConfigConstant.GPS_LOCATION, ConfigConstant.GPS_LOCATION_OPEN);
        }
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
        SPUtils.putValue(ConfigConstant.GPS_LOCATION, gpsLocation);
    }

    // 定位间隔
    private int locationInterval;

    public int getLocationInterval() {
        if (locationInterval == 0) {
            locationInterval = (int) SPUtils.getValue(ConfigConstant.LOCATION_TIME_INTERVAL, ConfigConstant.LOCATION_TIME_INTERVAL_FIFTEEN_SECOND);
        }
        return locationInterval;
    }

    public void setLocationInterval(int locationInterval) {
        this.locationInterval = locationInterval;
        SPUtils.putValue(ConfigConstant.LOCATION_TIME_INTERVAL, locationInterval);
    }

    //校车座位数
    private String schoolBusSeatCount;

    public String getSchoolBusSeatCount() {
        if (StringUtils.isEmpty(schoolBusSeatCount)) {
            schoolBusSeatCount = (String) SPUtils.getValue(ConfigConstant.KEY_SEAT_COUNT, "");
        }
        return schoolBusSeatCount;
    }

    public void setSchoolBusSeatCount(String seatCount) {
        this.schoolBusSeatCount = seatCount;
        SPUtils.putValue(ConfigConstant.KEY_SEAT_COUNT, seatCount);

    }

    //校车座位行数
    private String schoolBusSeatRow;

    public String getSchoolBusSeatRow() {
        if (StringUtils.isEmpty(schoolBusSeatRow)) {
            schoolBusSeatRow = (String) SPUtils.getValue(ConfigConstant.KEY_SEAT_ROW, "");
        }
        return schoolBusSeatRow;
    }

    public void setSchoolBusSeatRow(String schoolBusSeatRow) {
        this.schoolBusSeatRow = schoolBusSeatRow;
        SPUtils.putValue(ConfigConstant.KEY_SEAT_ROW, schoolBusSeatRow);

    }

    //校车座位列数
    private String schoolBusSeatColumn;

    public String getSchoolBusSeatColumn() {
        if (StringUtils.isEmpty(schoolBusSeatColumn)) {
            schoolBusSeatColumn = (String) SPUtils.getValue(ConfigConstant.KEY_SEAT_COLUMN, "");
        }
        return schoolBusSeatColumn;
    }

    public void setSchoolBusSeatColumn(String schoolBusSeatColumn) {
        this.schoolBusSeatColumn = schoolBusSeatColumn;
        SPUtils.putValue(ConfigConstant.KEY_SEAT_COLUMN, schoolBusSeatColumn);

    }

    //校车所有座椅编号
    private String schoolBusSeatAllNumber;

    public String getSchoolBusSeatAllNumber() {
        if (StringUtils.isEmpty(schoolBusSeatAllNumber)) {
            schoolBusSeatAllNumber = (String) SPUtils.getValue(ConfigConstant.KEY_SEAT_ALL_NUMBER, "");
        }
        return schoolBusSeatAllNumber;
    }

    public void setSchoolBusSeatAllNumber(String schoolBusSeatAllNumber) {
        this.schoolBusSeatAllNumber = schoolBusSeatAllNumber;
        SPUtils.putValue(ConfigConstant.KEY_SEAT_ALL_NUMBER, schoolBusSeatAllNumber);

    }

    //校车座位分布图
    private String schoolBusSeatDistrDiagram;

    public String getSchoolBusSeatDistrDiagram() {
        if (StringUtils.isEmpty(schoolBusSeatDistrDiagram)) {
            schoolBusSeatDistrDiagram = (String) SPUtils.getValue(ConfigConstant.KEY_SEAT_DISTR_DIAGRAM, "");
        }
        return schoolBusSeatDistrDiagram;
    }

    public void setSchoolBusSeatDistrDiagram(String schoolBusSeatDistrDiagram) {
        this.schoolBusSeatDistrDiagram = schoolBusSeatDistrDiagram;
        SPUtils.putValue(ConfigConstant.KEY_SEAT_DISTR_DIAGRAM, schoolBusSeatDistrDiagram);

    }

    // 静态内部类的单例模式
    private ConfigManager() {
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }
}
