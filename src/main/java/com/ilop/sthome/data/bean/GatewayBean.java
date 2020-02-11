package com.ilop.sthome.data.bean;

import java.net.InetAddress;

public class GatewayBean {


    private String name;
    private InetAddress ipAddress;
    //内网在线
    private boolean online;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "GatewayBean{" +
                "name='" + name + '\'' +
                ", ipAddress=" + ipAddress +
                ", online=" + online +
                '}';
    }
}
