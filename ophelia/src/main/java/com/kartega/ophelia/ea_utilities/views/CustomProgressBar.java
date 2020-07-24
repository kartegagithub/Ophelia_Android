package com.kartega.ophelia.ea_utilities.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.kartega.ophelia.ea_utilities.tools.Calculator;

/**
 * Created by Ahmet Kılıç on 2019-06-19.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

public class CustomProgressBar extends View {
    private int max = 100;
    private Path path = new Path();
    int colorText = 0xff44C8E5;
    int colorBorder = 0xff44C8E5;
    int colorProgress = 0xff44C8E5;

    private Paint paint;
    private Paint mPaintProgress;
    private RectF mRectF;
    private Paint textPaint;
    private String text = "0%";
    private final Rect textBounds = new Rect();
    private int centerY;
    private int centerX;
    private int swipeAndgle = 0;


    public CustomProgressBar(Context context) {
        super(context);
        initUI();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUI();
    }

    private void initUI() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(Calculator.pxFromDp(getContext(), 9));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorBorder);

        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeWidth(Calculator.pxFromDp(getContext(), 9));
        mPaintProgress.setColor(colorProgress);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(colorText);
        textPaint.setStrokeWidth(2);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        int radius = (Math.min(viewWidth, viewHeight) - (int) Calculator.pxFromDp(getContext(), 2)) / 2;

        path.reset();
        centerX = viewWidth / 2;
        centerY = viewHeight / 2;

        int smallCircleRadius = radius - (int) Calculator.pxFromDp(getContext(), 6);

        path.addCircle(centerX, centerY, smallCircleRadius, Path.Direction.CW);
        mRectF = new RectF(centerX - smallCircleRadius, centerY - smallCircleRadius, centerX + smallCircleRadius, centerY + smallCircleRadius);
        textPaint.setTextSize(radius * 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawArc(mRectF, 270, swipeAndgle, false, mPaintProgress);
        drawTextCentred(canvas);
    }

    public void drawTextCentred(Canvas canvas) {
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, centerX - textBounds.exactCenterX(), centerY - textBounds.exactCenterY(), textPaint);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        int percentage = progress * 100 / max;
        swipeAndgle = percentage * 360 / 100;
        text = percentage + "%";
        invalidate();
    }

    public void setColorText(int color) {
        this.colorText = color;
        textPaint.setColor(color);
    }

    public void setColorBorder(int color) {
        this.colorBorder = color;
        paint.setColor(color);
    }

    public void setColorProgress(int color) {
        this.colorProgress = color;
        mPaintProgress.setColor(color);
    }
}