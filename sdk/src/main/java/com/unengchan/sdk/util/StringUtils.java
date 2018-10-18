package com.unengchan.sdk.util;

/**
 * Created by unengchan on 2018/5/4
 * 字符串的辅助类
 */
public class StringUtils {

    /**
     * 判断是否为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

}
