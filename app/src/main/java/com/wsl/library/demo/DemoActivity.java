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
public class DemoActivity extends AppCompatActivity{

    DdBanner ddViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo);

        initViews();
    }

    private void initViews() {
        List<String> urls= new ArrayList<>();
        urls.add("http://pic.to8to.com/attch/day_160218/20160218_d968438a2434b62ba59dH7q5KEzTS6OH.png");
        urls.add("http://www.52ij.com/uploads/allimg/160317/1110104P8-4.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=4236942158,2307642402&fm=21&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=4031528430,1490936196&fm=21&gp=0.jpg");
        urls.add("http://img3.redocn.com/20100604/20100604_c70874ec96a92209ac38aph7WZPGfxe3.jpg");

        ddViewPager = (DdBanner) findViewById(R.id.banner);
        ddViewPager.setCanLoop(true);
        ddViewPager.update(urls);

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