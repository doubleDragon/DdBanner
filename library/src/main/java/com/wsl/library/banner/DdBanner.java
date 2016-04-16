package com.wsl.library.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.wsl.library.banner.transformer.AccordionPageTransformer;
import com.wsl.library.banner.transformer.AlphaPageTransformer;
import com.wsl.library.banner.transformer.CubePageTransformer;
import com.wsl.library.banner.transformer.DefaultPageTransformer;
import com.wsl.library.banner.transformer.DepthPageTransformer;
import com.wsl.library.banner.transformer.FadePageTransformer;
import com.wsl.library.banner.transformer.FlipPageTransformer;
import com.wsl.library.banner.transformer.RotatePageTransformer;
import com.wsl.library.banner.transformer.StackPageTransformer;
import com.wsl.library.banner.transformer.ZoomCenterPageTransformer;
import com.wsl.library.banner.transformer.ZoomFadePageTransformer;
import com.wsl.library.banner.transformer.ZoomPageTransformer;
import com.wsl.library.banner.transformer.ZoomStackPageTransformer;

import java.lang.ref.WeakReference;

/**
 * Created by wsl on 16-4-5.
 */
public class DdBanner extends RelativeLayout {

    private static final String TAG = DdBanner.class.getSimpleName();

    private static final int SCROLL_WHAT = 100000;
    private static final int DEFAULT_LOOP_DELAY = 3000;
    private static final int DEFAULT_CHANGE_DURATION = 800;

    private int defaultWidth;
    private int defaultHeight;
    private int targetX;
    private int targetY;
    private int loopDelay;
    private int changeDuration;

    private DdNormalIndicator ddIndicator;
    private DdViewPager ddViewPager;
    private DdAdapter ddAdapter;
    private LoopHandler loopHandler;
    private boolean canLoop;
    private boolean isLooping;
    private boolean loop;

    public DdBanner(Context context) {
        this(context, null);
    }

    public DdBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DdBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initViews(context);
        initOther();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        defaultWidth = point.x;
        defaultHeight = point.y;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DbBanner);
        targetX = a.getInt(R.styleable.DbBanner_dd_targetX, 0);
        targetY = a.getInt(R.styleable.DbBanner_dd_targetY, 0);
        loopDelay = a.getInt(R.styleable.DbBanner_dd_loop_delay, DEFAULT_LOOP_DELAY);
        changeDuration = a.getInt(R.styleable.DbBanner_dd_change_duration, DEFAULT_CHANGE_DURATION);

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

    private void initOther() {
        this.loopHandler = new LoopHandler(this);
        this.canLoop = false;
        this.isLooping = false;
    }

    private void initViews(Context context) {
        LayoutParams params0 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        ddViewPager = new DdViewPager(context);
        ddViewPager.setLayoutParams(params0);
        ddViewPager.setPageChangeDuration(changeDuration);
        ddViewPager.setPageTransformer(true, new DefaultPageTransformer());

        addView(ddViewPager);

        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params1.bottomMargin = dp2px(context, 8);
        ddIndicator = new DdNormalIndicator(context);
        ddIndicator.setGravity(Gravity.CENTER);
        ddIndicator.setLayoutParams(params1);

        addView(ddIndicator);
    }

    public void setAdapter(DdAdapter adapter) {
        if(adapter == null) {
            return;
        }
        if(ddAdapter != null) {
            throw new IllegalStateException("DdBanner set adapter only once");
        }
        ddAdapter = adapter;
        ddAdapter.setDdBanner(this);
        ddViewPager.setAdapter(ddAdapter);
        ddIndicator.setDdBanner(this);

        if(isLoop()) {
            startLoop();
        }
    }

    public DdViewPager getViewPager() {
        return ddViewPager;
    }

    public DdAdapter getAdapter() {
        return ddAdapter;
    }

    void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setIsLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public void startLoop() {
        if (isLoop() && isCanLoop() && !isLooping()) {
            setIsLooping(true);
            sendLoopMessage();
        }
    }

    public void stopLoop() {
        if (isLoop() && isCanLoop() && isLooping()) {
            setIsLooping(false);
            removeLoopMessage();
        }
    }

    private void scrollToNext() {
        int index = ddViewPager.getCurrentItem();
        ddViewPager.setCurrentItem(index + 1);
    }

    private void sendLoopMessage() {
        loopHandler.sendEmptyMessageDelayed(SCROLL_WHAT, loopDelay);
    }

    private void removeLoopMessage() {
        loopHandler.removeMessages(SCROLL_WHAT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startLoop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startLoop();
        } else if (visibility == INVISIBLE) {
            stopLoop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (canLoop) {
            stopLoop();
        }
    }

    public void setTransitionEffect(TransitionEffect effect) {
        switch (effect) {
            case Default:
                ddViewPager.setPageTransformer(true, new DefaultPageTransformer());
                break;
            case Alpha:
                ddViewPager.setPageTransformer(true, new AlphaPageTransformer());
                break;
            case Rotate:
                ddViewPager.setPageTransformer(true, new RotatePageTransformer());
                break;
            case Cube:
                ddViewPager.setPageTransformer(true, new CubePageTransformer());
                break;
            case Flip:
                ddViewPager.setPageTransformer(true, new FlipPageTransformer());
                break;
            case Accordion:
                ddViewPager.setPageTransformer(true, new AccordionPageTransformer());
                break;
            case ZoomFade:
                ddViewPager.setPageTransformer(true, new ZoomFadePageTransformer());
                break;
            case Fade:
                ddViewPager.setPageTransformer(true, new FadePageTransformer());
                break;
            case ZoomCenter:
                ddViewPager.setPageTransformer(true, new ZoomCenterPageTransformer());
                break;
            case ZoomStack:
                ddViewPager.setPageTransformer(true, new ZoomStackPageTransformer());
                break;
            case Stack:
                ddViewPager.setPageTransformer(true, new StackPageTransformer());
                break;
            case Depth:
                ddViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case Zoom:
                ddViewPager.setPageTransformer(true, new ZoomPageTransformer());
                break;
            default:
                break;
        }
    }

    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null) {
            ddViewPager.setPageTransformer(true, transformer);
        }
    }

    public enum TransitionEffect {
        Default,
        Alpha,
        Rotate,
        Cube,
        Flip,
        Accordion,
        ZoomFade,
        Fade,
        ZoomCenter,
        ZoomStack,
        Stack,
        Depth,
        Zoom
    }

    private static class LoopHandler extends Handler {

        private final WeakReference<DdBanner> reference;

        public LoopHandler(DdBanner banner) {
            this.reference = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_WHAT:
                    DdBanner banner = this.reference.get();
                    if (banner != null) {
                        banner.scrollToNext();
                        banner.sendLoopMessage();
                    }
                default:
                    break;
            }
        }
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}