package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;

/**
 * @author skygge
 * @Date on 2020-02-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public interface RoomContract {

    interface IView extends IBaseView{

        void showRoomName(String name);

        void showToastMsg(String msg);

        void doSuccess();
    }

    interface IPresent extends IBasePresenter<IView>{

        void getRoomInfo(int position);

        void onSaveRoom(String name);

        void onUpdateRoom(String name);

        void onDeleteRoom();
    }
}
