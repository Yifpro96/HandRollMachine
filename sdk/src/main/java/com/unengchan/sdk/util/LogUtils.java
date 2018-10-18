package com.unengchan.sdk.util;

import android.util.Log;

/**
 * Created by unengchan on 2018/2/9.
 * log日志工具类
 */

public class LogUtils {

    public static boolean openDebug = true;

    private LogUtils(){}

    public static void d(String tag, Object msg) {
        if (openDebug) {
            Log.d("unengchan————" + tag, msg+"");
        }
    }

    public static void v(String tag, Object msg) {
        if (openDebug) {
            Log.d("unengchan————" + tag, msg+"");
        }
    }
}
