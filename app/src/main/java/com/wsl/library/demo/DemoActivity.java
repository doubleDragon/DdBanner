package com.wsl.library.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wsl.library.banner.DdBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsl on 16-4-5.
 */
public class DemoActivity extends AppCompatActivity {

    DdBanner ddBanner;
    DemoAdapter ddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo);

        initViews();
    }

    private void checkUrls() {
        List<String> urls= new ArrayList<>();
        urls.add("http://images.qingsongchou.com/files/banner/14593422870267460258.jpg");
        urls.add("http://images.qingsongchou.com/files/banner/14593335342944e01017.jpg");
        ddAdapter.update(urls);
    }

    private void initViews() {
        List<String> urls= new ArrayList<>();
        urls.add("http://images.qingsongchou.com/files/banner/14593422870267460258.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14593335342944e01017.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/1458201861751491d645.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");


        ddAdapter = new DemoAdapter(this, urls);
        ddBanner = (DdBanner) findViewById(R.id.banner);
        ddBanner.setLoop(true);
        ddBanner.setAdapter(ddAdapter);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ddBanner.isLooping()) {
                    ddBanner.stopLoop();
                } else {
                    ddBanner.startLoop();
                }
            }
        });
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUrls();
            }
        });
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