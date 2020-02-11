package com.ilop.sthome.data.event;

/**
 * Created by henry on 2019/5/27.
 */

public class EventRefreshLogs {
    private String deviceName;
    private int is_over;
    private int page;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getIs_over() {
        return is_over;
    }

    public void setIs_over(int is_over) {
        this.is_over = is_over;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
