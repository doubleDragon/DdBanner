package com.wsl.library.demo;

import android.content.Context;

import com.wsl.library.banner.DdAdapter;

import java.util.List;

/**
 * Created by wsl on 16-4-12.
 */
public class DemoAdapter extends DdAdapter{

    public DemoAdapter(Context context) {
        super(context);
    }
    public DemoAdapter(Context context, List<String> urls) {
        super(context, urls);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dd_adapter;
    }

    @Override
    protected int getLayoutImageId() {
        return R.id.image;
    }

    @Override
    protected int getDefaultImageId() {
        return R.mipmap.ic_dd_default;
    }
}
