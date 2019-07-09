package com.botpy.constrainlayoutexample;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.botpy.constrainlayoutexample.fragment.BlankFragment;
import com.botpy.constrainlayoutexample.view.ColorTrackTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorTrackActivity extends AppCompatActivity {

    private static final String TAG = "ColorTrackActivity";
    private String[] items = {"直播", "推荐", "搞笑视频", "美女的图片", "段子", "精华"};
    private List<ColorTrackTextView> mTrackViews = new ArrayList<>();

    @BindView(R.id.linear_title)
    LinearLayout mLinearLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);
        ButterKnife.bind(this);
        
        initData();
        initViewPager();
    }

    private void initData() {
        for(int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;

            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setmNormalPaint(getResources().getColor(R.color.gray));
            colorTrackTextView.setmChangerPaint(getResources().getColor(R.color.colorAccent));
            colorTrackTextView.change(ColorTrackTextView.Direction.DIRECTION_LEFT, 0);

            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);

            mLinearLayout.addView(colorTrackTextView);
            mTrackViews.add(colorTrackTextView);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return BlankFragment.newInstance(items[i]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                Log.d(TAG, "----i: " + i + "-----v: " + v);
                //获取左边
                ColorTrackTextView leftColorTrackTextView = mTrackViews.get(i);
                leftColorTrackTextView.change(ColorTrackTextView.Direction.DIRECTION_RIGH,1 - v);

                //获取右边
                if(i < items.length - 1) {
                    ColorTrackTextView rightColorTrackTextView = mTrackViews.get(i + 1);
                    rightColorTrackTextView.change(ColorTrackTextView.Direction.DIRECTION_LEFT, v);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTrackViews.get(0).change(ColorTrackTextView.Direction.DIRECTION_LEFT, 1);
    }
}
