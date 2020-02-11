package com.ilop.sthome.network.api;


import android.content.Context;

import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.GatewayBean;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;
import com.ilop.sthome.utils.tools.ConnectionPojo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jishu0001 on 2016/11/17.
 */
public class SendCommandAli {
    public static final int EQUIPMENT_CONTROL = 1;
    public static final int INCREACE_EQUIPMENT = 2;
    public static final int REPLACE_EQUIPMENT = 3;
    public static final int DELETE_EQUIPMENT = 4;
    public static final int MODIFY_EQUIPMENT_NAME = 5;
    public static final int CHOOSE_SCENE_GROUP = 6;
    public static final int CANCEL_INCREACE_EQUIPMENT = 7;
    public static final int INCREACE_SCENE = 8;
    public static final int DELETE_SCENE = 10;
    public static final int TIME_CHECK = 21;
    public static final int INCREACE_SCENE_GROUP = 23;
    public static final int SYN_DEVICE_STATUS = 29;
    public static final int SYN_DEVICE_NAME = 24;
    public static final int SYN_SCENE = 30;
    public static final int SCENE_HANDLE = 38;
    public static final int SCENE_GROUP_DELETE = 39;
    public static final int MODEL_SWITCH_TIMER = 40;
    public static final int MODEL_TIMER_SYN = 41;
    public static final int MODEL_TIMER_DEL = 42;
    public static final int SEND_TIMEZONE = 251;
    public static final int ALARM_LIST_SYNC = 44;
    public static final int DELETE_GATEWAY_LIST = 46;
    public static final int SUBDEVICE_ALARM_LIST_SYNC = 47;
    public static final int DELETE_SUBDEVICE_ALARM_LIST = 49;
    //上行cmd值
    public static final int SEND_ACK = 11;
    public static final int UPLOAD_DEVICE_NAME = 17;
    public static final int UPLOAD_DEVICE_STATUS = 19;
    public static final int CHECK_TIME_NOW = 22;
    public static final int UPLOAD_SCENE_GROUP_INFO = 26;
    public static final int UPLOAD_SCENE_INFO = 27;
    public static final int UPLOAD_CURRENT_SCENE_GROUP = 28;
    public static final int UPLOAD_TIMER_INFO = 43;
    public static final int UPLOAD_ALARM_LOGS_INFO = 45;
    public static final int UPLOAD_SUBDEVICE_ALARM_LOGS_INFO = 48;

    private Context context;
    public DeviceInfoBean deviceInfoBean;

    public SendCommandAli(Context context){
        this.context = context;
    }

    public SendCommandAli(Context context, DeviceInfoBean deviceInfoBean) {
        this.context = context;
        this.deviceInfoBean = deviceInfoBean;
    }

