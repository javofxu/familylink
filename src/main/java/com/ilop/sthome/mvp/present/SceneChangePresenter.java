package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.mvp.contract.SceneChangeContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-14.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneChangePresenter extends BasePresenterImpl<SceneChangeContract.IView> implements SceneChangeContract.present{

    private Context mContext;
    private String mDeviceName;
    private SysmodelAliDAO mSysModelAliDAO;
    private SendSceneGroupDataAli mSendSceneDataAli;

    public SceneChangePresenter(Context context, String deviceName) {
        this.mContext = context;
        this.mDeviceName = deviceName;
        mSysModelAliDAO = new SysmodelAliDAO(mContext);
        DeviceAliDAO mDeviceAliDAO = new DeviceAliDAO(mContext);
        DeviceInfoBean mDeviceInfo = mDeviceAliDAO.findByDeviceid(mDeviceName, 0);
        mSendSceneDataAli = new SendSceneGroupDataAli(mContext, mDeviceInfo);
    }

    @Override
    public void getSceneList() {
        List<SysModelAliBean> mList = mSysModelAliDAO.findAllSys(mDeviceName);
        if (mList.size()>0){
            mView.showSceneList(mList);
        }else {
            mView.withOutScene();
        }
    }

    @Override
    public void changeScene(SysModelAliBean scene) {
        if(scene.getChoice()!=1 && mDeviceName.equals(scene.getDeviceName())){
            mSendSceneDataAli.sceneGroupChose(scene.getSid());
            mView.showProgress();
        }
    }
}
