package com.unengchan.sdk.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.unengchan.sdk.manager.AppManager;

import butterknife.ButterKnife;

/**
 * Created by unengchan on 2018/2/9.
 * 基类activity，做一些共同的操作
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final  String tag = this.getClass().getSimpleName();   // log的tag
    protected Context mContext; // 全局activity的上下文对象
    protected BaseActivity mActivity;
    public AppManager mAppManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // 设置布局
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData();
        initView(savedInstanceState);
    }

    /**
     * 获取当前页面的布局
     * 由子类实现
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化activity全局数据
     * 例如：上下文对象
     * 子类可以重写，初始化子类的数据
     */
    protected void initData() {
        mContext = this;
        mActivity = this;
        // 创建activity的时候入栈
        mAppManager = AppManager.getInstance();
        mAppManager.addActivity(this);
    }

    /**
     * 初始化控件、视图
     * 由子类实现
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 返回上一层界面
     */
    protected void back() {
        mAppManager.removeActivity(this);
    }

    /**
     * 跳转到指定activy
     *
     * @param cls
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 跳转到指定activy
     * 携带bundle
     *
     * @param cls
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        back();
    }
}
