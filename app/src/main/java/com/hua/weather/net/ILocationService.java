package com.hua.weather.net;

import com.hua.weather.bean.GeoCityBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

/**
 * Create By Spring 2021/5/29 09:15
 */
public interface ILocationService {
    //http://api.map.baidu.com/geocoder?location=24.343221,112.601253&output=json

    @GET("geocoder?")
    Observable<GeoCityBean> getCityName(@Query("location") String latlon, @Query("output") String xmlJson);



}
