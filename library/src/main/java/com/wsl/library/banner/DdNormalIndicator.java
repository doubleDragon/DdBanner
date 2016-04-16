package com.wsl.library.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Base on CircleIndicator
 * Created by wsl on 16-4-12.
 */
public class DdNormalIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 7;
    private final static int DEFAULT_INDICATOR_MARGIN = 3;
    private ViewPager mViewpager;
    private DdBanner mDdBanner;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mIndicatorStateResId = R.drawable.dd_indicator_state;

    private int mLastPosition = -1;

    public DdNormalIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public DdNormalIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        checkIndicatorConfig();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DdIndicator);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.DdIndicator_dd_width, -1);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.DdIndicator_dd_height, -1);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.DdIndicator_dd_margin, -1);

        typedArray.recycle();
    }

    private void checkIndicatorConfig() {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_MARGIN) : mIndicatorMargin;
    }

    public void setDdBanner(DdBanner ddBanner) {
        this.mDdBanner = ddBanner;
        if(mDdBanner != null) {
            mViewpager = ddBanner.getViewPager();
            if (mViewpager != null && mViewpager.getAdapter() != null) {
                mLastPosition = -1;
                createIndicators();
                mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
                mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
                mViewpager.getAdapter().registerDataSetObserver(mInternalDataSetObserver);
                mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
            }
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (mViewpager.getAdapter() == null) {
                return;
            }

            int count = getViewPagerCount();
            if (count <= 0 || count == Integer.MAX_VALUE) {
                return;
            }

            position = position % count;

            if (mLastPosition >= 0) {
                View currentIndicator = getChildAt(mLastPosition);
                currentIndicator.setSelected(false);
            }

            View selectedIndicator = getChildAt(position);
            selectedIndicator.setSelected(true);

            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            int newCount = getViewPagerCount();

            int currentCount = getChildCount();

            if (newCount == currentCount) {  // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = getViewPagerCurrentItem(newCount);
            } else {
                mLastPosition = 0;
            }

            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();
        int count = getViewPagerCount();

        if (count <= 0 || (mDdBanner.isLoop() && count == Integer.MAX_VALUE)) {
            return;
        }

        int currentItem = getViewPagerCurrentItem(count);

        for (int i = 0; i < count; i++) {
            addIndicator(currentItem == i);
        }
    }

    private void addIndicator(boolean selected) {
        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(mIndicatorStateResId);
        Indicator.setSelected(selected);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = mIndicatorMargin;
        lp.rightMargin = mIndicatorMargin;
        Indicator.setLayoutParams(lp);
    }

    private int getViewPagerCount() {
        int count = mViewpager.getAdapter().getCount();
        if (!mDdBanner.isLoop()) {
            return count;
        } else {
            if(count == 1) {
                return 1;
            }
            return Integer.MAX_VALUE - count;
        }
    }

    private int getViewPagerCurrentItem() {
        int count = getViewPagerCount();
        return getViewPagerCurrentItem(count);
    }

    private int getViewPagerCurrentItem(int count) {
        if (!mDdBanner.isLoop()) {
            return mViewpager.getCurrentItem();
        }
        return mViewpager.getCurrentItem() % count;
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
