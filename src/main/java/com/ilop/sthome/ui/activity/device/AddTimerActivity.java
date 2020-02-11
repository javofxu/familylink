package com.ilop.sthome.ui.activity.device;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.base.BasePActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.ResolveAliTimer;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.data.db.TimerAliDAO;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.mvp.contract.AddTimerContract;
import com.ilop.sthome.mvp.present.AddTimerPresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendOtherDataAli;
import com.ilop.sthome.ui.adapter.device.WeekAdapter;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddTimerBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-21.
 * @Dec:
 */
public class AddTimerActivity extends BasePActivity<AddTimerPresenter, ActivityAddTimerBinding> implements AddTimerContract.IView {

    private int mTimerId;
    private String deviceName;
    private String init_code = "";
    private TimerGatewayAliBean timerGatewayBean;
    private TimerAliDAO mTimerDAO;
    private SysmodelAliDAO mSysModelDAO;
    private SendOtherDataAli sendOtherData;
    private WeekAdapter mAdapter;

    private byte mWeekInt=0x00;
    private int confirmNum = 0;
    private String messagecode = "" ;
    private String repeatInfo ="";
    private List<SysModelAliBean> mSysList;
    private ArrayList<String> items_hour;
    private ArrayList<String> items_min;
    private ArrayList<String> mSysNameList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_timer;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(false);
        deviceName = getIntent().getStringExtra("deviceName");
        mTimerId = getIntent().getIntExtra("timerid",-1);
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new AddTimerPresenter(mContext, deviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mPresent.getWeightList();
        mTimerDAO = new TimerAliDAO(this);
        mSysModelDAO = new SysmodelAliDAO(this);
        DeviceAliDAO deviceAliDAO = new DeviceAliDAO(this);
        DeviceInfoBean deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName, 0);
        sendOtherData = new SendOtherDataAli(this, deviceInfoBean);
        mDBind.hour.setCyclic(true);
        mDBind.min.setCyclic(true);
        mDBind.mode.setCyclic(true);
        mDBind.hour.setAdapter(new NumberAdapter(items_hour, 30));
        mDBind.min.setAdapter(new NumberAdapter(items_min, 30));
        mDBind.mode.setAdapter(new NumberAdapter(mSysNameList, 30));

        if(mTimerId==-1){
            timerGatewayBean = new TimerGatewayAliBean();
        }else{
            timerGatewayBean = mTimerDAO.findByTid(mTimerId, deviceName);
            init_code = ResolveAliTimer.getCode(timerGatewayBean);
        }

        if(TextUtils.isEmpty(timerGatewayBean.getWeek())){
            mWeekInt = 0x00;
            mAdapter = new WeekAdapter(this, mWeekInt);
        }else{
            mWeekInt = ByteUtil.hexStr2Bytes(timerGatewayBean.getWeek())[0];
            mAdapter = new WeekAdapter(this, mWeekInt);
        }
        mDBind.weeklist.setLayoutManager(new GridLayoutManager(mContext, 5));
        mDBind.weeklist.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if(TextUtils.isEmpty(timerGatewayBean.getHour())){
            mDBind.hour.setCurrentItem(0);
        }else{
            mDBind.hour.setCurrentItem(Integer.valueOf(timerGatewayBean.getHour()));
        }

        if(TextUtils.isEmpty(timerGatewayBean.getMin())){
            mDBind.min.setCurrentItem(0);
        }else{
            mDBind.min.setCurrentItem(Integer.valueOf(timerGatewayBean.getMin()));
        }

