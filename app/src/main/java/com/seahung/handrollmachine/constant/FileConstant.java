package com.seahung.handrollmachine.constant;

import com.unengchan.sdk.util.SDCardUtils;

import java.io.File;

/**
 * Created by unengchan on 2018/5/25
 * 文件/文件夹相关常量类
 */
public class FileConstant {

    // apk的文件路径
    public static final String APK_FILE_PATH = SDCardUtils.getExternalFilesDir("Apk") + File.separator + "AccessDoorControl.apk";

    // 用户数据文件路径
    public static final String USER_DATA_DIR = SDCardUtils.getExternalFilesDir("UserData");

    // 用户名单
    public static final String USER_ROSTER_FILE_PATH = USER_DATA_DIR + File.separator + "Roster.csv";

    // 用户头像
    public static final String USER_PHOTO_DIR = USER_DATA_DIR+File.separator+"UserPhoto";

    // 照相机的拍摄照片
    public static final String USER_CAMERA_PICTURE = SDCardUtils.getExternalCacheDir("CameraPicture");
}
