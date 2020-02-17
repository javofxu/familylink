package com.ilop.sthome.utils;


import android.content.Context;
import android.text.TextUtils;

import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.utils.greenDao.AutomationDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneRelationDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneSwitchDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;

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

    public static String getWeekInfo(String week,Context context){

        try {
            String weektime = "";
            byte ds = ByteUtil.hexStr2Bytes(week)[0];
            byte f = 0x00;
            for(int i=0;i<7;i++){

                f =   (byte)((0x02 << i) & ds);
                if(f!=0){
                    weektime += (context.getResources().getStringArray(R.array.week2)[i] + " 、");
                }
            }
            weektime = weektime.substring(0,weektime.length()-1);
            return weektime;
        }catch (Exception e){
            android.util.Log.i(TAG,"week is null");
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

            List<DeviceInfoBean> allEquipmentInformation = DeviceDaoUtil.getInstance().findAllSubDevice(deviceid);
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
            List<DeviceInfoBean> allEquipmentInformation = DeviceDaoUtil.getInstance().findAllSubDevice(deviceid);
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

        List<AutomationBean> sceneList = AutomationDaoUtil.getInstance().findAllAutoWithoutDefault(deviceid);
        if(sceneList != null && sceneList.size()>0) {

            String oooo = "0", num = "";
            int codeLength = 2;
            int listLength = sceneList.get(sceneList.size() - 1).getMid();
            List<Integer> eqid = new ArrayList<>();
            for (AutomationBean e : sceneList) {
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
    public static String getSceneGroupCRC(Context context,String deviceName){
        String getSceneGroupCRC="";
        String num;

        List<SceneBean> slist = SceneDaoUtil.getInstance().findAllScene(deviceName);

        if(slist.size()>0){
            int codeLength = 2;
            List<Integer> sid = new ArrayList<>();
            for(SceneBean e : slist){
                sid.add(e.getSid());
            }
            for(int i = 0 ; i <slist.get(slist.size()-1).getSid()+1 ; i++) {//for here come with "0" , used slist.size()
                codeLength += 2;
                if (sid.contains(i)) {
                    List<SceneRelationBean> mRelation = SceneRelationDaoUtil.getInstance().findRelationsBySid(deviceName,i);
                    SceneBean sysModelBean = SceneDaoUtil.getInstance().findSceneBySid(i,deviceName);
                    String name = sysModelBean.getModleName();
                    int length = 0;
                    byte scene_default = 0;
                    length = 0;
                    length += 2;//the total num length

                    String id2 = String.valueOf(sysModelBean.getSid());
                    length += 1;//the scene id

                    String btnNum = "";
                    List<SceneSwitchBean> mSwitch = SceneSwitchDaoUtil.getInstance().findSwitchBySid(sysModelBean.getSid(), deviceName);

                    if (Integer.toHexString(mSwitch.size()).length()<2){  //new mid
                        btnNum = "0"+Integer.toHexString(mSwitch.size());
                    }else{
                        btnNum =Integer.toHexString(mSwitch.size());
                    }
                    length+=1;//button num

                    String shortcut = "";

                    for (int j = 0;j<mSwitch.size();j++){



                        String eqid = "";
                        String dessid = "";
                        if (Integer.toHexString(mSwitch.get(j).getDeviceId()).length()<2){  //new mid
                            eqid = "000"+Integer.toHexString(mSwitch.get(j).getDeviceId());
                        }else{
                            eqid ="00"+Integer.toHexString(mSwitch.get(j).getDeviceId());
                        }

                        if (Integer.toHexString(mSwitch.get(j).getDes_sid()).length()<2){  //new mid
                            dessid = "0"+Integer.toHexString(mSwitch.get(j).getDes_sid())+"000000";
                        }else{
                            dessid =Integer.toHexString(mSwitch.get(j).getDes_sid())+"000000";
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
                    for (int j = 0; j < mRelation.size(); j++) {
                        if(mRelation.get(j).getMid()<=128){
                            scene++;
                            length++;
                            String singleCode = "";

                            if (Integer.toHexString(mRelation.get(j).getMid()).length() < 2) {  //new mid
                                singleCode = "0" + Integer.toHexString(mRelation.get(j).getMid());
                            } else {
                                singleCode = Integer.toHexString(mRelation.get(j).getMid());
                            }
                            sceneCode += singleCode;
                        }else{
                            if(mRelation.get(j).getMid()==129){
                                scene_default = (byte)(scene_default|0x81);
                            }else if(mRelation.get(j).getMid()==130){
                                scene_default = (byte)(scene_default|0x82);
                            }else if(mRelation.get(j).getMid()==131){
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

}
