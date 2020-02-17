package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.content.Intent;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneRelationBean;
import com.ilop.sthome.mvp.contract.AutomationContract;
import com.ilop.sthome.network.api.DataFromAliSceneGroup;
import com.ilop.sthome.network.api.SendSceneDataAli;
import com.ilop.sthome.ui.activity.scene.ChooseActionActivity;
import com.ilop.sthome.ui.activity.scene.InputDetailActivity;
import com.ilop.sthome.ui.activity.scene.SettingTimingActivity;
import com.ilop.sthome.utils.CommandUtil;
import com.ilop.sthome.utils.greenDao.AutomationDaoUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneRelationDaoUtil;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-01-25.
 * @Dec:
 */
public class AutomationPresenter extends BasePresenterImpl<AutomationContract.IView> implements AutomationContract.IPresent {

    private static final String TAG = "AutomationPresenter";

    private Context mContext;
    private String mDeviceName;
    private String mAutoName = "";
    private String mTrigger = "00";
    private boolean mModify;
    private int mDeleteSceneId;

    private AutomationBean mAutomation;
    private SendSceneDataAli mSendSceneData;

    private DeviceInfoBean mDeviceInfoBean;
    private DeviceInfoBean updateInputDevice;
    private DeviceInfoBean updateOutputDevice;

    private List<DeviceInfoBean> inputShow;
    private List<DeviceInfoBean> outputShow;

