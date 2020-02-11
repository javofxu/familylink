package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.base.OnCallBackToRefresh;
import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.ResolveAliTimer;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.TimerAliDAO;
import com.ilop.sthome.mvp.contract.TimerContract;
import com.ilop.sthome.network.api.SendOtherDataAli;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.siterwell.familywellplus.R;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-22.
 * @Dec:
 */
public class TimerPresenter extends BasePresenterImpl<TimerContract.IView> implements TimerContract.IPresent {

    private Context mContext;
    private TimerAliDAO mTimerAliDAO;
    private SendOtherDataAli sendOtherDataAli;
    private List<TimerGatewayAliBean> mGatewayAliBeanList;

    private int switch_of_operation = -1;
    private int index_of_operation = -1;
    private int mTimerId;
    private String code;
    private String mDeviceName;


    public TimerPresenter(Context mContext, String deviceName) {
        this.mContext = mContext;
        this.mDeviceName = deviceName;
        mTimerAliDAO = new TimerAliDAO(mContext);
        DeviceAliDAO deviceAliDAO = new DeviceAliDAO(mContext);
        DeviceInfoBean deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,0);
        sendOtherDataAli = new SendOtherDataAli(mContext, deviceInfoBean);
    }

    @Override
    public void getTimerList() {
        mGatewayAliBeanList = mTimerAliDAO.findAllTimer(mDeviceName);
        if (mGatewayAliBeanList!=null && mGatewayAliBeanList.size()>0){
            mView.getTimerList(mGatewayAliBeanList);
        }else {
            mView.withoutTimer();
        }
    }

    @Override
    public void synchronous() {
        sendOtherDataAli.syncTimerModel(CoderALiUtils.getTimeCRC(mContext, mDeviceName));
    }

    @Override
    public void switchClick(int position) {
        mView.showProgress();
        index_of_operation = position;
        if (mGatewayAliBeanList.get(position).getEnable() == 1){
            switch_of_operation = 0;
            TimerGatewayAliBean timerGateway = mGatewayAliBeanList.get(position);
            timerGateway.setEnable(0);
            code = ResolveAliTimer.getCode(timerGateway);
            sendOtherDataAli.addTimerModel(code);
        }else{
            switch_of_operation = 1;
            TimerGatewayAliBean timerGateway = mGatewayAliBeanList.get(position);
            timerGateway.setEnable(1);
            code = ResolveAliTimer.getCode(timerGateway);
            sendOtherDataAli.addTimerModel(code);
        }
    }

    @Override
    public void refreshSwitch() {
        if (switch_of_operation != -1) {
            switch_of_operation = -1;
            mGatewayAliBeanList.get(index_of_operation).setEnable(switch_of_operation);
            mGatewayAliBeanList.get(index_of_operation).setCode(code);
            mTimerAliDAO.updateEnable(mGatewayAliBeanList.get(index_of_operation));
            synchronous();
            mView.showToastMsg(mContext.getString(R.string.operation_success));
        }
    }

    @Override
    public void deleteTimer(int position) {
        mTimerId = position;
        BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                mView.showProgress();
                sendOtherDataAli.deleteTimerModel(position);
            }

            @Override
            public void onCancel() {

            }
        });
        mDialog.setMsg(mContext.getString(R.string.delete_or_not));
        mDialog.show();
    }

    @Override
    public void refreshDelete() {
        if (index_of_operation != -1) {
            index_of_operation = -1;
            mTimerAliDAO.delete(mTimerId, mDeviceName);
            synchronous();
            mView.showToastMsg(mContext.getString(R.string.delete_success));
        }
    }
}
