package com.ilop.sthome.ui.ota.business.listener;

/**
 * 开始升级callback
 */
public interface IOTAStartUpgradeCallback {
    /**
     * 请求升级成功回调
     */
    void onSuccess();

    /**
     * 请求升级失败回调
     * @param msg 失败描述
     */
    void onFailure(String msg);
}
