package com.ilop.sthome.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.ilop.sthome.common.ControllerWifi;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;
import com.ilop.sthome.network.udp.UDPSendData;
import com.ilop.sthome.service.SiterService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jishu0001 on 2016/12/24.
 */
public class SiterwellUtil {
    private static final String TAG = "SiterwellUtil";
    private Context context;
    private static ExecutorService executorService;

    public SiterwellUtil(Context context){
        this.context = context;
        synchronized (context){
            if(executorService == null)
                executorService = Executors.newFixedThreadPool(7);
        }
    }

    public void sendData(final String code){
            UDPSendData udpSendData = new UDPSendData(ControllerWifi.getInstance().ds, ControllerWifi.getInstance().targetip,code);
            executorService.execute(udpSendData);

    }


    //主要用于内网的数据发送
    public void sendDeviceData(final String code,String name){

        if(isServiceExisted(context, SiterService.class.getName())){
            Log.i("SiterService","还活着");
        }else{
            Log.i("SiterService","已经死了");
        }


        Log.i(TAG,"send:"+ GatewayUdpListConstant.getInstance().checkByname(name).toString()+"======data==="+code);

        UDPSendData udpSendData = new UDPSendData(ControllerWifi.getInstance().ds,
                GatewayUdpListConstant.getInstance().checkByname(name).getIpAddress(),
                code);
        executorService.execute(udpSendData);

    }

    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

}
