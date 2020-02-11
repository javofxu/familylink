package com.ilop.sthome.ui.activity.detail;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailAlarmBinding;
import com.siterwell.familywellplus.databinding.ActivityDetailHumitureBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2019-12-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：温湿传感器详情页
 */
public class THCheckDetailActivity extends BaseDetailActivity<ActivityDetailHumitureBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_humiture;
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
                mDBind.detailName.setText(getString(R.string.thcheck) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private void doStatusShow(String aaa) {
        try {
            showDetail(R.drawable.device_status_normal, R.string.normal);
            String signal1 = aaa.substring(0,2);
            String quantity1 = aaa.substring(2,4);
            int qqqq = Integer.parseInt(quantity1,16);
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(qqqq));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(qqqq));
            if(TextUtils.isEmpty(signal1)){
                mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(signal1));
            }
            if( qqqq <= 15 ){
                showDetail(R.drawable.device_status_warn, R.string.low_battery);
            }

            String realT;
            String realH;
            String temp = aaa.substring(4,6);
            String humidity = aaa.substring(6,8);
            String temp2 = Integer.toBinaryString(Integer.parseInt(temp,16));
            if (temp2.length()==8){
                realT = "-"+ (128 - Integer.parseInt(temp2.substring(1),2));
            }else{
                realT = "" + Integer.parseInt(temp2,2);
            }

            if(Integer.parseInt(realT)>100 || Integer.parseInt(realT) < -40 || Integer.parseInt(humidity,16)<0 || Integer.parseInt(humidity,16)>100){
                showDetail(R.drawable.device_status_off_line, R.string.offline);
            }else{
                realH = "" +Integer.parseInt(humidity,16);
                mDBind.operation.setText(realT);
                mDBind.silence.setText(realH);
            }
        }catch (Exception e){
            showDetail(R.drawable.device_status_off_line, R.string.offline);
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(100));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(100));
            mDBind.operation.setVisibility(View.GONE);
            mDBind.silence.setVisibility(View.GONE);
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
