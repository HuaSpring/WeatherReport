package com.hua.weather.activity;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hua.weather.R;
import com.hua.weather.bean.WeatherInfo;
import com.hua.weather.databinding.FragmentCityitemDetailBinding;
import com.hua.weather.global.Constants;
import com.hua.weather.handler.WeatherHandler;
import com.hua.weather.net.RxWeatherManager;
import com.hua.weather.utils.SPHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hua.weather.handler.WeatherHandler.MSG_WEATHER_ERROR;
import static com.hua.weather.handler.WeatherHandler.MSG_WEATHER_OK;

/**
 * A fragment representing a single city_item detail screen.
 * This fragment is either contained in a {@link CityitemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class CityitemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_CITY_NAME = "item_name";
    public static final String ARG_CITY_CODE = "item_code";

    /**
     * The placeholder content this fragment is presenting.
     */
    private String cityInfo;
    private String cityCode;
    private FragmentCityitemDetailBinding binding;

    private SPHelper spHelper;
    private WeatherHandler mWeatherHandler;
    private FloatingActionButton fab;
    private TextView tvInfo;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityitemDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_CITY_NAME)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            cityInfo = getArguments().getString(ARG_CITY_NAME);
            cityCode = getArguments().getString(ARG_CITY_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCityitemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        CollapsingToolbarLayout toolbarLayout = rootView.findViewById(R.id.toolbar_layout);

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        if (cityInfo != null) {
            tvInfo = binding.cityitemDetail;
            tvInfo.setText(cityInfo + ":" + cityCode);
            if (toolbarLayout != null) {
                toolbarLayout.setTitle(cityInfo);
            }
        }
        fab = binding.fab;

        fab.setOnClickListener(v -> {
            rxWeather();
        });

        mWeatherHandler = new WeatherHandler(Looper.getMainLooper(), tvInfo, null);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        spHelper = SPHelper.getInstant(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void rxWeather() {
        Observable<WeatherInfo> weatherInfoObservable = RxWeatherManager.getWeatherInfo(
                RxWeatherManager.WEATHER_BASE_URL, cityCode
        );
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

                            spHelper.putData2SP(getContext(), Constants.LAST_CITY_WEATHER, weatherinfo.getWeather());
                            spHelper.putData2SP(getContext(), Constants.LAST_CITY_TEMP1, weatherinfo.getTemp1());
                            spHelper.putData2SP(getContext(), Constants.LAST_CITY_TEMP2, weatherinfo.getTemp2());

                            Log.d("HHHH", "CityDetialFragment rxWeather: " + weatherinfo.toString());
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