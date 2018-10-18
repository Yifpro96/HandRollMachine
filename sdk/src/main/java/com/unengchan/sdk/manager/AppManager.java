package com.unengchan.sdk.manager;

import com.unengchan.sdk.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Created by unengchan on 2018/2/9.
 * activity 的堆栈管理
 * 使用弱引用实现，有效避免内存溢出
 */

public class AppManager {
    private Stack<WeakReference<BaseActivity>> mStack;

    private AppManager() {
    }

    // 静态内部类的单例模式
    private static class AppManagerHolder {
        private static final AppManager INSTANCE = new AppManager();
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static AppManager getInstance() {
        return AppManagerHolder.INSTANCE;
    }

    /**
     * activity进栈
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        if (mStack == null) {
            mStack = new Stack<>();
        }
        // 弱引用包裹的activity
        WeakReference<BaseActivity> activityWeakReference = new WeakReference<>(activity);
        mStack.add(activityWeakReference);
    }

    /**
     * activity出栈
     * 同时结束指定类名的activity
     *
     * @param cls 类对象
     */
    public void removeActivity(Class<?> cls) {
        ListIterator<WeakReference<BaseActivity>> iterator = mStack.listIterator();
        while (iterator.hasNext()) {
            BaseActivity activity = iterator.next().get();
            if (activity == null) {
                iterator.remove();
                continue;
            }
            if (activity.getClass() == cls) {
                activity.finish();
                iterator.remove();
                break;
            }
        }
    }

    /**
     * activity出栈
     * 同时结束指定类名的activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        ListIterator<WeakReference<BaseActivity>> iterator = mStack.listIterator();
        while (iterator.hasNext()) {
            BaseActivity activity1 = iterator.next().get();
            if (activity == null) {
                iterator.remove();
                continue;
            }
            if (activity1 == activity) {
                activity.finish();
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 判断栈里面是否存在指定的activity
     *
     * @param cls 指定activity对象
     * @return true：存在；false：不存在
     */
    public boolean existActivity(Class<?> cls) {
        ListIterator<WeakReference<BaseActivity>> iterator = mStack.listIterator();
        while (iterator.hasNext()) {
            BaseActivity activity = iterator.next().get();
            if (activity == null) {
                iterator.remove();
                continue;
            }
            if (activity.getClass() == cls) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有activity
     */
    public void removeAllActivity() {
        for (int i = 0; i < mStack.size(); i++) {
            BaseActivity activity = mStack.get(i).get();
            if (activity != null) {
                activity.finish();
            }
        }
        mStack.clear();
    }

    /**
     * 获取入栈的activity的数量
     *
     * @return
     */
    public int getStackSize() {
        if (mStack == null) {
            return 0;
        }
        return mStack.size();
    }
}
