package com.botpy.constrainlayoutexample.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.botpy.constrainlayoutexample.R;
import com.botpy.constrainlayoutexample.view.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    @BindView(R.id.frame_text)
    TextView mTextView;
    @BindView(R.id.step_view)
    StepView mStepView;

    private String mName;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String mName) {
        Bundle bundle = new Bundle();
        bundle.putString("name", mName);
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setArguments(bundle);
        return blankFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mName = bundle.getString("name");
        mTextView.setText(mName);

        mStepView.setMaxStepNum(1000f);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 700);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float num = (float) animation.getAnimatedValue();
                Log.d("------", "animation: " + num);
                mStepView.setStepNum(num.intValue());
            }
        });
        animator.start();
    }
}
