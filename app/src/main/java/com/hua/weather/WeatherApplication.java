package com.hua.weather;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.hua.weather.bean.CityInfoBean;
import com.hua.weather.utils.FileUtils;
import com.hua.weather.utils.ThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherApplication extends Application {
    public static final String TAG = "HHHH";
    // nanyang:10010, zhengzhou:10011......
    public static HashMap<String,String> cityMaps = new HashMap<>();
    // henan { nanyang,luoyang,zhengzhou....}
    public static HashMap<String,List<String>> provinceCityMaps = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                parseCityList(R.raw.citylist);
            }
        });

    }

    private void parseCityList(int rawResId) {
        String s = FileUtils.parseCityList(this, rawResId);
        Gson gson = new Gson();
        CityInfoBean cityInfoBean = gson.fromJson(s, CityInfoBean.class);
        List<CityInfoBean.ProvincesBean> provinces = cityInfoBean.getProvinces();
        for(CityInfoBean.ProvincesBean provincesBean:provinces){

            List<CityInfoBean.ProvincesBean.CityBean> cityList = provincesBean.getCity();
            List<String> cityL = new ArrayList<>();
            for(CityInfoBean.ProvincesBean.CityBean cityBean:cityList) {
                String cityName = cityBean.getCityName();
                String cityCode = cityBean.getCityCode();
                cityMaps.put(cityName, cityCode);

                cityL.add(cityName);
            }
            provinceCityMaps.put(provincesBean.getProvince(),cityL);
        }
    }
}
