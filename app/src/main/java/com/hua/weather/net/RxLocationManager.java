package com.hua.weather.net;

import com.hua.weather.bean.GeoCityBean;

import io.reactivex.Observable;

/**
 * Create By Spring 2021/5/29 09:13
 */
public class RxLocationManager {
    // http://api.map.baidu.com/geocoder?location=24.343221,112.601253&output=json

    public static String LOCATION_BASE_URL = "http://api.map.baidu.com/";

    public static Observable<GeoCityBean> getCityName(String url, double lat, double lon) {
        ILocationService api = RetrofitManager.getRetrofit(url).create(ILocationService.class);
        return api.getCityName(lat + "," + lon, "json");
    }


    public static Observable<GeoCityBean> getCityName(String url, String lat, String lon) {
        ILocationService api = RetrofitManager.getRetrofit(url).create(ILocationService.class);
        return api.getCityName(lat + "," + lon, "json");
    }
}
