package com.example.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @Author skygge.
 * @Date on 2019-09-26.
 * @Dec: 屏幕适配方案
 */
public class ScreenAdapterUtil {

    private static float sNonCompatDensity;
    private static float sNonCompatScaleDensity;

    public static void setCustomDensity(Activity activity, Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    sNonCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }

                @Override
                public void onLowMemory() {

                }
            });

        }

        //屏幕宽 的像素除以360 获得Density的值
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final int targetDensityDpi = (int) (targetDensity * 160);
        final float targetScaledDensity = (int) (targetDensity * (sNonCompatScaleDensity / sNonCompatDensity));

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;


        //这里把scaledDensity的值 等于获得Density的值（会有bug 下面会解决）
        final DisplayMetrics activityDisplayMetrics = application.getResources().getDisplayMetrics();

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }
}
