package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.SubDeviceHistoryBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.greenDao.HistoryBean;
import com.ilop.sthome.mvp.contract.SubDeviceHistoryContract;
import com.ilop.sthome.network.api.SendOtherDataAli;
import com.ilop.sthome.utils.greenDao.HistoryDaoUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skygge
 * @date 2020-01-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SubDeviceHistoryPresenter extends BasePresenterImpl<SubDeviceHistoryContract.IView> implements SubDeviceHistoryContract.present{

    private Context mContext;
    private String mDeviceName;
    private int mDeviceId;
    private SendOtherDataAli mSendData;
    private List<HistoryBean> mHistoryList;
    private List<HistoryBean> mSortHistoryList;
    private List<SubDeviceHistoryBean> mList;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    public SubDeviceHistoryPresenter(Context mContext) {
        this.mContext = mContext;
        mHistoryList = new ArrayList<>();
        mSortHistoryList = new ArrayList<>();
        mList = new ArrayList<>();
    }

    @Override
    public void getDeviceInfo(String deviceName, int deviceId) {
        this.mDeviceName = deviceName;
        this.mDeviceId = deviceId;
        DeviceAliDAO mDeviceDAO = new DeviceAliDAO(mContext);
        DeviceInfoBean deviceInfoBean = mDeviceDAO.findByDeviceid(mDeviceName,0);
        mSendData = new SendOtherDataAli(mContext, deviceInfoBean);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getHistoryList() {
        mHistoryList.clear();
        mSortHistoryList.clear();
        mHistoryList = HistoryDaoUtil.getInstance().getHistoryByNameAndId(mDeviceName, mDeviceId);
        if (mHistoryList.size()>0){
            for (int i = 0; i < mHistoryList.size(); i++) {
                HistoryBean historyBean = mHistoryList.get(i);
                Date date = new Date(historyBean.getTime() * 1000);
                historyBean.setActivityTime((sdf.format(date)));
                if (historyBean.getActivityTime() != null) {
                    mSortHistoryList.add(historyBean);
                }
            }
            mList.clear();
            Map<String, List<HistoryBean>> groupByTime = mSortHistoryList.stream().collect(Collectors.groupingBy(HistoryBean::getActivityTime));
            for (Map.Entry<String, List<HistoryBean>> entry : groupByTime.entrySet()) {
                SubDeviceHistoryBean device = new SubDeviceHistoryBean();
                device.setMonth(entry.getKey());
                device.setNumber(entry.getValue().size());
                device.setList(entry.getValue());
                mList.add(device);
            }
            Collections.reverse(mList);
            mView.showHistory(mList);
        }else {
            mView.withoutData();
        }
    }

    @Override
    public void syncSubAlarm(int page, int deviceId) {
        mSendData.syncSubAlarms(page, deviceId);
    }

    @Override
    public void deleteSubAlarm(int deviceId) {
        mSendData.deleteSubAlarms(255, deviceId);
    }

    @Override
    public void deleteHistory() {
        HistoryDaoUtil.getInstance().deleteHistoryByNameAndId(mDeviceName, mDeviceId);
        mView.withoutData();
    }
}
