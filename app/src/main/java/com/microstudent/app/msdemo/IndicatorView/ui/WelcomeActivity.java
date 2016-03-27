package com.microstudent.app.msdemo.IndicatorView.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


import com.microstudent.app.msdemo.IndicatorView.IndicatorView;
import com.microstudent.app.msdemo.R;
import com.microstudent.app.msdemo.util.ImageResizer;
import com.microstudent.app.msdemo.util.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private IndicatorView mIndicatorView;
    private List<ImageView> imageViewList;
    private static final int PAGER_NUMBER = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initImageView();

        initView();
        setupView();

    }

    private void initImageView() {
        imageViewList = new ArrayList<>();
        ImageView imageView1 = new ImageView(this);
        ImageView imageView2= new ImageView(this);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView1.setImageBitmap( ImageResizer.decodeSampledBitmapFromResource(getResources(), R.drawable.cover, 200, 100));
        imageView2.setImageBitmap( ImageResizer.decodeSampledBitmapFromResource(getResources(), R.drawable.cover1, 200, 100));
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
    }


    public void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new WelcomePagerAdapter(imageViewList);
        mIndicatorView = (IndicatorView) findViewById(R.id.indicator);
    }

    public void setupView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        mViewPager.setAdapter(mAdapter);
        mIndicatorView.setViewPager(mViewPager);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }



    private class WelcomePagerAdapter extends PagerAdapter {

        private List<ImageView> mImageViewList;

        public WelcomePagerAdapter(List<ImageView> imageViewList) {
            super();
            if (imageViewList != null) {
                this.mImageViewList = imageViewList;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public int getCount() {
            return PAGER_NUMBER;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
