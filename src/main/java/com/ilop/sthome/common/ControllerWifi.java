package com.ilop.sthome.common;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by jishu0001 on 2016/12/20.
 */
public class ControllerWifi {
    public InetAddress targetip;
    public String deviceTid;
    public String bind;
    public String ctrlKey;
    public DatagramSocket ds;

    private static ControllerWifi instance = null;
    private ControllerWifi(){

    }
    public static ControllerWifi getInstance(){
        if (instance == null) {
//            synchronized (ConnectionPojo.class) {
//                if (instance == null) {
            return instance = new ControllerWifi();
//                }
//            }
        }
        return instance;
    }
}
