package com.seahung.handrollmachine.fragment.setting;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.adapter.setting.SkinListAdapter;
import com.seahung.handrollmachine.bean.setting.SkinItem;
import com.seahung.handrollmachine.constant.ConfigConstant;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.seahung.handrollmachine.global.GlobalSettingFragment;
import com.seahung.handrollmachine.manager.ConfigManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by unengchan on 2018/6/5
 * 切换皮肤
 */
public class ChangeSkinFragment extends GlobalSettingFragment implements BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.rv_change_skin)
    RecyclerView rvChangeSkin;
    public List<SkinItem> mSkinItems;
    public SkinListAdapter mAdapter;
    public ConfigManager mConfigManager;
    public int mCurrentSkinIndex;

    @Override
    protected void initData() {
        super.initData();
        mSkinItems = new ArrayList<>();
        mSkinItems.add(new SkinItem(R.drawable.skin_sky_blue, mContext.getString(R.string.skin_sky_blue), ConfigConstant.SKIN_SKY_BLUE, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_dark_gray, mContext.getString(R.string.skin_dark_gray), ConfigConstant.SKIN_DARK_GRAY, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_grayish_blue, mContext.getString(R.string.skin_grayish_blue), ConfigConstant.SKIN_GRAYISH_BLUE, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_red, mContext.getString(R.string.skin_red), ConfigConstant.SKIN_RED, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_aqua, mContext.getString(R.string.skin_aqua), ConfigConstant.SKIN_AQUA, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_green, mContext.getString(R.string.skin_green), ConfigConstant.SKIN_GREEN, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_orange, mContext.getString(R.string.skin_orange), ConfigConstant.SKIN_ORANGE, false));
        mSkinItems.add(new SkinItem(R.drawable.skin_wathet_blue, mContext.getString(R.string.skin_wathet_blue), ConfigConstant.SKIN_WATHET_BLUE, false));

        // 找到当前配置的皮肤颜色，默认是暗黑色。
        mConfigManager = ConfigManager.getInstance();
        String skinColors = mConfigManager.getSkinColors();
        for (int i = 0; i < mSkinItems.size(); i++) {
            SkinItem skinItem = mSkinItems.get(i);
            if (skinColors.equals(skinItem.getColors())) {
                skinItem.setSelected(true);
                mCurrentSkinIndex = i;
                break;
            }
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvTitleName.setText(mContext.getString(R.string.setting_change_skin));
        mAdapter = new SkinListAdapter(R.layout.setting_recycle_item_change_skin, mSkinItems);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        rvChangeSkin.setLayoutManager(layoutManager);
        rvChangeSkin.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.change_skin_fragment;
    }

    @Override
    protected int getIndex() {
        return FragmentConstant.SETTING_CHANGE_SKIN_FRAGMENT;
    }


    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        mFragmentListener.onSwitchFragment(FragmentConstant.SETTING_SETTING_LIST_FRAGMENT);
    }

    /**
     * 皮肤列表的点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mCurrentSkinIndex == position) {
            // 当前皮肤跟所选皮肤一样
            return;
        }
        mSkinItems.get(mCurrentSkinIndex).setSelected(false);
        SkinItem skinItem = mSkinItems.get(position);
        skinItem.setSelected(true);
        mConfigManager.setSkinColors(skinItem.getColors());
        mAdapter.notifyItemChanged(mCurrentSkinIndex);
        mAdapter.notifyItemChanged(position);
        // 刷新完成后
        mCurrentSkinIndex = position;
    }
}
