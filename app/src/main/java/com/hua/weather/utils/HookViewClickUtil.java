package com.hua.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * hook指定view的点击事件,替换为自定义的 {@link OnClickListenerProxy}, 以便进行额外的统一操作, 如埋点等
 * 使用方法:
 * 1. HookViewClickUtil.hookView(btn);
 * 2. 直接使用 {@link OnClickListenerProxy}
 */
public class HookViewClickUtil {
    private static final String TAG = "HookViewClickUtil";

    public static HookViewClickUtil getInstance() {
        return UtilHolder.mHookViewClickUtil;
    }

    private static class UtilHolder {
        private static HookViewClickUtil mHookViewClickUtil = new HookViewClickUtil();
    }

    /**
     * hook指定view的点击事件,替换为自定义的 {@link OnClickListenerProxy}
     *
     * @param view               要进行hook的目标view
     * @param oriOnClickListener 用户设置的原始点击事件, 若为null,则通过反射进行获取
     */
    public static void hookView(View view, @Nullable View.OnClickListener oriOnClickListener) {
        if (oriOnClickListener != null) {
            view.setOnClickListener(new OnClickListenerProxy(oriOnClickListener));
            return;
        }

        try {
            Class viewClazz = Class.forName("android.view.View");
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfoObj = listenerInfoMethod.invoke(view);
            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");
            if (!onClickListenerField.isAccessible()) {
                onClickListenerField.setAccessible(true);
            }
            // 替换为自定义的代理事件监听器
            oriOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
            View.OnClickListener onClickListenerProxy = new OnClickListenerProxy(oriOnClickListener);
            onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义的代理事件监听器
    public static class OnClickListenerProxy implements View.OnClickListener {

        private View.OnClickListener originOnClickListener;
        private int MIN_CLICK_DELAY_TIME = 0; // 同一按钮连续两次点击之间最小时间间隔
        private long lastClickTime = 0;

        public OnClickListenerProxy(View.OnClickListener oriListener) {
            this(oriListener, 0);
        }

        /**
         * @param minClickDelayTime 本控件连续两次点击之间的最小事件间隔,单位: 毫秒
         */
        public OnClickListenerProxy(View.OnClickListener oriListener, int minClickDelayTime) {
            this.originOnClickListener = oriListener;
            if (minClickDelayTime < 0) {
                minClickDelayTime = 0;
            }

            this.MIN_CLICK_DELAY_TIME = minClickDelayTime;
        }

        @Override
        public void onClick(View v) {
            //点击时间控制
            long currentTime = System.currentTimeMillis();
            int id = v.getId();
            String title = "";
            if (v instanceof TextView) {
                title = ((TextView) v).getText().toString();
            }
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                if (originOnClickListener != null) {
                    Context context = v.getContext();
                    String actName = "";
                    if (context instanceof Activity) {
                        Activity act = (Activity) context;
                        actName = act.getLocalClassName() + "@" + act.hashCode();
                    }

                    Log.w(TAG, "btn click: " + actName + " ,id=" + id + " ,text=" + title);
                    if (v.getVisibility() == View.VISIBLE) {
                        originOnClickListener.onClick(v);
                    }
                }
            }
        }
    }
}