package com.seahung.handrollmachine.helper;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.seahung.handrollmachine.R;
import com.unengchan.sdk.util.AppUtils;

/**
 * Created by unengchan on 2018/6/6
 * view 辅助类
 * 抽出多次调用的方法
 */
public class ViewHelper {


    /**
     * 刷新textview
     *
     * @param index
     * @param textViews
     */
    public static void refreshTextViews(int index, TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = textViews[i];
            Drawable rightDrawable;
            if (i == index) {
                rightDrawable = ContextCompat.getDrawable(AppUtils.getAppContext(), R.drawable.setting_checkbox_select);
            } else {
                rightDrawable = ContextCompat.getDrawable(AppUtils.getAppContext(), R.drawable.setting_checkbox_unselect);
            }
            setTextViewDrawble(textView, null, null, rightDrawable, null);
        }
    }

    /**
     * 设置textview的drawble
     *
     * @param tv
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setTextViewDrawble(TextView tv, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

}