        if(timerGatewayBean.getModeid()==-1){
            mDBind.mode.setCurrentItem(0);
        }else{
            mSysList = mSysModelDAO.findAllSys(deviceName);
            for(int i=0;i<mSysList.size();i++){
                if(mSysList.get(i).getSid()==timerGatewayBean.getModeid()){
                    mDBind.mode.setCurrentItem(i);
                    break;
                }
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivAddDeviceBack.setOnClickListener(v ->{
            if(mTimerId!=-1) {
                String ds = mPresent.getTimerStringFromContent(mAdapter.getIsSelected());
                if ("00".equals(ds)) {
                    finish();
                } else {
                    onSaveOrBack();
                }
            }else {
                if(init_code.toUpperCase().equals(getCode().toUpperCase())){
                    finish();
                }else {
                    onSaveOrBack();
                }
            }
        });
        mDBind.ivAddTimer.setOnClickListener(v -> onSaveOrBack());
    }
    private void onSaveOrBack(){
        BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                showProgressDialog();
                confirmNum ++;
                verfication();
                if (confirmNum==1){
                    confirmToSys();
                }else if(confirmNum==-1){
                    dismissProgressDialog();
                    showToast(getString(R.string.set_weekday));
                    confirmNum = 0;
                }else if(confirmNum == -2){
                    dismissProgressDialog();
                    showToast(repeatInfo);
                    confirmNum = 0;
                }else{
                    sendOtherData.addTimerModel(messagecode);
                }
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
        mDialog.setTitleAndButton(getString(R.string.save_or_not), getString(R.string.no_save), getString(R.string.save));
        mDialog.show();
    }
    @Subscribe
    public  void onEventMainThread(EventAnswerOK event){
        if(event.getData_str1().length()==9){
            int cmd = Integer.parseInt( event.getData_str1().substring(0,4),16);
            if(cmd== SendCommandAli.MODEL_SWITCH_TIMER){
                if("OK".equals(event.getData_str2())){
                    confirmNum = 0;
                    mTimerDAO.insertTimer(timerGatewayBean);
                    finish();
                }else {
                    showToast(getString(R.string.failed));
                }
            }
        }

    }

    @Override
    public void getListForUi(ArrayList<String> hour, ArrayList<String> minute, ArrayList<String> scene) {
        this.items_hour = hour;
        this.items_min = minute;
        this.mSysNameList = scene;
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {

        public NumberAdapter(ArrayList<String> items,int length) {
            super(items, length);
        }

    }

    private void verfication(){
        String ds = mPresent.getTimerStringFromContent(mAdapter.getIsSelected());
        if("00".equals(ds)){
            confirmNum = -1;
            return;
        }

        String hour = mDBind.hour.getCurrentItem()<10?("0"+mDBind.hour.getCurrentItem()):String.valueOf(mDBind.hour.getCurrentItem());
        String min  = mDBind.min.getCurrentItem()<10?("0"+mDBind.min.getCurrentItem()):String.valueOf(mDBind.min.getCurrentItem());

        List<String> weekList = mTimerDAO.findAllTimerByTime(hour,min, deviceName);

        HashMap<Integer,Boolean> result = mPresent.CheckRepeat(weekList);
        Log.i(TAG,"result:"+result.toString());
        boolean flag_repeat = false;
        HashMap<Integer,Boolean> result2 = new HashMap<>();
        for(int i=0;i<7;i++){
            if(mAdapter.getIsSelected().get(i) && result.get(i) && mTimerId==-1){
                flag_repeat = true;
                result2.put(i,true);
            }else {
                result2.put(i,false);
            }
        }
        if(flag_repeat){
            repeatInfo = ResolveAliTimer.getWeekinfoHash(result2,this);
            confirmNum = -2;

            return;
        }
        if(mTimerId==-1){
            timerGatewayBean.setTimerid(mPresent.getTid(mTimerDAO.findAllTimerTid(deviceName)));
            timerGatewayBean.setEnable(1);
        }

        timerGatewayBean.setModeid(mSysList.get(mDBind.mode.getCurrentItem()).getSid());
        timerGatewayBean.setWeek(ds);
        timerGatewayBean.setHour(hour);
        timerGatewayBean.setMin(min);
        timerGatewayBean.setDeviceName(deviceName);
    }

    private void confirmToSys() {
        messagecode = ResolveAliTimer.getCode(timerGatewayBean);
        Log.i(TAG,"code++++++++++:"+messagecode);
        timerGatewayBean.setCode(messagecode);
        sendOtherData.addTimerModel(messagecode);
        confirmNum = 0;
    }

    private String getCode(){
        String ds = mPresent.getTimerStringFromContent(mAdapter.getIsSelected());

        String hour = mDBind.hour.getCurrentItem()<10?("0"+mDBind.hour.getCurrentItem()):String.valueOf(mDBind.hour.getCurrentItem());
        String min  = mDBind.min.getCurrentItem()<10?("0"+mDBind.min.getCurrentItem()):String.valueOf(mDBind.min.getCurrentItem());

        timerGatewayBean.setModeid(mSysList.get(mDBind.mode.getCurrentItem()).getSid());
        timerGatewayBean.setWeek(ds);
        timerGatewayBean.setHour(hour);
        timerGatewayBean.setMin(min);
        return ResolveAliTimer.getCode(timerGatewayBean);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
