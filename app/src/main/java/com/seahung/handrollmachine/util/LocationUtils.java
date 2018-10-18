package com.seahung.handrollmachine.util;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.unengchan.sdk.util.AppUtils;

/**
 * Created by unengchan on 2018/5/22
 * 高德定位辅助类
 */
public class LocationUtils {

    public static AMapLocationClient mLocationClient;
    public static AMapLocationClientOption mLocationOption;
    public static int locationInterval = 15000;

    private LocationUtils() {
    }

    /**
     * 初始化定位
     *
     * @return
     */
    public static void initLocationClient() {
        if (mLocationClient != null) {
            return;
        }
        mLocationClient = new AMapLocationClient(AppUtils.getAppContext());
        mLocationOption = getDefaultOption();
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * 定位参数配置
     *
     * @return
     */
    private static AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        option.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        option.setHttpTimeOut(8000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        option.setInterval(locationInterval);//可选，设置定位间隔。默认为2秒
        option.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        option.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        option.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        option.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        option.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        option.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        option.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return option;
    }

    /**
     * 启动定位
     * @param locationListener
     */
    public static void startLocation(AMapLocationListener locationListener) {
        if (mLocationClient != null) {
            mLocationClient.setLocationListener(locationListener);
            mLocationClient.startLocation();
        }
    }

    /**
     * 设置定位间隔
     * @param time
     */
    public static void setLocationInterval(int time){
        locationInterval = time;
    }

    /**
     * 停止并销毁定位
     */
    public static void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }
}
