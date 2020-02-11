package com.ilop.sthome.data.bean;

import java.io.Serializable;

/**
 * Created by gc-0001 on 2017/5/15.
 */

public class TimerGatewayAliBean implements Serializable {
    private int enable;
    private String code;
    private int timerid;
    private int modeid;
    private String week;
    private String hour;
    private String min;
    private String modename;
    private String deviceName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getModeid() {
        return modeid;
    }

    public void setModeid(int modeid) {
        this.modeid = modeid;
    }

    public int getTimerid() {
        return timerid;
    }

    public void setTimerid(int timerid) {
        this.timerid = timerid;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getModename() {
        return modename;
    }

    public void setModename(String modename) {
        this.modename = modename;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "TimerGatewayAliBean{" +
                "enable=" + enable +
                ", code='" + code + '\'' +
                ", timerid='" + timerid + '\'' +
                ", modeid='" + modeid + '\'' +
                ", week='" + week + '\'' +
                ", hour='" + hour + '\'' +
                ", min='" + min + '\'' +
                ", modename='" + modename + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
