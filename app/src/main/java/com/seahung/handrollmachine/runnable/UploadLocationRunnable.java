package com.seahung.handrollmachine.runnable;

import android.os.SystemClock;

import com.seahung.handrollmachine.bean.GpsInfo;
import com.seahung.handrollmachine.manager.ConfigManager;
import com.seahung.handrollmachine.request.UploadLocationRequest;
import com.seahung.handrollmachine.response.BaseResponse;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.TimeUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.unengchan.sdk.util.StringUtils;

import java.util.List;

/**
 * Created by unengchan on 2018/8/20
 * 上传位置信息
 */
public class UploadLocationRunnable implements Runnable {

    private boolean stop;
    private List<GpsInfo> mGpsInfos;
    private long delaySeconds = 5;
    private UploadLocationListener mListener;

    public interface UploadLocationListener {
        // 当errorcode不为0的时候，errorstring 有效
        void onUploadResult(int errorCode, String errorString);
    }

    public UploadLocationRunnable(UploadLocationListener listener) {
        mListener = listener;
    }

    /**
     * 定时刷新h5页面回调
     */
    public interface IntervalRefreshH5Listener{
        void onIntervalRefreshH5();
    }
    public void setGpsInfos(List<GpsInfo> gpsInfos) {
        mGpsInfos = gpsInfos;
    }

    @Override
    public void run() {

        while (!stop) {
            // 默认延时
            if (mGpsInfos == null || mGpsInfos.size() == 0) {
                SystemClock.sleep(delaySeconds * 1000);
                continue;
            }
            GpsInfo gpsInfo = mGpsInfos.get(0);
            ConfigManager configManager = ConfigManager.getInstance();
            UploadLocationRequest request = new UploadLocationRequest();
            request.setKqTerminalId(configManager.getTerminalId());
            request.setSchoolId(configManager.getSchoolId());
            request.setReportTime(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));

            request.setGpsTime(gpsInfo.getGpsTime());
            request.setLatitude(gpsInfo.getLatitude());
            request.setLongitude(gpsInfo.getLongitude());
            request.setAltitude(gpsInfo.getAltitude());
            request.setSpeed(gpsInfo.getSpeed());
            request.setBearing(gpsInfo.getDirection());
            request.setAddress(gpsInfo.getAddress());
            request.setGpsTime(gpsInfo.getGpsTime());

            String serviceUrl = HttpUtils.getServiceUrl(request.getTrans_code(), request.getAction());
            if (StringUtils.isEmpty(serviceUrl)) {
                continue;
            }
            String xmlStr = HttpUtils.doPost(serviceUrl, request.toXMLString());
            BaseResponse baseResponse = XmlHandlerUtils.getBaseResponse(xmlStr);
            if (baseResponse == null || StringUtils.isNotEmpty(baseResponse.getError_code())) {
                continue;
            }
            String delaySeconds = baseResponse.getDelayMillis();

            String redirectUrl = baseResponse.getRedirect_url();
            if (StringUtils.isEmpty(redirectUrl)) {
                redirectUrl = serviceUrl;
            }
            xmlStr = HttpUtils.doPost(redirectUrl, request.toXMLString());
//            UploadLocationResponse response = XmlHandlerUtils.getUploadLocationResponse(xmlStr);
//            String uploadDuration = response.getNextUploadDuration();
            if (StringUtils.isEmpty(delaySeconds)) {
                delaySeconds = "5";
            }
            this.delaySeconds = Integer.parseInt(delaySeconds);
            // 上传成功后
            mGpsInfos.remove(gpsInfo);
            if (mListener != null) {
                mListener.onUploadResult(0, "上传成功");
            }
        }

    }

    /**
     * 停止线程
     */
    public void stop() {
        stop = true;
        mListener = null;
    }
}
