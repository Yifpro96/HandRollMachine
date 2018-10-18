package com.seahung.handrollmachine.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.seahung.handrollmachine.activity.MainActivity;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.LogUtils;
import com.unengchan.sdk.util.SDCardUtils;
import com.unengchan.sdk.util.StringUtils;

import java.io.File;
import java.util.Random;

/**
 * Created by unengchan on 2018/5/24
 * 设备帮助类，获取os,ismi,手机状态等信息
 */
public class DeviceUtils {

    // 当前使用APN数据库位置
    private static final Uri APN_PREFER_URI = Uri.parse("content://telephony/carriers/preferapn");

    private DeviceUtils() {
    }

    /**
     * 截取图片
     *
     * @return
     */
    public static String captureScreen() {
        // 获取内置SD卡路径
        String sdCardPath = SDCardUtils.getExternalCacheDir("Screenshot");
        // 图片文件路径
        String filePath = sdCardPath + File.separator + "screenshot.png";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "screencap -p " + filePath});
            process.waitFor();
        } catch (Exception e) {
            LogUtils.d(DeviceUtils.class.getSimpleName(), e.getMessage());
        }
        return filePath;
    }

    /**
     * 重启
     */
    public static void reboot() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"});
            process.waitFor();
        } catch (Exception e) {
            // 调用重启异常
            LogUtils.d(DeviceUtils.class.getSimpleName(), e.getMessage());
        }
    }

    /**
     * 关机
     */
    public static void shutdown() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
            process.waitFor();
        } catch (Exception e) {
            // 调用关机异常
            LogUtils.d(DeviceUtils.class.getSimpleName(), e.getMessage());
        }
    }

    /**
     * 获取分辨率
     *
     * @return
     */
    public static String getResolution() {
        WindowManager wm = (WindowManager) AppUtils.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return String.valueOf(dm.heightPixels) + "x" + String.valueOf(dm.widthPixels);
    }

    /**
     * 获取apn
     *
     * @return
     */
    public static String getApn() {
        String apn = "unknown";
        Cursor cursor = null;
        try {
            cursor = AppUtils.getAppContext().getContentResolver()
                    .query(APN_PREFER_URI,
                            new String[]{"_id", "apn", "name", "type"},
                            null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                apn = cursor.getString(cursor.getColumnIndex("apn"));
            }
        } catch (Exception e) {
            return apn;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return apn;
    }

    /**
     * 获取imei
     *
     * @return
     */
    public static String getImei() {
        Context context = AppUtils.getAppContext();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限，不获取imei
            return "unknown";
        }
        String deviceId = manager.getDeviceId();
        if (StringUtils.isEmpty(deviceId)) {
            return "unknown";
        }
        return deviceId;
    }

    /**
     * 获取imsi
     *
     * @return
     */

    public static String getImsi() {
        Context context = AppUtils.getAppContext();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限
            return "unknown";
        }
        String imsi = manager.getSubscriberId();
        if (StringUtils.isEmpty(imsi)) {
            return "unknown";
        }
        return imsi;
    }

    /**
     * 获取MAC地址
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        String mac;
        WifiManager wm = ((WifiManager) ((Context) AppUtils.getAppContext()).getSystemService(Context.WIFI_SERVICE));
        WifiInfo wi = wm.getConnectionInfo();
        // 从WIFI连接中获取MAC地址
        mac = wi.getMacAddress();
        if (TextUtils.isEmpty(mac)) {
            // 随机生成MAC地址
            mac = createMacAddress();
        } else {
            mac = mac.toUpperCase();
        }
        return mac;
    }


    /**
     * 生成随机的MAC地址
     *
     * @return MAC地址
     */
    private static String createMacAddress() {
        String sRandom = "ABCDEF0123456789";
        Random random = new Random();
        StringBuffer sbMac = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sbMac.append(sRandom.charAt(random.nextInt(16)));
            sbMac.append(sRandom.charAt(random.nextInt(16)));
            sbMac.append(":");
        }
        String sMac = sbMac.toString();
        return sMac.substring(0, sMac.length() - 1).toUpperCase();
    }

    /**
     * 获取运营商
     *
     * @return
     */
    public static String getOperatorName() {
        String imsi = getImsi();
        if (imsi.startsWith("46001")) {
            return "中国联通";
        }
        if (imsi.startsWith("46003")) {
            return "中国电信";
        }
        if (!"unknown".equals(imsi)) {
            return "中国移动";
        }
        return "unknown";
    }

    /**
     * 获取手机制式
     *
     * @return
     */
    public static String getPhoneType() {
        TelephonyManager manager = (TelephonyManager) AppUtils.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = manager.getPhoneType();
        if (TelephonyManager.PHONE_TYPE_GSM == phoneType) {
            return "GSM";
        }
        if (TelephonyManager.PHONE_TYPE_CDMA == phoneType) {
            return "CDMA";
        }
        return "unknown";
    }

    /**
     * 获取漫游状态
     *
     * @return
     */
    public static String getRoamStatus() {
        TelephonyManager manager = (TelephonyManager) AppUtils.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.isNetworkRoaming()) {
            return "Y";
        }
        return "N";
    }


    /**
     * 获取wifi状态，
     * 连接 “Y"、未连接“N”
     *
     * @return
     */
    public static String getWifiStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppUtils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return "Y";
        }
        return "N";
    }


    /**
     * 获取手机通信技术
     *
     * @return 手机通信技术
     */
    public static String getGeneration() {
        String sGeneration;
        TelephonyManager tm = (TelephonyManager) AppUtils.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getNetworkType() == 1 || tm.getNetworkType() == 2 || tm.getNetworkType() == 4 || tm.getNetworkType() == 7 || tm.getNetworkType() == 11) {
            sGeneration = "2G";
        } else if (tm.getNetworkType() == 3 || tm.getNetworkType() == 5 || tm.getNetworkType() == 6 || tm.getNetworkType() == 8 || tm.getNetworkType() == 9
                || tm.getNetworkType() == 10 || tm.getNetworkType() == 12 || tm.getNetworkType() == 14 || tm.getNetworkType() == 15) {
            sGeneration = "3G";
        } else if (tm.getNetworkType() == 13) {
            sGeneration = "4G";
        } else {
            sGeneration = "unknown";
        }
        return sGeneration;
    }

    /**
     * 获取手机网络类型
     *
     * @return 网络类型
     */
    public static String getNetworkType() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        String sNetworkType;
        if (networkType == 1) {
            sNetworkType = "GPRS";
        } else if (networkType == 2) {
            sNetworkType = "EDGE";
        } else if (networkType == 3) {
            sNetworkType = "UMTS";
        } else if (networkType == 4) {
            sNetworkType = "CDMA";
        } else if (networkType == 5) {
            sNetworkType = "CDMA - EvDo rev. 0";
        } else if (networkType == 6) {
            sNetworkType = "CDMA - EvDo rev. A";
        } else if (networkType == 7) {
            sNetworkType = "CDMA - 1xRTT";
        } else if (networkType == 8) {
            sNetworkType = "HSDPA";
        } else if (networkType == 9) {
            sNetworkType = "HSUPA";
        } else if (networkType == 10) {
            sNetworkType = "HSPA";
        } else if (networkType == 11) {
            sNetworkType = "iDEN";
        } else if (networkType == 12) {
            sNetworkType = "CDMA - EvDo rev. B";
        } else if (networkType == 13) {
            sNetworkType = "LTE";
        } else if (networkType == 14) {
            sNetworkType = "CDMA - eHRPD";
        } else if (networkType == 15) {
            sNetworkType = "HSPA+";
        } else {
            sNetworkType = "unknown";
        }
        return sNetworkType;
    }

    /**
     * 获取设备厂商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

}
