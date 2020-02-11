package com.ilop.sthome.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.aliyun.iot.aep.component.router.IUrlHandler;
import com.aliyun.iot.aep.sdk.framework.bundle.PageConfigure;
import com.aliyun.iot.aep.sdk.log.ALog;

/**
 * @author skygge
 * @date 2020-01-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class NativeUrlHandler implements IUrlHandler {

    private final String TAG = "ApplicationHelper$NativeUrlHandler";

    private final PageConfigure.NavigationConfigure navigationConfigure;

    NativeUrlHandler(PageConfigure.NavigationConfigure configures) {
        this.navigationConfigure = configures;
    }

    @Override
    public void onUrlHandle(Context context, String url, Bundle bundle, boolean startActForResult, int reqCode) {
        ALog.d(TAG, "onUrlHandle: url: " + url);
        if (null == context || null == url || url.isEmpty())
            return;

        /* prepare the intent */
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));

        if (null != this.navigationConfigure.navigationIntentAction)
            intent.setAction(this.navigationConfigure.navigationIntentAction);
        if (null != this.navigationConfigure.navigationIntentCategory)
            intent.addCategory(this.navigationConfigure.navigationIntentCategory);

        if (Build.VERSION.SDK_INT >= 26) {//解决android8.0路由冲突问题，将intent行为限制在本应用内
            intent.setPackage(context.getPackageName());
        }

        /* start the navigated activity */
        ALog.d(TAG, "iStartActivity(): url: " + this.navigationConfigure.navigationIntentUrl + ", startActForResult: " + startActForResult + ", reqCode: " + reqCode);
        this.startActivity(context, intent, bundle, startActForResult, reqCode);
    }

    private void startActivity(Context context, Intent intent, Bundle bundle, boolean startActForResult, int reqCode) {
        if (null == context || null == intent)
            return;


        if (null != bundle) {
            intent.putExtras(bundle);
        }
        /* startActivityForResult() 场景，只能被 Activity 调用 */
        if (startActForResult) {
            if (false == (context instanceof Activity))
                return;

            ((Activity) context).startActivityForResult(intent, reqCode);

            return;
        }

        /* iStartActivity 被 Application 调用时的处理 */
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        /* iStartActivity 被 Activity、Service 调用时的处理 */
        else if (context instanceof Activity || context instanceof Service) {
            context.startActivity(intent);
        }
        /* iStartActivity 被其他组件调用时的处理 */
        else {
            // 暂不支持
        }
    }
}
