package com.seahung.handrollmachine.adapter.setting;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.bean.setting.SettingItem;
import com.seahung.handrollmachine.constant.FragmentConstant;
import com.unengchan.sdk.util.DensityUtils;

import java.util.List;

/**
 * Created by unengchan on 2018/6/4
 * 设置项的适配器
 */
public class SettingListAdapter extends BaseQuickAdapter<SettingItem, BaseViewHolder> {

    private int mClickItemPosition;
    public int mPosition;

    public void setClickItemPosition(int clickItemPosition) {
        mClickItemPosition = clickItemPosition;
        notifyItemChanged(clickItemPosition);
        notifyItemChanged(mPosition);
//        notifyDataSetChanged();
    }

    public int getClickItemPosition() {
        return mClickItemPosition;
    }

    public SettingListAdapter(int layoutResId, @Nullable List<SettingItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingItem item) {
        helper.setImageResource(R.id.iv_setting_item_icon, item.getIconId());
        helper.setText(R.id.tv_setting_item_name, item.getItemName());
        // 设置分隔线的高度
        View view = helper.getView(R.id.line_setting_item);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int position = item.getItemIndex();
        switch (position) {
            case FragmentConstant.SETTING_PLATFORM_ID_FRAGMENT:
//            case FragmentConstant.SETTING_CHANGE_SKIN_FRAGMENT:
            case FragmentConstant.SETTING_INOUT_SCHOOL_FRAGMENT:
            case FragmentConstant.SETTING_ANNOUNCE_BOARD_FRAGMENT:
                layoutParams.height = DensityUtils.dp2px(12);
                break;
            default:
                layoutParams.height = DensityUtils.dp2px(1);
                break;
        }
        view.setLayoutParams(layoutParams);

        // 设置选中效果
//        View itemView = helper.itemView;
//        if (position == getClickItemPosition()) {
//            mPosition = position;
//            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_4f73a7));
//        }else {
//            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTransparent));
//        }
    }
}
