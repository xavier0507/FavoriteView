package com.xy.favoriteview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xy.favoriteview.R;

import static com.xy.favoriteview.view.HeartView.FINAL_COLOR;
import static com.xy.favoriteview.view.HeartView.ORIGINAL_COLOR;

/**
 * Created by Xavier Yin on 5/5/17.
 */

public class FavoriteView extends FrameLayout {
    private CircleView circleView;
    private SideCircleView sideCircleView;
    private HeartView heartView;

    private AnimatorSet animatorSet;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private OvershootInterpolator overshootInterpolator = new OvershootInterpolator(4);

    public FavoriteView(Context context) {
        super(context);
        this.init();
    }

    public FavoriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public FavoriteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.custom_view_favorite, this, true);
        this.circleView = (CircleView) view.findViewById(R.id.view_circle);
        this.sideCircleView = (SideCircleView) view.findViewById(R.id.view_side_circle);
        this.heartView = (HeartView) view.findViewById(R.id.view_heart);
    }

    public void launchAnim() {
        this.circleView.setInnerCircleRadiusProgress(0);
        this.circleView.setOuterCircleRadiusProgress(0);
        this.sideCircleView.setCurrentProgress(0);

        final ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(this.circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        outerCircleAnimator.setDuration(300);
        outerCircleAnimator.setInterpolator(this.decelerateInterpolator);

        final ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(this.circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        innerCircleAnimator.setDuration(350);
        innerCircleAnimator.setStartDelay(100);
        innerCircleAnimator.setInterpolator(this.decelerateInterpolator);

        final ObjectAnimator sideCircleAnimator = ObjectAnimator.ofFloat(this.sideCircleView, this.sideCircleView.SIDE_CIRCLE_PROGRESS, 0, 1f);
        sideCircleAnimator.setDuration(600);
        sideCircleAnimator.setStartDelay(300);
        sideCircleAnimator.setInterpolator(this.decelerateInterpolator);

        final ValueAnimator heartColorAnimator = ValueAnimator.ofInt(ORIGINAL_COLOR, FINAL_COLOR);
        heartColorAnimator.setEvaluator(this.argbEvaluator);
        heartColorAnimator.setDuration(500);
        heartColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                heartView.setHeartColor((int) animation.getAnimatedValue());
            }
        });

        final ObjectAnimator heartScaleYAnimator = ObjectAnimator.ofFloat(this.heartView, ImageView.SCALE_Y, 0.2f, 1f);
        heartScaleYAnimator.setDuration(500);
        heartScaleYAnimator.setInterpolator(this.overshootInterpolator);

        final ObjectAnimator heartScaleXAnimator = ObjectAnimator.ofFloat(this.heartView, ImageView.SCALE_X, 0.2f, 1f);
        heartScaleXAnimator.setDuration(500);
        heartScaleXAnimator.setInterpolator(this.overshootInterpolator);

        this.animatorSet = new AnimatorSet();
        this.animatorSet.playTogether(outerCircleAnimator, innerCircleAnimator, heartColorAnimator, heartScaleYAnimator, heartScaleXAnimator, sideCircleAnimator);
        this.animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationCancel(Animator animation) {
                circleView.setInnerCircleRadiusProgress(0);
                circleView.setOuterCircleRadiusProgress(0);
                sideCircleView.setCurrentProgress(0);
            }
        });
        this.animatorSet.start();
    }

    public void cleanHearColor() {
        final ValueAnimator heartColorAnimator = ValueAnimator.ofInt(FINAL_COLOR, ORIGINAL_COLOR);
        heartColorAnimator.setEvaluator(this.argbEvaluator);
        heartColorAnimator.setDuration(1500);
        heartColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                heartView.setHeartColor((int) animation.getAnimatedValue());
            }
        });
        heartColorAnimator.start();
    }
}
