package com.ilop.sthome.ui.activity.detail;

import android.text.TextUtils;
import android.view.View;

import com.example.common.base.OnCallBackToEdit;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailAlarmBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author skygge
 * @date 2019-12-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：门锁详情页
 */

public class LockDetailActivity extends BaseDetailActivity<ActivityDetailAlarmBinding> {
    private AtomicBoolean flag_active = new AtomicBoolean(false);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_alarm;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mDBind.operation.setVisibility(View.VISIBLE);
        mDBind.operation.setText(getResources().getString(R.string.unlock));
    }

    @Override
    protected void initData() {
        super.initData();
        doStatusShow(mDevice.getDevice_status());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.operation.setOnClickListener(view -> {
            BaseEditDialog mDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
                @Override
                public void onConfirm(String name) {
                    if(!TextUtils.isEmpty(name)){
                        if(flag_active.get()){
                            mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(), name);
                        }else{
                            showToast(getString(R.string.no_actived));
                        }
                    }else{
                        showToast(getString(R.string.please_input_complete));
                    }
                }

                @Override
                public void onCancel() {

                }
            });
            mDialog.setPwdText(true);
            mDialog.setTitle(getString(R.string.please_input_unlock_password));
            mDialog.show();
        });
    }

    private void doStatusShow(String aaa) {
        try {
            String signal1 = aaa.substring(0,2);
            String quantity1 = aaa.substring(2,4);
            String draw = aaa.substring(4,6);
            String num = aaa.substring(6,8);
            int qqq = Integer.parseInt(quantity1,16);
            int number = Integer.parseInt(num,16);
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(qqq));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(qqq));
            if(TextUtils.isEmpty(signal1)){
                mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(signal1));
            }
            switch (draw) {
                case "AA":
                    if (qqq <= 15) {
                        showDetail(R.drawable.device_status_warn, R.string.low_battery);
                    } else {
                        showDetail(R.drawable.device_status_normal, R.string.normal);
                    }
                    flag_active.set(false);
                    break;
                case "AB":
                    showDetail(R.drawable.device_status_normal, R.string.actived);
                    flag_active.set(true);
                    break;
                case "51":
                    String msg1 = "#" + number + "\n" + getString(R.string.card_open);
                    showDetailText(msg1);
                    flag_active.set(false);
                    break;
                case "52":
                    String msg2 = "#" + number + "\n" + getString(R.string.card_open);
                    showDetailText(msg2);
                    flag_active.set(false);
                    break;
                case "53":
                    String msg3 = "#" + number + "\n" + getResources().getString(R.string.footprint_open);
                    showDetailText(msg3);
                    flag_active.set(false);
                    break;
                case "10":
                    showDetail(R.drawable.device_status_alarm, R.string.illegal_operation);
                    flag_active.set(false);
                    break;
                case "20":
                    showDetail(R.drawable.device_status_alarm, R.string.dismantle);
                    flag_active.set(false);
                    break;
                case "30":
                    showDetail(R.drawable.device_status_alarm, R.string.coercion);
                    flag_active.set(false);
                    break;
                case "40":
                    showDetail(R.drawable.device_status_warn, R.string.low_battery);
                    flag_active.set(false);
                    break;
                case "55":
                    showDetail(R.drawable.device_status_alarm, R.string.remote_unlock_success);
                    flag_active.set(true);
                    break;
                case "56":
                    showDetail(R.drawable.device_status_alarm, R.string.code_fault);
                    flag_active.set(true);
                    break;
                case "60":
                    showDetail(R.drawable.device_status_normal, R.string.alarm_remove);
                    flag_active.set(false);
                    break;
                default:
                    showDetail(R.drawable.device_status_off_line, R.string.offline);
                    flag_active.set(false);
                    break;
            }
        }catch (Exception e){
            showDetail(R.drawable.device_status_off_line, R.string.offline);
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(100));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(100));
            mDBind.operation.setVisibility(View.VISIBLE);
            flag_active.set(false);
        }

    }

    private void showDetail(int background, int textResources){
        mDBind.detailLayout.setBackgroundResource(background);
        mDBind.showStatus.setText(getResources().getString(textResources));
    }

    private void showDetailText(String textMsg){
        mDBind.detailLayout.setBackgroundColor(R.drawable.device_status_alarm);
        mDBind.showStatus.setText(textMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.lock) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
            mDBind.detailIcon.setImageResource(R.mipmap.gs920);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
