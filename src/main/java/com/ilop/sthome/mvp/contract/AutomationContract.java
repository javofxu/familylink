package com.ilop.sthome.mvp.contract;

import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2020-01-25.
 * @Dec:
 */
public interface AutomationContract {

    interface IView extends IBaseView{

        void showInputList(List<DeviceInfoBean> deviceList);

        void showOutputList(List<DeviceInfoBean> deviceList);

        void startActivityByIntent(Intent intent);

        void showToastMsg(String msg);

        void finishActivity();
    }

    interface IPresent extends IBasePresenter<IView>{
        /**
         * 是修改还是添加？
         * @param modify
         * @param mSceneAli
         */
        void isModifyForShow(boolean modify, SceneAliBean mSceneAli);

        void setInputList(List<DeviceInfoBean> deviceList);

        void setOutputList(List<DeviceInfoBean> deviceList);

        boolean isTimerOrClick();

        void addNewInput();

        void addNewOutput();

        void updateInput(DeviceInfoBean device);

        void updateOutput(DeviceInfoBean device);

        void deleteInput();

        void deleteOutput();

        void onSaveAutomation();

        void onDeleteAutomation(int sceneId);

        void sendSuccess();

        void deleteSuccess();

        boolean checkContent();

        boolean checkInput(List<DeviceInfoBean> device);

        void checkUpdateInput(List<DeviceInfoBean> device);

        void checkUpdateOutput(List<DeviceInfoBean> device);
    }

}
