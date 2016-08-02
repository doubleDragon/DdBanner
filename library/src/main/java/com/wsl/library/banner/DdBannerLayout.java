package com.wsl.library.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * banner layout version 2.0
 * Created by wsl on 16-8-1.
 */

public class DdBannerLayout extends ViewGroup {

    private View mViewPager;
    private View mIndicator;

    private final ArrayList<View> mMatchParentChildren = new ArrayList<View>(1);

    public DdBannerLayout(Context context) {
        this(context, null);
    }

    public DdBannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DdBannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPager = findViewById(R.id.dd_banner_viewpager);
        mIndicator = findViewById(R.id.dd_banner_indicator);

        if (mViewPager == null) {
            throw new IllegalArgumentException("child viewPager not exist");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();

        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        if (mViewPager.getVisibility() != GONE) {
            measureChildWithMargins(mViewPager, widthMeasureSpec, 0, heightMeasureSpec, 0);
            final LayoutParams lp = (LayoutParams) mViewPager.getLayoutParams();
            maxWidth = Math.max(maxWidth,
                    mViewPager.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            maxHeight = Math.max(maxHeight,
                    mViewPager.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            childState = combineMeasuredStates(childState, mViewPager.getMeasuredState());
            if (measureMatchParentChildren) {
                if (lp.width == LayoutParams.MATCH_PARENT ||
                        lp.height == LayoutParams.MATCH_PARENT) {
                    mMatchParentChildren.add(mViewPager);
                }
            }
        }

        if (mIndicator != null && mIndicator.getVisibility() != GONE) {
            measureChildWithMargins(mIndicator, widthMeasureSpec, 0, heightMeasureSpec, 0);
            final LayoutParams lp = (LayoutParams) mIndicator.getLayoutParams();
            maxWidth = Math.max(maxWidth,
                    mIndicator.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            maxHeight = Math.max(maxHeight,
                    mIndicator.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            childState = combineMeasuredStates(childState, mIndicator.getMeasuredState());
            if (measureMatchParentChildren) {
                if (lp.width == LayoutParams.MATCH_PARENT ||
                        lp.height == LayoutParams.MATCH_PARENT) {
                    mMatchParentChildren.add(mIndicator);
                }
            }
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));

        //measure children
        int count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);

                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidthMeasureSpec;
                int childHeightMeasureSpec;

                if (lp.width == LayoutParams.MATCH_PARENT) {
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() -
                                    lp.leftMargin - lp.rightMargin,
                            MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            lp.leftMargin + lp.rightMargin,
                            lp.width);
                }

                if (lp.height == LayoutParams.MATCH_PARENT) {
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() -
                                    lp.topMargin - lp.bottomMargin,
                            MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            lp.topMargin + lp.bottomMargin,
                            lp.height);
                }
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();
        //layout viewPager
        if (mViewPager != null) {
            MarginLayoutParams lp = (MarginLayoutParams) mViewPager.getLayoutParams();
            int pagerLeft = paddingLeft + lp.leftMargin;
            int pagerTop = paddingTop + lp.topMargin;
            int pagerRight = width - paddingRight - lp.rightMargin;
            int pagerBottom = height - paddingBottom - lp.bottomMargin;
            mViewPager.layout(pagerLeft, pagerTop, pagerRight, pagerBottom);
        }
        if (mIndicator != null) {
            MarginLayoutParams lp = (MarginLayoutParams) mIndicator.getLayoutParams();
            int indicatorLeft = paddingLeft + lp.leftMargin;
            int indicatorTop = height - paddingBottom - lp.bottomMargin - lp.height;
            int indicatorRight = width - paddingRight - lp.rightMargin;
            int indicatorBottom = height - paddingBottom - lp.bottomMargin;
            mIndicator.layout(indicatorLeft, indicatorTop, indicatorRight, indicatorBottom);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
