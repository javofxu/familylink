package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.DeviceHistoryBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.WarnBean;
import com.ilop.sthome.mvp.contract.DeviceHistoryContract;
import com.ilop.sthome.network.api.SendOtherDataAli;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.WarnDaoUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skygge
 * @date 2020-01-14.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：历史记录
 */
public class DeviceHistoryPresenter extends BasePresenterImpl<DeviceHistoryContract.IView> implements DeviceHistoryContract.present {

    private Context mContext;
    private String mDeviceName;
    private List<WarnBean> mList;
    private List<WarnBean> mWarnList;
    private List<DeviceInfoBean> mGatewayList;
    private List<DeviceHistoryBean> mHistoryList;
    private SendOtherDataAli mSendDeviceHistory;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public DeviceHistoryPresenter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
        mWarnList = new ArrayList<>();
        mHistoryList = new ArrayList<>();
    }

    @Override
    public void getAllGateway() {
        mGatewayList = DeviceDaoUtil.getInstance().findAllGateway();
        if (mGatewayList.size() > 0) {
            sendSync(1);
        }else {
            mView.withoutData();
        }
    }

    @Override
    public void getGatewayByName(String deviceName) {
        this.mDeviceName = deviceName;
        DeviceInfoBean gateway = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
        mSendDeviceHistory = new SendOtherDataAli(mContext, gateway);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getHistoryList(boolean isGateway) {
        mWarnList.clear();
        mList.clear();
        if (isGateway){
            mWarnList = WarnDaoUtil.getInstance().findWarnByDeviceName(mDeviceName);
        }else {
            mWarnList = WarnDaoUtil.getInstance().getWarnDao().queryAll();
        }
        if (mWarnList.size()>0) {
            for (int i = 0; i < mWarnList.size(); i++) {
                WarnBean warnBean = mWarnList.get(i);
                Date date = new Date(warnBean.getTime() * 1000);
                warnBean.setActivityTime((sdf.format(date)));
                warnBean.setActivtiyTimeDetail(sdf1.format(date));
                if (warnBean.getActivityTime() != null) {
                    mList.add(warnBean);
                }
            }
            mHistoryList.clear();
            Map<String, List<WarnBean>> groupByTime = mList.stream().collect(Collectors.groupingBy(WarnBean::getActivityTime));
            for (Map.Entry<String, List<WarnBean>> entry : groupByTime.entrySet()) {
                DeviceHistoryBean device = new DeviceHistoryBean();
                device.setMonth(entry.getKey());
                device.setNumber(entry.getValue().size());
                device.setList(entry.getValue());
                mHistoryList.add(device);
            }
            Collections.reverse(mHistoryList);
            mView.showHistory(mHistoryList);
        }else {
            mView.withoutData();
        }
    }

    @Override
    public void deleteGatewayAlarm(int num) {
        mSendDeviceHistory.deleteGatewayAlarms(num);
    }

    @Override
    public void deleteHistory(String deviceName) {
        WarnDaoUtil.getInstance().deleteWarnByDeviceName(deviceName);
        mView.withoutData();
    }

    @Override
    public void sendSync(int num) {
        for (int i = 0; i < mGatewayList.size(); i++) {
            String deviceName = mGatewayList.get(i).getDeviceName();
            DeviceInfoBean gateway = DeviceDaoUtil.getInstance().findGatewayByDeviceName(deviceName);
            SendOtherDataAli mSendOtherDataAli = new SendOtherDataAli(mContext, gateway);
            mSendOtherDataAli.syncAlarms(num);
        }
    }

    @Override
    public void sendGateWaySync(int num) {
        mSendDeviceHistory.syncAlarms(num);
    }

}
