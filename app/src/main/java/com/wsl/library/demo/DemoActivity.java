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
//        urls.add("http://images.qingsongchou.com/files/banner/14593422870267460258.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14593335342944e01017.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/1458201861751491d645.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14588996149673db9641.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");
//        urls.add("http://images.qingsongchou.com/files/banner/14592338992009010c85.jpg");

        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/14602972143661a36c34.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/14602972311425a7b4e7.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/14602972368205f434e2.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/1460297246903795dd9b.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/14606425151774a6131f.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/146064252811031232.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/1460642674276737eb97.jpg");
        urls.add("http://images.qingsongchou.com/files/qschou.com/project/17/a7f4be14-6103-46c9-af0e-c68bfad257cf/1460642680673299678e.jpg");


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