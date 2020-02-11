package com.ilop.sthome.data.bean;

import com.ilop.sthome.data.greenDao.WarnBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class DeviceHistoryBean {

    private String month;

    private int number;

    private List<WarnBean> list;

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

    public List<WarnBean> getList() {
        return list;
    }

    public void setList(List<WarnBean> list) {
        this.list = list;
    }
}
