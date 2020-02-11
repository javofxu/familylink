package com.ilop.sthome.utils;


import android.content.Context;
import android.text.TextUtils;

import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.ResolveAliTimer;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.SceneRelationBean;
import com.ilop.sthome.data.bean.ShortcutAliBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.SceneAliDAO;
import com.ilop.sthome.data.db.SceneRelaitonAliDAO;
import com.ilop.sthome.data.db.ShortcutAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.db.TimerAliDAO;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.litesuits.android.log.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jishu0001 on 2017/2/9.
 */

public class CoderALiUtils {
    private static final String TAG = CoderALiUtils.class.getName();


    /*
    @method getStringFromAscii
    @autor Administrator
    @time 2017/6/30 9:51
    @email xuejunju_4595@qq.com
    从ascii 码转成GBK编码的String类型变量，若格式不对则返回NUll
    */
    public static String getStringFromAscii(String input){



        try {
            if(input.length()!=32){
                return "";
            }
            byte[]a = ByteUtil.hexStr2Bytes(input);
            String name  = new String(a,"GBK");
            if(name.indexOf("$")==-1){
                return "";
            }
            int index = -1;
            if(name.indexOf("@")!=-1){
                index = name.lastIndexOf("@");
            }

            return  name.substring(index + 1, name.indexOf("$"));

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }


    }

        /*
    @method getStringFromAscii2
    @autor Administrator
    @time 2017/6/30 9:51
    @email xuejunju_4595@qq.com
    从ascii 码转成GBK编码的String类型变量，若格式不对则返回NUll
    */
    public static String getStringFromAscii2(String input){

        try {

            byte[]a = ByteUtil.hexStr2Bytes(input);
            String name  = new String(a,"GBK");
            return  name;

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }


    }


    /**
     * the scene name to code
     * @param input
     * @return
     */

