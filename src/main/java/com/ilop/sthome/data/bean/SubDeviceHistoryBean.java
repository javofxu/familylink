package com.ilop.sthome.data.bean;

import com.ilop.sthome.data.greenDao.HistoryBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：子设备历史详情
 */
public class SubDeviceHistoryBean {
    private String month;

    private int number;

    private List<HistoryBean> list;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<HistoryBean> getList() {
        return list;
    }

    public void setList(List<HistoryBean> list) {
        this.list = list;
    }
}
