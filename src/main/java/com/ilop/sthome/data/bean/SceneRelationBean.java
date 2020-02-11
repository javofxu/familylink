package com.ilop.sthome.data.bean;

/**
 * Created by henry on 2019/4/18.
 */

public class SceneRelationBean {
    private int sid;
    private int mid;
    private String deviceName;


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "SceneRelationBean{" +
                "sid=" + sid +
                ", mid=" + mid +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
