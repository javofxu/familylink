package com.ilop.sthome.ui.activity.detail;

import android.text.TextUtils;
import android.view.View;

import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailOutdoorSirenBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2019-12-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：户外警笛详情页
 */
public class OutDoorDetailActivity extends BaseDetailActivity<ActivityDetailOutdoorSirenBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_outdoor_siren;
    }


    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
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
        mDBind.operation.setOnClickListener(view -> mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),"51000000"));
        mDBind.volume.setOnClickListener(view -> mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),"52000000"));
    }

    private void showBattery(){
        try {
            if("1".equals(mDevice.getDevice_type().substring(0,1))){
                mDBind.quantityDesc.setVisibility(View.GONE);
                mDBind.quantityPosition.setVisibility(View.GONE);
            }else{
                mDBind.quantityDesc.setVisibility(View.VISIBLE);
                mDBind.quantityPosition.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Log.i(TAG,"data err");
        }
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
                case "A0":
                    showDetail(R.drawable.device_status_alarm, R.string.has_been_moved);
                    break;
                case "A1":
                    showDetail(R.drawable.device_status_warn, R.string.low_battery);
                    break;
                case "AA":
                    if (qqq <= 15) {
                        showDetail(R.drawable.device_status_warn, R.string.low_battery);
                    } else {
                        showDetail(R.drawable.device_status_normal, R.string.normal);
                    }
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

    @Override
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.outdoor_siren) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
            mDBind.detailIcon.setImageResource(R.mipmap.gs380);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
