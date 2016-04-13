package com.wsl.library.banner;

import android.view.View;

/**
 * Created by wsl on 16-4-13.
 */
public class DdViewHolder {

    View itemView;
    int position;

    public DdViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}