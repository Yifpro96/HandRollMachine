package com.seahung.handrollmachine.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by unengchan on 2017/10/18.
 */

public class LampProgressBar extends View {

    private static final String TAG = LampProgressBar.class.getSimpleName();

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private int mLampSum;
    private int mLampWidth;
    private int mLampSpace;
    private long mLampCount;

    public LampProgressBar(Context context) {
        this(context, null);
    }

    public LampProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LampProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLamp(canvas);
    }

    /**
     * 绘制不亮的灯的个数
     *
     * @param canvas
     */
    private void drawLamp(Canvas canvas) {

        // 绘制彩色灯的个数
        for (int i = 0; i < mLampSum; i++) {

            // 绘制没有点亮的灯的个数
            int left = (mLampSpace + mLampWidth) * i;
            int right = (mLampSpace + mLampWidth) * i + mLampWidth;
            RectF rectF = new RectF(left, 0, right, mHeight);

            if (i < mLampCount) {
                int color = getColor(i);
                mPaint.setColor(color);

            } else {
                mPaint.setColor(Color.GRAY);
            }
            canvas.drawRoundRect(rectF, mLampWidth / 2, mLampWidth / 2, mPaint);
        }

    }

    private int getColor(int i) {

        int color = Color.GREEN;
        int middle = mLampSum / 2 -1;

        // 如果没有灯或者只有一个灯。
        if (middle <= 0) {
            return color;
        }

        if (i <= middle) {
            int red = 255 / middle * i;
            color = Color.rgb(red, 255, 0);
        } else {
            if (i <= 2 * middle) {
                int green = 255 - 255 / middle * (i - middle);
                color = Color.rgb(255, green, 0);
            }else {
                color = Color.RED;
            }
        }
        return color;
    }

    /**
     * 设置控件的宽高，不使用onmeasure的原因是防止每次绘制的时候都要重新测量。
     * @param width
     * @param height
     */
    public void setWidthHeight(int width, int height) {
        mWidth = width;
        mHeight = height;

        // 一个灯的宽，灯的高度等于控件的高度
        mLampWidth = mHeight / 2;

        // 每个灯之间的间隙
        mLampSpace = mLampWidth / 3;

        // 灯的总数，防止被 0 整除。
        if (mLampWidth == 0) {
            mLampWidth = 1;
            mLampSpace = 1;
        }
        mLampSum = (mWidth + mLampSpace) / (mLampSpace + mLampWidth);
    }

    /**
     * 设置进度的大小，通过这个大小获取灯的个数。
     *
     * @param size
     */
    public void setSize(long size) {

        long lampCount = 0;

        // 分成三部分
        int parts = mLampSum / 3;

        if (mLampSum < 3 || size <= parts) {
            lampCount = size;
        } else if (size <= 6 * parts) {
            lampCount = parts + (size - parts) / 5;
        } else {
            lampCount = 2 * parts  + (size - 6*parts) / 25;
        }

        // 如果计算出来的灯的个数大于总数量，那么最多只绘制总数量的个数
        if (lampCount <= mLampSum) {
            if (lampCount != mLampCount) {
                mLampCount = lampCount;
                postInvalidate();
            }
        } else{
            mLampCount = mLampSum;
            postInvalidate();
        }
    }
}
