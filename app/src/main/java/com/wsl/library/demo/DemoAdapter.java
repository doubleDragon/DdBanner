package com.wsl.library.demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wsl.library.banner.DdAdapter;
import com.wsl.library.banner.DdViewHolder;

import java.util.List;

/**
 * Created by wsl on 16-4-12.
 */
public class DemoAdapter extends DdAdapter<String>{


    public DemoAdapter(Context context) {
        super(context);
    }

    public DemoAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_dd_adapter_second, parent, false);
    }

    @Override
    protected void onBindView(int position, DdViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.imageView.setImageResource(R.mipmap.ic_dd_default);
        Picasso.with(getContext())
                .load(getItem(position))
                .placeholder(R.mipmap.ic_dd_default)
                .error(R.mipmap.ic_dd_default)
                .into(holder.imageView);
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