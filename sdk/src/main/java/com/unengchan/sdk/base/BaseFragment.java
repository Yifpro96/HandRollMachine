package com.unengchan.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by unengchan on 2018/2/9.
 * 基类fragment
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Unbinder bind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this,view);
        initData();
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * 初始化数据
     * 子类也可以重写，初始化自己的数据
     */
    protected void initData() {}

    /**
     * 初始化view
     * 由子类实现
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 当前fragment的布局id
     * 由子类实现
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 获取自己的索引
     * @return
     */
    protected abstract int getIndex();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
