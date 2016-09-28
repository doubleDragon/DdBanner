package com.wsl.library.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-4-5.
 */
public abstract class DdAdapter<T> extends PagerAdapter {

    private List<T> data;

    private Context context;
    private LayoutInflater inflater;

    private DdBanner ddBanner;

    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent, int position);

    public DdAdapter(Context context) {
        this(context, null);
    }

    public DdAdapter(Context context, List<T> list) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            this.data.addAll(list);
        }
    }

    public void setDdBanner(DdBanner ddBanner) {
        this.ddBanner = ddBanner;
        configBanner();
    }

    protected Context getContext() {
        return context;
    }

    protected T getItem(int position) {
        if(isLoop()) {
            position = getPositionOffset(position);
        }
        return data.get(position);
    }

    public void update(List<T> list) {
        if (list == null) {
            return;
        }
        this.data.clear();
        this.data.addAll(list);
        this.notifyDataSetChanged();

        configBanner();
    }

    private void configBanner() {
        this.ddBanner.setCanLoop(data.size() > 1);
        if (ddBanner.isLoop()) {
            ddBanner.startLoop();
        }
    }

    @Override
    public int getCount() {
        int size = data.size();
        if (!isLoop()) {
            return size;
        } else {
            if (size <= 1) {
                return size;
            }
            return Integer.MAX_VALUE - size;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = onCreateView(inflater, container, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;//fix a bug, replace one web url to another web url
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

    private int getPositionOffset(int position) {
        if (!isLoop()) {
            return position;
        }
        int size = data.size();
        if (size == 0) {
            return 0;
        }
        return position % size;
    }

    private boolean isLoop() {
        if (ddBanner == null) {
            return false;
        }
        return ddBanner.isLoop();
    }
}