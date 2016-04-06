package com.wsl.library.banner.transformer;

import android.view.View;

public class FadePageTransformer extends DdPageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setTranslationX(-view.getWidth() * position);
        view.setAlpha(1 + position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setTranslationX(-view.getWidth() * position);
        view.setAlpha(1 - position);
    }

}