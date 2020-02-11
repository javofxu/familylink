package com.ilop.sthome.data.event;

import com.ilop.sthome.data.greenDao.HistoryBean;

import java.util.List;

/**
 * Created by henry on 2019/5/27.
 */

public class EventRefreshSubDeviceLogs {
    private String deviceName;
    private int is_over;
    private int eqid;
    private int page;
    private List<HistoryBean> list;

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

    public int getEqid() {
        return eqid;
    }

    public void setEqid(int eqid) {
        this.eqid = eqid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<HistoryBean> getList() {
        return list;
    }

    public void setList(List<HistoryBean> list) {
        this.list = list;
    }
}
