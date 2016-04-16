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

    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    protected abstract DdViewHolder onCreateHolder(View itemView);

    protected abstract void onBindView(int position, DdViewHolder viewHolder);

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
        if(ddBanner.isLoop()) {
            ddBanner.startLoop();
        }
    }

    @Override
    public int getCount() {
        int size = data.size();
        if(!isLoop()) {
            return size;
        } else {
            if(size <= 1) {
                return size;
            }
            return Integer.MAX_VALUE - size;
        }
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

    private int getPositionOffset(int position) {
        if(!isLoop()) {
            return position;
        }
        int size = data.size();
        if(size == 0) {
            return 0;
        }
        return position % size;
    }

    private boolean isLoop() {
        if(ddBanner == null) {
            return false;
        }
        return ddBanner.isLoop();
    }

    private View getView(int position, ViewGroup parent) {
        int offset = getChildOffset(position);
        View child = parent.getChildAt(offset);
        DdViewHolder viewHolder;
        if (child == null) {
            child = onCreateView(inflater, parent);
            viewHolder = onCreateHolder(child);
            child.setTag(viewHolder);
            parent.addView(child);
        } else {
            viewHolder = (DdViewHolder) child.getTag();
        }
        position = getPositionOffset(position);
        viewHolder.setPosition(position);
        onBindView(position, viewHolder);
        return child;
    }
}