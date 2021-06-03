package com.hua.weather.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hua.weather.bean.WeatherInfo;

/**
 * Create By Spring 2021/5/31 16:45
 */
public class WeatherHandler extends Handler {
    public static final int MSG_WEATHER_OK = 1;
    public static final int MSG_WEATHER_ERROR = MSG_WEATHER_OK + 1;
    public static final int MSG_UPDATE_CITY_NAME = MSG_WEATHER_ERROR + 1;
    public static final int MSG_UPDATE_CITY_ERROR = MSG_UPDATE_CITY_NAME + 1;


    private TextView tvResult;
    private Button btnCommon;

    public WeatherHandler(Looper looper, TextView tvResult, Button btnCommon) {
        this.tvResult = tvResult;
        this.btnCommon = btnCommon;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Log.d("HHHH", "WeatherHandler handleMessage: " + msg.what);
        switch (msg.what) {
            case MSG_WEATHER_OK:
                WeatherInfo.WeatherinfoBean weatherinfo = (WeatherInfo.WeatherinfoBean) msg.obj;
                tvResult.setText(weatherinfo.toString());
                break;
            case MSG_WEATHER_ERROR:
                tvResult.setText((String) msg.obj);
                break;
            case MSG_UPDATE_CITY_NAME:
            case MSG_UPDATE_CITY_ERROR:
                btnCommon.setText((String) msg.obj);
                break;
        }
    }
}

