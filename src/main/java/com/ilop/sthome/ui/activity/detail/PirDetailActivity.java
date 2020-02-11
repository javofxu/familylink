package com.ilop.sthome.ui.activity.detail;

import android.text.TextUtils;
import android.view.View;

import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailAlarmBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2019-12-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：移动探测器详情页
 */
public class PirDetailActivity extends BaseDetailActivity<ActivityDetailAlarmBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_alarm;
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
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.pir) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
            mDBind.detailIcon.setImageResource(R.mipmap.gs300d);
        }
    }


    private void doStatusShow(String aaaa) {
        try {
            String signal1 = aaaa.substring(0,2);
            String quantity1 = aaaa.substring(2,4);
            String draw = aaaa.substring(4,6);

            int qqqq = Integer.parseInt(quantity1,16);
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(qqqq));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(qqqq));
            if(TextUtils.isEmpty(signal1)){
                mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(signal1));
            }
            switch (draw) {
                case "A0":
                    showDetail(R.drawable.device_status_alarm, R.string.has_been_moved);
                    break;
                case "55":
                    showDetail(R.drawable.device_status_alarm, R.string.has_people);
                    break;
                case "11":
                    showDetail(R.drawable.device_status_alarm, R.string.Illegal_demolition);
                    break;
                case "AA":
                    if (qqqq <= 15) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
