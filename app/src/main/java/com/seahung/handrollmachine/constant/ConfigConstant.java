package com.seahung.handrollmachine.constant;

/**
 * Created by unengchan on 2018/5/4
 * 配置常量，通常是默认配置
 */
public class ConfigConstant {

    // 设备位置
    public static final String DEVICE_LOCATION = "device_location";
    public static final String DEVICE_LOCATION_DEFAULT = "广州市";

    // 学校id
    public static final String SCHOOL_ID = "school_id";
    public static final String SCHOOL_ID_DEFAULT = "sch_";

    // 终端id
    public static final String TERMINAL_ID = "terminal_id";

    // 平台id
    public static final String PLATFORM_ID = "platform_id";
    public static final String PLATFORM_ID_DEFAULT = "921";

    // 串口
    public static final String SERIAL_PORT = "serial_port";
    public static final String SERIAL_PORT_ONE = "/dev/ttyS1";
    public static final String SERIAL_PORT_TWO = "/dev/ttyS2";
    public static final String SERIAL_PORT_THREE = "/dev/ttyS3";
    public static final String SERIAL_PORT_FOUR = "/dev/ttyS4";

    // 波特率
    public static final String BAUD_RATE = "baud_rate";
    public static final int BAUD_RATE_2400 = 2400;
    public static final int BAUD_RATE_4800 = 4800;
    public static final int BAUD_RATE_9600 = 9600;
    public static final int BAUD_RATE_19200 = 19200;

    // 考勤方式
    public static final String ATTEND_TYPE = "attend_type";
    public static final String ATTEND_TYPE_CARD = "card";
    public static final String ATTEND_TYPE_FACE = "face";
    public static final String ATTEND_TYPE_CARD_FACE = "card_face";

    // 置信度
    public static final String FACE_SEMBLANCE = "face_semblance";
    public static final int FACE_SEMBLANCE_DEFAULT = 600;

    // 拍照模式
    public static final String PHOTO_MODE = "photo_mode";
    public static final String PHOTO_MODE_WITHOUT_PHOTO = "without_photo";
    public static final String PHOTO_MODE_ONE_PHOTO = "one_photo";
    public static final String PHOTO_MODE_SIX_PHOTO = "six_photo";

    // 教师/学生
    public static final String USER_TYPE = "user_type";
    public static final String USER_TYPE_GENERAL = "general";
    public static final String USER_TYPE_TEACHER = "teacher";
    public static final String USER_TYPE_STUDENT = "student";

    // 出校/入校
    public static final String INOUT_SCHOOL = "inout_school";
    public static final String INOUT_SCHOOL_GENERAL = "general";
    public static final String INOUT_SCHOOL_IN = "in";
    public static final String INOUT_SCHOOL_OUT = "out";

    // 语音播报
    public static final String PLAY_VOICE = "play_voice";
    public static final String PLAY_VOICE_OPEN = "open";
    public static final String PLAY_VOICE_CLOSE = "close";

    // 语音播报方式
    public static final String PLAY_VOICE_TYPE = "play_voice_type";
    public static final String PLAY_VOICE_TYPE_LIVE_TYPE_NAME = "paly_voice_type_live_type_name";
    public static final String PLAY_VOICE_TYPE_LIVE_TYPE = "play_voice_type_live_type";
    public static final String PLAY_VOICE_TYPE_NAME = "play_voice_type_name";

    // 屏保公告
    public static final String ANNOUNCE_BOARD = "announce_board";
    public static final String ANNOUNCE_BOARD_OPEN = "open";
    public static final String ANNOUNCE_BOARD_CLOSE = "close";

    // 皮肤设置
    public static final String SKIN_COLOR = "skin_color";
    public static final String SKIN_SKY_BLUE = "#009bdb";
    public static final String SKIN_DARK_GRAY = "#191919";
    public static final String SKIN_GRAYISH_BLUE = "#5ea7e5";
    public static final String SKIN_GREEN = "#159c77";
    public static final String SKIN_ORANGE = "#dd8449";
    public static final String SKIN_RED = "#e24b56";
    public static final String SKIN_AQUA = "#00829a";
    public static final String SKIN_WATHET_BLUE = "#6389b3";

    // 定位设置
    public static final String GPS_LOCATION = "gps_location";
    public static final String GPS_LOCATION_OPEN = "gps_location_open";
    public static final String GPS_LOCATION_CLOSE = "gps_location_close";
    // 定位时间间隔
    public static final String LOCATION_TIME_INTERVAL = "location_time_interval";
    public static final int LOCATION_TIME_INTERVAL_ONE_SECOND = 1000;
    public static final int LOCATION_TIME_INTERVAL_FIVE_SECOND = 5000;
    public static final int LOCATION_TIME_INTERVAL_FIFTEEN_SECOND = 15000;
    public static final int LOCATION_TIME_INTERVAL_THIRTY_SECOND = 30000;
    public static final int LOCATION_TIME_INTERVAL_ONE_MINUTE = 60000;
    public static final int LOCATION_TIME_INTERVAL_FIVE_MINUTE = 300000;
}
