package com.hua.weather.net;

import com.hua.weather.bean.WeatherInfo;

import io.reactivex.Observable;


/**
 * Create By Spring 2021/5/28 20:39
 */
public class RxWeatherManager {
    public static String WEATHER_BASE_URL = "http://www.weather.com.cn/data/cityinfo/";

    public static Observable<WeatherInfo> getWeatherInfo(String url, String cityCode){
        IWeatherService api = RetrofitManager.getRetrofit(url).create(IWeatherService.class);
        return api.getWeatherInfo(url + cityCode + ".html");
    }




}
