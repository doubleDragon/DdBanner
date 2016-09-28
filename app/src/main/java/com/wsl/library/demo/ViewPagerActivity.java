package com.wsl.library.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wsl on 16-9-28.
 */

public class ViewPagerActivity extends AppCompatActivity {

    List<String> data;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_dd_default)
                .showImageOnFail(R.mipmap.ic_dd_default)
                .showImageForEmptyUri(R.mipmap.ic_dd_default)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        data = new ArrayList<>();
        data.add("http://i4.piimg.com/567571/1cb06f22d0abc62c.png");
        data.add("http://i4.piimg.com/567571/1cb06f22d0abc62c.png");
        data.add("http://i4.piimg.com/567571/1cb06f22d0abc62c.png");
        data.add("http://i4.piimg.com/567571/1cb06f22d0abc62c.png");

        viewPager.setAdapter(new TestAdapter());
    }

    private class TestAdapter extends PagerAdapter {

        private String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d("test", "instantiateItem position: " + position + "---count: " + container.getChildCount());
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item_dd_adapter_second, container, false);
            ImageView imageView = (ImageView) view;
            ImageLoader.getInstance().displayImage(getItem(position), imageView, options);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d("test", "destroyItem position: " + position + "---count: " + container.getChildCount());
            container.removeView((View)object);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
