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

import java.util.Calendar;

/**
 * Created by jishu0001 on 2016/11/17.
 */
public class SendOtherDataAli {
    private static final String TAG ="SendOtherDataAli";
    private Context context;
    private boolean wifiTag;
    private SendCommandAli sc;
    private DeviceInfoBean deviceInfoBean;


    public SendOtherDataAli(Context context, DeviceInfoBean deviceInfoBean) {
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

        //  Log.i("UDPSendData",gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName()).toString());
        Log.i(TAG,"+++++++++++++++++++++++=send content=== "+deviceInfoBean.getDeviceName() +";code="+groupCode);
        GatewayBean gb = gatewayUdpListConstant.checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            GatewayUdpListConstant.getInstance().startSendData();
            SiterwellUtil siterwellUtil = new SiterwellUtil(context);
//            siterwellUtil.sendDeviceData(groupCode,deviceInfoBean.getDeviceName());
            Log.i(TAG,gb.toString());
            new SendWithReHelper(new SendWithReHelper.MyTaskCallback() {
                @Override
                public void operationFailed(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=Failed="+deviceInfoBean.getDeviceName()+"="+count);

                    GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName()).setOnline(false);

                    Log.i(TAG,GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName()).toString()+"="+count);
                }

                @Override
                public void operationSuccess(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=Success="+deviceInfoBean.getDeviceName()+"="+count);
                }

                @Override
                public void doReSendAction(int count) {
                    Log.i(TAG,"+++++++++++++++++++++++=ReSend="+deviceInfoBean.getDeviceName()+"="+count);
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

    private String nowData(){
        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        String wd = "";
        if(weekday == 1){
            weekday = 7;
            wd = "0" + weekday;
        }else{
            weekday -= 1;
            wd = "0" + weekday;
        }

        String hour1 ="";
        String hour2 = Integer.toHexString(hour);
        if(hour2.length()<2){
            hour1 ="0"+hour2;
        }else{
            hour1 = hour2;
        }

        String min1 ="";
        String min2 =Integer.toHexString(min);
        if(min2.length()<2){
            min1 ="0"+min2;
        }else{
            min1 = min2;
        }
        String sec1 ="";
        String sec2 =Integer.toHexString(sec);
        if(sec2.length()<2){
            sec1 ="0"+sec2;
        }else{
            sec1 =sec2;
        }

        String timeCode = wd+hour1+min1+sec1;

        return timeCode;
    }

    /**
     * time check
     *
     */
    public void timeCheck(){
        sendAction(sc.timeCheck(nowData()));
    }

    public void timeZoneCheck(int timezone){
        sendAction(sc.timeZoneCheck(timezone));
    }



    /**
     * faceback加密
     */
    public void dataGetOk(int cmd){
        SiterwellUtil siterwellUtil = new SiterwellUtil(context);
        siterwellUtil.sendDeviceData(sc.appAnswerOk(cmd),deviceInfoBean.getDeviceName());
//        sendAction(sc.appAnswerOk(cmd));
    }

    /**
     * methodname:addTimerModel
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:timer
     * 返回:
     */
    public void addTimerModel(final String timer){
//        myApplication.setFaceback(false);
        sendAction(sc.increaceGroupTimer(timer));
    }

    /**
     * methodname:deleteTimerModel
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:timer
     * 返回:
     */
    public void deleteTimerModel(final int timer){
//        myApplication.setFaceback(false);
        sendAction(sc.deleteGroupTimer(timer));
    }

    /**
     * methodname:deleteTimerModel
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:timer
     * 返回:
     */
    public void syncTimerModel(final String timer){
//        myApplication.setFaceback(false);
        sendAction(sc.synGroupTimer(timer));
    }

    /**
     * methodname:syncAlarms
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:page
     * 返回:
     */
    public void syncAlarms(final int page){
//        myApplication.setFaceback(false);
        sendAction(sc.syncAlarmLogs(page));
    }

    /**
     * methodname:deleteGatewayAlarms
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:id
     * 返回:
     */
    public void deleteGatewayAlarms(final int id){
        sendAction(sc.deleteGatewayLog(id));
    }


    /**
     * methodname:syncSubAlarms
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:page
     * 返回:
     */
    public void syncSubAlarms(final int page,final int eqid){
//        myApplication.setFaceback(false);
        sendAction(sc.syncSubAlarmLogs(page,eqid));
    }

    /**
     * methodname:deleteGatewayAlarms
     * 作者：Henry on 2017/5/16 10:06
     * 邮箱：xuejunju_4595@qq.com
     * 参数:id
     * 返回:
     */
    public void deleteSubAlarms(final int id,final int eqid){
        sendAction(sc.deleteSubDeviceLog(id,eqid));
    }
}
