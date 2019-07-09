package com.botpy.constrainlayoutexample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.botpy.constrainlayoutexample.view.ColorTrackTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorTrackActivity extends AppCompatActivity {
    @BindView(R.id.text_track)
    ColorTrackTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.text_track)
    public void onClick() {
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animator = ObjectAnimator.ofFloat(mTextView, "text", 0, 1);
        animator.setDuration(3000)
                .start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                Log.d("ColorTrackActivity", "----progress----" + progress);
                mTextView.change(ColorTrackTextView.Direction.DIRECTION_RIGH, progress);
            }
        });
    }
}