    private String jsonToString(String code){
        JSONObject json = null;
        try {
             json = new JSONObject(code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * equipment control
     * 1
     * @param eqid
     * @param status2
     * @return
     */
    public String equipControl(int eqid,String status2){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }
        String de = Integer.toHexString(eqid);
        int leng = de.length();
        for(int i = 0;i<4-leng;i++){
            de= "0" +de;
        }
        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+EQUIPMENT_CONTROL+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \""+status2+"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else{
            groupCode ="{ " +
                    "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+EQUIPMENT_CONTROL+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \""+status2+"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }

        return jsonToString(groupCode);
    }

    /**
     * equipControlUdp
     * 1
     * @param eqid
     * @param status2
     * @return
     */
    public String equipControlUdp(int eqid,String status2){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }

        String de = Integer.toHexString(eqid);
        int leng = de.length();
        for(int i = 0;i<4-leng;i++){
            de= "0" +de;
        }

        String groupCode ="{ " +
                "    \"action\": \"APP_SEND\",  " +
                "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                "    \"msg\": { " +
                "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                "    \"CMD_CODE\": "+EQUIPMENT_CONTROL+",  " +
                "    \"rev_str1\": \""+de+"\"," +
                "    \"rev_str2\": \""+status2+"\"," +
                "    \"rev_str3\": \"\"" +
                "    } " +
                "}";
        return jsonToString(groupCode);
    }


    /**
     * equipment replace
     * 1
     * @param eqid
     * @return
     */
    public String equipReplace(int eqid){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }
        String de = Integer.toHexString(eqid);
        int leng = de.length();
        for(int i = 0;i<4-leng;i++){
            de= "0" +de;
        }

        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+REPLACE_EQUIPMENT+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else{
            groupCode ="{ " +
                    "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+REPLACE_EQUIPMENT+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }
        return jsonToString(groupCode);
    }

    /**
     * increace equipment
     * 2
     * @return
     */
    public String equipIncreace(){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }
        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+INCREACE_EQUIPMENT+",  " +
                    "    \"rev_str1\": \"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else{
            groupCode ="{ " +
                    "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+INCREACE_EQUIPMENT+",  " +
                    "    \"rev_str1\": \"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }
        return jsonToString(groupCode);
    }
    /**
     * equipment delete
     * 4
     * @param eqid
     * @return
     */
        public String equipDelete(int eqid){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String de = Integer.toHexString(eqid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }
            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+DELETE_EQUIPMENT+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+DELETE_EQUIPMENT+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * chose scene group
     * 6
     * @param sid
     * @return
     */
        public String sceneGroupChose(int sid ){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+CHOOSE_SCENE_GROUP+",  " +
                        "    \"rev_str1\": \"000"+sid+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+CHOOSE_SCENE_GROUP+",  " +
                        "    \"rev_str1\": \"000"+sid+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * increace scene
     * 8
     * @param deCode
     * @return
     */
        public String increaceScene(String deCode){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+INCREACE_SCENE+",  " +
                        "    \"rev_str1\": \"\"," +
                        "    \"rev_str2\": \""+deCode+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+INCREACE_SCENE+",  " +
                        "    \"rev_str1\": \"\"," +
                        "    \"rev_str2\": \""+deCode+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }

            return jsonToString(groupCode);
        }

    /**
     * delete scene
     * 10
     * @param mid
     * @return
     */
        public String deleteScene(int mid){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String de = Integer.toHexString(mid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }
            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+DELETE_SCENE+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else {
                groupCode = "{ " +
                        "    \"iotId\": \"" + deviceInfoBean.getIotId() + "\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": " + ConnectionPojo.getInstance().msgid + ",  " +
                        "    \"CMD_CODE\": " + DELETE_SCENE + ",  " +
                        "    \"rev_str1\": \"" + de + "\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }


    /**
     * check time
     * 21
     * @param timeCode
     * @return
     */
        public String timeCheck(String timeCode){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+TIME_CHECK+",  " +
                        "    \"rev_str1\": \""+timeCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+TIME_CHECK+",  " +
                        "    \"rev_str1\": \""+timeCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }

            return jsonToString(groupCode);
        }


    /**
     * check time
     * 251
     * @param timezoneCode
     * @return
     */
        public String timeZoneCheck(int timezoneCode){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SEND_TIMEZONE+",  " +
                        "    \"rev_str1\": \""+timezoneCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SEND_TIMEZONE+",  " +
                        "    \"rev_str1\": \""+timezoneCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * increace scene group
     * 23
     * @param fullCode
     * @return
     */
        public String increaceSceneGroup(String fullCode){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+INCREACE_SCENE_GROUP+",  " +
                        "    \"rev_str1\": \""+fullCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+INCREACE_SCENE_GROUP+",  " +
                        "    \"rev_str1\": \""+fullCode+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * modify equipment name
     * 5
     * @param eqid
     * @param newname
     * @return
     */
        public String modifyEquipmentName(int eqid, String newname){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String de = Integer.toHexString(eqid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODIFY_EQUIPMENT_NAME+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \""+newname+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else {
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODIFY_EQUIPMENT_NAME+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \""+newname+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }

            return jsonToString(groupCode);
        }


    /**
     * syn device status
     *29
     * @param statusCRC
     * @return
     */
        public String synDeviceStatus(String statusCRC){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SYN_DEVICE_STATUS+",  " +
                        "    \"rev_str1\": \""+statusCRC+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SYN_DEVICE_STATUS+",  " +
                        "    \"rev_str1\": \""+statusCRC+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }

            return jsonToString(groupCode);
        }
    /**
     * syn device name
     *30
     * @param nameCRC
     * @return
     */
        public String synDeviceName(String nameCRC){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SYN_DEVICE_NAME+",  " +
                        "    \"rev_str1\": \""+nameCRC+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SYN_DEVICE_NAME+",  " +
                        "    \"rev_str1\": \""+nameCRC+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }
    /**
     * syc scene
     * @param sid,sceneGCRC,sceneCRC
     * @return
     */
        public String synScene(int sid,String sceneGCRC,String sceneCRC){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+(SYN_SCENE+sid)+",  " +
                        "    \"rev_str1\": \""+sceneCRC+"\"," +
                        "    \"rev_str2\": \""+sceneGCRC+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+(SYN_SCENE+sid)+",  " +
                        "    \"rev_str1\": \""+sceneCRC+"\"," +
                        "    \"rev_str2\": \""+sceneGCRC+"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * Scene handle
     *31
     * @param mid
     * @return
     */
        public String sceneHandle(int mid){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String de = Integer.toHexString(mid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SCENE_HANDLE+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SCENE_HANDLE+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * Scene handle
     *7
     * @return
     */
        public String cancelEquipIncreace(){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+CANCEL_INCREACE_EQUIPMENT+",  " +
                        "    \"rev_str1\": \"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+CANCEL_INCREACE_EQUIPMENT+",  " +
                        "    \"rev_str1\": \"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * delete scene group
     *33
     * @param sid
     * @return
     */
        public String deleteSceneGroup(int sid){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String de = Integer.toHexString(sid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SCENE_GROUP_DELETE+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+SCENE_GROUP_DELETE+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }

            return jsonToString(groupCode);
        }

    /**
     * increace a scene group timer
     *34
     * @param code
     * @return
     */
        public String increaceGroupTimer(String code){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODEL_SWITCH_TIMER+",  " +
                        "    \"rev_str1\": \""+code+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else{
                groupCode ="{ " +
                        "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODEL_SWITCH_TIMER+",  " +
                        "    \"rev_str1\": \""+code+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * scene group timer syn
     *35
     * @param code
     * @return
     */
        public String synGroupTimer(String code){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODEL_TIMER_SYN+",  " +
                        "    \"rev_str1\": \""+code+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else {
                groupCode = "{ " +
                        "    \"iotId\": \"" + deviceInfoBean.getIotId() + "\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": " + ConnectionPojo.getInstance().msgid + ",  " +
                        "    \"CMD_CODE\": " + MODEL_TIMER_SYN + ",  " +
                        "    \"rev_str1\": \"" + code + "\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }

    /**
     * delete scene group timer
     *37
     * @param tid
     * @return
     */
        public String deleteGroupTimer(int tid){
            if(ConnectionPojo.getInstance().msgid < 65536){
                ConnectionPojo.getInstance().msgid ++;
            }else {
                ConnectionPojo.getInstance().msgid = 0;
            }
            String de = Integer.toHexString(tid);
            int leng = de.length();
            for(int i = 0;i<4-leng;i++){
                de= "0" +de;
            }

            String groupCode=null;
            GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
            if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null){
                groupCode ="{ " +
                        "    \"action\": \"APP_SEND\",  " +
                        "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                        "    \"msg\": { " +
                        "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                        "    \"CMD_CODE\": "+MODEL_TIMER_DEL+",  " +
                        "    \"rev_str1\": \""+de+"\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }else {

                groupCode = "{ " +
                        "    \"iotId\": \"" + deviceInfoBean.getIotId() + "\",  " +
                        "    \"identifier\": \"data_revive\",  " +
                        "    \"args\": { " +
                        "    \"msg_ID\": " + ConnectionPojo.getInstance().msgid + ",  " +
                        "    \"CMD_CODE\": " + MODEL_TIMER_DEL + ",  " +
                        "    \"rev_str1\": \"" + de + "\"," +
                        "    \"rev_str2\": \"\"," +
                        "    \"rev_str3\": \"\"" +
                        "    } " +
                        "}";
            }
            return jsonToString(groupCode);
        }


    /**
     * sync alarm logs
     *37
     * @param page
     * @return
     */
    public String syncAlarmLogs(int page){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }
        String de = Integer.toHexString(page);
        int leng = de.length();
        for(int i = 0;i<2-leng;i++){
            de= "0" +de;
        }

        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null) {
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+ALARM_LIST_SYNC+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else {

            groupCode = "{ " +
                    "    \"iotId\": \"" + deviceInfoBean.getIotId() + "\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": " + ConnectionPojo.getInstance().msgid + ",  " +
                    "    \"CMD_CODE\": " + ALARM_LIST_SYNC + ",  " +
                    "    \"rev_str1\": \"" + de + "\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }
        return jsonToString(groupCode);
    }

    /**
     * delete gateway logs
     * 10
     * @param id
     * @return
     */
    public String deleteGatewayLog(int id){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }

        String de = Integer.toHexString(id);
        int leng = de.length();
        for(int i = 0;i<2-leng;i++){
            de= "0" +de;
        }

        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null) {
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+DELETE_GATEWAY_LIST+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else {
            groupCode ="{ " +
                    "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+DELETE_GATEWAY_LIST+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }

        return jsonToString(groupCode);
    }

    /**
     * sync subdevice alarm logs
     *37
     * @param page
     * @return
     */
    public String syncSubAlarmLogs(int page,int eqid){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }
        String de = Integer.toHexString(page);
        int leng = de.length();
        for(int i = 0;i<2-leng;i++){
            de= "0" +de;
        }

        String de2 = Integer.toHexString(eqid);
        int leng2 = de2.length();
        for(int j = 0;j<4-leng2;j++){
            de2= "0" +de2;
        }

        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null) {
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+SUBDEVICE_ALARM_LIST_SYNC+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \""+de2+"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else{
            groupCode ="{ " +
                    "    \"iotId\": \""+deviceInfoBean.getIotId()+"\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+SUBDEVICE_ALARM_LIST_SYNC+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \""+de2+"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }


        return jsonToString(groupCode);
    }


    /**
     * delete gateway logs
     * 10
     * @param id
     * @return
     */
    public String deleteSubDeviceLog(int id,int eqid){
        if(ConnectionPojo.getInstance().msgid < 65536){
            ConnectionPojo.getInstance().msgid ++;
        }else {
            ConnectionPojo.getInstance().msgid = 0;
        }

        String de = Integer.toHexString(id);
        int leng = de.length();
        for(int i = 0;i<2-leng;i++){
            de= "0" +de;
        }

        String de2 = Integer.toHexString(eqid);
        int leng2 = de2.length();
        for(int i = 0;i<4-leng2;i++){
            de2= "0" +de2;
        }

        String groupCode=null;
        GatewayBean gb = GatewayUdpListConstant.getInstance().checkByname(deviceInfoBean.getDeviceName());
        if(gb!=null&&gb.isOnline()&&gb.getIpAddress()!=null) {
            groupCode ="{ " +
                    "    \"action\": \"APP_SEND\",  " +
                    "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                    "    \"msg\": { " +
                    "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                    "    \"CMD_CODE\": "+DELETE_SUBDEVICE_ALARM_LIST+",  " +
                    "    \"rev_str1\": \""+de+"\"," +
                    "    \"rev_str2\": \""+de2+"\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }else {

            groupCode = "{ " +
                    "    \"iotId\": \"" + deviceInfoBean.getIotId() + "\",  " +
                    "    \"identifier\": \"data_revive\",  " +
                    "    \"args\": { " +
                    "    \"msg_ID\": " + ConnectionPojo.getInstance().msgid + ",  " +
                    "    \"CMD_CODE\": " + DELETE_SUBDEVICE_ALARM_LIST + ",  " +
                    "    \"rev_str1\": \"" + de + "\"," +
                    "    \"rev_str2\": \"" + de2 + "\"," +
                    "    \"rev_str3\": \"\"" +
                    "    } " +
                    "}";
        }
        return jsonToString(groupCode);
    }

    /**
     * udp应答
     * 251
     * @param
     * @return
     */
    public String appAnswerOk(int cmd){
        String Code ="{ " +
                "    \"action\": \"APP_ACK\",  " +
                "    \"devID\": \""+deviceInfoBean.getDeviceName()+"\",  " +
                "    \"msg\": { " +
                "    \"msg_ID\": "+ConnectionPojo.getInstance().msgid+",  " +
                "    \"CMD_CODE\": "+SEND_ACK+",  " +
                "    \"rev_str1\": \""+cmd+"\"," +
                "    \"rev_str2\": \"OK\"," +
                "    \"rev_str3\": \"\"" +
                "    } " +
                "}";
        return jsonToString(Code);
    }

}
