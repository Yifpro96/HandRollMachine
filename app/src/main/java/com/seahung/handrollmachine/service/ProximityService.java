package com.seahung.handrollmachine.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.common.pos.api.util.PosUtil;

import java.io.DataOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by unengchan on 2018/7/20
 */
public class ProximityService extends Service {

    private Timer mTimer = null;
    private MyTimerTask mTask = null;
    private static final String TAG = "WINDOW";
    private int mDelayTime = 1000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mTask == null) {
            mTask = new MyTimerTask();
        }
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(mTask, 0, mDelayTime);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTask = null;
        mTimer = null;
        Log.i(TAG, "service is onDestroy");
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            int ret = PosUtil.getPriximitySensorStatus();
            switch (ret) {
                case 1:
                    String cmd = "echo 255 > /sys/class/backlight/rk28_bl/brightness";
                    try {
                        Process p = Runtime.getRuntime().exec("su");
                        DataOutputStream os = new DataOutputStream(p.getOutputStream());
                        os.writeBytes(cmd + "\n");
                        os.writeBytes("exit\n");
                        os.flush();
                        p.waitFor();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 默认关掉LED
                    PosUtil.setLedPower(0);
                    // 检测到有人的时候，10分钟再检测
                    mDelayTime = 10 * 60 * 1000;
                    // 取消定时器
                    mTimer.cancel();
                    mTask = new MyTimerTask();
                    mTimer = new Timer();
                    mTimer.schedule(mTask, mDelayTime,mDelayTime);

                    break;
                case 0:

                    // 默认关掉LED
                    PosUtil.setLedPower(0);

                    String cmd1 = "echo 0 > /sys/class/backlight/rk28_bl/brightness";
                    try {
                        Process p1 = Runtime.getRuntime().exec("su");
                        DataOutputStream os1 = new DataOutputStream(p1.getOutputStream());
                        os1.writeBytes(cmd1 + "\n");
                        os1.writeBytes("exit\n");
                        os1.flush();
                        p1.waitFor();
                        mDelayTime = 1000;

                        // 取消定时器
                        mTimer.cancel();
                        mTimer = new Timer();
                        mTask = new MyTimerTask();
                        mTimer.schedule(mTask, 0,mDelayTime);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    mDelayTime = 1000;
                    // 取消定时器
                    mTimer.cancel();
                    mTimer = new Timer();
                    mTask = new MyTimerTask();
                    mTimer.schedule(mTask, mDelayTime,mDelayTime);
                    break;
            }
        }
    }
}
