package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.mvp.contract.SceneChangeContract;
import com.ilop.sthome.network.api.SendSceneGroupDataAli;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;

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
    private SendSceneGroupDataAli mSendSceneDataAli;

    public SceneChangePresenter(Context context, String deviceName) {
        this.mContext = context;
        this.mDeviceName = deviceName;
        DeviceInfoBean mDeviceInfo = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceName);
        mSendSceneDataAli = new SendSceneGroupDataAli(mContext, mDeviceInfo);
    }

    @Override
    public void getSceneList() {
        List<SceneBean> mList = SceneDaoUtil.getInstance().findAllScene(mDeviceName);
        if (mList.size()>0){
            mView.showSceneList(mList);
        }else {
            mView.withOutScene();
        }
    }

    @Override
    public void changeScene(SceneBean scene) {
        if(scene.getChoice()!=1 && mDeviceName.equals(scene.getDeviceName())){
            mSendSceneDataAli.sceneGroupChose(scene.getSid());
            mView.showProgress();
        }
    }
}
