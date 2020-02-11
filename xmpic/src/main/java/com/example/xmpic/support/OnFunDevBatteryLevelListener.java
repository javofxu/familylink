package com.example.xmpic.support;

/**
 * @author hws
 * @name FunSDKDemo_Android_Old2018
 * @class name：com.example.xmpic.support
 * @class 设备电量、存储状态回调
 * @time 2019-05-07 17:26
 */
public interface OnFunDevBatteryLevelListener extends OnFunListener {
    void onRegister(boolean isSuccess);
    void onBatteryLevel(int devStorageStatus, int electable, int level);
}
