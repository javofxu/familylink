package com.ilop.sthome.data.bean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-02-07.
 * @Dec: 安全评测
 */
public class CheckDeviceBean {

    private int deviceIcon;

    private String deviceType;

    private List<DeviceInfoBean> device;

    public int getDeviceIcon() {
        return deviceIcon;
    }

    public void setDeviceIcon(int deviceIcon) {
        this.deviceIcon = deviceIcon;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<DeviceInfoBean> getDevice() {
        return device;
    }

    public void setDevice(List<DeviceInfoBean> device) {
        this.device = device;
    }
}
