package com.wsl.library.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by wsl on 16-2-23.
 */
public class DbView extends ImageView{

    private int defaultWidth;
    private int defaultHeight;
    private float scale;
    private int yOffset;

    public DbView(Context context) {
        this(context, null);
    }

    public DbView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        defaultWidth = point.x;
        defaultHeight = point.y;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DbView);
        scale = a.getFloat(R.styleable.DbView_ratio, -1);
        yOffset = a.getDimensionPixelSize(R.styleable.DbView_offsetY, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = defaultWidth;
        int height = defaultHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if(scale != -1) {
            height = (int) (width / scale + yOffset);
        }

        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }
}
