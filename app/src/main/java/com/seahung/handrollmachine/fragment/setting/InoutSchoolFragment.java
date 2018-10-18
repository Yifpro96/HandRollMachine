package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.helper.ViewHelper;
import com.seahung.handrollmachine.manager.ConfigManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 出校/入校
 */
public class InoutSchoolFragment extends GlobalSettingFragment {
    @BindView(R.id.tv_inout_school_general)
    TextView tvInoutSchoolGeneral;
    @BindView(R.id.tv_inout_school_out)
    TextView tvInoutSchoolOut;
    @BindView(R.id.tv_inout_school_in)
    TextView tvInoutSchoolIn;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    public ConfigManager mConfigManager;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_inout_school));
        String inoutSchool = mConfigManager.getInoutSchool();
        refreshInoutSchoolUi(inoutSchool);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.inout_school_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_INOUT_SCHOOL_FRAGMENT;
    }

    @OnClick({R.id.tv_inout_school_general, R.id.tv_inout_school_out, R.id.tv_inout_school_in,R.id.iv_title_back})
    public void onViewClicked(View view) {
        String inoutSchool;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_inout_school_general:
                inoutSchool = ConfigConstant.INOUT_SCHOOL_GENERAL;
                break;
            case R.id.tv_inout_school_out:
                inoutSchool = ConfigConstant.INOUT_SCHOOL_OUT;
                break;
            case R.id.tv_inout_school_in:
                inoutSchool = ConfigConstant.INOUT_SCHOOL_IN;
                break;
            default:
                inoutSchool = ConfigConstant.INOUT_SCHOOL_GENERAL;
                break;
        }
        refreshInoutSchoolUi(inoutSchool);
        mConfigManager.setInoutSchool(inoutSchool);
    }

    /**
     * 出校入校设置，ui更新
     *
     * @param inoutSchool
     */
    private void refreshInoutSchoolUi(String inoutSchool) {
        int index = -1;
        switch (inoutSchool) {
            case ConfigConstant.INOUT_SCHOOL_GENERAL:
                index = 0;
                break;
            case ConfigConstant.INOUT_SCHOOL_IN:
                index = 1;
                break;
            case ConfigConstant.INOUT_SCHOOL_OUT:
                index = 2;
                break;
        }
        ViewHelper.refreshTextViews(index, tvInoutSchoolGeneral, tvInoutSchoolIn, tvInoutSchoolOut);
    }
}
