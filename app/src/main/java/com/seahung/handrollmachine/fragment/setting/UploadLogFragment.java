package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;

/**
 * Created by unengchan on 2018/6/5
 * 上传日志
 */
public class UploadLogFragment extends GlobalSettingFragment {
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.upload_log_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_UPLOAD_LOG_FRAGMENT;
    }
}
