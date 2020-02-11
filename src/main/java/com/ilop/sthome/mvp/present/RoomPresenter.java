package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.VirtualUserBean;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.mvp.contract.RoomContract;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;


import java.util.List;

/**
 * @author skygge
 * @Date on 2020-02-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class RoomPresenter extends BasePresenterImpl<RoomContract.IView> implements RoomContract.IPresent {

    private Context mContext;
    private CommonModelImpl mModel;
    private List<RoomBean> mRoomList;
    private RoomBean mRoom;
    private Handler mHandler;

    public RoomPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new CommonModel();
        mHandler = new Handler();
    }

    @Override
    public void getRoomInfo(int position) {
        mRoomList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        mRoom = mRoomList.get(position);
        mView.showRoomName(mRoom.getRoom_name());
    }

    @Override
    public void onSaveRoom(String name) {
        int roomId;
        if (mRoomList.size()>0){
            roomId = mRoomList.get(mRoomList.size()-1).getRid() + 1;
        }else {
            roomId = 1;
        }
        mModel.onCreateRoom(String.valueOf(roomId), name, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    Object data = response.getData();
                    String mUserId = null;
                    List<VirtualUserBean> virtualList = JSON.parseArray(data.toString(),VirtualUserBean.class);
                    if (virtualList.size()>0){
                        mUserId = virtualList.get(0).getUserId();
                    }
                    RoomBean mRoom = new RoomBean();
                    mRoom.setRid(roomId);
                    mRoom.setUserId(mUserId);
                    mRoom.setRoom_name(name);
                    RoomDaoUtil.getInstance().getRoomDao().insert(mRoom);
                    mHandler.post(()-> {
                        mView.showToastMsg(mContext.getString(R.string.success));
                        mView.doSuccess();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void onUpdateRoom(String name) {
        mModel.onUpdateRoomName(mRoom.getUserId(), name, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    mHandler.post(()-> {
                        mRoom.setRoom_name(name);
                        RoomDaoUtil.getInstance().getRoomDao().update(mRoom);
                        mView.showToastMsg(mContext.getString(R.string.success));
                        mView.doSuccess();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void onDeleteRoom() {
        String sd = String.format(mContext.getResources().getString(R.string.want_to_delete_confirm_eq), mRoom.getRoom_name());
        TipDialog dialog = new TipDialog(mContext, ()-> mModel.onDeleteRoom(mRoom.getUserId(), new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    RoomDaoUtil.getInstance().getRoomDao().delete(mRoom);
                    mHandler.post(()-> {
                        mView.showToastMsg(response.getMessage());
                        mView.doSuccess();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        }));
        dialog.setMsg(sd);
        dialog.show();
    }
}
