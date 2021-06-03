package com.hua.weather.utils;

import android.content.Context;
import android.util.Log;

import com.hua.weather.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Create by Spring on 2020/9/1 13:26
 */
public class LoginCheckUtils {

    /**
     *
     * @param ctx  Context
     * @param id   The number of the Stu
     * @return
     */
    public static boolean checkLogin(Context ctx, String id) {
        InputStream is = ctx.getResources().openRawResource(R.raw.idsnames);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(is, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                if(split[0].trim().equalsIgnoreCase(id))   // 这里因为 python 分割的原因，要去掉多余的空格
                    return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }


    public static String getNameByID(Context ctx, String id) {
        InputStream is = ctx.getResources().openRawResource(R.raw.idsnames);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(is, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.split(":")[0].trim().equals(id))
                    return line.split(":")[1].trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Not found";

    }
}
