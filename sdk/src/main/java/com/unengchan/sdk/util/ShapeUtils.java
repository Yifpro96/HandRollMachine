package com.unengchan.sdk.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by unengchan on 2018/7/4
 * background 获取圆角，颜色，边框不同的背景
 */
public class ShapeUtils {

    public static final int RECTANGLE = GradientDrawable.RECTANGLE;
    public static final int OVAL = GradientDrawable.OVAL;
    public static final int LINE = GradientDrawable.LINE;
    public static final int RING = GradientDrawable.RING;

    private ShapeUtils() {
    }

    /**
     * 获取 shape 背景
     *
     * @param shape      形状
     * @param solidColor 填充颜色
     * @return
     */
    public static GradientDrawable getBackgroudDrawable(int shape, String solidColor) {
        return getBackgroudDrawable(shape, 0, solidColor, 0, "");
    }

    /**
     * 获取shape圆角背景
     *
     * @param shape        形状
     * @param cornerRadius 圆角半径
     * @param solidColor   填充颜色
     * @return
     */
    public static GradientDrawable getBackgroudDrawable(int shape, int cornerRadius, String solidColor) {
        return getBackgroudDrawable(shape, cornerRadius, solidColor, 0, "");
    }

    /**
     * 获取shape 带边框的圆角背景
     *
     * @param shape        形状
     * @param cornerRadius 圆角半径
     * @param solidColor   填充颜色
     * @param strokeWidth  边框宽度
     * @param strokeColor  边框颜色
     * @return
     */
    public static GradientDrawable getBackgroudDrawable(int shape, int cornerRadius, String solidColor,
                                                        int strokeWidth, String strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(shape);
        drawable.setColor(Color.parseColor(solidColor));
        if (cornerRadius != 0) {
            drawable.setCornerRadius(DensityUtils.dp2px(cornerRadius));
        }
        if (strokeWidth != 0) {
            drawable.setStroke(DensityUtils.dp2px(strokeWidth), Color.parseColor(strokeColor));
        }
        return drawable;
    }
}
