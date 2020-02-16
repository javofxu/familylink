package com.ilop.sthome.network.udp;


import com.ilop.sthome.data.bean.GatewayBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class GatewayUdpListConstant {
    private List<DeviceInfoBean> deviceInfoBeans;

    private List<GatewayBean> lists = new ArrayList<>();

    private static GatewayUdpListConstant instance = null;

    private String currentGateway=null;

    //下发指令，用来校对是否接收到正确上报信息
    private int currentCmdId;

    private boolean reveiveDataOrNot=false;

//    public Timer timer;
//
//    public Timer getTimer() {
//        return timer;
//    }
//
//    public void setTimer(Timer timer) {
//        this.timer = timer;
//    }
//
//    public void closeTimer(){
//        if(timer!=null){
//            timer.cancel();
//            timer=null;
//        }
//    }

    //获取当前
    public boolean isReveiveDataOrNot() {
        return reveiveDataOrNot;
    }

    //在发送数据前将标志位设置成初始值
    public void startSendData(){
        this.reveiveDataOrNot=false;
    }

    //用于网关内网的连接状态判断，没有介绍到信息，标志位将变成false
    public void setReveiveDataOrNot(String devicename,int receiveCmdId) {
        if(currentGateway.equalsIgnoreCase(devicename)){
            switch (receiveCmdId){
                case 11:
                case 17:
                case 19:
                case 22:
                case 26:
                case 27:
                case 28:
                case 43:
                case 45:
                case 48:
                    reveiveDataOrNot=true;
                    break;
                default:
                    reveiveDataOrNot=false;
                    break;
            }
        }else{
            reveiveDataOrNot=false;
        }
    }

    public int getCurrentCmdId() {
        return currentCmdId;
    }

    public void setCurrentCmdId(int currentCmdId) {
        this.currentCmdId = currentCmdId;
    }

    public String getCurrentGateway() {
        return currentGateway;
    }

    public void setCurrentGateway(String currentGateway) {
        this.currentGateway = currentGateway;
    }

    private GatewayUdpListConstant (){

    }
    public static GatewayUdpListConstant getInstance(){
        if (instance == null) {
            synchronized (GatewayUdpListConstant.class) {//进行线程的控制，不能两个地方同时在线修改数据
                if (instance == null) {
                    return instance = new GatewayUdpListConstant();
                }
            }
        }
        return instance;
    }

    //添加网关内网信息
    public void addGateBean(GatewayBean gb){
        if(!this.hasGate(gb)){
            lists.add(gb);
        }
    }

    //移除网关内网信息
    public void removeGateBean(GatewayBean gb){
        lists.remove(gb);
    }

    //根据devicename 名字是唯一的
    public GatewayBean checkByname(String name){
        GatewayBean gb1 = null;
        if(lists.size()>0){
            for(GatewayBean gb:lists){
                if(name.equalsIgnoreCase(gb.getName())){
                    gb1= gb;
                }
            }
        }
        return gb1;
    }

    //根据名字进行网关信息删减
    public void remove(String name){
        if(lists.size()>0){
            for(GatewayBean gb:lists){
                if(name.equalsIgnoreCase(gb.getName())){
                    lists.remove(gb);
                }
            }
        }
    }

    //根据ip地址进行网关信息查找
    public GatewayBean checkByIp(InetAddress ipAddress){
        if(lists.size()>0){
            for(GatewayBean gb : lists){
                if(gb.getIpAddress()==ipAddress){
                    return gb;
                }
            }
        }
        return null;
    }

    //将获取到的网关列表直接放入到内网的对比表格之中
    public void setDeviceInfoBeans(List<DeviceInfoBean> deviceInfoBeans ){
        this.deviceInfoBeans = deviceInfoBeans;
    }

    //获取本人的网关号
    public DeviceInfoBean getDeviceIfoBeanByName(String name){
        if(deviceInfoBeans.size()>0){
            for(DeviceInfoBean deviceInfoBean:deviceInfoBeans){
                if(name.equalsIgnoreCase(deviceInfoBean.getDeviceName())){
                    return deviceInfoBean;
                }
            }
        }
        return null;
    }

    //检查列表中是否存在
    private boolean hasGate(GatewayBean gatewayBean){
        boolean flag=false;
        if(lists.size()>0){
            for(GatewayBean gatewayBean1:lists){
                if(gatewayBean.getName().equalsIgnoreCase(gatewayBean1.getName())){
                    flag= true;
                }
            }
        }
        return flag;
    }

    //检查内网中收索到的设备，是否是该用户的设备
    public boolean checkUdpDeviceBelongToUser(String name){
        boolean flag=false;
        if(deviceInfoBeans.size()>0){
            for(DeviceInfoBean db:deviceInfoBeans){
                if(name.equalsIgnoreCase(db.getDeviceName())){
                    flag= true;
                }
            }
        }
        return flag;
    }

    public boolean checkCurrentGatewayIsOnlineOrNot(){
        try {
            return checkByname(currentGateway).isOnline();
        }catch (Exception e){
            return false;
        }
    }

}
