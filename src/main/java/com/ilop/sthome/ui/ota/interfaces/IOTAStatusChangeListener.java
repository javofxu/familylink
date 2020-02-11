package com.ilop.sthome.ui.ota.interfaces;

import com.ilop.sthome.ui.ota.bean.OTAStatusInfo;

/**
 * OTA 状态变更监听
 */
public interface IOTAStatusChangeListener {
    /**
     * ota状态变更回调
     * @param info
     */
    void onStatusChange(OTAStatusInfo info);
}
