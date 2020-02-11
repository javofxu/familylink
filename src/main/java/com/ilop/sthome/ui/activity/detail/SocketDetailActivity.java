package com.ilop.sthome.ui.activity.detail;

import android.text.TextUtils;

import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailSocketBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2019-12-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：插座详情
 */
public class SocketDetailActivity extends BaseDetailActivity<ActivityDetailSocketBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_socket;
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
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.operation.setOnClickListener(view -> {
            switchOn();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.socket) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
            mDBind.detailIcon.setImageResource(R.mipmap.gs350);
        }
    }

    private void switchOn(){

        if(mDevice.getDevice_status() == null || "".equals(mDevice.getDevice_status()) || mDevice.getDevice_status().length()!=8){
            mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),"0101FFFF");
        }else {
            String status2 = mDevice.getDevice_status().substring(6,8);
            String status_to = "00";
            if("00".equals(status2)){
                status_to = "01";
            }else if("01".equals(status2)){
                status_to = "00";
            }
            String aaa = "01" + status_to +"FF"+"FF";//目前固定使用01
            mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),aaa);
        }
    }

    private void doStatusShow(String aaa) {
        try {
            String signal1 = aaa.substring(0,2);
            String socketStatus = aaa.substring(6,8);
            mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(signal1));
            if ("01".equals(socketStatus)) {//holder.s.setText("闭合");
                String status = getResources().getStringArray(R.array.socket_actions)[0];
                showDetailMsg(R.drawable.device_status_alarm, status);
                mDBind.operation.setImageResource(R.mipmap.power_on);
            } else if ("00".equals(socketStatus)) { //holder.s.setText("断开");
                String status = getResources().getStringArray(R.array.socket_actions)[1];
                showDetailMsg(R.drawable.device_status_normal, status);
                mDBind.operation.setImageResource(R.mipmap.power_off);
            }else {
                showDetail();
                mDBind.operation.setImageResource(R.mipmap.power_off);
            }
        }catch (Exception e){
            showDetail();
            mDBind.operation.setImageResource(R.mipmap.power_off);
            mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic("04"));
        }

    }

    private void showDetail(){
        mDBind.detailLayout.setBackgroundResource(R.drawable.device_status_off_line);
        mDBind.showStatus.setText(getResources().getString(R.string.offline));
    }

    private void showDetailMsg(int background, String text){
        mDBind.detailLayout.setBackgroundResource(background);
        mDBind.showStatus.setText(text);
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
