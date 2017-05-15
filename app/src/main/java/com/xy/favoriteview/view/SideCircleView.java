package com.xy.favoriteview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

/**
 * Created by Xavier Yin on 5/11/17.
 */

public class SideCircleView extends View {
    private static final int DOTS_COUNT = 8;
    private static final int OUTER_DOTS_POSITION_ANGLE = 360 / DOTS_COUNT;
    private static final int COLOR = 0xFF99BBFF;

    private final Paint[] circlePaints = new Paint[4];

    private int centerX;
    private int centerY;

    private float maxDotSize;
    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;

    private float currentRadius1 = 0;
    private float currentRadius2 = 0;
    private float currentDotSize1 = 0;
    private float currentDotSize2 = 0;
    private float currentProgress = 0;

    public SideCircleView(Context context) {
        this(context, null);
    }

    public SideCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        for (int i = 0; i < this.circlePaints.length; i++) {
            this.circlePaints[i] = new Paint();
            this.circlePaints[i].setStyle(Paint.Style.FILL);
            this.circlePaints[i].setColor(COLOR);
        }
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + this.currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + this.currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, this.currentDotSize1, this.circlePaints[i % this.circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + this.currentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            int cY = (int) (centerY + this.currentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            canvas.drawCircle(cX, cY, this.currentDotSize2, this.circlePaints[(i + 1) % this.circlePaints.length]);
        }
    }

    private void updateOuterDotsPosition() {
        this.currentRadius1 = (float) this.rangeValue(currentProgress, 0.0f, 1.0f, 0.0f, maxOuterDotsRadius);

        if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize;
        } else {
            this.currentDotSize1 = (float) this.rangeValue(currentProgress, 0.7f, 1.0f, maxDotSize, 0.0f);
        }
    }

    private void updateInnerDotsPosition() {
        this.currentRadius2 = (float) this.rangeValue(currentProgress, 0.0f, 1.0f, 0.0f, maxInnerDotsRadius);

        if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = (float) this.rangeValue(currentProgress, 0.2f, 0.5f, maxDotSize, 0.8f * maxDotSize);
        } else {
            this.currentDotSize2 = (float) this.rangeValue(currentProgress, 0.5f, 1.0f, maxDotSize * 0.8f, 0.0f);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.centerX = w / 2;
        this.centerY = h / 2;
        this.maxDotSize = 20;
        this.maxOuterDotsRadius = w / 2 - this.maxDotSize * 2;
        this.maxInnerDotsRadius = this.maxOuterDotsRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.drawOuterDotsFrame(canvas);
        this.drawInnerDotsFrame(canvas);
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        this.updateOuterDotsPosition();
        this.updateInnerDotsPosition();
        this.postInvalidate();
    }

    public double rangeValue(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static final Property<SideCircleView, Float> SIDE_CIRCLE_PROGRESS = new Property<SideCircleView, Float>(Float.class, "dotsProgress") {

        @Override
        public Float get(SideCircleView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(SideCircleView object, Float value) {
            object.setCurrentProgress(value);
        }
    };
}
