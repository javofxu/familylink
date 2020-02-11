package com.ilop.sthome.ui.activity.detail;

import android.text.TextUtils;
import android.view.View;

import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.dialog.BaseListDialog;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.ilop.sthome.utils.tools.ConnectionPojo;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailAlarmBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skygge
 * @date 2019-12-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：水浸探测器详情页
 */
public class WaterDetailActivity extends BaseDetailActivity<ActivityDetailAlarmBinding> {

    private List<String> itemList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_alarm;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        try {
            int ds = Integer.parseInt(mDevice.getDevice_type().substring(mDevice.getDevice_type().length()-1),16);
            if(ds<7){
                mDBind.operation.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        itemList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        super.initData();
        doStatusShow(mDevice.getDevice_status());
        showBattery();
    }


    @Override
    protected void initListener() {
        super.initListener();
        mDBind.operation.setOnClickListener(view -> {
            if(ConnectionPojo.getInstance().testMode){
                openTestModeAlert();
            }else {
                mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),"BB000000");
            }
        });
    }


    private void doStatusShow(String aaa) {
     try {
         String signal1 = aaa.substring(0,2);
         String quantity1 = aaa.substring(2,4);
         String draw = aaa.substring(4,6);

         int qqq = Integer.parseInt(quantity1,16);
         mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(qqq));
         mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(qqq));
         if(TextUtils.isEmpty(signal1)){
             mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(signal1));
         }
         switch (draw) {
             case "11":
                 showDetail(R.drawable.device_status_alarm, R.string.Illegal_demolition);
                 break;
             case "55":
                 showDetail(R.drawable.device_status_alarm, R.string.alarm);
                 break;
             case "AA":
                 if (qqq <= 15) {
                     showDetail(R.drawable.device_status_warn, R.string.low_battery);
                 } else {
                     showDetail(R.drawable.device_status_normal, R.string.normal);
                 }
                 break;
             case "BB":
                 showDetail(R.drawable.device_status_alarm, R.string.test);
                 break;
             case "50":
                 showDetail(R.drawable.device_status_normal, R.string.silence);
                 break;
             default:
                 showDetail(R.drawable.device_status_off_line, R.string.offline);
                 break;
            }
         }catch (Exception e){
             showDetail(R.drawable.device_status_off_line, R.string.offline);
             mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(100));
             mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(100));
         }
    }

    private void showDetail(int background, int textResources){
        mDBind.detailLayout.setBackgroundResource(background);
        mDBind.showStatus.setText(getResources().getString(textResources));
    }

    private void showBattery(){
        try {
            if("1".equals(mDevice.getDevice_type().substring(0,1))){
                mDBind.quantityPosition.setVisibility(View.GONE);
                mDBind.quantityDesc.setVisibility(View.GONE);
            }else{
                mDBind.quantityDesc.setVisibility(View.VISIBLE);
                mDBind.quantityPosition.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Log.i(TAG,"data err");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.wt) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
            mDBind.detailIcon.setImageResource(R.mipmap.gs156);
        }
    }


    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventRefreshDevice event){
        String new_status = event.getDevice_status();
        int new_id = event.getDevice_id();
        String new_type = event.getDevice_type();
        String new_name = event.getDeviceName();

        if(new_name.equals(mDevice.getDeviceName()) &&
                new_id == mDevice.getDevice_ID() &&
                new_type.equals(mDevice.getDevice_type())){
            mDevice.setDevice_status(new_status);
            doStatusShow(new_status);
        }
    }

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventAnswerOK event) {
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.EQUIPMENT_CONTROL) {
                if ("OK".equals(event.getData_str2())) {
                    showToast(getString(R.string.success_test));
                }
            }
        }
    }

    private void openTestModeAlert(){
        BaseListDialog mDialog = new BaseListDialog(mContext, position -> {
            switch (position){
                case 0:
                    mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),"BB000000");
                    break;
                case 1:
                    openSelectSecond();
                    break;
            }
        });
        mDialog.setMsgAndButton(getResources().getStringArray(R.array.testMode), getString(R.string.cancel));
        mDialog.setTitle(getString(R.string.please_choose_alert_time));
        mDialog.show();
    }


    private void openSelectSecond(){

        for(int i=0;i<256;i++){
            itemList.add(i * 10 +getResources().getString(R.string.device_setup_record_second));
        }

        BaseListDialog mDialog = new BaseListDialog(mContext, position -> {
            String minute = Integer.toHexString(position);

            for(int i=0;i<2-minute.length();i++){
                minute = "0"+minute;
            }

            mSendEquipment.sendEquipmentCommand(0,"55BB"+minute.toUpperCase()+"FF");
        });
        mDialog.setMsgAndButton((String[]) itemList.toArray(), getString(R.string.cancel));
        mDialog.setTitle(getString(R.string.please_choose_alert_time));
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
