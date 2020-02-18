package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.mvp.contract.SceneDetailContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;
import com.ilop.sthome.ui.activity.scene.AutomationDetailActivity;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.AutomationDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneRelationDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneSwitchDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-19.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneDetailPresenter extends BasePresenterImpl<SceneDetailContract.IView> implements SceneDetailContract.IPresent {

    private static final String TAG = "SceneDetailPresenter";

    private Context mContext;
    private SendSceneGroupDataAli mSendScene;

    private String mDeviceName;
    private String mSceneName;
    private String mColors;
    private int mSceneId;
    private SceneBean mScene;

    public SceneDetailPresenter(Context mContext, String deviceName, int sceneId) {
        this.mContext = mContext;
        this.mDeviceName = deviceName;
        this.mSceneId = sceneId;
        DeviceInfoBean mDeviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
        mSendScene = new SendSceneGroupDataAli(mContext, mDeviceInfoBean);
    }

    @Override
    public void getSceneName() {
        mScene = SceneDaoUtil.getInstance().findSceneBySid(mSceneId, mDeviceName);
        mColors = mScene.getColor();
        String mTitleName = mScene.getModleName();
        switch (mSceneId){
            case 0:
                mSceneName = mContext.getString(R.string.home_mode);
                break;
            case 1:
                mSceneName = mContext.getString(R.string.out_mode);
                break;
            case 2:
                mSceneName = mContext.getString(R.string.sleep_mode);
                break;
            case 3:
                mSceneName = mContext.getString(R.string.custom_scene);
                break;
            default:
                mSceneName = mTitleName;
                mView.showSceneName(mTitleName);
                break;
        }
        mView.showSceneName(mSceneName);
    }

    @Override
    public void getAutoList() {
        List<AutomationBean> mSceneList = AutomationDaoUtil.getInstance().findAllAutoWithoutDefault(mDeviceName);
        if (mSceneList.size()>0){
            List<Integer> beforeList = SceneRelationDaoUtil.getInstance().findMidBySid(mDeviceName, mSceneId);
            mView.showAutoList(mSceneList, beforeList);
        }else {
            mView.withoutAuto();
        }
    }

    @Override
    public void onSaveScene(List<AutomationBean> list) {
        createCode(list);
    }

    @Override
    public void onSaveSuccess(List<AutomationBean> mSceneList) {
        SceneRelationDaoUtil.getInstance().deleteAllRelation(mSceneId, mDeviceName);
        if(mSceneList.size()>0) {
            for (int i = 0; i < mSceneList.size(); i++) {
                toAddSceneDb(mSceneList.get(i), mScene);
            }
        }
    }

    @Override
    public void skipActivityToUpdate(AutomationBean automationBean) {
        Intent intent = new Intent();
        intent.setClass(mContext, AutomationDetailActivity.class);
        intent.putExtra("deviceName", automationBean.getDeviceName());
        intent.putExtra("Modify", true);
        intent.putExtra("Scene", automationBean);
        mView.skipActivity(intent);
    }

    private void createCode(List<AutomationBean> mSceneLists) {
        byte scene_default = 0;
        String name;
        if(mSceneId>3){
            name = mSceneName;
        }else{
            name = "";
        }

        int length = 0;
        length +=2;//the total num length

        String id2 = String.valueOf(mSceneId);
        length += 1;//the scene id

        String btnNum;
        List<SceneSwitchBean> mSwitch = SceneSwitchDaoUtil.getInstance().findSwitchBySid(mSceneId, mDeviceName);

        if (Integer.toHexString(mSwitch.size()).length()<2){  //new mid
            btnNum = "0"+Integer.toHexString(mSwitch.size());
        }else{
            btnNum =Integer.toHexString(mSwitch.size());
        }
        length +=1;//button num

        String shortcut = "";

        for (int i = 0;i<mSwitch.size();i++){
            String eqid ;
            String dessid ;
            if (Integer.toHexString(mSwitch.get(i).getDeviceId()).length()<2){  //new mid
                eqid = "000"+Integer.toHexString(mSwitch.get(i).getDeviceId());
            }else{
                eqid ="00"+Integer.toHexString(mSwitch.get(i).getDeviceId());
            }

            if (Integer.toHexString(mSwitch.get(i).getDes_sid()).length()<2){  //new mid
                dessid = "0"+Integer.toHexString(mSwitch.get(i).getDes_sid())+"000000";
            }else{
                dessid =Integer.toHexString(mSwitch.get(i).getDes_sid())+"000000";
            }

            shortcut+=(eqid+dessid+"00");
            length += 7;
        }


        //self-define scene num
        length +=1;
        int scene =0;
        //scene id
        String sceneCode ="";
        if(mSceneLists.size()>0){
            for(int i = 0; i< mSceneLists.size();i++){
                scene++;
                length++;
                String singleCode;
                if (Integer.toHexString(mSceneLists.get(i).getMid()).length()<2){  //new mid
                    singleCode = "0"+Integer.toHexString(mSceneLists.get(i).getMid());
                }else{
                    singleCode =Integer.toHexString(mSceneLists.get(i).getMid());
                }
                sceneCode += singleCode;
            }
        }else {
            length +=0;
            sceneCode = "";
        }

        scene_default = (byte)(scene_default|0x80);
        //增加了颜色的定制
        length +=2;

        String oooo ="0";
        if(Integer.toHexString(length +32).length() < 4){
            for (int i = 0; i<4-Integer.toHexString(length +32).length()-1; i++ ){
                oooo += 0;
            }
            oooo += Integer.toHexString(length +32);
        }else{
            oooo = Integer.toHexString(length +32);
        }

        String oo = "0";
        if(Integer.toHexString(scene).length()<2){
            oo = oo + Integer.toHexString(scene);
        }else{
            oo = Integer.toHexString(scene);
        }
        String ds = CoderALiUtils.getAscii(name);
        String str_default_scene = ByteUtil.convertByte2HexString(scene_default);
        String fullCode = oooo +"0"+id2 + ds + btnNum  + oo + shortcut + sceneCode + mColors +str_default_scene;
        String crc = ByteUtil.CRCmakerCharAndCode(fullCode);
        mSendScene.increaceSceneGroup(fullCode + crc);
    }

    private void toAddSceneDb(AutomationBean ab, SceneBean sys2) {
        try {
            SceneRelationBean sceneRelationBean = new SceneRelationBean();
            sceneRelationBean.setSid(sys2.getSid());
            sceneRelationBean.setDeviceName(ab.getDeviceName());
            sceneRelationBean.setMid(ab.getMid());
            SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
        }catch (Exception e){
            Log.i(TAG,"SceneBean is null or SysModelBean is null");
        }

    }
}
