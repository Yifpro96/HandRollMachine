package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.global.GlobalSettingFragment;

/**
 * Created by unengchan on 2018/6/5
 * 班级id的设置
 */
public class ClassIdFragment extends GlobalSettingFragment {
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.class_id_fragment;
    }

    @Override
    protected int getIndex() {
        return 0;
    }
}
