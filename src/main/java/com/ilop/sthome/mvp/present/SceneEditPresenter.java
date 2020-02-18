package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.mvp.contract.SceneEditContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;
import com.ilop.sthome.ui.dialog.TipDialog;
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
 * @date 2020-01-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneEditPresenter extends BasePresenterImpl<SceneEditContract.IView> implements SceneEditContract.IPresent {

    private Context mContext;
    private int mSceneId;
    private String mDeviceName;
    private String mName;
    private String mColors;
    private SceneBean mScene;
    private SendSceneGroupDataAli mSendSceneDataAli;

    public SceneEditPresenter(Context mContext, String mDeviceName, int sceneId) {
        this.mContext = mContext;
        this.mSceneId = sceneId;
        this.mDeviceName = mDeviceName;
        DeviceInfoBean mDeviceInfo = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceName);
        mSendSceneDataAli = new SendSceneGroupDataAli(mContext, mDeviceInfo);
    }

    @Override
    public void refreshName() {
        mScene = SceneDaoUtil.getInstance().findSceneBySid(mSceneId, mDeviceName);
        mColors = mScene.getColor();
        switch (mSceneId){
            case 0:
                mName = mContext.getString(R.string.home_mode);
                break;
            case 1:
                mName = mContext.getString(R.string.out_mode);
                break;
            case 2:
                mName = mContext.getString(R.string.sleep_mode);
                break;
            case 3:
                mName = mContext.getString(R.string.custom_scene);
                break;
            default:
                mName = mScene.getModleName();
                break;

        }
        mView.showSceneName(mName);
        mView.showSceneColor(Integer.parseInt(mColors.substring(1)));
    }

    @Override
    public void setSceneColor(int color) {
        mColors = "F" + color;
    }

    @Override
    public void onSaveScene() {
        List<AutomationBean> mSceneList = AutomationDaoUtil.getInstance().findAllAutoWithoutDefault(mDeviceName);
        createCode(mSceneList);
    }

    @Override
    public void onSaveSuccess() {
        mScene.setColor(mColors);
        mScene.setModleName(mName);
        SceneDaoUtil.getInstance().getSceneDao().update(mScene);
        mView.onSuccess();
    }

    @Override
    public void deleteScene() {
        List<AutomationBean> sceneList = AutomationDaoUtil.getInstance().findAllAutoBySid(mSceneId, mDeviceName);
        if (sceneList.size()>0){
            mView.showToastMsg(mContext.getString(R.string.current_mode_not_allow_delete));
        }else {
            String sd = String.format(mContext.getResources().getString(R.string.want_to_delete_confirm_eq), mName);
            TipDialog dialog = new TipDialog(mContext, ()->{
                mView.showProgress();
                mSendSceneDataAli.deleteSceneGroup(mSceneId);
            });
            dialog.setMsg(sd);
            dialog.show();
        }
    }

    @Override
    public void deleteSceneSuccess(int sceneId) {
        SceneDaoUtil.getInstance().deleteBySid(sceneId, mDeviceName);
        SceneRelationDaoUtil.getInstance().deleteAllRelation(sceneId, mDeviceName);
        SceneSwitchDaoUtil.getInstance().deleteAllSwitch(sceneId, mDeviceName);
        mView.onSuccess();
    }

    private void createCode(List<AutomationBean> mAutoLists) {
        byte scene_default = 0;
        String name;
        if(mSceneId>3){
            name = mName;
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
        if(mAutoLists.size()>0){
            for(int i = 0; i< mAutoLists.size();i++){
                scene++;
                length++;
                String singleCode;
                if (Integer.toHexString(mAutoLists.get(i).getMid()).length()<2){  //new mid
                    singleCode = "0"+Integer.toHexString(mAutoLists.get(i).getMid());
                }else{
                    singleCode =Integer.toHexString(mAutoLists.get(i).getMid());
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
        mSendSceneDataAli.increaceSceneGroup(fullCode + crc);
    }
}
