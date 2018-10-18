package com.unengchan.sdk.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by unengchan on 2018/7/20
 * handler 的基类
 */
public abstract class BaseHandler<T extends BaseActivity> extends Handler {

    private final WeakReference<T> mActivityWeakReference;

    public BaseHandler(T activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        T activity = mActivityWeakReference.get();
        if (activity == null) {
            return;
        }
        onHandleMessage(activity,msg);
    }

    protected abstract void onHandleMessage(T acivity, Message msg);
}
