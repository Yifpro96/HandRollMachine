package com.seahung.handrollmachine.adapter.setting;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seahung.handrollmachine.R;
import com.seahung.handrollmachine.bean.setting.SkinItem;
import com.seahung.handrollmachine.helper.ViewHelper;
import com.unengchan.sdk.util.AppUtils;

import java.util.List;

/**
 * Created by unengchan on 2018/7/16
 * 皮肤列表适配器
 */
public class SkinListAdapter extends BaseQuickAdapter<SkinItem, BaseViewHolder> {
    public SkinListAdapter(int layoutResId, @Nullable List<SkinItem> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, SkinItem item) {
        helper.setImageResource(R.id.iv_skin_icon, item.getIcon());
        TextView tvSkinName = (TextView) helper.getView(R.id.tv_skin_name);
        helper.setText(R.id.tv_skin_name, item.getName());
        boolean selected = item.isSelected();
        Drawable leftDrawable;
        if (selected) {
            leftDrawable = ContextCompat.getDrawable(AppUtils.getAppContext(), R.drawable.setting_checkbox_select);
        } else {
            leftDrawable = ContextCompat.getDrawable(AppUtils.getAppContext(), R.drawable.setting_checkbox_unselect);
        }
        ViewHelper.setTextViewDrawble(tvSkinName, leftDrawable, null, null, null);
    }

}
