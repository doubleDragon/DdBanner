package com.wsl.library.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by wsl on 16-4-5.
 */
public class DdBanner extends RelativeLayout {

    private static final int SCROLL_WHAT = 100000;
    private static final int LOOP_DELAY_TIME = 3000;

    private DdViewPager ddViewPager;
    private LoopHandler loopHandler;
    private boolean canLoop;
    private boolean isLooping;

    public DdBanner(Context context) {
        this(context, null);
    }

    public DdBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DdBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initOther();
    }

    private void initOther() {
        this.loopHandler = new LoopHandler(this);
        this.canLoop = false;
        this.isLooping = false;
    }

    private void initViews(Context context) {
        LayoutParams params0 = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        DdAdapter ddAdapter = new DdAdapter(context);
        ddViewPager = new DdViewPager(context);
        ddViewPager.setLayoutParams(params0);
        ddViewPager.setPageChangeDuration(800);
        ddViewPager.setPageTransformer(true, new DefaultPageTransformer());
        ddViewPager.setAdapter(ddAdapter);

        addView(ddViewPager);

        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(context, 48));
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        CircleIndicator circleIndicator = new CircleIndicator(context);
        circleIndicator.setGravity(Gravity.CENTER);
        circleIndicator.setLayoutParams(params1);
        circleIndicator.setViewPager(ddViewPager);

        addView(circleIndicator);
    }

    public void update(List<String> urls) {
        if(urls == null || urls.isEmpty()) {
            return;
        }
        if(urls.size() == 1 && isCanLoop()) {
            setCanLoop(false);
        }
        DdAdapter ddAdapter = (DdAdapter) ddViewPager.getAdapter();
        ddAdapter.update(urls);
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setIsLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public void startLoop() {
        if (isCanLoop() && !isLooping()) {
            setIsLooping(true);
            sendLoopMessage();
        }
    }

    public void stopLoop() {
        if (isCanLoop() && isLooping()) {
            setIsLooping(false);
            removeLoopMessage();
        }
    }

    private void scrollToNext() {
        int count = ddViewPager.getAdapter().getCount();
        int index = ddViewPager.getCurrentItem();
        if(index == (count - 1)) {
            ddViewPager.setCurrentItem(0);
        } else {
            ddViewPager.setCurrentItem(index + 1);
        }
    }

    private void sendLoopMessage() {
        loopHandler.sendEmptyMessageDelayed(SCROLL_WHAT, LOOP_DELAY_TIME);
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