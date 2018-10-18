package com.seahung.handrollmachine.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class FaceRectView extends View {

    //    public Paint paint;
    public Path path1;
    public Paint paint1;
    public Path path2;
    public Path path3;
    public Path path4;


    public FaceRectView(Context context) {
        this(context, null);
    }

    public FaceRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(8f);//设置线宽
//        paint.setAlpha(100);

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        //paint1.setColor(getResources().getColor(R.color.color_0681aa));
        paint1.setColor(Color.GREEN);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(4f);//设置线宽
        paint1.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawRect(faceRect, paint); //绘制识别线框

        if (path1 == null || path2 == null || path3 == null || path4 == null) {
            return;
        }
        canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint1);
        canvas.drawPath(path3, paint1);
        canvas.drawPath(path4, paint1);
    }

    /**
     * 画识别框  左上右下
     *
     * @param rect
     * @param scaleWidth  宽缩放
     * @param scaleHeight 高缩放
     */
    public void setFaceRectView(Rect rect, float scaleWidth, float scaleHeight) {

        // 绘制人脸
        if (rect == null) {
            path1 = null;
            path2 = null;
            postInvalidate();
            return;
        }

        int left = rect.left;
        int top = rect.top;
        int right = rect.right;
        int bottom = rect.bottom;

        left = ((int) (left * scaleWidth));
        right = ((int) (right * scaleWidth) + 1);
        top = (int) (top * scaleHeight);
        bottom = (int) (bottom * scaleHeight + 1);


        int rx = (right - left) / 5;//增加宽
        int ry = rx;//增加高
        path1 = new Path();
        path1.moveTo(left, top + ry);
        path1.lineTo(left, top);
        path1.lineTo(left + rx, top);
        path2 = new Path();
        path2.moveTo(right - rx, top);
        path2.lineTo(right, top);
        path2.lineTo(right, top + ry);
        path3 = new Path();
        path3.moveTo(right - rx, bottom);
        path3.lineTo(right, bottom);
        path3.lineTo(right, bottom - ry);
        path4 = new Path();
        path4.moveTo(left, bottom - ry);
        path4.lineTo(left, bottom);
        path4.lineTo(left + rx, bottom);
        postInvalidate();
    }
}
