package com.ilop.sthome.mvp.contract;


import android.content.Intent;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.greenDao.AutomationBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-19.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景详情
 */
public interface SceneDetailContract {

    interface IView extends IBaseView {

        void showSceneName(String name);

        void showAutoList(List<AutomationBean> mSceneList, List<Integer> mBeforeList);

        void withoutAuto();

        void skipActivity(Intent intent);
    }

    interface IPresent extends IBasePresenter<IView> {

        void getSceneName();

        void getAutoList();

        void onSaveScene(List<AutomationBean> list);

        void onSaveSuccess(List<AutomationBean> list);

        void skipActivityToUpdate(AutomationBean sceneAliBean);
    }
}
