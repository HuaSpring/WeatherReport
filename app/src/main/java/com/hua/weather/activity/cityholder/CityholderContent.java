package com.hua.weather.activity.cityholder;

import com.hua.weather.WeatherApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Create By Spring 2021/5/31 15:32
 * <p>
 * "province": "广东",
 * "city": [
 * {
 * "cityName": "广州",
 * "cityCode": "101280101"
 * },
 * {
 * "cityName": "惠州",
 * "cityCode": "101280301"
 * },
 * {
 * "cityName": "梅州",
 * "cityCode": "101280401"
 * },
 * {
 * "cityName": "汕头",
 * "cityCode": "101280501"
 * }
 * ]
 */
public class CityholderContent {

    public static final List<CityHolderItem> ITEMS = new ArrayList<CityHolderItem>();

    // nanyang:10010, zhengzhou:10011......
    public static HashMap<String, String> cityMaps = WeatherApplication.cityMaps;
    // henan { nanyang,luoyang,zhengzhou....}
    public static HashMap<String, List<String>> provinceCityMaps = WeatherApplication.provinceCityMaps;

    // City Or Province Nums
    private static final int COUNT = provinceCityMaps.size();


    static {
        Set<String> provinceKeys = provinceCityMaps.keySet();
        for (String province : provinceKeys) {
            List<String> cityL = provinceCityMaps.get(province);
            assert cityL != null;
            for (String city : cityL)
                addItem(createCityHolderItem(city, cityMaps.get(city)));
        }
    }


    private static void addItem(CityholderContent.CityHolderItem item) {
        ITEMS.add(item);
    }

    private static CityholderContent.CityHolderItem createCityHolderItem(String cityName, String cityCode) {
        return new CityholderContent.CityHolderItem(cityName, cityCode, makeDetails(cityName));
    }

    private static String makeDetails(String city) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(city).append("\r\n");
        for (int i = 0; i < (int) (Math.random() * 10); i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static class CityHolderItem {
        public final String cityName;
        public final String cityCode;
        public final String details;

        public CityHolderItem(String cityName, String cityCode, String details) {
            this.cityName = cityName;
            this.cityCode = cityCode;
            this.details = details;
        }

        @Override
        public String toString() {
            return cityName;
        }
    }


}
