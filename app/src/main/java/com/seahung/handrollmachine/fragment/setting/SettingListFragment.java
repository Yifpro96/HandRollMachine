package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.adapter.setting.SettingListAdapter;
import com.seahung.handrollmachine.bean.setting.SettingItem;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/7/12
 */
public class SettingListFragment extends GlobalSettingFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_setting)
    RecyclerView rvSetting;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;

    private List<SettingItem> mSettingItems;
    private SettingListAdapter mAdapter;


    @Override
    protected void initData() {
        super.initData();
        mSettingItems = new ArrayList<>();
        mSettingItems.add(new SettingItem(R.drawable.setting_school_id, mContext.getString(R.string.setting_school_id), FragmentConstant.SETTING_SCHOOL_ID_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_class_id, mContext.getString(R.string.setting_class_id), FragmentConstant.SETTING_CLASS_ID_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_terminal_id, mContext.getString(R.string.setting_terminal_id), FragmentConstant.SETTING_TERMINAL_ID_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_platform_id, mContext.getString(R.string.setting_platform_id), FragmentConstant.SETTING_PLATFORM_ID_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_card_reading, mContext.getString(R.string.setting_card_reading), FragmentConstant.SETTING_CARD_READING_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_attend_type, mContext.getString(R.string.setting_attend_type), FragmentConstant.SETTING_ATTEND_TYPE_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_camera_type, mContext.getString(R.string.setting_camera_type), FragmentConstant.SETTING_PHOTO_MODE_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_gps_location, mContext.getString(R.string.setting_gps_location), FragmentConstant.SETTING_LOCATION_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_change_skin, mContext.getString(R.string.setting_change_skin), FragmentConstant.SETTING_CHANGE_SKIN_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_teacher_student, mContext.getString(R.string.setting_teacher_student),FragmentConstant.SETTING_USER_TYPE_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_inout_school, mContext.getString(R.string.setting_inout_school), FragmentConstant.SETTING_INOUT_SCHOOL_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_sync_data, mContext.getString(R.string.setting_sync_data), FragmentConstant.SETTING_SYNC_DATA_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_voice_play, mContext.getString(R.string.setting_play_voice), FragmentConstant.SETTING_PLAY_VOICE_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_announcement_board, mContext.getString(R.string.setting_announce_board), FragmentConstant.SETTING_ANNOUNCE_BOARD_FRAGMENT));
        mSettingItems.add(new SettingItem(R.drawable.setting_check_update, mContext.getString(R.string.setting_check_update), FragmentConstant.SETTING_ABOUT_FRAGMENT));
//        mSettingItems.add(new SettingItem(R.drawable.setting_upload_log, mContext.getString(R.string.seting_upload_log),FragmentConstant.SETTING_UPLOAD_LOG_FRAGMENT));
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mAdapter = new SettingListAdapter(R.layout.setting_recycle_item_setting, mSettingItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvSetting.setLayoutManager(layoutManager);
        rvSetting.setAdapter(mAdapter);
        // item 的点击事件
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_list_fragment;
    }

    @Override
    protected int getIndex() {
        return 0;
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        // 退出设置界面
        getActivity().finish();
    }
    /**
     * 设置列表的item点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // fragment 的下标表示
        mFragmentListener.onSwitchFragment(mSettingItems.get(position).getItemIndex());
    }


}
