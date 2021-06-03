package com.hua.weather.net;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create By Spring 2021/5/28 20:27
 */
public class RetrofitManager {
    //定义OKHttpClient类
    private static OkHttpClient mOkHttpClient;
    //定义Retrofit类
    private static  Retrofit mRetrofit;

    //得到Retrofit对象的实例
    public static Retrofit getRetrofit(String url){
        if(mRetrofit != null) return mRetrofit;
        //创建OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                // 设置超时时间
                .connectTimeout(10*1000,TimeUnit.MILLISECONDS)
                // 增加拦截器
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(@NotNull Chain chain) throws IOException {
//                        return null;
//                    }
//                })
                .build();



        //构建Retrofit
        mRetrofit = new Retrofit.Builder()
                //配置服务器地址
                .baseUrl(url)
                // 配置转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create())
                // 配置回调库，采用RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 设置OKHttpClient为网络客户端
                .client(mOkHttpClient)
                .build();
        return mRetrofit;
    }
}
