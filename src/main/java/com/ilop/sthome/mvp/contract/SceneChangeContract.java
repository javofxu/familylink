package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.data.bean.SysModelAliBean;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-14.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface SceneChangeContract{

    interface IView extends IBaseView{

        void showSceneList(List<SysModelAliBean> scene);

        void withOutScene();

        void showProgress();
    }

    interface present extends IBasePresenter<IView>{

        void getSceneList();

        void changeScene(SysModelAliBean scene);
    }
}
