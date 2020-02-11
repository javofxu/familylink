package com.ilop.sthome.ui.ota.interfaces;

import com.ilop.sthome.ui.ota.business.listener.IOTAQueryStatusCallback;
import com.ilop.sthome.ui.ota.business.listener.IOTAStartUpgradeCallback;
import com.ilop.sthome.ui.ota.business.listener.IOTAStopUpgradeCallback;


public interface IOTAExecutor {
    /**
     * 查询ota状态
     * @param iotId
     * @param callback
     */
    void queryOTAStatus(String iotId, IOTAQueryStatusCallback callback);

    /**
     * 开始ota升级
     * @param iotId
     * @param callback
     */
    void startUpgrade(String iotId, IOTAStartUpgradeCallback callback);

    /**
     * 停止ota升级
     * @param iotId
     * @param callback
     */
    void stopUpgrade(String iotId, IOTAStopUpgradeCallback callback);

    /**
     * 释放
     * destroy
     */
    void destroy();
}
