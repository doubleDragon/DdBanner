package com.wsl.library.demo;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by wsl on 16-9-27.
 */

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initUIL();
    }

    public void initUIL() {
        // options is used for images smaller in size (5kb-150kb)
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_photo_black_48dp)
                .showImageOnFail(R.drawable.ic_broken_image_black_48dp)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .defaultDisplayImageOptions(options)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

    }
}
