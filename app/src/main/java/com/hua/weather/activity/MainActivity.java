package com.hua.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.weather.R;
import com.hua.weather.WeatherApplication;
import com.hua.weather.base.BaseActivity;
import com.hua.weather.bean.GeoCityBean;
import com.hua.weather.bean.WeatherInfo;
import com.hua.weather.global.Constants;
import com.hua.weather.handler.WeatherHandler;
import com.hua.weather.net.HttpHelper;
import com.hua.weather.net.RxLocationManager;
import com.hua.weather.net.RxWeatherManager;
import com.hua.weather.utils.SPHelper;
import com.hua.weather.utils.ThreadPool;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hua.weather.handler.WeatherHandler.MSG_UPDATE_CITY_ERROR;
import static com.hua.weather.handler.WeatherHandler.MSG_UPDATE_CITY_NAME;
import static com.hua.weather.handler.WeatherHandler.MSG_WEATHER_ERROR;
import static com.hua.weather.handler.WeatherHandler.MSG_WEATHER_OK;

public class MainActivity extends BaseActivity {
    public static final String TAG = "HHHH";

    private TextView tvResult;
    private Button btnNanyang;
    private Button btn_select_city;
    private ImageView ivAutoLocation;
    private WeatherHandler mWeatherHandler;
    private SPHelper spHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
        initHandler();

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initHandler() {
        HandlerThread ht = new HandlerThread("weather", Process.THREAD_PRIORITY_DEFAULT);
        mWeatherHandler = new WeatherHandler(ht.getLooper(), tvResult, btnNanyang);
        ht.start();
    }

    @Override
    public void loadData() {
        spHelper = SPHelper.getInstant(MainActivity.this);
        String currentCityName = spHelper.getStringFromSP(MainActivity.this,
                Constants.LAST_CITY_NAME);
        if (!TextUtils.isEmpty(currentCityName)) {
            btnNanyang.setText(currentCityName);
        }

        String lastCityWeather = spHelper.getStringFromSP(MainActivity.this, Constants.LAST_CITY_WEATHER);
        String lastCityTemp1 = spHelper.getStringFromSP(MainActivity.this, Constants.LAST_CITY_TEMP1);
        String lastCityTemp2 = spHelper.getStringFromSP(MainActivity.this, Constants.LAST_CITY_TEMP2);

        StringBuilder sb = new StringBuilder();
        sb.append(" Weather: ").append(lastCityWeather).append("\r\n")
                .append(" Temp1 : ").append(lastCityTemp1).append("\r\n")
                .append(" Temp2 : ").append(lastCityTemp2).append("\r\n");
        tvResult.setText(sb.toString());
    }

    @Override
    public void initView() {
        tvResult = findViewById(R.id.tvResult);
        btnNanyang = findViewById(R.id.btnNanyang);
        btn_select_city = findViewById(R.id.btn_select_city);
        ivAutoLocation = findViewById(R.id.ivAutoLocation);
    }

    @Override
    public void addListener() {
        btnNanyang.setOnClickListener(this);
        btn_select_city.setOnClickListener(this);
        ivAutoLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNanyang:
                rxWeather();
//                httpWeather();
                break;

            case R.id.btn_select_city:
                startActivity(new Intent(MainActivity.this, CityitemDetailHostActivity.class));
                break;

            case R.id.ivAutoLocation:
                rxAutoCity();
                break;


            default:
                break;
        }
    }


    private void httpWeather() {
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                new HttpHelper().httpGet("http://www.weather.com.cn/data/cityinfo/101010100.html", "");
            }
        });

    }


    private void rxAutoCity() {
        Observable<GeoCityBean> geoCityBeanObservable = RxLocationManager.getCityName(
                RxLocationManager.LOCATION_BASE_URL,
//                24.343221, 112.601253);
                "24.343221", "112.601253");

        geoCityBeanObservable.subscribeOn(Schedulers.io())
                .subscribe(new Observer<GeoCityBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeoCityBean value) {
                        if ("OK".equals(value.getStatus())) {
                            GeoCityBean.ResultBean.AddressComponentBean addressComponent = value.getResult().getAddressComponent();
                            String province = addressComponent.getProvince();
                            String city = addressComponent.getCity();
                            String province_city = province + " -- " + city;
                            Message msg = Message.obtain();
                            msg.what = MSG_UPDATE_CITY_NAME;
                            msg.obj = province_city;
                            mWeatherHandler.sendMessage(msg);

                            if (city.endsWith("市")) { // because in  res/raw/citylist.txt , cityName is simple,doesn't not contain "市"
                                city = city.substring(0, city.length() - 1);
                                Log.d(TAG, "OkCity : " + city);
                            }
                            spHelper.putData2SP(MainActivity.this, Constants.LAST_CITY_NAME, city);
                            spHelper.putData2SP(MainActivity.this, Constants.LAST_PROVINCE_NAME, province);

                            spHelper.putData2SP(MainActivity.this, Constants.LAST_PROVINCE_CITY, province_city);

                            Log.d(TAG, "rxAutoCity: " + province_city);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Message msg = Message.obtain();
                        msg.what = MSG_UPDATE_CITY_ERROR;
                        msg.obj = e.getMessage();
                        mWeatherHandler.sendMessage(msg);
                        Log.d(TAG, "autoCity  Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void rxWeather() {
        String lastCityName = spHelper.getStringFromSP(MainActivity.this, Constants.LAST_CITY_NAME);
        if (TextUtils.isEmpty(lastCityName)) lastCityName = "南阳";
        Observable<WeatherInfo> weatherInfoObservable = RxWeatherManager.getWeatherInfo(RxWeatherManager.WEATHER_BASE_URL,
                WeatherApplication.cityMaps.get(lastCityName));
        weatherInfoObservable.subscribeOn(Schedulers.computation())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherInfo value) {
                        WeatherInfo.WeatherinfoBean weatherinfo = value.getWeatherinfo();
                        if (weatherinfo != null) {
                            Message msg = Message.obtain();
                            msg.what = MSG_WEATHER_OK;
                            msg.obj = weatherinfo;
                            mWeatherHandler.sendMessage(msg);

                            spHelper.putData2SP(MainActivity.this, Constants.LAST_CITY_WEATHER, weatherinfo.getWeather());
                            spHelper.putData2SP(MainActivity.this, Constants.LAST_CITY_TEMP1, weatherinfo.getTemp1());
                            spHelper.putData2SP(MainActivity.this, Constants.LAST_CITY_TEMP2, weatherinfo.getTemp2());

                            Log.d(TAG, "rxWeather: " + weatherinfo.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Message msg = Message.obtain();
                        msg.what = MSG_WEATHER_ERROR;
                        msg.obj = e.getMessage();
                        mWeatherHandler.sendMessage(msg);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}