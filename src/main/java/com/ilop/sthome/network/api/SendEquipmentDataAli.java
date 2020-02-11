package com.ilop.sthome.network.api;

import android.content.Context;
import android.util.Log;

import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.ilop.sthome.common.SendWithReHelper;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.GatewayBean;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;
import com.ilop.sthome.utils.SiterwellUtil;


/**
 * Created by jishu0001 on 2016/11/16.
 */
public class SendEquipmentDataAli {
    private static final String TAG = "SendEquipmentDataAli";
    private Context context;
    private SendCommandAli sc;
    private boolean wifiTag;
    private DeviceInfoBean deviceInfoBean;

    public SendEquipmentDataAli(Context context, DeviceInfoBean deviceInfoBean) {
        this.context = context;
        this.deviceInfoBean = deviceInfoBean;
        sc = new SendCommandAli(context,deviceInfoBean);
    }

    /**
     * send code
     * @param groupCode
     */
    private void sendAction(String groupCode){

        GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
        gatewayUdpListConstant.setCurrentGateway(deviceInfoBean.getDeviceName());

        Log.i(TAG,"+++++++++++++++++++++++=send content=== "+deviceInfoBean.getDeviceName() +";code="+groupCode);

        GatewayBean gb = gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null && gb.isOnline() && gb.getIpAddress()!=null){
            GatewayUdpListConstant.getInstance().startSendData();
            SiterwellUtil siterwellUtil = new SiterwellUtil(context);
            Log.i(TAG,gb.toString());
            new SendWithReHelper(new SendWithReHelper.MyTaskCallback() {
                @Override
                public void operationFailed(int count) {
                    GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName()).setOnline(false);

                    Log.i(TAG,GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName()).toString()+"="+count);
                }

                @Override
                public void operationSuccess(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=Success="+deviceInfoBean.getDeviceName()+"="+count);
                }

                @Override
                public void doReSendAction(int count) {
                    siterwellUtil.sendDeviceData(groupCode,deviceInfoBean.getDeviceName());
                    Log.i(TAG,"+++++++++++++++++++++++=ReSend="+deviceInfoBean.getDeviceName()+"="+count);
                }
            });
        }else {
            PanelDevice panelDevice = new PanelDevice(this.deviceInfoBean.getIotId());
            panelDevice.init(context, null);
            panelDevice.invokeService(groupCode, (b, o) -> {});
        }
    }
    /**
     * send control command
     * @param eqid
     * @param status2
     */
    public void sendEquipmentCommand(final int eqid, final String status2){//send equipment detail
        sendAction(sc.equipControl(eqid,status2));
    }

    /**
     * sendGateWaySilence
     */
    public void sendGateWaySilence(){//send equipment detail
        sendAction(sc.equipControl(0,"00000000"));
    }


    /**
     * increace equipment command
     */
    public void increaceEquipment(){
        sendAction(sc.equipIncreace());
    }

    /**
     * delete equipment
     * @param eqid
     */
    public void deleteEquipment(final int eqid){
        sendAction(sc.equipDelete(eqid));
    }


    /**
     * delete equipment
     * @param eqid
     */
    public void replaceEquipment(final int eqid){
        sendAction(sc.equipReplace(eqid));
    }



    /**
     * delete equipment
     * @param eqid
     */
    public void modifyEquipmentName(final int eqid,final String newname){
        sendAction(sc.modifyEquipmentName(eqid,newname));
    }


    /**
     * syn get device status
     * @param deviceCRC
     */
    public void synGetDeviceStatus(String deviceCRC){
//        sendAction(sc.synDeviceStatus(deviceCRC));
        GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
        gatewayUdpListConstant.setCurrentGateway(deviceInfoBean.getDeviceName());
        //  Log.i("UDPSendData",gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName()).toString());
        Log.i(TAG,"+++++++++++++++++++++++=send content=== "+deviceInfoBean.getDeviceName() +";code="+sc.synDeviceStatus(deviceCRC));
        GatewayBean gb = gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            GatewayUdpListConstant.getInstance().startSendData();
            SiterwellUtil siterwellUtil = new SiterwellUtil(context);
            siterwellUtil.sendDeviceData(sc.synDeviceStatus(deviceCRC),deviceInfoBean.getDeviceName());

        }else {
            PanelDevice panelDevice = new PanelDevice(this.deviceInfoBean.getIotId());
//        初始化
            panelDevice.init(context, null);
            panelDevice.invokeService(sc.synDeviceStatus(deviceCRC), new IPanelCallback() {
                @Override
                public void onComplete(boolean b, Object o) {
                }
            });
        }
    }

    /**
     * 激活udp状态
     */
    public void ActivtyUdp(){
        String code = "{\"action\":\"IOT_KEY?\",\"devID\":"+deviceInfoBean.getDeviceName()+"}";
        Log.i(TAG,"jihuo:"+code);
        sendActivity(code);
    }
    public void sendActivity(String code){
        GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
        gatewayUdpListConstant.setCurrentGateway(deviceInfoBean.getDeviceName());
        //  Log.i("UDPSendData",gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName()).toString());
        Log.i(TAG,"+++++++++++++++++++++++=send content=== "+deviceInfoBean.getDeviceName() +";code="+code);
        GatewayBean gb = gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null) {
            SiterwellUtil siterwellUtil = new SiterwellUtil(context);
            siterwellUtil.sendDeviceData(code, deviceInfoBean.getDeviceName());
        }
    }

    /**
     * syn get device name
     * @param deviceNameCRC
     */
    public void synGetDeviceName(String deviceNameCRC){
        sendAction(sc.synDeviceName(deviceNameCRC));
    }

    /**
     * cancel increase equipment
     */
    public void cancelIncreaseEq(){
        sendAction(sc.cancelEquipIncreace());
    }
}