    public AutomationPresenter(Context mContext, String deviceName) {
        this.mContext = mContext;
        this.mDeviceName = deviceName;
        mDeviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceName);
        mSendSceneData = new SendSceneDataAli(mContext, mDeviceInfoBean);
    }

    @Override
    public void isModifyForShow(boolean modify, AutomationBean mAutomation) {
        mModify = modify;
        if (!modify){
            this.mAutomation = new AutomationBean();
            inputShow = new ArrayList<>();
            outputShow = new ArrayList<>();
        }else {
            this.mAutomation = mAutomation;
            setInputList(mAutomation.getInput(mContext, mDeviceName));
            setOutputList(mAutomation.getOutput(mContext, mDeviceName));
        }
    }

    @Override
    public void setInputList(List<DeviceInfoBean> deviceList) {
        inputShow = deviceList;
        mView.showInputList(deviceList);
    }

    @Override
    public void setOutputList(List<DeviceInfoBean> deviceList) {
        outputShow = deviceList;
        mView.showOutputList(deviceList);
    }

    @Override
    public boolean isTimerOrClick() {
        for (DeviceInfoBean mDevice: inputShow) {
            if ("TIMER".equals(mDevice.getDevice_type()) || "CLICK".equals(mDevice.getDevice_type())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewInput() {
        Intent addItem = new Intent(mContext, ChooseActionActivity.class);
        addItem.putExtra("isInput", true);
        addItem.putExtra("deviceName", mDeviceName);
        addItem.putExtra("deviceNum", inputShow.size());
        mView.startActivityByIntent(addItem);
    }

    @Override
    public void addNewOutput() {
        Intent addItem = new Intent(mContext, ChooseActionActivity.class);
        addItem.putExtra("isInput", false);
        addItem.putExtra("deviceName", mDeviceName);
        mView.startActivityByIntent(addItem);
    }

    @Override
    public void updateInput(DeviceInfoBean device) {
        updateInputDevice = device;
        if ("CLICK".equals(device.getDevice_type())){
            Intent intent = new Intent(mContext, InputDetailActivity.class);
            intent.putExtra("deviceType", "CLICK");
            mView.startActivityByIntent(intent);
        }else if ("TIMER".equals(device.getDevice_type())){
            Intent intent = new Intent(mContext, SettingTimingActivity.class);
            intent.putExtra("isUpdate", true);
            intent.putExtra("device", device);
            mView.startActivityByIntent(intent);
        }else {
            Intent intent = new Intent(mContext, ChooseActionActivity.class);
            intent.putExtra("isInput", true);
            intent.putExtra("update", true);
            intent.putExtra("deviceName", mDeviceName);
            mView.startActivityByIntent(intent);
        }
    }

    @Override
    public void updateOutput(DeviceInfoBean device) {
        updateOutputDevice = device;
        if ("PHONE".equals(device.getDevice_type())){
            Intent intent = new Intent(mContext, InputDetailActivity.class);
            intent.putExtra("deviceType", "PHONE");
            mView.startActivityByIntent(intent);
        }else {
            Intent intent = new Intent(mContext, ChooseActionActivity.class);
            intent.putExtra("isInput", false);
            intent.putExtra("update", true);
            intent.putExtra("deviceName", mDeviceName);
            mView.startActivityByIntent(intent);
        }
    }

    @Override
    public void deleteInput() {
        inputShow.remove(updateInputDevice);
        setInputList(inputShow);
    }

    @Override
    public void deleteOutput() {
        outputShow.remove(updateOutputDevice);
        setOutputList(outputShow);
    }

    @Override
    public void onSaveAutomation() {
        if (checkContent()){
            int number = inputShow.size();
            mAutoName = "";
            for (int i = 0; i < number; i++) {
                String name = mContext.getString(SmartProduct.getType(inputShow.get(i).getDevice_type()).getTypeStrId());
                mAutoName = mAutoName + name + "-";
            }
            mAutoName = mAutoName.substring(0, mAutoName.length()-1);
            if(!mModify){
                mAutomation.setName(mAutoName);
                mAutomation.setDeviceName(mDeviceName);
                int amid = CommandUtil.getMid(mContext, mDeviceName);
                mAutomation.setMid(amid);
            }
            String msg = CommandUtil.addModle( mAutoName, mTrigger, mAutomation, inputShow, outputShow);
            mSendSceneData.increaceScene(msg);
        }
    }

    @Override
    public void onDeleteAutomation(int sceneId) {
        mDeleteSceneId = sceneId;
        mSendSceneData.deleteScene(sceneId);
    }

    @Override
    public void sendSuccess() {
        try{
            mAutomation.setName(mAutoName);
            mAutomation.setCode(CommandUtil.getMessageCode());

            if(!mModify){
                AutomationDaoUtil.getInstance().getAutomationDao().insert(mAutomation);
                SceneBean mScene =  SceneDaoUtil.getInstance().findSceneByChoice(mDeviceName);

                if(mScene!=null){
                    SceneRelationDaoUtil.getInstance().deleteAllRelation2(mAutomation.getMid(), mDeviceName);
                    SceneRelationBean sceneRelationBean = new SceneRelationBean();
                    sceneRelationBean.setDeviceName(mDeviceName);
                    sceneRelationBean.setMid(mAutomation.getMid());
                    sceneRelationBean.setSid(mScene.getSid());
                    SceneRelationDaoUtil.getInstance().insertSceneRelation(sceneRelationBean);
                }
            }else{
                try{
                    AutomationDaoUtil.getInstance().getAutomationDao().update(mAutomation);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mView.finishActivity();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSuccess() {
        if(mDeleteSceneId>-1){
            SceneRelationDaoUtil.getInstance().deleteAllRelation2(mDeleteSceneId, mDeviceName);
            AutomationDaoUtil.getInstance().deleteByMid(mDeleteSceneId, mDeviceName);
            mDeleteSceneId = -1;
            DataFromAliSceneGroup dataFromAliSceneGroup = new DataFromAliSceneGroup(mContext, mDeviceInfoBean);
            dataFromAliSceneGroup.doSendSynCode();
            mView.finishActivity();
        }
    }

    @Override
    public boolean checkContent() {
        if (inputShow.size() == 0){
            mView.showToastMsg(mContext.getString(R.string.intput_must_has_eq));
            return false;
        }else if (outputShow.size() == 0){
            mView.showToastMsg(mContext.getString(R.string.output_must_has_eq));
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean checkInput(List<DeviceInfoBean> device) {
        boolean isIdentical = true;
        for (int i = 0; i < inputShow.size(); i++) {
            if (inputShow.get(i).getDevice_type().equals(device.get(0).getDevice_type())&&
                    inputShow.get(i).getDevice_status().equals(device.get(0).getDevice_status())){
                isIdentical = false;
            }
        }
        return isIdentical;
    }

    @Override
    public boolean checkOutput(List<DeviceInfoBean> device) {
        boolean isIdentical = true;
        for (int i = 0; i < outputShow.size(); i++) {
            if ("PHONE".equals(device.get(0).getDevice_type())){
                if ("PHONE".equals(outputShow.get(i).getDevice_type())){
                    isIdentical = false;
                }
            }
        }
        return isIdentical;
    }

    @Override
    public void checkUpdateInput(List<DeviceInfoBean> device) {
        if (checkInput(device)){
            inputShow.remove(updateInputDevice);
            inputShow.add(device.get(0));
            setInputList(inputShow);
        }else {
            mView.showToastMsg(mContext.getString(R.string.input_exist));
        }
    }

    @Override
    public void checkUpdateOutput(List<DeviceInfoBean> device) {
        int position = 0;
        DeviceInfoBean deviceInfoBean = null;
        for (int i = 0; i < outputShow.size(); i++) {
            if (updateOutputDevice == outputShow.get(i)){
                position = i;
                break;
            }
        }
        if (position > 0){
            deviceInfoBean = outputShow.get(position-1);
        }

        for (int i = 0; i < device.size(); i++) {
            if (position == 0){
                outputShow.remove(updateOutputDevice);
            }else {
                if (deviceInfoBean!=null && "DELAY".equals(deviceInfoBean.getDevice_type())){
                    outputShow.remove(deviceInfoBean);
                    outputShow.remove(updateOutputDevice);
                }else {
                    outputShow.remove(updateOutputDevice);
                }
            }
        }
        outputShow.addAll(device);
        setOutputList(outputShow);
    }
}
