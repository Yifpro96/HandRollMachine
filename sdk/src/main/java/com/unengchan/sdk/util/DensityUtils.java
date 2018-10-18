package com.unengchan.sdk.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * Created by unengchan on 2017/11/10.
 * 屏幕尺寸，单位转换帮助类
 */

public class DensityUtils {

    /**
     * 私有的构造方法，不能够实例化对象
     */
    private DensityUtils() {
    }

    /**
     * 获取屏幕分辨率大小，例如720*1280
     * @param activity
     * @return
     */
    public static Point getScreenDisplay(Activity activity) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        } else {
            activity.getWindowManager().getDefaultDisplay().getSize(point);
        }
        return point;
    }

    /**
     * 获取屏幕的英寸数，多少英寸
     * @param activity
     * @return
     */
    public static double getScreenInches(Activity activity) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        }else {
            activity.getWindowManager().getDefaultDisplay().getSize(point);
        }
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    /**
     * 获取状态栏的高度，即屏幕顶部显示信号、电量等信息的高度
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(){
        int height = -1;
        //获取status_bar_height资源的ID
        Resources resources = AppUtils.getAppContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = resources.getDimensionPixelSize(resourceId);
        }else {
            // 根据R类的反射获取状态栏的高度
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int barHeight = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                height = resources.getDimensionPixelSize(barHeight);
            } catch (Exception e) {
                LogUtils.d(DensityUtils.class.getSimpleName(),e.getMessage());
            }
        }
        return height;
    }

    /**
     * 获取状态栏的高度，即屏幕顶部显示信号、电量等信息的高度
     * @return 状态栏高度
     */
    public static int getNavigationBarHeight(){
        int height = -1;
        //获取status_bar_height资源的ID
        Resources resources = AppUtils.getAppContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = resources.getDimensionPixelSize(resourceId);
        }else {
            // 根据R类的反射获取状态栏的高度
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int barHeight = Integer.parseInt(clazz.getField("navigation_bar_height")
                        .get(object).toString());
                height = resources.getDimensionPixelSize(barHeight);
            } catch (Exception e) {
                LogUtils.d(DensityUtils.class.getSimpleName(),e.getMessage());
            }
        }
        return height;
    }

    /**
     * dp转px
     * @param dpValue    传入的dp值
     * @return
     */
    public static int dp2px(float dpValue) {
        float scale = AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param pxValue    传入的px值
     * @return
     */
    public static int px2dp(float pxValue) {
        float scale = AppUtils.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     * @param spValue    传入的sp的值
     * @return
     */
    public static int sp2px(float spValue) {
        float fontScale = AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * px转sp
     * @param pxValue    传入的px的值
     * @return
     */
    public static float px2sp(float pxValue) {
        float fontScale = AppUtils.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
