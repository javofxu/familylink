package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;

/**
 * @author skygge
 * @date 2020-01-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface SceneEditContract {


    interface IView extends IBaseView{

        void showSceneName(String name);

        void showSceneColor(int position);

        void onSuccess();

        void showToastMsg(String msg);

        void showProgress();
    }

    interface IPresent extends IBasePresenter<IView>{

        void refreshName();

        void setSceneColor(int color);

        void onSaveScene();

        void onSaveSuccess();

        void deleteScene();

        void deleteSceneSuccess(int sceneId);
    }
}
