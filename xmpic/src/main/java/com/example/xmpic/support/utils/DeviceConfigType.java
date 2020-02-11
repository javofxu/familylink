package com.example.xmpic.support.utils;

import com.example.xmpic.support.config.AVEncVideoWidget;
import com.example.xmpic.support.config.AlarmOut;
import com.example.xmpic.support.config.CameraClearFog;
import com.example.xmpic.support.config.CameraParam;
import com.example.xmpic.support.config.CameraParamEx;
import com.example.xmpic.support.config.CloudStorage;
import com.example.xmpic.support.config.DetectBlind;
import com.example.xmpic.support.config.DetectMotion;
import com.example.xmpic.support.config.FVideoOsdLogo;
import com.example.xmpic.support.config.LocalAlarm;
import com.example.xmpic.support.config.PowerSocketArm;
import com.example.xmpic.support.config.PowerSocketImage;
import com.example.xmpic.support.config.RecordParam;
import com.example.xmpic.support.config.RecordParamEx;
import com.example.xmpic.support.config.SimplifyEncode;

public class DeviceConfigType {
	//通道相关的配置类型
	public static final String[] DeviceConfigByChannel = {
			CameraParam.CONFIG_NAME,
			CameraParamEx.CONFIG_NAME,
			AVEncVideoWidget.CONFIG_NAME ,
			DetectMotion.CONFIG_NAME,
			DetectBlind.CONFIG_NAME,
			LocalAlarm.CONFIG_NAME,
			RecordParam.CONFIG_NAME,
			RecordParamEx.CONFIG_NAME,
			CameraClearFog.CONFIG_NAME,
	};
	//通道无关的配置类型
	public static final String[] DeviceConfigCommon = {
			CloudStorage.CONFIG_NAME,
			PowerSocketImage.CONFIG_NAME,
			SimplifyEncode.CONFIG_NAME,
			FVideoOsdLogo.CONFIG_NAME,
			PowerSocketArm.CONFIG_NAME,
			AlarmOut.CONFIG_NAME,
	};
}
