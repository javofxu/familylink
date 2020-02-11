package com.ilop.sthome.utils;

import android.content.Context;
import android.content.Intent;

import com.example.xmpic.support.models.FunDevType;
import com.example.xmpic.support.models.FunDevice;
import com.ilop.sthome.ui.activity.xmipc.ActivityGuideDeviceCamera;

import java.util.HashMap;
import java.util.Map;


public class DeviceActivityUtil {

	private static Map<FunDevType, Class<?>> sDeviceActivityMap = new HashMap<FunDevType, Class<?>>();
	static {
		// 监控设备
		sDeviceActivityMap.put(FunDevType.EE_DEV_NORMAL_MONITOR,
				ActivityGuideDeviceCamera.class);

	}
	
	public static void startDeviceActivity(Context context, FunDevice funDevice,String Monitorname) {
		Class<?> a = sDeviceActivityMap.get(funDevice.devType);
		if ( null != a ) {
			Intent intent = new Intent();
			intent.setClass(context, a);
			intent.putExtra("FUN_DEVICE_ID", funDevice.getId());
			intent.putExtra("FUN_DEVICE_NAME",Monitorname);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	public static void startDeviceActivity(Context context, String devSn,String Monitorname) {
		Class<?> a = sDeviceActivityMap.get(FunDevType.EE_DEV_NORMAL_MONITOR);
			Intent intent = new Intent();
			intent.setClass(context, a);
			intent.putExtra("FUN_DEVICE_ID", devSn);
			intent.putExtra("FUN_DEVICE_NAME",Monitorname);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);

	}

	public static void startDeviceActivityByAli(Context context, String devSn,String Monitorname) {
		Class<?> a = sDeviceActivityMap.get(FunDevType.EE_DEV_NORMAL_MONITOR);
		Intent intent = new Intent();
		intent.setClass(context, a);
		intent.putExtra("FUN_DEVICE_ID", devSn);
		intent.putExtra("FUN_DEVICE_NAME",Monitorname);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}
	
}
