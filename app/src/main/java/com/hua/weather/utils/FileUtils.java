package com.hua.weather.utils;

import android.content.Context;

import com.hua.weather.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * author by spring. 2021/05/28 11:41
 */
public class FileUtils {

    public static String parseCityList(Context ctx, int rawResId) {
        InputStream is = ctx.getResources().openRawResource(rawResId);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(is, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }

}
