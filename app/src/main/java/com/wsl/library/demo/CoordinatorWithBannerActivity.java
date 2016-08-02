package com.wsl.library.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wsl.library.banner.DdBannerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-8-1.
 */

public class CoordinatorWithBannerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_banner);
        initViews();
    }

    private void initViews() {
        List<String> urls= new ArrayList<>();
        urls.add("http://img5.imgtn.bdimg.com/it/u=1746418361,2823897370&fm=21&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=1479621666,13296461&fm=21&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3614602665,2140950684&fm=21&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2562925395,761784532&fm=21&gp=0.jpg");
        BbAdapter adapter = new BbAdapter(this, urls);

        mViewPager = (ViewPager) findViewById(R.id.dd_banner_viewpager);
        mViewPager.setAdapter(adapter);

        DdBannerIndicator indicator = (DdBannerIndicator) findViewById(R.id.dd_banner_indicator);
        indicator.setupViewpager(mViewPager);
    }
}