    public static String getAscii(String input){
        int countf = 0;

        try {
            countf = 15 - input.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<countf;i++){
            buffer.append("@");
        }
        String newname = buffer.toString()+input+"$";

        byte[] nameBt = new byte[16];
        try {
            nameBt = newname.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String ds = "";
        for(int i=0;i<nameBt.length;i++){
            String str = ByteUtil.convertByte2HexString(nameBt[i]);
            ds+=str;
        }

        return ds;
    }


    /**
     * 获取字符串的ascii码
     * @param input
     * @return
     */

    public static String getAscii2(String input){

        byte[] nameBt = new byte[input.length()];
        try {
            nameBt = input.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String ds = "";
        for(int i=0;i<nameBt.length;i++){
            String str = ByteUtil.convertByte2HexString(nameBt[i]);
            ds+=str;
        }

        return ds;
    }

    public static String getEncrypt(String input){
        String ds = "";
        try {

        byte[] nameBt = new byte[input.getBytes("GBK").length];
        try {
            nameBt = input.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        for(int i=0;i<nameBt.length;i++){
            nameBt[i] = (byte)(0x24 ^nameBt[i]);
            String str = ByteUtil.convertByte2HexString(nameBt[i]);
            ds+=str;
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ds;
    }


    public static String getDecrypt(String input){



        try {
            byte[]a = ByteUtil.hexStr2Bytes(input);

            for(int i=0;i < a.length;i++){
                a[i] = (byte)(0x24 ^a[i]);
            }

            String name  = new String(a,"GBK");

            return  name;

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }


    }

    /**
     * syn get device status
     */
    public static String getEqCRC(Context context,String deviceid) {

            DeviceAliDAO ED = new DeviceAliDAO(context);
            List<DeviceInfoBean> allEquipmentInformation = ED.findAllSubDevice(deviceid);
            if (allEquipmentInformation.size() > 0) {


                int listLength = allEquipmentInformation.get(allEquipmentInformation.size() - 1).getDevice_ID();

                List<Integer> eqid = new ArrayList<>();
                for (DeviceInfoBean e : allEquipmentInformation) {
                    eqid.add(e.getDevice_ID());
                }
                String statusCRC = "";
                for (int i = 1; i <= listLength; i++) {
                    if (eqid.contains(i)) {
                        String crc = ByteUtil.CRCmakerChar(allEquipmentInformation.get(eqid.indexOf(i)).getDevice_status());
                        statusCRC += crc;
                    } else {
                        statusCRC += "0000";
                    }
                }
                Log.i(TAG, "ceshi crc " + statusCRC);

                String oooo = "0", num = "";
                if (Integer.toHexString(listLength*2+2).length() < 4) {
                    for (int i = 0; i < 4 - Integer.toHexString(listLength*2+2).length() - 1; i++) {
                        oooo += 0;
                    }
                    num = oooo + Integer.toHexString(listLength*2+2);
                } else {
                    num = Integer.toHexString(listLength*2+2);
                }

                return (num + statusCRC);
            } else {
                Log.i(TAG, "there is no equipment exist");
                return "00020000";
            }

    }


    /**
     * syn get device name
     */
    public static String getEqNameCRC(Context context,String deviceid) {
           DeviceAliDAO ED = new DeviceAliDAO(context);
            List<DeviceInfoBean> allEquipmentInformation = ED.findAllSubDevice(deviceid);
            if (allEquipmentInformation.size() > 0) {
                int listLength = allEquipmentInformation.get(allEquipmentInformation.size() - 1).getDevice_ID();
                List<Integer> eqid = new ArrayList<>();
                for (DeviceInfoBean e : allEquipmentInformation) {
                    eqid.add(e.getDevice_ID());
                }
                String statusCRC = "";
                for (int i = 1; i <= listLength; i++) {
                    if (eqid.contains(i)) {
                        if(!TextUtils.isEmpty(allEquipmentInformation.get(eqid.indexOf(i)).getSubdeviceName())){
                            ByteUtil byteUtil = new ByteUtil();
                            String asciiName = CoderALiUtils.getAscii(allEquipmentInformation.get(eqid.indexOf(i)).getSubdeviceName());
                            String crc = ByteUtil.CRCmaker(asciiName);
                            statusCRC += crc;
                        }else{
                            statusCRC += "0000";
                        }

                    } else {
                        statusCRC += "0000";
                    }
                }
                Log.i(TAG, "ceshi crc " + statusCRC);

                String oooo = "0", num = "";
                if (Integer.toHexString(listLength*2+2).length() < 4) {
                    for (int i = 0; i < 4 - Integer.toHexString(listLength*2+2).length() - 1; i++) {
                        oooo += 0;
                    }
                    num = oooo + Integer.toHexString(listLength*2+2);
                } else {
                    num = Integer.toHexString(listLength*2+2);
                }

                return (num + statusCRC);
            } else {
                return("00020000");
            }


    }




    /**
     * syn get scene list crc
     * @param context
     * @return
     */
    public static String getSceneCRC(Context context,String deviceid){

        SceneAliDAO SD = new SceneAliDAO(context);
        List<SceneAliBean> sceneList = SD.findAllAmWithoutDefault(deviceid);
        if(sceneList != null && sceneList.size()>0) {

            String oooo = "0", num = "";
            int codeLength = 2;
            int listLength = sceneList.get(sceneList.size() - 1).getMid();
            List<Integer> eqid = new ArrayList<>();
            for (SceneAliBean e : sceneList) {
                eqid.add(e.getMid());
            }
            String sceneCRC = "";
            for (int i = 1; i <= listLength; i++) {
                codeLength += 2;
                if (eqid.contains(i)) {
                    ByteUtil byteUtil = new ByteUtil();
                    String ds = sceneList.get(eqid.indexOf(i)).getCode();

                    String crc = ByteUtil.CRCmakerCharAndCode(ds);
                    sceneCRC += crc;
                } else {
                    sceneCRC += "0000";
                }
            }
            Log.i(TAG, "ceshi crc " + sceneCRC);

            int totalLength = codeLength ;
            Integer.toHexString(totalLength);
            if (Integer.toHexString(totalLength).length() < 4) {
                for (int i = 0; i < 4 - Integer.toHexString(totalLength).length()-1; i++) {
                    oooo += 0;
                }
                num = oooo + Integer.toHexString(totalLength);
            } else {
                num = Integer.toHexString(codeLength);
            }
            return num + sceneCRC;
        }else {
            return "00040000";
        }

    }

    /**
     * get
     * @param context
     * @return
     */
    public static String getSceneGroupCRC(Context context,String deviceid){
        String getSceneGroupCRC="",num="";

        SysmodelAliDAO SD = new SysmodelAliDAO(context);
        ShortcutAliDAO shortcutDAO = new ShortcutAliDAO(context);
        List<SysModelAliBean> slist = SD.findAllSys(deviceid);

        if(slist.size()>0){
            int codeLength = 2;
            List<Integer> sid = new ArrayList<>();
            for(SysModelAliBean e : slist){
                sid.add(e.getSid());
            }
            for(int i = 0 ; i <slist.get(slist.size()-1).getSid()+1 ; i++) {//for here come with "0" , used slist.size()
                codeLength += 2;
                if (sid.contains(i)) {
                    SceneRelaitonAliDAO SED = new SceneRelaitonAliDAO(context);
                    List<SceneRelationBean> sysSceneList = SED.findRelationsBysid(deviceid,i);
                    SysModelAliBean sysModelBean = SD.findBySid(i,deviceid);
                    String name = sysModelBean.getModleName();
                    int length = 0;
                    byte scene_default = 0;
                    length = 0;
                    length += 2;//the total num length

                    String id2 = String.valueOf(sysModelBean.getSid());
                    length += 1;//the scene id

                    String btnNum = "";
                    List<ShortcutAliBean> shortcutBeans = shortcutDAO.findShorcutsBysid(deviceid,sysModelBean.getSid());

                    if (Integer.toHexString(shortcutBeans.size()).length()<2){  //new mid
                        btnNum = "0"+Integer.toHexString(shortcutBeans.size());
                    }else{
                        btnNum =Integer.toHexString(shortcutBeans.size());
                    }
                    length+=1;//button num

                    String shortcut = "";

                    for (int j = 0;j<shortcutBeans.size();j++){



                        String eqid = "";
                        String dessid = "";
                        if (Integer.toHexString(shortcutBeans.get(j).getEqid()).length()<2){  //new mid
                            eqid = "000"+Integer.toHexString(shortcutBeans.get(j).getEqid());
                        }else{
                            eqid ="00"+Integer.toHexString(shortcutBeans.get(j).getEqid());
                        }

                        if (Integer.toHexString(shortcutBeans.get(j).getDes_sid()).length()<2){  //new mid
                            dessid = "0"+Integer.toHexString(shortcutBeans.get(j).getDes_sid())+"000000";
                        }else{
                            dessid =Integer.toHexString(shortcutBeans.get(j).getDes_sid())+"000000";
                        }

                        shortcut+=(eqid+dessid+"00");
                        length += 7;
                    }

                    String color = sysModelBean.getColor();
                    length+=1;
                    //self-define scene num
                    length += 1;
                    int scene = 0;
                    //scene id
                    String sceneCode = "";
                    for (int j = 0; j < sysSceneList.size(); j++) {
                        if(sysSceneList.get(j).getMid()<=128){
                            scene++;
                            length++;
                            String singleCode = "";

                            if (Integer.toHexString(sysSceneList.get(j).getMid()).length() < 2) {  //new mid
                                singleCode = "0" + Integer.toHexString(sysSceneList.get(j).getMid());
                            } else {
                                singleCode = Integer.toHexString(sysSceneList.get(j).getMid());
                            }
                            sceneCode += singleCode;
                        }else{
                            if(sysSceneList.get(j).getMid()==129){
                                scene_default = (byte)(scene_default|0x81);
                            }else if(sysSceneList.get(j).getMid()==130){
                                scene_default = (byte)(scene_default|0x82);
                            }else if(sysSceneList.get(j).getMid()==131){
                                scene_default = (byte)(scene_default|0x84);
                            }
                        }

                    }
                    scene_default = (byte)(scene_default|0x80);
                    length +=1;
                    String oooo = "0";
                    if (Integer.toHexString(length + 32).length() < 4) {
                        for (int k = 0; k < 4 - Integer.toHexString(length + 32).length() - 1; k++) {
                            oooo += 0;
                        }
                        oooo += Integer.toHexString(length + 32);
                    } else {
                        oooo = Integer.toHexString(length + 32);
                    }

                    String oo = "0";
                    if (Integer.toHexString(scene).length() < 2) {
                        oo = oo + Integer.toHexString(scene);
                    } else {
                        oo = Integer.toHexString(scene);
                    }
                    String ds = "";
                    if(i<=2){
                        ds = CoderALiUtils.getAscii("");
                    }else {
                        ds = CoderALiUtils.getAscii(name);
                    }

                    String str_default_scene = ByteUtil.convertByte2HexString(scene_default);
                    String fullCode = oooo + "0" + id2 + ds + btnNum + oo + shortcut + sceneCode + color+str_default_scene;
                    getSceneGroupCRC += ByteUtil.CRCmakerCharAndCode(fullCode);

                }else {
                    getSceneGroupCRC += "0000";
                }
            }
            String oooo = "0";
            int totalLength = codeLength ;
            Integer.toHexString(totalLength);
            if (Integer.toHexString(totalLength).length() < 4) {
                for (int i = 0; i < 4 - Integer.toHexString(totalLength).length()-1; i++) {
                    oooo += 0;
                }
                num = oooo + Integer.toHexString(totalLength);
            } else {
                num = Integer.toHexString(codeLength);
            }
            return num + getSceneGroupCRC;
        }else{
            return "00040000";
        }

    }

    /**
     * get
     * @param context
     * @return
     */
    public static String getTimeCRC(Context context,String deviceid){
        String getSceneGroupCRC="",num="";

        TimerAliDAO SD = new TimerAliDAO(context);
        List<TimerGatewayAliBean> slist = SD.findAllTimer(deviceid);

        if(slist.size()>0) {
            int codeLength = 2;
            List<Integer> tid = new ArrayList<>();
            for (TimerGatewayAliBean e : slist) {
                tid.add(e.getTimerid());
            }


            for (int i = 0; i < slist.get(slist.size() - 1).getTimerid() + 1; i++) {//for here come with "0" , used slist.size()
                codeLength += 2;
                if (tid.contains(i)) {
                    TimerGatewayAliBean timerGatewayBean = SD.findByTid(i,deviceid);
                    getSceneGroupCRC += ResolveAliTimer.getCRCCode(timerGatewayBean);
                } else {
                    getSceneGroupCRC += "0000";
                }
            }


            String oooo = "0";

            if (Integer.toHexString(codeLength).length() < 4) {
                for (int i = 0; i < 4 - Integer.toHexString(codeLength).length() - 1; i++) {
                    oooo += 0;
                }
                num = oooo + Integer.toHexString(codeLength);
            } else {
                num = Integer.toHexString(codeLength);
            }
            return num + getSceneGroupCRC;
        }else {
            return "0000";
        }
    }

}
