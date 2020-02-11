package com.ilop.sthome.utils.tools;

import android.content.Context;

/**
 * Created by hekr_jds on 3/27 0027.
 * Description: 缓存的封装
 */
public class CacheUtil {

    public static void init(Context context) {
        SpCache.init(context);
    }


    public static String getString(String key, String defValue) {
        return SpCache.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SpCache.putString(key, value);
    }
}
