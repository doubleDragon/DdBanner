package com.wsl.library.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.wsl.library.banner.DbImageView;
import com.wsl.library.banner.DdBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-4-5.
 */
public class DemoActivity extends AppCompatActivity {

    DdBanner ddViewPager;
    DemoAdapter ddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo);

        initViews();
    }

    private void initViews() {
        List<String> urls= new ArrayList<>();
        urls.add("http://images.qingsongchou.com/files/banner/14593422870267460258.jpg");
        urls.add("http://images.qingsongchou.com/files/banner/14593335342944e01017.jpg");
        urls.add("http://images.qingsongchou.com/files/banner/1458201861751491d645.jpg");
        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");

        ddAdapter = new DemoAdapter(this, urls);
        ddViewPager = (DdBanner) findViewById(R.id.banner);
        ddViewPager.setCanLoop(true);
        ddViewPager.setAdapter(ddAdapter);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ddViewPager.isLooping()) {
                    ddViewPager.stopLoop();
                } else {
                    ddViewPager.startLoop();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ddViewPager.startLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ddViewPager.stopLoop();
    }
}