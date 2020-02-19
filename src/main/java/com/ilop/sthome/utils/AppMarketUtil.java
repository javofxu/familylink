package com.ilop.sthome.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author skygge
 * @date 2020-02-19.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：跳转到应用商店详情页面
 */
public class AppMarketUtil {

    //Google Play(优先选择）
    private static final String PACKAGE_GOOGLE_MARKET = "com.android.vending";
    //应用宝
    private static final String PACKAGE_TENCENT_MARKET = "com.tencent.android.qqdownloader";
    //华为应用商店
    private static final String PACKAGE_HUAWEI_MARKET = "com.huawei.appmarket";
    //小米应用商店
    private static final String PACKAGE_MI_MARKET = "com.xiaomi.market";
    //魅族应用商店
    private static final String PACKAGE_MEIZU_MARKET = "com.meizu.mstore";
    //VIVO应用商店
    private static final String PACKAGE_VIVO_MARKET = "com.bbk.appstore";
    //OPPO应用商店
    private static final String PACKAGE_OPPO_MARKET = "com.oppo.market";

    private static String[] store = {PACKAGE_GOOGLE_MARKET, PACKAGE_TENCENT_MARKET, PACKAGE_HUAWEI_MARKET, PACKAGE_MI_MARKET,
            PACKAGE_MEIZU_MARKET, PACKAGE_VIVO_MARKET, PACKAGE_OPPO_MARKET};


    public static void versionUpdate(Context mContext){
        String app_store;
        for (String packageName: store) {
            if (!TextUtils.isEmpty(isExistence(mContext, packageName))){
                app_store = packageName;
                goThirdApp(mContext, app_store);
                return;
            }
        }
    }


    /** 跳转到appStore下载软件 */
    private static void goThirdApp(Context mContext, String appStore) {
        String appName = getPackageName(mContext);
        if (!TextUtils.isEmpty(appStore)) {// 市场存在
            startAppStore(mContext, appName, appStore);
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + appName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(it);
        }
    }

    /**
     * 获取app包名
     *
     * @return 返回包名
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /** 判断软件是否存在 */
    private static String isExistence(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /** 启动到app详情界面 */
    private static void startAppStore(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(marketPkg);
            assert intent != null;
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
