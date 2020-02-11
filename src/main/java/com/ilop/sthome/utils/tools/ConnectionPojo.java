package com.ilop.sthome.utils.tools;

/**
 * Created by jishu0001 on 2016/11/23.
 */
public class ConnectionPojo {
    public String deviceTid;
    public int msgid = 0;
    public boolean testMode = false;
    private static ConnectionPojo instance = null;
    private ConnectionPojo (){

    }
    public static ConnectionPojo getInstance(){
        if (instance == null) {
//            synchronized (ConnectionPojo.class) {
//                if (instance == null) {
                    return instance = new ConnectionPojo();
//                }
//            }
        }
        return instance;
    }
}
