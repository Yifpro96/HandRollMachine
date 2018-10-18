package com.unengchan.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by unengchan on 2018/3/1.
 * 网络相关辅助类
 */

public class NetworkUtils {

    private NetworkUtils(){}

    public interface NetworkCallbackListener {
        void onNetworkResponse(boolean response);
    }

    /**
     * 判断网络是否打开，同时判断连接的网络是否可用
     */
    public static void isAvailable(final NetworkCallbackListener callback) {

        ConnectivityManager manager = (ConnectivityManager) AppUtils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            callback.onNetworkResponse(false);
            return;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            callback.onNetworkResponse(false);
            return;
        }
        AppUtils.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean available = false;
                Runtime runtime = Runtime.getRuntime();
                try {
                    // ping 百度地址 检测网络是否可用
                    Process p = runtime.exec("ping -c 3 www.baidu.com");
                    int ret = p.waitFor();
                    // 网络可用：ret=0;
                    // 不可用：ret=2
                    // 需要验证的wifi下：ret=1
                    if (ret == 0) {
                        available = true;
                    }
                } catch (Exception e) {
                   available = false;
                } finally {
                    runtime.gc();
                    callback.onNetworkResponse(available);
//                    final boolean finalAvailable = available;
//                    AppUtils.getHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onResponse(finalAvailable);
//                        }
//                    });
                }
            }
        });

    }
}
