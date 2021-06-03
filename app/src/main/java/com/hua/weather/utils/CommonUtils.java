package com.hua.weather.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class CommonUtils {

    public static void autoSetClickListener(View containerView, Class type, View.OnClickListener listener) {
        autoSetClickListener(containerView, type, listener, 0);
    }

    /**
     * 自动遍历指定容器的所有子view,查找指定类型的控件,统一设置其clickListener
     *
     * @param type                要设置点击事件的控件类型, 如 Button.class
     * @param doubleClickDuration 同一个控件连续点击时间间隔,单位: 毫秒
     */
    public static void autoSetClickListener(View containerView, Class type, View.OnClickListener listener, int doubleClickDuration) {
        if (containerView == null || type == null) return;
        // long startTs = System.currentTimeMillis();
        if (containerView instanceof ViewGroup) {
            int childCount = ((ViewGroup) containerView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = ((ViewGroup) containerView).getChildAt(i);
                if (child instanceof ViewGroup) {
                    autoSetClickListener(child, type, listener, doubleClickDuration);
                } else if (child.getClass() == type) {
                    child.setOnClickListener(new HookViewClickUtil.OnClickListenerProxy(listener, doubleClickDuration));
                }
            }
        } else if (containerView.getClass() == type) {
            containerView.setOnClickListener(new HookViewClickUtil.OnClickListenerProxy(listener, doubleClickDuration));
        }
    }


    public static DisplayMetrics getScreenMetrics(Context ctx){
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}
