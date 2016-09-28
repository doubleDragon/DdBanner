package com.wsl.library.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;
import com.wsl.library.banner.DdAdapter;
import com.wsl.library.banner.DdViewHolder;

import java.util.List;

/**
 * Created by wsl on 16-4-12.
 */
public class DemoAdapter extends DdAdapter<String>{

    private DisplayImageOptions options;

    public DemoAdapter(Context context) {
        this(context, null);
    }

    public DemoAdapter(Context context, List<String> list) {
        super(context, list);
        init();
    }

    private void init() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_dd_default)
                .showImageOnFail(R.mipmap.ic_dd_default)
                .showImageForEmptyUri(R.mipmap.ic_dd_default)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.item_dd_adapter_second, parent, false);
        ImageView imageView = (ImageView) view;
        ImageLoader.getInstance().displayImage(getItem(position), imageView, options);
        return view;
    }
}