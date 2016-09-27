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
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_dd_adapter_second, parent, false);
    }

    @Override
    protected void onBindView(int position, DdViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.imageView.setImageResource(R.mipmap.ic_dd_default);
//        Picasso.with(getContext())
//                .load(getItem(position))
//                .placeholder(R.mipmap.ic_dd_default)
//                .error(R.mipmap.ic_dd_default)
//                .into(holder.imageView);
        ImageLoader.getInstance().displayImage(getItem(position), holder.imageView, options);
    }

    @Override
    protected DdViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    private class ViewHolder extends DdViewHolder implements View.OnClickListener{

        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view;
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            String url = getItem(position);
            Log.d("test", "click position: " + position + "--url: " + url);
        }
    }
}