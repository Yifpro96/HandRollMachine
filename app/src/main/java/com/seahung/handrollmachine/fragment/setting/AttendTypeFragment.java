package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
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
 * 考勤方式
 */
public class AttendTypeFragment extends GlobalSettingFragment implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.tv_attend_type_card)
    TextView tvAttendTypeCard;
    @BindView(R.id.tv_attend_type_face)
    TextView tvAttendTypeFace;
    @BindView(R.id.tv_attend_type_card_face)
    TextView tvAttendTypeCardFace;
    @BindView(R.id.tv_face_semblance)
    TextView tvFaceSemblance;
    @BindView(R.id.seek_bar_semblance)
    SeekBar seekBarSemblance;
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
        tvTitleName.setText(mContext.getString(R.string.setting_attend_type));
        seekBarSemblance.setOnSeekBarChangeListener(this);
        String attendType = mConfigManager.getAttendType();
        int faceSemblance = mConfigManager.getFaceSemblance();
        tvFaceSemblance.setText(faceSemblance/10f+"%");
        seekBarSemblance.setProgress(faceSemblance);
        refreshAttendTypeUi(attendType);
    }

    /**
     * 刷新考勤方式Ui
     *
     * @param attendType
     */
    private void refreshAttendTypeUi(String attendType) {
        int index = -1;
        switch (attendType) {
            case ConfigConstant.ATTEND_TYPE_CARD:
                index = 0;
                break;
            case ConfigConstant.ATTEND_TYPE_FACE:
                index = 1;
                break;
            case ConfigConstant.ATTEND_TYPE_CARD_FACE:
                index = 2;
                break;
        }
        ViewHelper.refreshTextViews(index, tvAttendTypeCard, tvAttendTypeFace, tvAttendTypeCardFace);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.attend_type_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_ATTEND_TYPE_FRAGMENT;
    }

    @OnClick({R.id.tv_attend_type_card, R.id.tv_attend_type_face, R.id.tv_attend_type_card_face,R.id.iv_title_back})
    public void onViewClicked(View view) {
        String attendType=ConfigConstant.ATTEND_TYPE_CARD;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_attend_type_card:
                attendType = ConfigConstant.ATTEND_TYPE_CARD;
                break;
            case R.id.tv_attend_type_face:
                attendType = ConfigConstant.ATTEND_TYPE_FACE;
                break;
            case R.id.tv_attend_type_card_face:
                attendType = ConfigConstant.ATTEND_TYPE_CARD_FACE;
                break;
        }
        refreshAttendTypeUi(attendType);
        mConfigManager.setAttendType(attendType);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvFaceSemblance.setText(progress/10f+"%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mConfigManager.setFaceSemblance(progress);
    }
}
