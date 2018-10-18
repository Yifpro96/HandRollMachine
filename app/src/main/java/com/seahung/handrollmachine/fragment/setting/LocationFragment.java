package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
 * Created by unengchan on 2018/8/18
 */
public class LocationFragment extends GlobalSettingFragment {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_refresh)
    ImageView ivTitleRefresh;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.tv_location_open)
    TextView tvLocationOpen;
    @BindView(R.id.tv_location_close)
    TextView tvLocationClose;
    @BindView(R.id.tv_location_interval_one_second)
    TextView tvLocationIntervalOneSecond;
    @BindView(R.id.tv_location_interval_five_second)
    TextView tvLocationIntervalFiveSecond;
    @BindView(R.id.tv_location_interval_fifteen_second)
    TextView tvLocationIntervalFifteenSecond;
    @BindView(R.id.tv_location_interval_thirty_second)
    TextView tvLocationIntervalThirtySecond;
    @BindView(R.id.tv_location_interval_one_minute)
    TextView tvLocationIntervalOneMinute;
    @BindView(R.id.tv_location_interval_five_minute)
    TextView tvLocationIntervalFiveMinute;
    public ConfigManager mConfigManager;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_gps_location));
        ivTitleRefresh.setVisibility(View.INVISIBLE);

        mConfigManager = ConfigManager.getInstance();
        String gpsLocation = mConfigManager.getGpsLocation();
        refreshGpsLocationUi(gpsLocation);

        int locationInterval = mConfigManager.getLocationInterval();
        refreshLocationIntervalUi(locationInterval);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.location_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_LOCATION_FRAGMENT;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_location_open, R.id.tv_location_close,
            R.id.tv_location_interval_one_second, R.id.tv_location_interval_five_second,
            R.id.tv_location_interval_fifteen_second, R.id.tv_location_interval_thirty_second,
            R.id.tv_location_interval_one_minute, R.id.tv_location_interval_five_minute})
    public void onViewClicked(View view) {
        String gpsLocation = null;
        int locationInterval = 0;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_location_open:
                gpsLocation = ConfigConstant.GPS_LOCATION_OPEN;
                break;
            case R.id.tv_location_close:
                gpsLocation = ConfigConstant.GPS_LOCATION_CLOSE;
                break;
            case R.id.tv_location_interval_one_second:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_ONE_SECOND;
                break;
            case R.id.tv_location_interval_five_second:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_FIVE_SECOND;
                break;
            case R.id.tv_location_interval_fifteen_second:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_FIFTEEN_SECOND;
                break;
            case R.id.tv_location_interval_thirty_second:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_THIRTY_SECOND;
                break;
            case R.id.tv_location_interval_one_minute:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_ONE_MINUTE;
                break;
            case R.id.tv_location_interval_five_minute:
                locationInterval = ConfigConstant.LOCATION_TIME_INTERVAL_FIVE_MINUTE;
                break;
        }

        if (gpsLocation != null) {
            refreshGpsLocationUi(gpsLocation);
            mConfigManager.setGpsLocation(gpsLocation);
        }
        if (locationInterval != 0) {
            refreshLocationIntervalUi(locationInterval);
            mConfigManager.setLocationInterval(locationInterval);
        }
    }

    /**
     * 打开/关闭实时定位
     *
     * @param gpsLocation
     */
    private void refreshGpsLocationUi(String gpsLocation) {
        int index = -1;
        switch (gpsLocation) {
            case ConfigConstant.GPS_LOCATION_OPEN:
                index = 0;
                break;
            case ConfigConstant.GPS_LOCATION_CLOSE:
                index = 1;
                break;
        }
        ViewHelper.refreshTextViews(index, tvLocationOpen, tvLocationClose);
    }

    /**
     * 定位时间间隔的选择
     *
     * @param locationInterval
     */
    private void refreshLocationIntervalUi(int locationInterval) {
        int index = -1;
        switch (locationInterval) {
            case ConfigConstant.LOCATION_TIME_INTERVAL_ONE_SECOND:
                index = 0;
                break;
            case ConfigConstant.LOCATION_TIME_INTERVAL_FIVE_SECOND:
                index = 1;
                break;
            case ConfigConstant.LOCATION_TIME_INTERVAL_FIFTEEN_SECOND:
                index = 2;
                break;
            case ConfigConstant.LOCATION_TIME_INTERVAL_THIRTY_SECOND:
                index = 3;
                break;
            case ConfigConstant.LOCATION_TIME_INTERVAL_ONE_MINUTE:
                index = 4;
                break;
            case ConfigConstant.LOCATION_TIME_INTERVAL_FIVE_MINUTE:
                index = 5;
                break;
        }
        ViewHelper.refreshTextViews(index, tvLocationIntervalOneSecond, tvLocationIntervalFiveSecond,
                tvLocationIntervalFifteenSecond, tvLocationIntervalThirtySecond, tvLocationIntervalOneMinute, tvLocationIntervalFiveMinute);
    }

}
