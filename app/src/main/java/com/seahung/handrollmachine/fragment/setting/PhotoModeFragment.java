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
 * 拍照模式
 */
public class PhotoModeFragment extends GlobalSettingFragment {


    @BindView(R.id.tv_photo_mode_one)
    TextView tvPhotoModeOne;
    @BindView(R.id.tv_photo_mode_six)
    TextView tvPhotoModeSix;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_photo_mode_none)
    TextView tvPhotoModeNone;
    public ConfigManager mConfigManager;

    @Override
    protected void initData() {
        super.initData();
        mConfigManager = ConfigManager.getInstance();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_camera_type));
        String photoMode = mConfigManager.getPhotoMode();
        refreshPhotoModeUi(photoMode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.photo_mode_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_PHOTO_MODE_FRAGMENT;
    }

    @OnClick({R.id.tv_photo_mode_none, R.id.tv_photo_mode_one, R.id.tv_photo_mode_six, R.id.iv_title_back})
    public void onViewClicked(View view) {
        String photoMode = ConfigConstant.PHOTO_MODE_ONE_PHOTO;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_photo_mode_none:
                photoMode = ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO;
                break;
            case R.id.tv_photo_mode_one:
                photoMode = ConfigConstant.PHOTO_MODE_ONE_PHOTO;
                break;
            case R.id.tv_photo_mode_six:
                photoMode = ConfigConstant.PHOTO_MODE_SIX_PHOTO;
                break;
        }
        refreshPhotoModeUi(photoMode);
        mConfigManager.setPhotoMode(photoMode);
    }

    /**
     * 刷新界面
     *
     * @param photoMode 拍照模式
     */
    private void refreshPhotoModeUi(String photoMode) {
        int index = -1;
        switch (photoMode) {
            case ConfigConstant.PHOTO_MODE_ONE_PHOTO:
                index = 0;
                break;
            case ConfigConstant.PHOTO_MODE_SIX_PHOTO:
                index = 1;
                break;
            case ConfigConstant.PHOTO_MODE_WITHOUT_PHOTO:
                index = 2;
                break;
        }
        ViewHelper.refreshTextViews(index, tvPhotoModeOne, tvPhotoModeSix, tvPhotoModeNone);
    }
}
