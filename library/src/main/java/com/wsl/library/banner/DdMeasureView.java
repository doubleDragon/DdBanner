package com.wsl.library.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by wsl on 16-4-17.
 */
public class DdMeasureView extends ImageView{

    private int defaultWidth;
    private int defaultHeight;

    private int targetX;
    private int targetY;

    public DdMeasureView(Context context) {
        this(context, null);
    }

    public DdMeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DdMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        defaultWidth = point.x;
        defaultHeight = point.y;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DdMeasureView);
        targetX = a.getInt(R.styleable.DdMeasureView_dd_targetX, 0);
        targetY = a.getInt(R.styleable.DdMeasureView_dd_targetY, 0);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = defaultWidth;
        int height = defaultHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if (targetX > 0 && targetY > 0) {
            height = (int) Math.ceil(width * targetY / targetX);
        }

        int newHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        int newWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }


}
