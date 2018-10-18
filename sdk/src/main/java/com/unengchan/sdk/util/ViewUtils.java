package com.unengchan.sdk.util;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by unengchan on 2017/11/29.
 * view的相关辅助类
 */

public class ViewUtils {

    private ViewUtils() {}

    /**
     * 测量控件的宽高
     * @param view  将要测量的控件
     */
    public static void measureWidthHeight(final View view, final ViewCallbackListener callback) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                int width = view.getWidth();
                int height = view.getHeight();
                // 回调测量结果
                callback.onMeasureResponse(width,height);
            }
        });
    }

    /**
     * 设置view的宽高
     * @param view 将要设置宽高的view，例如 textview
     * @param width 目标宽度
     * @param height 目标高度
     */
    public static void setWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 测量成功的接口
     */
    public interface ViewCallbackListener {
        void onMeasureResponse(int width, int height);
    }
}
