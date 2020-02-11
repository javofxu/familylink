package com.ilop.sthome.app;

import android.content.Context;
import android.content.Intent;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import com.aliyun.iot.aep.routerexternal.RouterExternal;
import com.aliyun.iot.aep.sdk.EnvConfigure;
import com.aliyun.iot.aep.sdk.PushManager;
import com.aliyun.iot.aep.sdk.base.delegate.APIGatewaySDKDelegate;
import com.aliyun.iot.aep.sdk.base.delegate.OpenAccountSDKDelegate;
import com.aliyun.iot.aep.sdk.delegate.DownstreamConnectorSDKDelegate;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.bundle.BundleManager;
import com.aliyun.iot.aep.sdk.framework.bundle.PageConfigure;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.example.xmpic.support.FunSupport;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ilop.sthome.service.SiterService;
import com.ilop.sthome.utils.greenDao.DaoManager;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.siterwell.familywellplus.BuildConfig;
import com.siterwell.familywellplus.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by skygge on 12/3.
 **/

public class MyApplication extends AApplication {

    private static MyApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        closeAndroidPDialog();
        FunSupport.getInstance().init(getApplicationContext());
        SiterSDK.init(getApplicationContext(),R.raw.config_proj);

        Intent intent = new Intent(this, SiterService.class);
        startService(intent);

        initGreenDao();

        // Push SDK 需要在主进程和子进程都初始化
        PushManager.getInstance().init(this);
        // 初始化拍照裁剪
        Fresco.initialize(this);
        // 其他 SDK, 仅在 主进程上初始化
        String packageName = this.getPackageName();
        if (!packageName.equals(ThreadTools.getProcessName(this, android.os.Process.myPid()))) {
            return;
        }
        // 设置日志级别
        ALog.setLevel(ALog.LEVEL_DEBUG);
        com.aliyun.alink.linksdk.tools.ALog.setLevel(com.aliyun.alink.linksdk.tools.ALog.LEVEL_DEBUG);

        String build_country = BuildConfig.BUILD_COUNTRY;// 在buildTypes 内配置不同版本类别 CHINA 国内版  OVERSEA 海外版
        // set env for sdks .begin
        // api gateway
        EnvConfigure.putEnvArg(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_API_ENV, "RELEASE");
        if ("CHINA".equalsIgnoreCase(build_country)) {
            EnvConfigure.putEnvArg(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_DEFAULT_HOST, "api.link.aliyun.com");
        } else {
            // 海外版初始化
            EnvConfigure.putEnvArg(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_DEFAULT_HOST, "api-iot.ap-southeast-1.aliyuncs.com");
        }
        // OA
        if ("CHINA".equalsIgnoreCase(build_country)) {
            EnvConfigure.putEnvArg(OpenAccountSDKDelegate.ENV_KEY_OPEN_ACCOUNT_HOST, null);
        } else {
            // 海外版初始化
            EnvConfigure.putEnvArg(OpenAccountSDKDelegate.ENV_KEY_OPEN_ACCOUNT_HOST, "sgp-sdk.openaccount.aliyun.com");
        }

        // MQTT
        EnvConfigure.putEnvArg(DownstreamConnectorSDKDelegate.ENV_KEY_MQTT_HOST, null);
        if ("CHINA".equalsIgnoreCase(build_country)) {
            EnvConfigure.putEnvArg(DownstreamConnectorSDKDelegate.ENV_KEY_MQTT_AUTO_HOST, "false");
        } else {
            // 海外版初始化
            EnvConfigure.putEnvArg(DownstreamConnectorSDKDelegate.ENV_KEY_MQTT_AUTO_HOST, "true");
        }
        EnvConfigure.putEnvArg(DownstreamConnectorSDKDelegate.ENV_KEY_MQTT_CHECK_ROOT_CRT, "true");
        // set env for sdks .end


        EnvConfigure.putEnvArg(EnvConfigure.KEY_LANGUAGE, "zh-CN");

        // the key set from sp that need to be put into AConfigure.envArgs
        HashSet spKeySet = new HashSet();
        EnvConfigure.init(this, spKeySet);

        new ApplicationHelper().onCreate(this);

        /* 加载Native页面 */
        BundleManager.init(this, (application, configure) -> {
            if (null == configure || null == configure.navigationConfigures)
                return;

            ArrayList<String> nativeUrls = new ArrayList<>();
            ArrayList<PageConfigure.NavigationConfigure> configures = new ArrayList<>();

            PageConfigure.NavigationConfigure deepCopyItem = null;
            for (PageConfigure.NavigationConfigure item : configure.navigationConfigures) {
                if (null == item.navigationCode || item.navigationCode.isEmpty() || null == item.navigationIntentUrl || item.navigationIntentUrl.isEmpty())
                    continue;

                deepCopyItem = new PageConfigure.NavigationConfigure();
                deepCopyItem.navigationCode = item.navigationCode;
                deepCopyItem.navigationIntentUrl = item.navigationIntentUrl;
                deepCopyItem.navigationIntentAction = item.navigationIntentAction;
                deepCopyItem.navigationIntentCategory = item.navigationIntentCategory;

                configures.add(deepCopyItem);

                nativeUrls.add(deepCopyItem.navigationIntentUrl);

                ALog.d("BundleManager", "register-native-page: " + item.navigationCode + ", " + item.navigationIntentUrl);

                RouterExternal.getInstance().registerNativeCodeUrl(deepCopyItem.navigationCode, deepCopyItem.navigationIntentUrl);
                RouterExternal.getInstance().registerNativePages(nativeUrls, new NativeUrlHandler(deepCopyItem));
            }
        });

    }

    private void initGreenDao(){
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }

    public static Context getAppContext() {
        return mInstance;
    }


    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
