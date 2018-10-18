package com.seahung.handrollmachine.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.seahung.handrollmachine.manager.ConfigManager;
import com.seahung.handrollmachine.request.QpushRequest;
import com.seahung.handrollmachine.response.BaseResponse;
import com.seahung.handrollmachine.response.QpushResponse;
import com.seahung.handrollmachine.runnable.SyncUserDataRunnable;
import com.seahung.handrollmachine.util.HttpUtils;
import com.seahung.handrollmachine.util.XmlHandlerUtils;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.StringUtils;

/**
 * Created by unengchan on 2018/7/23
 * qpush 推送的服务
 */
public class QpushService extends Service {
    private int qpush_delay_seconds = 5;
    public QpushThread mQpushThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mQpushThread = new QpushThread();
        mQpushThread.setStop(false);
        AppUtils.getThreadPool().execute(mQpushThread);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQpushThread.setStop(true);
    }


    /**
     * qpush线程
     */
    private class QpushThread extends Thread {
        private volatile boolean stop;

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
//                LogUtils.d(QpushService.class.getSimpleName(), "-----qpushservice------------");

                SystemClock.sleep(qpush_delay_seconds * 1000);
                QpushRequest request = new QpushRequest();
                request.setKqTerminalId(ConfigManager.getInstance().getTerminalId());
                String url = HttpUtils.getServiceUrl(request.getTrans_code(), request.getAction());
                if (TextUtils.isEmpty(url)) {
                    continue;
                }
                // 重定向获得最终结果
                String xmlStr = HttpUtils.doPost(url, request.toXMLString());
                BaseResponse baseResponse = XmlHandlerUtils.getBaseResponse(xmlStr);
                if (baseResponse != null) {
                    String redirectUrl = baseResponse.getRedirect_url();
                    if (StringUtils.isNotEmpty(redirectUrl)) {
                        xmlStr = HttpUtils.doPost(redirectUrl, request.toXMLString());
                    }
                }
                // 解析xml
                if (StringUtils.isEmpty(xmlStr)) {
                    continue;
                }
                QpushResponse qpushResponse = XmlHandlerUtils.getQpushResponse(xmlStr);
                if (qpushResponse == null) {
                    continue;
                }
                String delaySeconds = qpushResponse.getNext_qpush_delay_seconds();
                if (TextUtils.isEmpty(delaySeconds)) {
                    qpush_delay_seconds = 60;
                } else {
                    qpush_delay_seconds = Integer.parseInt(delaySeconds) + 1;
                }

                String cmd = qpushResponse.getCmd();
//                LogUtils.d(QpushService.class.getSimpleName(), cmd);
                if (cmd == null) {
                    continue;
                }
                switch (cmd) {
//                    case "cplate_restart":
//                        UploadManager.uploadCaptureScreenImage(FileUtil.getSaveShutdownImagePath());
//                        DeviceUtil.reboot();
//                        break;
//                    case "cplate_turn_off":
//                        UploadManager.uploadCaptureScreenImage(FileUtil.getSaveShutdownImagePath());
//                        DeviceUtil.shutdown();
//                        break;
//                    case "cplate_query_device_opcl_time":
//                        // 获取定时开关机的数据
//                        queryDeviceOpenCloseTime();
//                        break;
//                    case "ui_show":
//                        queryExamData(qpushResponse.getAction(), qpushResponse.getAction_parameter());
//                        break;
                    case "rollm_sync_user_info":
                        SyncUserDataRunnable syncUserDataRunnable = new SyncUserDataRunnable();
                        syncUserDataRunnable.run();
//                        syncUserData();
                        break;
                    default:
                        break;
                }

            }
        }
    }


}
