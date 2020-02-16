package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.SceneRelationBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.SceneRelaitonAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.contract.AddSceneContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AddScenePresenter extends BasePresenterImpl<AddSceneContract.IView> implements AddSceneContract.IPresent {
    private static final String TAG = "AddScenePresenter";
    private Context mContext;
    private String mDeviceName;
    private String mSceneName;
    private String messageCode;
    private int confirmNum = 0;
    private int length = 0;
    private int mGateway = 0;
    private String mColorCode = "F0";
    private SysmodelAliDAO mSysModelAliDAO;
    private SysModelAliBean mSysModelAliBean;
    private SendSceneGroupDataAli mSendScene;
    private List<SceneAliBean> mSceneList;
    private List<DeviceInfoBean> mGatewayList;

    public AddScenePresenter(Context mContext) {
        this.mContext = mContext;
        mGatewayList = new ArrayList<>();
        mSysModelAliDAO = new SysmodelAliDAO(mContext);
    }

    @Override
    public void getDefaultColorCode(String code) {
        mColorCode = "F"+code;
    }

    @Override
    public void getGatewayList() {
        mGatewayList.clear();
        mGatewayList = DeviceDaoUtil.getInstance().findAllGateway();
        if (mGatewayList.size()>0){
            mView.showGatewayList(mGatewayList);
        }else {
            mView.withoutGateway();
        }
    }


    @Override
    public void onSaveScene(String name, int gateway, List<SceneAliBean> list) {
        mView.showProgress();
        this.mGateway = gateway;
        mDeviceName = mGatewayList.get(gateway).getDeviceName();
        mSceneName = name;
        mSceneList = list;
        confirmNum ++;
        check(name);
        if (confirmNum==1){
            confirmToSys(list);
        }else if(confirmNum==-1){
            mView.showToastMsg(mContext.getString(R.string.name_is_null));
            confirmNum = 0;
        }else if(confirmNum==-2){
            mView.showToastMsg(mContext.getString(R.string.name_is_too_long));
            confirmNum = 0;
        }else if(confirmNum==-3){
            mView.showToastMsg(mContext.getString(R.string.name_is_illegal));
            confirmNum = 0;
        }else{
            sendDoData(messageCode);
        }
    }

    @Override
    public void onSaveSuccess() {
        mSysModelAliDAO = new SysmodelAliDAO(mContext);
        if(!mSysModelAliDAO.isHasSysmodel(mSysModelAliBean.getSid(), mDeviceName)){
            mSysModelAliDAO.add(mSysModelAliBean);
            for (int i = 0; i < mSceneList.size(); i++) {
                SceneAliBean ab = mSceneList.get(i);
                doAddToSceneTable(ab, mSysModelAliBean);
            }
        }
    }


    @Override
    public void onSaveFailed() {
        mView.showToastMsg(mContext.getString(R.string.EE_AS_SEND_EMAIL_FOR_CODE4));
        mView.disProgress();
    }

    @Override
    public int getSid() {
        List<Integer> list = mSysModelAliDAO.findAllSysmodelSid(mDeviceName);
        if(list.size()<3){
            return -1;
        }else{
            int m = 0;
            for(int i=0;i<list.size()-1;i++){
                if( (list.get(i)+1) < list.get(i+1)){
                    m = list.get(i)+1;
                    break;
                }else{
                    m = i+2;
                }
            }
            return m;
        }
    }


    private void doAddToSceneTable(SceneAliBean ab, SysModelAliBean sys2) {
        try {
            SceneRelaitonAliDAO sceneRelaitonAliDAO = new SceneRelaitonAliDAO(mContext);
            SceneRelationBean sceneRelationBean = new SceneRelationBean();
            sceneRelationBean.setSid(sys2.getSid());
            sceneRelationBean.setMid(ab.getMid());
            sceneRelationBean.setDeviceName(ab.getDeviceName());
            sceneRelaitonAliDAO.insertSceneRelation(sceneRelationBean);
        }catch (Exception e){
            Log.i(TAG,"SceneBean is null or SysModleBean is null");
        }
        mView.disProgress();
    }

    private void sendDoData(String fullCode) {
        mSendScene = new SendSceneGroupDataAli(mContext, mGatewayList.get(mGateway));
        mSendScene.increaceSceneGroup(fullCode);
    }

    private void check(String name) {
        try {
            if(TextUtils.isEmpty(name)){
                confirmNum = -1;
            }
            else if(name.getBytes("GBK").length>15){
                confirmNum = -2;
            }   else if(name.contains("@") || name.contains("$")){
                confirmNum = -3;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void confirmToSys(List<SceneAliBean> list) {
        byte scene_default = 0;
        int id2 = getSid();
        mSysModelAliBean = new SysModelAliBean();
        mSysModelAliBean.setModleName(mSceneName);
        mSysModelAliBean.setChoice(0);
        mSysModelAliBean.setSid(id2);
        mSysModelAliBean.setDeviceName(mDeviceName);
        length += 2;
        length += 1;
        length += 1;
        String button4 = "00";
        length += 1;
        int scene = 0;
        //scene id
        String sceneCode = "";
        for (int i = 0; i < list.size(); i++) {
            SceneAliBean ab = list.get(i);
            if(ab.getMid()<=128){
                scene++;
                length++;
                String singleCode;
                if (Integer.toHexString(ab.getMid()).length() < 2) {  // new  mid
                    singleCode = "0" + Integer.toHexString(ab.getMid());
                } else {
                    singleCode = Integer.toHexString(ab.getMid());
                }
                sceneCode += singleCode;
            }else{
                if(ab.getMid()==129){
                    scene_default = (byte)(scene_default|0x81);
                }else if(ab.getMid()==130){
                    scene_default = (byte)(scene_default|0x82);
                }else if(ab.getMid()==131){
                    scene_default = (byte)(scene_default|0x84);
                }
            }
        }
        scene_default = (byte)(scene_default|0x80);
        //增加了颜色的定制
        length+=2;
        mSysModelAliBean.setColor(mColorCode);

        String oooo = "0";
        if (Integer.toHexString(length+32).length() < 4) {
            for (int i = 0; i < 4 - Integer.toHexString(length+32).length() - 1; i++) {
                oooo += 0;
            }
            oooo += Integer.toHexString(length+32);
        } else {
            oooo = Integer.toHexString(length+32);
        }

        String oo = "0";
        if (Integer.toHexString(scene).length() < 2) {
            oo = oo + Integer.toHexString(scene);
        } else {
            oo = Integer.toHexString(scene);
        }
        String name = mSceneName;
        String ds = CoderALiUtils.getAscii(name);
        String str_default_scene = ByteUtil.convertByte2HexString(scene_default);
        String fullCode = oooo + "0" + id2 + ds + button4 + oo + sceneCode + mColorCode + str_default_scene;
        messageCode = fullCode;
        String crc = ByteUtil.CRCmakerCharAndCode(fullCode);
        sendDoData(fullCode + crc);
        confirmNum = 0;
    }
}
