package com.xy.favoriteview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

/**
 * Created by Xavier Yin on 5/5/17.
 */

public class CircleView extends View {
    private static final int COLOR = 0xFFFFB3FF;

    private Paint outerCirclePaint = new Paint();
    private Paint innerCirclePaint = new Paint();
    private Bitmap tempBitmap;
    private Canvas tempCanvas;
    private int maxCircleSize;

    private float innerCircleRadiusProgress;
    private float outerCircleRadiusProgress;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.outerCirclePaint.setStyle(Paint.Style.FILL);
        this.outerCirclePaint.setColor(COLOR);
        this.innerCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.innerCirclePaint.setColor(COLOR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.maxCircleSize = w / 2;
        this.tempBitmap = Bitmap.createBitmap(this.getWidth(), this.getWidth(), Bitmap.Config.ARGB_8888);
        this.tempCanvas = new Canvas(this.tempBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.tempCanvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);
        this.tempCanvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, this.outerCircleRadiusProgress * this.maxCircleSize, this.outerCirclePaint);
        this.tempCanvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, this.innerCircleRadiusProgress * this.maxCircleSize, this.innerCirclePaint);
        canvas.drawBitmap(this.tempBitmap, 0, 0, null);
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        this.postInvalidate();
    }

    public float getOuterCircleRadiusProgress() {
        return this.outerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        this.postInvalidate();
    }

    public float getInnerCircleRadiusProgress() {
        return this.innerCircleRadiusProgress;
    }

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {

        @Override
        public Float get(CircleView object) {
            return object.getOuterCircleRadiusProgress();
        }

        @Override
        public void set(CircleView object, Float value) {
            object.setOuterCircleRadiusProgress(value);
        }
    };

    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {

        @Override
        public Float get(CircleView object) {
            return object.getInnerCircleRadiusProgress();
        }

        @Override
        public void set(CircleView object, Float value) {
            object.setInnerCircleRadiusProgress(value);
        }
    };
}
