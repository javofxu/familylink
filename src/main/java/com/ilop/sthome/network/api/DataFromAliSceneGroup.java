package com.ilop.sthome.network.api;

import android.content.Context;
import android.util.Log;

import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneRelationDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneSwitchDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ST-020111 on 2017/4/2.
 */

public class DataFromAliSceneGroup {
    private static final String TAG = DataFromAliSceneGroup.class.getName();
    private DeviceInfoBean deviceInfoBean;
    private Context context;
    private SendSceneGroupDataAli ssgd;
    private List<SceneBean> sceneList;
    public DataFromAliSceneGroup(Context context,DeviceInfoBean deviceInfoBean){
        this.context = context;
        sceneList = new ArrayList<>();
        this.deviceInfoBean = deviceInfoBean;
        getAllSceneId();
    }

    public List<SceneBean> getAllSceneId(){
        sceneList = SceneDaoUtil.getInstance().findAllScene(this.deviceInfoBean.getDeviceName());
        ssgd = new SendSceneGroupDataAli(context,this.deviceInfoBean);
        return sceneList;
    }

    public void doSendSynCode(){
        for (SceneBean smb : sceneList){
            ssgd.increaceSceneGroup(makeSceneCode(smb));
        }
    }


    /**
     * get scene group from local sql
     * @return
     */
    public String makeSceneCode(SceneBean smb){
        byte scene_default  = 0;
        int sceneGroupId = smb.getSid();
        int length = 0;
        String name = null;
        if( sceneGroupId > 2){
            name = smb.getModleName();
        }else{
            name = "";
        }

        length+=2;//the total num length

        int id2 = smb.getSid();
        length += 1;//the scene id

        String btnNum = "";
        List<SceneSwitchBean> mSwitch = SceneSwitchDaoUtil.getInstance().findSwitchBySid(smb.getSid(), smb.getDeviceName());

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


        String color = smb.getColor();
        if(color.indexOf("F")==-1){
            color = "F" + smb.getSid();
        }
        length+=1;//button num


        //self-define scene num
        length+=1;
        int scene =0;
        //scene id
        String sceneCode ="";
        List<SceneRelationBean> sceneLists = SceneRelationDaoUtil.getInstance().findRelationsBySid(deviceInfoBean.getDeviceName(),smb.getSid());
        if(sceneLists != null && sceneLists.size()>0){
            for(int i = 0; i<sceneLists.size();i++){
                if(sceneLists.get(i).getMid()<=128){
                    scene++;
                    length++;
                    String singleCode ="";
                    if (Integer.toHexString(sceneLists.get(i).getMid()).length()<2){  //new mid
                        singleCode = "0"+Integer.toHexString(sceneLists.get(i).getMid());
                    }else{
                        singleCode =Integer.toHexString(sceneLists.get(i).getMid());
                    }
                    sceneCode += singleCode;
                }else {
                    if(sceneLists.get(i).getMid()==129){
                        scene_default = (byte)(scene_default|0x81);
                    }else if(sceneLists.get(i).getMid()==130){
                        scene_default = (byte)(scene_default|0x82);
                    }else if(sceneLists.get(i).getMid()==131){
                        scene_default = (byte)(scene_default|0x84);
                    }
                }

            }
        }else {
            length+=0;
            sceneCode = "";
        }
        scene_default = (byte)(scene_default|0x80);
        length+=1;

        String oooo ="0";
        if(Integer.toHexString(length+32).length() < 4){
            for (int i = 0 ; i<4-Integer.toHexString(length+32).length()-1;i++ ){
                oooo += 0;
            }
            oooo += Integer.toHexString(length+32);
        }else{
            oooo = Integer.toHexString(length+32);
        }

        String oo = "0";
        if(Integer.toHexString(scene).length()<2){
            oo = oo + Integer.toHexString(scene);
        }else{
            oo = Integer.toHexString(scene);
        }
        String ds = CoderALiUtils.getAscii(name);
        String str_default_scene = ByteUtil.convertByte2HexString(scene_default);
        String fullCode = oooo +"0"+id2 + ds + btnNum + oo +shortcut+ sceneCode + color+str_default_scene;
        String deCode = fullCode;
        String crc = ByteUtil.CRCmakerCharAndCode(fullCode);
        Log.i(TAG,"fullCode:"+fullCode);
//        sendMsg(fullCode + crc);
        Log.i(TAG, " sysdetail CRC +++++++++++++++"+ crc);
        return deCode + crc;
    }
}
