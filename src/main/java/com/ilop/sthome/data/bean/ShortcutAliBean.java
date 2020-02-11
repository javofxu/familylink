package com.ilop.sthome.data.bean;

/**
 * Created by Administrator on 2017/10/27.
 * 场景开关
 */

public class ShortcutAliBean {


    private int src_sid;
    private int des_sid;
    private String deviceName;
    private int eqid;
    private int delay;

    public int getSrc_sid() {
        return src_sid;
    }

    public void setSrc_sid(int src_sid) {
        this.src_sid = src_sid;
    }

    public int getDes_sid() {
        return des_sid;
    }

    public void setDes_sid(int des_sid) {
        this.des_sid = des_sid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getEqid() {
        return eqid;
    }

    public void setEqid(int eqid) {
        this.eqid = eqid;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "ShortcutAliBean{" +
                "src_sid=" + src_sid +
                ", des_sid=" + des_sid +
                ", deviceName='" + deviceName + '\'' +
                ", eqid=" + eqid +
                ", delay=" + delay +
                '}';
    }
}
