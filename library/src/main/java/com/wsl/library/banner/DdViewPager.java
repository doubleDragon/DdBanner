package com.wsl.library.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

public class DdViewPager extends ViewPager {

    private static final String FIELD_SCROLLER = "mScroller";
    private boolean mScrollable = true;

    public DdViewPager(Context context) {
        super(context);
    }

    public DdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPageChangeDuration(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField(FIELD_SCROLLER);
            scrollerField.setAccessible(true);
            scrollerField.set(this, new DdChangeScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAllowUserScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}