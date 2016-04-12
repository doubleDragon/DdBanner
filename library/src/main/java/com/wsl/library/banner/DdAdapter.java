package com.wsl.library.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-4-5.
 */
public abstract class DdAdapter extends PagerAdapter {

    private List<String> urls;

    private Context context;
    private LayoutInflater inflater;

    protected abstract int getLayoutId();
    protected abstract int getLayoutImageId();
    protected abstract int getDefaultImageId();

    public DdAdapter(Context context) {
        this(context, null);
    }

    public DdAdapter(Context context, List<String> urls) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.urls = new ArrayList<>();
        if(urls != null && !urls.isEmpty()) {
            this.urls.addAll(urls);
        }
    }

    public void update(List<String> urls) {
        if (urls == null) {
            return;
        }
        this.urls.clear();
        this.urls.addAll(urls);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getView(position, container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * parent child index
     *
     * @param position, [0 1 2]
     * @return
     */
    private int getChildOffset(int position) {
        return position % 3;
    }

    private View getView(int position, ViewGroup parent) {
        int offset = getChildOffset(position);
        View child = parent.getChildAt(offset);
        if (child == null) {
            child = inflater.inflate(getLayoutId(), parent, false);
            parent.addView(child);
        }
        ImageView imageView = (ImageView) child.findViewById(getLayoutImageId());
        if(imageView != null) {
            Picasso.with(context)
                    .load(urls.get(position))
                    .placeholder(getDefaultImageId())
                    .error(getDefaultImageId())
                    .into(imageView);
        }
        return child;
    }
}