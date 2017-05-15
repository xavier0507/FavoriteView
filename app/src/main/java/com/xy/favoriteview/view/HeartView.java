package com.xy.favoriteview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Xavier Yin on 5/12/17.
 */

public class HeartView extends View {
    public static final int ORIGINAL_COLOR = 0xFF444444;
    public static final int FINAL_COLOR = 0xFFFF88C2;

    private Paint circlePaint;
    private int heartColor = ORIGINAL_COLOR;
    private int currentColor = ORIGINAL_COLOR;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.circlePaint = new Paint();
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setStyle(Paint.Style.FILL);
        this.circlePaint.setColor(this.heartColor);
    }

    private void drawHeart(Canvas canvas) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        float radius = this.getWidth() / 6;

        canvas.rotate(45, centerX, centerY);
        canvas.drawRect(centerX - radius, centerY - radius, centerX + radius, centerY + radius, this.circlePaint);
        canvas.drawCircle(centerX - radius, centerY, radius, this.circlePaint);
        canvas.drawCircle(centerX, centerY - radius, radius, this.circlePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawHeart(canvas);
    }

    public void setHeartColor(int currentColor) {
        this.currentColor = currentColor;
        this.circlePaint.setColor(this.currentColor);
        this.postInvalidate();
    }
}
