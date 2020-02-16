package com.ilop.sthome.ui.activity.detail;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.ShowDetailInfoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.litesuits.android.log.Log;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDetailTempControlBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author skygge
 * @date 2019-12-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：温控器详情页
 */
public class TempControlDetailActivity extends BaseDetailActivity<ActivityDetailTempControlBinding> implements OnClickListener{

    private int count_s;
    private boolean flag_set = false;
    private int setting_mode = -1;
    private Timer timer_set;
    private UpdateCommandTask timerTask;

    private TipDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_temp_control;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        timer_set = new Timer();
        timerTask = new UpdateCommandTask();
        timer_set.schedule(timerTask, 0,1000);
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
        mDBind.timerMode.setOnClickListener(this);
        mDBind.handleMode.setOnClickListener(this);
        mDBind.fangdongMode.setOnClickListener(this);
        mDBind.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDevice == null){
            finish();
        }else {
            if(TextUtils.isEmpty(mDevice.getSubdeviceName())){
                mDBind.detailName.setText(getString(R.string.temp_controler) + mDevice.getDevice_ID());
            }else{
                mDBind.detailName.setText(mDevice.getSubdeviceName());
            }
        }
    }

    private void doStatusShow(String aaa) {
        try {
            String signal1 = aaa.substring(0,2);
            String quantity1 = aaa.substring(2,4);
            String status1 = aaa.substring(4,6);
            String status2 = aaa.substring(6,8);
            mDBind.showStatus.setText(getResources().getString(R.string.normal));
            mDBind.root.setBackgroundResource(R.drawable.device_status_normal);
            int qqq = Integer.parseInt(quantity1,16);
            if( qqq <= 15 ){
                mDBind.root.setBackgroundResource(R.drawable.device_status_warn);
                mDBind.showStatus.setText(getResources().getString(R.string.low_battery));
            }
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(qqq));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(qqq));
            byte d = (byte)Integer.parseInt(signal1,16);
            mDBind.signalPosition.setImageResource(ShowDetailInfoUtil.choseSPic(ByteUtil.convertByte2HexString((byte)(d&0x07))));

            byte ds = (byte)Integer.parseInt(status1,16);
            byte ds2 = (byte)Integer.parseInt(status2,16);
            byte ds3 = (byte)Integer.parseInt(signal1,16);

            int shineng_window2 = (((byte)((0x80) & ds3))==0?0:1);
            int shineng_valve2 =  (((byte)((0x40) & ds3))==0?0:1);
            int  shishi_temp2= ((0x3F) & (ds2>>2));
            int mode2 = ((0x03) & (ds2));
            byte status_window2 = (byte)((0x80) & ds);
            byte status_valve2 = (byte)((0x40) & ds);
            int status_tongsuo = (((byte)((0x20) & ds3))==0?0:1);
            int xiaoshu = (((byte)((0x20) & ds))==0?0:1);
            int sta =  ((0x1F) & ds);
            float setting_temp = ((float) sta)+(xiaoshu== 0 ? 0f : 0.5f);

            if (status_tongsuo != 0) {
                mDBind.tongsuoStatus.setImageResource(R.mipmap.lock_enable);
                mDBind.tongsuoBtn.setChecked(true);
            } else {
                mDBind.tongsuoStatus.setImageResource(R.mipmap.lock_not_enable);
                mDBind.tongsuoBtn.setChecked(false);
            }

            switch (mode2){
                case 0:
                    setting_mode = 0;
                    showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_nor, R.mipmap.freeze_sel);
                    mDBind.settingTemp.setMaxValues(15f);
                    if(mDialog!=null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    break;
                case 1:
                    setting_mode = 1;
                    showFunctionView(R.mipmap.timing_sel, R.mipmap.manual_nor, R.mipmap.freeze_nor);
                    mDBind.settingTemp.setMaxValues(30f);
                    if(mDialog!=null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    break;
                case 2:
                    setting_mode = 2;
                    showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_sel, R.mipmap.freeze_nor);
                    mDBind.settingTemp.setMaxValues(30f);
                    if(mDialog!=null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    break;
                case 3:
                    mDBind.showStatus.setText(getResources().getString(R.string.install_mode));
                    setting_mode = 2;
                    showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_sel, R.mipmap.freeze_nor);
                    mDBind.settingTemp.setMaxValues(30f);
                    if((mDialog == null || !mDialog.isShowing()) && setting_temp <= 30.0f){
                        mDialog = new TipDialog(mContext, ()->{});
                        mDialog.setMsg(getString(R.string.gs361_install_construction));
                        mDialog.show();
                    }
                    break;
            }

            if(setting_temp>30.0f){
                mDBind.settingTemp.setCurrentValues(0f);
                mDBind.settingTemp.setSubCurrentValues(0f);
                mDBind.showStatus.setText(getResources().getString(R.string.offline));
                mDBind.root.setBackgroundResource(R.drawable.device_status_off_line);
            }else{
                if(mode2==0){
                    SpUtil.putString(mContext, "cold", String.valueOf(setting_temp));
                }else if(mode2 == 1){
                    SpUtil.putString(mContext, "auto", String.valueOf(setting_temp));
                }else if(mode2 == 2){
                    SpUtil.putString(mContext, "hand", String.valueOf(setting_temp));
                }

                mDBind.settingTemp.setCurrentValues(setting_temp);
                mDBind.settingTemp.setSubCurrentValues(shishi_temp2);

                if(shineng_window2!=0) {
                    mDBind.windowCheck.setChecked(true);
                    if (status_window2 == 0) {
                        mDBind.windowStatus.setImageResource(R.mipmap.window_enable);
                    } else {
                        mDBind.windowStatus.setImageResource(R.mipmap.window_enable);
                    }
                }else {
                    mDBind.windowStatus.setImageResource(R.mipmap.window_un_enable);
                    mDBind.windowCheck.setChecked(false);
                }
                if(shineng_valve2!=0) {
                    mDBind.valveCheck.setChecked(true);
                    if (status_valve2 == 0) {
                        mDBind.valveStatus.setImageResource(R.mipmap.valve_enable);
                    } else {
                        mDBind.valveStatus.setImageResource(R.mipmap.valve_enable);
                    }
                }else{
                    mDBind.valveStatus.setImageResource(R.mipmap.valve_not_enable);
                    mDBind.valveCheck.setChecked(false);
                }
            }
        }catch (Exception e){
            mDBind.showStatus.setText(getResources().getString(R.string.offline));
            mDBind.quantityPosition.setImageResource(ShowDetailInfoUtil.choseQPic(100));
            mDBind.quantityDesc.setText(ShowDetailInfoUtil.choseLNum(100));
            mDBind.settingTemp.setCurrentValues(0f);
            mDBind.settingTemp.setSubCurrentValues(0f);
            setting_mode = 2;
            showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_sel, R.mipmap.freeze_nor);
            if(mDialog!=null && mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }

    private void showFunctionView(int time, int hand, int freeze){
        mDBind.timerMode.setImageResource(time);
        mDBind.handleMode.setImageResource(hand);
        mDBind.fangdongMode.setImageResource(freeze);
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
                    showToast(getString(R.string.success_set));
                    dismissProgressDialog();
                }
            }
        }
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.timer_mode:
                if(setting_mode==1){

                }else {
                    setting_mode = 1;
                    mDBind.settingTemp.setMaxValues(30f);
                    String auto_temp = SpUtil.getString(mContext, "auto");
                    if(TextUtils.isEmpty(auto_temp)){
                        mDBind.settingTemp.setCurrentValues(21f);
                    }else {
                        mDBind.settingTemp.setCurrentValues(Float.parseFloat(auto_temp));
                    }
                    showFunctionView(R.mipmap.timing_sel, R.mipmap.manual_nor, R.mipmap.freeze_nor);
                }

                break;
            case R.id.handle_mode:
                setting_mode = 2;
                mDBind.settingTemp.setMaxValues(30f);
                String hand_temp = SpUtil.getString(mContext, "hand");
                if(TextUtils.isEmpty(hand_temp)){
                    mDBind.settingTemp.setCurrentValues(21f);
                }else {
                    mDBind.settingTemp.setCurrentValues(Float.parseFloat(hand_temp));
                }
                showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_sel, R.mipmap.freeze_nor);
                break;
            case R.id.fangdong_mode:
                setting_mode = 0;
                mDBind.settingTemp.setMaxValues(15f);
                String fang_temp = SpUtil.getString(mContext, "cold");
                if(TextUtils.isEmpty(fang_temp)){
                    mDBind.settingTemp.setCurrentValues(5f);
                }else {
                    mDBind.settingTemp.setCurrentValues(Float.parseFloat(fang_temp));
                }
                showFunctionView(R.mipmap.timing_nor, R.mipmap.manual_nor, R.mipmap.freeze_sel);
                break;
            case R.id.btnConfirm:
                showProgressDialog();
                float da = mDBind.settingTemp.getCurrentValues();
                int settemtp = (int)Math.floor(da);
                int settemtpxiaoshu = (String.valueOf(da).contains(".5")?1:0);
                int valve = (mDBind.valveCheck.isChecked()?1:0);
                int window = (mDBind.windowCheck.isChecked()?1:0);
                int tongsuo = (mDBind.tongsuoBtn.isChecked()?1:0);
                flag_set = true;
                mSendEquipment.sendEquipmentCommand(mDevice.getDevice_ID(),getSetFrom(settemtp,settemtpxiaoshu,setting_mode,valve,window,tongsuo));
                break;
        }

    }

    private String getSetFrom(int set_temp,int set_temp_xiaoshu,int mode,int valve,int window,int suo){

        int set_temp_shiji = set_temp;
        byte [] ds= {0x00,0x00};
        ds[0]  = (byte)((window==0?0x00:0x80)|ds[0]);
        ds[0]  = (byte)((valve==0?0x00:0x40)|ds[0]);
        ds[0]  = (byte)((set_temp_xiaoshu==0?0x00:0x20)|ds[0]);
        ds[0]  = (byte)((set_temp_shiji&0x1F)|ds[0]);

        ds[1]  = (byte)((suo==0?0x00:0x04)|ds[1]);
        ds[1]  = (byte)((mode&0x03)|ds[1]);
        String str1 = ByteUtil.convertByte2HexString(ds[0]);
        String str2 = ByteUtil.convertByte2HexString(ds[1]);
        return ((str1+str2+"0000").toUpperCase());
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    flag_set = false;
                    count_s = 0;
                    dismissProgressDialog();
                    showToast(getString(R.string.failed));
                    doStatusShow(mDevice.getDevice_status());
                    break;
            }
        }
    };


    class UpdateCommandTask extends TimerTask {
        @Override
        public void run() {
            if(flag_set) {
                android.util.Log.i(TAG,"设置命令超时计数:"+count_s);
                count_s ++;
            }
            if(count_s >= 25){
                count_s = 0;
                handler.sendEmptyMessage(2);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(timer_set!=null){
            timer_set.cancel();
            timer_set = null;
            timerTask = null;
        }
    }
}
