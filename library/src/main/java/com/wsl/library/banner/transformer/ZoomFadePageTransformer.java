package com.wsl.library.banner.transformer;

import android.view.View;

public class ZoomFadePageTransformer extends DdPageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setTranslationX(-view.getWidth() * position);

        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setScaleX(1 + position);
        view.setScaleY(1 + position);

        view.setAlpha(1 + position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setTranslationX(-view.getWidth() * position);

        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setScaleX(1 - position);
        view.setScaleY(1 - position);
        view.setAlpha(1 - position);
    }

}