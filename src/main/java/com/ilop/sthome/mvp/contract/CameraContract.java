package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

public interface CameraContract {

    interface IModel{

        void toAddCamera(String deviceId, String name, onModelCallBack callBack);

        void toDeleteCamera(String userId, onModelCallBack callBack);

        void toUpdateCamera(String userId, String newName, onModelCallBack callBack);
    }

    interface IView extends IBaseView{

    }

    interface IPresent extends IBasePresenter<IView>{

    }
}
