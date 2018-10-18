package com.seahung.handrollmachine.runnable;

import android.widget.TextView;

import com.seahung.handrollmachine.helper.RealmHelper;
import com.seahung.handrollmachine.util.TimeUtils;
import com.unengchan.sdk.util.AppUtils;
import com.unengchan.sdk.util.SPUtils;

/**
 * Created by unengchan on 2018/7/17
 */
public class RefreshTimeRunnable implements Runnable {

    private TextView mTv;

    public RefreshTimeRunnable(TextView tv) {
        mTv = tv;
    }

    @Override
    public void run() {
        String currentTime = TimeUtils.getCurrentTime("yyyy-MM-dd  EEEE  HH:mm:ss");
        if (mTv != null) {
            mTv.setText(currentTime);
            AppUtils.getHandler().postDelayed(this, 1000);
        }

        // 判断是不是超过一天了
        if (currentTime.endsWith("00:00:01")) {
            RealmHelper.clearSwipeCardData();
        }else {
            // 每天启动的时候，检查当前日期
            String currentDate = (String) SPUtils.getValue("current_date", "0");
            if (currentTime.startsWith(currentDate)) {

            }else {
                SPUtils.putValue("current_date", currentTime.substring(0, 10));
                RealmHelper.clearSwipeCardData();
            }
        }

    }
}
