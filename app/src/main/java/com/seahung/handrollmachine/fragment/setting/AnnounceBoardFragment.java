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
 * 屏保公告栏
 */
public class AnnounceBoardFragment extends GlobalSettingFragment {


    @BindView(R.id.tv_announce_board_open)
    TextView tvAnnounceBoardOpen;
    @BindView(R.id.tv_announce_board_close)
    TextView tvAnnounceBoardClose;
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
        tvTitleName.setText(mContext.getString(R.string.setting_announce_board));
        String announceBoard = mConfigManager.getAnnounceBoard();
        refreshAnnounceBoardUi(announceBoard);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.announce_board_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_ANNOUNCE_BOARD_FRAGMENT;
    }

    @OnClick({R.id.tv_announce_board_open, R.id.tv_announce_board_close,R.id.iv_title_back})
    public void onViewClicked(View view) {
        String announceBoard;
        switch (view.getId()) {
            case R.id.iv_title_back:
                mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
                return;
            case R.id.tv_announce_board_open:
                announceBoard = ConfigConstant.ANNOUNCE_BOARD_OPEN;
                break;
            case R.id.tv_announce_board_close:
                announceBoard = ConfigConstant.ANNOUNCE_BOARD_CLOSE;
                break;
            default:
                announceBoard = ConfigConstant.ANNOUNCE_BOARD_OPEN;
                break;
        }
        refreshAnnounceBoardUi(announceBoard);
        mConfigManager.setAnnounceBoard(announceBoard);
    }

    /**
     * 刷新屏保公告的选择ui
     *
     * @param announceBoard
     */
    private void refreshAnnounceBoardUi(String announceBoard) {
        int index = -1;
        switch (announceBoard) {
            case ConfigConstant.ANNOUNCE_BOARD_OPEN:
                index = 0;
                break;
            case ConfigConstant.ANNOUNCE_BOARD_CLOSE:
                index = 1;
                break;
        }
        ViewHelper.refreshTextViews(index, tvAnnounceBoardOpen, tvAnnounceBoardClose);
    }
}
