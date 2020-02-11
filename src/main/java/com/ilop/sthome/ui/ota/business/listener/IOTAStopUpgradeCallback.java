package com.ilop.sthome.ui.ota.business.listener;

/**
 * 停止ota回调（暂时没有暂停功能）
 */
public interface IOTAStopUpgradeCallback {
    /**
     * 暂停升级成功回调
     */
    void onSuccess();

    /**
     * 暂停升级失败回调
     * @param e
     */
    void onFailure(Exception e);
}
