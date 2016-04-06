package com.wsl.library.banner;

import android.content.Context;
import android.widget.Scroller;

public class DdChangeScroller extends Scroller {
    private int mDuration = 1000;

    public DdChangeScroller(Context context) {
        super(context);
    }

    public DdChangeScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}