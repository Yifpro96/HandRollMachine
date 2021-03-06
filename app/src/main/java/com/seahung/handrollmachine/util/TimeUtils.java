package com.seahung.handrollmachine.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by unengchan on 2018/7/17
 * 时间辅助工具类
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    private static String TAG = "*********-" + TimeUtils.class.getSimpleName() + "-************* ";

    /**
     * 获取当前时间
     *
     * @param timeFormat 时间格式
     * @return
     */
    public static String getCurrentTime(String timeFormat) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        String dateStr = format.format(date);
        return dateStr;
    }

    /**
     * 格式化毫秒数为19位的时间日期
     *
     * @param millis
     * @return
     */
    public static String formatMillisToDateTime(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }

    /**
     * 格式化秒数到时间 00：00：00
     *
     * @param seconds
     * @return
     */
    public static String formatSecondsToTime(long seconds) {
        long hour = 0;
        long minute = 0;
        long second;
        if (seconds >= 60) {
            minute = seconds / 60;         //取整
            second = seconds % 60;         //取余
        } else {
            second = seconds;
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }

        String hourStr;
        String minuteStr;
        String secondStr;
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = String.valueOf(minute);
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = String.valueOf(second);
        }

        String strtime = hourStr + ":" + minuteStr + ":" + secondStr;
        return strtime;
    }

    /**
     * 当前时间与刷卡时间比较
     *
     * @param date 刷卡时间
     * @return 秒
     */
    public static long compare2Date(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parseDate = format.parse(date);
                long time = parseDate.getTime() / 1000;
                long timeMillis = System.currentTimeMillis() / 1000;

                return timeMillis - time;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return 60;
    }
}
