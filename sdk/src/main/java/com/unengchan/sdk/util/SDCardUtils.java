package com.unengchan.sdk.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by unengchan on 2017/11/9.
 * SDcard帮助类
 */

public class SDCardUtils {

    private SDCardUtils() {
    }

    /**
     * 判断内置sd卡是否挂载
     *
     * @return
     */
    private static boolean isMounted() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     * 获取内置sd卡的外部缓存路径   例如：/storage/emulated/0/Android/data/com.seahung.toolapp/cache
     *
     * @return 外部缓存路径
     */
    public static String getExternalCacheDir() {
        if (!isMounted()) {
            return null;
        }
        File cacheDir = AppUtils.getAppContext().getExternalCacheDir();
        if (cacheDir != null) {
            return cacheDir.getAbsolutePath();
        }
        // 防止在应用管理清除数据时，没有清掉包名文件夹，从而导致获取不到缓存路径的问题。
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data"
                + File.separator + AppUtils.getPackageName() + File.separator + "cache";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }


    /**
     * 获取内置sd卡的外部缓存路径   例如：/storage/emulated/0/Android/data/com.seahung.toolapp/cache/type
     *
     * @return 外部缓存路径
     */
    public static String getExternalCacheDir(String type) {
        if (!isMounted()) {
            return null;
        }
        File cacheDir = AppUtils.getAppContext().getExternalCacheDir();
        if (cacheDir != null) {
            File dir = new File(cacheDir.getAbsolutePath() + File.separator + type);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        }
        // 防止在应用管理清除数据时，没有清掉包名文件夹，从而导致获取不到缓存路径的问题。
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data"
                + File.separator + AppUtils.getPackageName() + File.separator + "cache" + File.separator + type;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;

    }

    /**
     * 获取内置sd卡的外部文件路径   例如：/storage/emulated/0/Android/data/com.seahung.toolapp/files/image
     *
     * @param type 文件夹名字，例如：image
     * @return 外部文件路径
     */
    public static String getExternalFilesDir(String type) {
        if (!isMounted()) {
            return null;
        }
        File cacheDir = AppUtils.getAppContext().getExternalFilesDir(type);
        if (cacheDir != null) {
            return cacheDir.getAbsolutePath();
        }
        // 防止在应用管理清除数据时，没有清掉包名文件夹，从而导致获取不到缓存路径的问题。
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data"
                + File.separator + AppUtils.getPackageName() + File.separator + "files" + File.separator + type;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

}
