package com.hua.weather.net;

import com.hua.weather.bean.WeatherInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Create By Spring 2021/5/28 20:18
 */
public interface IWeatherService {

    ///////////////////////////////////////////////////////////////////////////
    // GET /data/cityinfo/101010100.html HTTP/1.1
    // Host: www.weather.com.cn
    ///////////////////////////////////////////////////////////////////////////

    @GET
    Observable<WeatherInfo> getWeatherInfo(@Url String url);
}
