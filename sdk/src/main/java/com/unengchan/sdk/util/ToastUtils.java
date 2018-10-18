package com.unengchan.sdk.util;

import android.content.Context;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by unengchan on 2018/2/9.
 * toast提示语辅助类
 */

public class ToastUtils {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private ToastUtils() {
    }

    /**
     * 显示toast信息
     *
     * @param msg 要显示的信息
     */
    private static void show(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppUtils.getAppContext(), msg, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
        }
        oneTime = twoTime;
    }


    /**
     * 显示toast信息,短时间
     *
     * @param resId 在res文件夹里面的values文件夹下的strings文件里面的id
     */
    public static void show(int resId) {
        show(AppUtils.getAppContext().getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast信息，短时间
     *
     * @param msg 要显示信息内容
     */
    public static void show(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast信息，长时间
     *
     * @param resId 在res文件夹里面的values文件夹下的strings文件里面的id
     */
    public static void showLong(int resId) {
        show(AppUtils.getAppContext().getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 显示toast信息，长时间
     *
     * @param msg 要显示的信息的内容
     */
    public static void showLong(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        show(msg, Toast.LENGTH_LONG);
    }


    /**
     * 显示弹窗
     * @param context 不能使用appContext，必须使用activity的context
     * @param msg
     * @return
     */
    public static KProgressHUD showHud(Context context , String msg) {
        KProgressHUD hud = KProgressHUD.create(context)
                .setLabel(msg)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .setAnimationSpeed(1)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .show();
        return hud;
    }

}
