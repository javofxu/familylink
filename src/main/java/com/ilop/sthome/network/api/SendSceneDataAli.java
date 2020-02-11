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
 * Created by jishu0001 on 2016/11/17.
 */
public class SendSceneDataAli {
    private static final String TAG = "SendSceneDataAli";
    private Context context;
    private SendCommandAli sc;
    private boolean wifiTag;
    private DeviceInfoBean deviceInfoBean;


    public SendSceneDataAli(Context context, DeviceInfoBean deviceInfoBean) {
        this.context = context;
        this.deviceInfoBean = deviceInfoBean;
        sc = new SendCommandAli(context,deviceInfoBean);
    }

    /**
     * send code
     * @param groupCode
     */
    private void sendAction(String groupCode){
        Log.i(TAG,"====send content=== "+groupCode);

        GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
        gatewayUdpListConstant.setCurrentGateway(deviceInfoBean.getDeviceName());

//        Log.i("UDPSendData",gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName()).toString());
        Log.i(TAG,"====send content=== "+deviceInfoBean.getDeviceName());

        GatewayBean gb = gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            GatewayUdpListConstant.getInstance().startSendData();
            SiterwellUtil siterwellUtil = new SiterwellUtil(context);
//            siterwellUtil.sendDeviceData(groupCode,deviceInfoBean.getDeviceName());
            Log.i(TAG,gb.toString());
            new SendWithReHelper(new SendWithReHelper.MyTaskCallback() {
                @Override
                public void operationFailed(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=Failed");
                    gatewayUdpListConstant.checkByname(gatewayUdpListConstant.getCurrentGateway()).setOnline(false);
                }

                @Override
                public void operationSuccess(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=Success");
                }

                @Override
                public void doReSendAction(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=ReSend");
                    siterwellUtil.sendDeviceData(groupCode,deviceInfoBean.getDeviceName());
                }
            });
        }else {

            PanelDevice panelDevice = new PanelDevice(this.deviceInfoBean.getIotId());
//        初始化
            panelDevice.init(context, null);

            panelDevice.invokeService(groupCode, new IPanelCallback() {
                @Override
                public void onComplete(boolean b, Object o) {

                }
            });
        }
    }

    /**
     * increace scene
     * @param deCode
     */
    public void increaceScene(final String deCode){
        sendAction(sc.increaceScene(deCode));
    }


    /**
     * delete scene
     * @param mid
     */
    public void deleteScene(final int mid){
        sendAction(sc.deleteScene(mid));
    }

    /**
     * syn get scene information
     * @param groupId
     * @param sceneGCRC
     * @param sceneCRC
     */
    public void synGetSceneInformation(int groupId,String sceneGCRC,String sceneCRC){
        sendAction(sc.synScene(groupId,sceneGCRC,sceneCRC));
    }

    /**
     * handleScene
     * @param mid
     */
    public void handleScene(final int mid){
        sendAction(sc.sceneHandle(mid));
    }
}
