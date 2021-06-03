package com.hua.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.hua.weather.R;
import com.hua.weather.adapter.SplashPageAdapter;
import com.hua.weather.base.BaseActivity;
import com.hua.weather.global.Constants;
import com.hua.weather.utils.SPHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity {
    private CirclePageIndicator cpi;
    private ViewPager vp;
    private Button btnLogin;
    private List<ImageView> vpImages = new ArrayList<>();

    private SPHelper spHelper;

    private SplashPageAdapter splashPageAdapter;
    private int imgIds[] = {R.mipmap.main_bg_sunny,
            R.mipmap.main_bg_cloudly,
            R.mipmap.main_bg_rain,
            R.mipmap.main_bg_snow,
            R.mipmap.main_bg_over_cast,
            R.mipmap.main_bg_thunder_lighting,
    };//向导页面的四张图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
        loadData();
        addListener();
        checkSP();
    }

    private void checkSP() {
        spHelper = SPHelper.getInstant(WelcomeActivity.this);
        boolean hasLogin = spHelper.getBooleanFromSP(WelcomeActivity.this,Constants.FIRST_LOGIN);
        if(hasLogin){
            enterMainActivity();
        }
    }


    private void enterMainActivity(){
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            spHelper.putData2SP(WelcomeActivity.this, Constants.FIRST_LOGIN,true);
            enterMainActivity();
        }
    }

    @Override
    public void loadData() {
        for (int i = 0; i < imgIds.length; i++) {
            ImageView iv = new ImageView(WelcomeActivity.this);
            iv.setImageResource(imgIds[i]);
            vpImages.add(iv);
        }
        splashPageAdapter = new SplashPageAdapter(vpImages);
        vp.setAdapter(splashPageAdapter);
        cpi.setViewPager(vp, 0);
    }

    @Override
    public void initView() {
        vp = findViewById(R.id.vp);
        cpi = findViewById(R.id.cpi);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void addListener() {
        cpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                btnLogin.setVisibility((position == imgIds.length - 1) ?
                        View.VISIBLE : View.INVISIBLE);


            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgIds.length - 1) {


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}