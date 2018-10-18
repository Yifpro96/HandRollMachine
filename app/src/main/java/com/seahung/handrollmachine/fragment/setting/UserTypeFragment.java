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
 * 教师/学生
 */
public class UserTypeFragment extends GlobalSettingFragment {

    @BindView(R.id.tv_user_type_general)
    TextView tvUserTypeGeneral;
    @BindView(R.id.tv_user_type_teacher)
    TextView tvUserTypeTeacher;
    @BindView(R.id.tv_user_type_student)
    TextView tvUserTypeStudent;
    public ConfigManager mConfigManager;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        String userType = mConfigManager.getUserType();
        refreshUserTypeUi(userType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_type_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_USER_TYPE_FRAGMENT;
    }

    @OnClick({R.id.tv_user_type_general, R.id.tv_user_type_teacher, R.id.tv_user_type_student})
    public void onViewClicked(View view) {
        String userType = ConfigConstant.USER_TYPE_GENERAL;
        switch (view.getId()) {
            case R.id.tv_user_type_general:
                userType = ConfigConstant.USER_TYPE_GENERAL;
                break;
            case R.id.tv_user_type_teacher:
                userType = ConfigConstant.USER_TYPE_TEACHER;
                break;
            case R.id.tv_user_type_student:
                userType = ConfigConstant.USER_TYPE_STUDENT;
                break;
        }
        refreshUserTypeUi(userType);
        mConfigManager.setUserType(userType);
    }

    /**
     * 刷新界面
     *
     * @param userType
     */
    private void refreshUserTypeUi(String userType) {
        int index = -1;
        switch (userType) {
            case ConfigConstant.USER_TYPE_GENERAL:
                index = 0;
                break;
            case ConfigConstant.USER_TYPE_STUDENT:
                index = 1;
                break;
            case ConfigConstant.USER_TYPE_TEACHER:
                index = 2;
                break;
        }
        ViewHelper.refreshTextViews(index, tvUserTypeGeneral, tvUserTypeStudent, tvUserTypeTeacher);
    }
}
