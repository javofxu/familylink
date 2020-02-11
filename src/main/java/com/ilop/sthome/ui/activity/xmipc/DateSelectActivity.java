package com.ilop.sthome.ui.activity.xmipc;

import android.content.Intent;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.DevCmdOPSCalendarInMonth;
import com.example.xmpic.support.models.FunDevice;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDateBinding;

import java.util.Calendar;

/**
 * Created by 许格 on 2017/9/7.
 */

public class DateSelectActivity extends BaseActivity<ActivityDateBinding> implements View.OnClickListener,OnFunDeviceOptListener {

    private FunDevice mFunDevice = null;
    private Calendar calendar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_date;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        calendar = (Calendar)getIntent().getSerializableExtra("Date");
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if (devId==0) {
            funDevice = FunSupport.getInstance().mCurrDevice;
        }
        if (null == funDevice) {
            finish();
        } else {
            mFunDevice = funDevice;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.monthDateView.setTextView(mDBind.dateText);
        mDBind.monthDateView.setToday(calendar);
        // 1. 注册录像文件搜索结果监听 - 在搜索完成后以回调的方式返回

        // 注册设备操作监听
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        // 获取时间
        DevCmdOPSCalendarInMonth devCmdOPSCalendarInMonth = new DevCmdOPSCalendarInMonth();
        devCmdOPSCalendarInMonth.setYear(calendar.get(Calendar.YEAR));
        devCmdOPSCalendarInMonth.setMonth(calendar.get(Calendar.MONTH)+1);
        FunSupport.getInstance().requestDeviceCmdGeneral(
                mFunDevice, devCmdOPSCalendarInMonth);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivLeft.setOnClickListener(this);
        mDBind.ivRight.setOnClickListener(this);
        mDBind.ivSelectDateBack.setOnClickListener(view -> finish());
        mDBind.ivSelectDateSave.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("year", mDBind.monthDateView.getmSelYear());
            intent.putExtra("month", mDBind.monthDateView.getmSelMonth());
            intent.putExtra("day", mDBind.monthDateView.getmSelDay());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.iv_left:
                 mDBind.monthDateView.onLeftClick();
                 int Year = mDBind.monthDateView.getmSelYear();
                 int Month = mDBind.monthDateView.getmSelMonth();
                 DevCmdOPSCalendarInMonth devCmdOPSCalendarInMonth = new DevCmdOPSCalendarInMonth();
                 devCmdOPSCalendarInMonth.setYear(Year);
                 devCmdOPSCalendarInMonth.setMonth(Month+1);
                 FunSupport.getInstance().requestDeviceCmdGeneral(
                         mFunDevice, devCmdOPSCalendarInMonth);
                 break;
             case R.id.iv_right:
                 mDBind.monthDateView.onRightClick();
                 int Year2 = mDBind.monthDateView.getmSelYear();
                 int Month2 = mDBind.monthDateView.getmSelMonth();
                 DevCmdOPSCalendarInMonth devCmdOPSCalendarInMonth2 = new DevCmdOPSCalendarInMonth();
                 devCmdOPSCalendarInMonth2.setYear(Year2);
                 devCmdOPSCalendarInMonth2.setMonth(Month2+1);
                 FunSupport.getInstance().requestDeviceCmdGeneral(
                         mFunDevice, devCmdOPSCalendarInMonth2);
                 break;
         }
    }


    @Override
    protected void onDestroy() {
        // 注册设备操作监听
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
        super.onDestroy();
    }

    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {
        if(DevCmdOPSCalendarInMonth.CONFIG_NAME.equals(configName)){
            DevCmdOPSCalendarInMonth devCmdOPSCalendarInMonth = (DevCmdOPSCalendarInMonth) mFunDevice
                    .getConfig(DevCmdOPSCalendarInMonth.CONFIG_NAME);

            mDBind.monthDateView.setDaysHasThingList(devCmdOPSCalendarInMonth.getData());
        }
    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {

    }

    @Override
    public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {

    }

    @Override
    public void onDeviceChangeInfoSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceChangeInfoFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceOptionSuccess(FunDevice funDevice, String option) {

    }

    @Override
    public void onDeviceOptionFailed(FunDevice funDevice, String option, Integer errCode) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

    }

    @Override
    public void onDeviceFileListGetFailed(FunDevice funDevice) {

    }
}
