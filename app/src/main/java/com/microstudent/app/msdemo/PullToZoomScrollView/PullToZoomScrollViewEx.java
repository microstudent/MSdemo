package com.microstudent.app.msdemo.PullToZoomScrollView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.microstudent.app.msdemo.R;


/**
 * from:https://github.com/Frank-Zhu/PullZoomView
 * Edit:Microstudent
 * centerCrop与Layout的ClipChild(false)不兼容，会导致视图超出范围
 * 增加了在xml中设置height的选项
 * 增加了最大下拉长度
 * Created by 45517 on 2015/11/4.
 */
public class PullToZoomScrollViewEx extends PullToZoomBase<ScrollView> {
    private static final String TAG = PullToZoomScrollViewEx.class.getSimpleName();
    private boolean isCustomHeaderHeight = false;
    private FrameLayout mHeaderContainer;
    private LinearLayout mRootContainer;
    private View mContentView;
    private int mHeaderHeight;
    private ScalingRunnable mScalingRunnable;

    private ValueAnimator reducerAnimator;  //设置下拉时的最大下拉值

    private int mMaxDragHeight = 600;//最长下拉高度

    public PullToZoomScrollViewEx(Context context) {
        this(context, null);
    }

    public PullToZoomScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScalingRunnable = new ScalingRunnable();
        ((InternalScrollView) mRootView).setOnScrollViewChangedListener(new OnScrollViewChangedListener() {
            @Override
            public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                if (isPullToZoomEnabled() && isParallax()) {//如果可以下拉了，而且开启了视差模式
                    Log.d(TAG, "onScrollChanged --> getScrollY() = " + mRootView.getScrollY());

                    float f = mHeaderHeight - mHeaderContainer.getBottom() + mRootView.getScrollY();

                    Log.d(TAG, "onScrollChanged --> f = " + f);

                    if ((f > 0.0F) && (f < mHeaderHeight)) {
                        int i = (int) (0.65D * f);
                        mHeaderContainer.scrollTo(0, -i);
                    } else if (mHeaderContainer.getScrollY() != 0) {
                        mHeaderContainer.scrollTo(0, 0);
                    }
                }
            }
        });

    }

    @Override
    protected void pullHeaderToZoom(int newScrollValue) {
        Log.d(TAG, "pullHeaderToZoom --> newScrollValue = " + newScrollValue);
        Log.d(TAG, "pullHeaderToZoom --> mHeaderHeight = " + mHeaderHeight);
        if (mScalingRunnable != null && !mScalingRunnable.isFinished()) {
            mScalingRunnable.abortAnimation();
        }

        reducerAnimator.setCurrentPlayTime(Math.abs(newScrollValue));

        Log.d(TAG, "pullHeaderToZoom --> wrappedInt = " + reducerAnimator.getAnimatedValue());

        ViewGroup.LayoutParams localLayoutParams = mHeaderContainer.getLayoutParams();
        localLayoutParams.height = (int)reducerAnimator.getAnimatedValue() + mHeaderHeight;
        mHeaderContainer.setLayoutParams(localLayoutParams);

        if (isCustomHeaderHeight) {
            ViewGroup.LayoutParams zoomLayoutParams = mZoomView.getLayoutParams();
            zoomLayoutParams.height = (int)reducerAnimator.getAnimatedValue() + mHeaderHeight;
            mZoomView.setLayoutParams(zoomLayoutParams);
        }
    }

    /**
     * 是否显示headerView
     *
     * @param isHideHeader true: show false: hide
     */
    @Override
    public void setHideHeader(boolean isHideHeader) {
        if (isHideHeader != isHideHeader() && mHeaderContainer != null) {
            super.setHideHeader(isHideHeader);
            if (isHideHeader) {
                mHeaderContainer.setVisibility(GONE);
            } else {
                mHeaderContainer.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void setHeaderView(View headerView) {
        if (headerView != null) {
            mHeaderView = headerView;
            updateHeaderView();
        }
    }

    @Override
    public void setZoomView(View zoomView) {
        if (zoomView != null) {
            mZoomView = zoomView;
            updateHeaderView();
        }
    }

    private void updateHeaderView() {
        if (mHeaderContainer != null) {
            mHeaderContainer.removeAllViews();

            if (mZoomView != null) {
                mHeaderContainer.addView(mZoomView);
            }

            if (mHeaderView != null) {
                mHeaderContainer.addView(mHeaderView);
            }
        }
    }

    public void setScrollContentView(View contentView) {
        if (contentView != null) {
            if (mContentView != null) {
                mRootContainer.removeView(mContentView);
            }
            mContentView = contentView;
            mRootContainer.addView(mContentView);
        }
    }

    @Override
    protected ScrollView createRootView(Context context, AttributeSet attrs) {
        ScrollView scrollView = new InternalScrollView(context, attrs);
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    @Override
    protected void smoothScrollToTop() {
        Log.d(TAG, "smoothScrollToTop --> ");
        mScalingRunnable.startAnimation(200L);
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRootView.getScrollY() == 0;
    }

    @Override
    public void handleStyledAttributes(TypedArray a) {
        mRootContainer = new LinearLayout(getContext());
        mRootContainer.setOrientation(VERTICAL);
        mHeaderContainer = new FrameLayout(getContext());

        if (mZoomView != null) {
            mHeaderContainer.addView(mZoomView);
        }
        if (mHeaderView != null) {
            mHeaderContainer.addView(mHeaderView);
        }
        int contentViewResId = a.getResourceId(R.styleable.PullToZoomView_contentView, 0);
        if (contentViewResId > 0) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
            mContentView = mLayoutInflater.inflate(contentViewResId, null, false);
        }

        mHeaderHeight = (int) a.getDimension(R.styleable.PullToZoomView_headerViewHeight, 0);
        mMaxDragHeight = (int) a.getDimension(R.styleable.PullToZoomView_maxDragHeight, 600);

        mRootContainer.addView(mHeaderContainer);
        if (mContentView != null) {
            mRootContainer.addView(mContentView);
        }

        Log.d(TAG, "mHeaderHeight = " + mHeaderHeight);

        setHeaderViewSize(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderHeight);

        //允许子view超出layout范围！
        mRootContainer.setClipChildren(false);
        mHeaderContainer.setClipChildren(false);

        mRootView.addView(mRootContainer);

        reducerAnimator = ValueAnimator.ofInt(0, mMaxDragHeight);
        reducerAnimator.setInterpolator(new DecelerateInterpolator());
        reducerAnimator.setDuration(mContentView.getMeasuredHeight() + mHeaderHeight);
    }

    /**
     * 设置HeaderView高度
     *
     * @param width
     * @param height
     */
    public void setHeaderViewSize(int width, int height) {
        if (mHeaderContainer != null) {
            ViewGroup.LayoutParams localObject = mHeaderContainer.getLayoutParams();
            if (localObject == null) {
                localObject = new ViewGroup.LayoutParams(width, height);
            }
            localObject.width = width;
            localObject.height = height;
            mHeaderContainer.setLayoutParams(localObject);

            mHeaderHeight = height;
            isCustomHeaderHeight = true;
        }
    }

    /**
     * 设置HeaderView LayoutParams
     *
     * @param layoutParams LayoutParams
     */
    public void setHeaderLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (mHeaderContainer != null) {
            mHeaderContainer.setLayoutParams(layoutParams);
            mHeaderHeight = layoutParams.height;
            isCustomHeaderHeight = true;
        }
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
                            int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        Log.d(TAG, "onLayout -->");
        if (mHeaderHeight == 0 && mZoomView != null) {
            mHeaderHeight = mHeaderContainer.getHeight();
        }
    }

    public void stopRunable() {
        if (mScalingRunnable != null) {
            mScalingRunnable.abortAnimation();
        }
    }

    class ScalingRunnable implements Runnable {
        protected long mDuration;
        protected boolean mIsFinished = true;
        protected float mScale;
        protected long mStartTime;

        ScalingRunnable() {
        }

        public void abortAnimation() {
            mIsFinished = true;
        }

        public boolean isFinished() {
            return mIsFinished;
        }

        public void run() {
            if (mZoomView != null) {
                float f2;

                ViewGroup.LayoutParams localLayoutParams;

                if ((!mIsFinished) && (mScale > 1.0D)) {
                    //经过时间比例值
                    float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) mStartTime) / (float) mDuration;

                    DecelerateInterpolator interpolator = new DecelerateInterpolator(3);

                    f2 = mScale - (mScale - 1.0F) * interpolator.getInterpolation(f1);
                    localLayoutParams = mHeaderContainer.getLayoutParams();

                    Log.d(TAG, "ScalingRunnable --> f2 = " + f2);

                    if (f2 > 1.0F) {
                        localLayoutParams.height = ((int) (f2 * mHeaderHeight));
                        mHeaderContainer.setLayoutParams(localLayoutParams);
                        if (isCustomHeaderHeight) {
                            ViewGroup.LayoutParams zoomLayoutParams;
                            zoomLayoutParams = mZoomView.getLayoutParams();
                            zoomLayoutParams.height = ((int) (f2 * mHeaderHeight));
                            mZoomView.setLayoutParams(zoomLayoutParams);
                        }
                        post(this);
                        return;
                    }
                    mIsFinished = true;
                }
            }
        }

        public void startAnimation(long paramLong) {
            if (mZoomView != null) {
                mStartTime = SystemClock.currentThreadTimeMillis();
                mDuration = paramLong;
                mScale = ((float) (mHeaderContainer.getBottom()) / mHeaderHeight);
                mIsFinished = false;
                post(this);
            }
        }
    }

    private class InternalScrollView extends ScrollView {
        private OnScrollViewChangedListener onScrollViewChangedListener;

        public InternalScrollView(Context context) {
            this(context, null);
        }

        public InternalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setOnScrollViewChangedListener(OnScrollViewChangedListener onScrollViewChangedListener) {
            this.onScrollViewChangedListener = onScrollViewChangedListener;
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            if (onScrollViewChangedListener != null) {
                onScrollViewChangedListener.onInternalScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    protected interface OnScrollViewChangedListener {
        public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop);
    }

}
