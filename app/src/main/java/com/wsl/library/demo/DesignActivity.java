package com.wsl.library.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wsl.library.banner.DdBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-8-1.
 */

public class DesignActivity extends AppCompatActivity{

    DdBanner ddBanner;
    DemoAdapter ddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_design);

        initViews();
    }

    private void initViews() {
        List<String> urls= new ArrayList<>();
        urls.add("http://img5.imgtn.bdimg.com/it/u=1746418361,2823897370&fm=21&gp=0.jpg");

        urls.add("http://img5.imgtn.bdimg.com/it/u=1479621666,13296461&fm=21&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3614602665,2140950684&fm=21&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2562925395,761784532&fm=21&gp=0.jpg");
//        urls.add("http://img5.imgtn.bdimg.com/it/u=1746418361,2823897370&fm=21&gp=0.jpg");
//        urls.add("http://img1.imgtn.bdimg.com/it/u=4229814767,1238709582&fm=21&gp=0.jpg");
//        urls.add("http://img4.imgtn.bdimg.com/it/u=3734860925,1898675427&fm=21&gp=0.jpg");
//        urls.add("http://img0.imgtn.bdimg.com/it/u=1274452826,1426086346&fm=21&gp=0.jpg");
//        urls.add("http://img3.imgtn.bdimg.com/it/u=2598241770,261736119&fm=21&gp=0.jpg");

        ddAdapter = new DemoAdapter(this, urls);
        ddBanner = (DdBanner) findViewById(R.id.banner);
        if(ddBanner == null) {
            throw new RuntimeException("banner is null");
        }
        ddBanner.setLoop(false);
        ddBanner.setAdapter(ddAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ddBanner.startLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ddBanner.stopLoop();
    }
}
