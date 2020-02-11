package com.ilop.sthome.ui.ota.business.listener;

import com.ilop.sthome.ui.ota.bean.OTADeviceInfo;

/**
 * 查询ota状态callback
 */
public interface IOTAQueryStatusCallback {
    /**
     * 查询设备状态回调
     * @param deviceInfo
     */
    void onResponse(OTADeviceInfo deviceInfo);

    /**
     * 查询设备状态失败回调
     * @param msg 失败描述
     */
    void onFailure(String msg);
}
