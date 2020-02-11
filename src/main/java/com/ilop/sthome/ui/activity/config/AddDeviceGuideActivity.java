package com.ilop.sthome.ui.activity.config;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.MediaPlayerUtil;
import com.example.common.utils.RxTimerUtil;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddDeviceGuideBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author skygge
 * @date 2019-10-28.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：添加子设备页面
 */

public class AddDeviceGuideActivity extends BaseActivity<ActivityAddDeviceGuideBinding> {

    private AnimationDrawable mAnimation;
    private ProductGroup config;
    private SendEquipmentDataAli sendEquipmentDataAli;
    private String deviceName;
    private DeviceInfoBean deviceInfoBean;
    private DeviceAliDAO deviceAliDAO;
    private int mDeviceId;
    private BaseDialog mDialog;
    private String newName;
    private int eqid2;
    private String mName;
    private MediaPlayerUtil mMediaPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device_guide;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        EventBus.getDefault().register(this);
        mDeviceId = getIntent().getIntExtra("deviceId",0);
        deviceName = getIntent().getStringExtra("deviceName");
    }

    @Override
    protected void initView() {
        super.initView();
        deviceAliDAO = new DeviceAliDAO(this);
        mMediaPlayer = new MediaPlayerUtil(mContext);
        deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,0);
        sendEquipmentDataAli = new SendEquipmentDataAli(this, deviceInfoBean);
    }

    @Override
    protected void initData() {
        super.initData();
        if(mDeviceId==0){
            config = (ProductGroup)getIntent().getSerializableExtra("guide");
            mDBind.ivConfigGuide.setBackgroundResource(SmartProduct.getType(config.getDevType()).getDrawGuideResId());
            mName = getString(config.getTypeStrId());

            if(config== ProductGroup.EE_DEV_LOCK){
                mDBind.guideText1.setText(getString(R.string.ali_add_guide_lock_1));
                mDBind.guideText2.setText(getString(R.string.ali_add_guide_lock_2));
            }else if(config== ProductGroup.EE_TWO_CHANNEL_SOCKET1){
                mDBind.guideText1.setText(getResources().getString(R.string.ali_add_switch_1));
                mDBind.guideText2.setText(getResources().getString(R.string.ali_add_switch_2));
            }else if(config== ProductGroup.EE_TEMP_OUTDOOR_SIREN3){
                mDBind.guideText1.setText(getResources().getString(R.string.ali_add_shiwai_1));
                mDBind.guideText2.setText(getResources().getString(R.string.ali_add_shiwai_2));
            }
        }else {
            DeviceInfoBean deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,mDeviceId);
            mDBind.ivConfigGuide.setBackgroundResource(SmartProduct.getType(deviceInfoBean.getDevice_type()).getDrawGuideResId());
            mName = getResources().getString(R.string.replace_equipment);
        }
        mAnimation = (AnimationDrawable) mDBind.ivConfigGuide.getBackground();
        mDBind.tvAddSubTitle.setText(mName);
        mDBind.ivConfigGuide.post(() -> mAnimation.start());
        if(mDeviceId == 0){
            sendEquipmentDataAli.increaceEquipment();
        }else {
            sendEquipmentDataAli.replaceEquipment(mDeviceId);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSubBack.setOnClickListener(view -> {
            sendEquipmentDataAli.cancelIncreaseEq();
            finish();
        });
    }

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventRefreshDevice event){
        if(mDeviceId == 0){
            if(mDialog == null || !mDialog.isShowing()){
                if(event.getDevice_id()>0){
                    showToast(getString(R.string.add_success));
                    mMediaPlayer.play("prompt");
                    eqid2 = event.getDevice_id();
                    setDeviceName(event.getType(),event.getDevice_id());
                }else {
                    showToast(getString(R.string.failed));
                }
            }
        }else{
            if(mDeviceId==event.getDevice_id()){
                showToast(getString(R.string.replace_success));
                RxTimerUtil.timer(1000, number -> finish());
            }
        }
    }

    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(EventAnswerOK event){
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME) {
                if ("OK".equals(event.getData_str2())) {
                    if(!TextUtils.isEmpty(newName)&&eqid2>0){
                        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                        deviceInfoBean.setSubdeviceName(newName);
                        deviceInfoBean.setDeviceName(deviceName);
                        deviceInfoBean.setDevice_ID(eqid2);
                        deviceAliDAO.updateName(deviceInfoBean);
                        RxTimerUtil.timer(1000, number -> finish());
                        hideSoftKeyboard();
                        newName = null;
                    }
                }
            }
        }
    }

    private void setDeviceName(int type, int deviceId){
        if (deviceId>0) {
            if (type == 0){
                addDeviceSuccess(deviceId);
            }else {
                mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                    @Override
                    public void onConfirm() {
                        addDeviceSuccess(deviceId);
                    }

                    @Override
                    public void onCancel() {
                        RxTimerUtil.timer(1000, number -> finish());
                    }
                });
                mDialog.setTitleAndButton(getString(R.string.already_in_gateway), getString(R.string.cancel), getString(R.string.ok));
                mDialog.show();
            }
        }
    }


    private void addDeviceSuccess(int deviceId){
        String mDeviceName = SpUtil.getString(mContext, "device");
        String mRoomName = SpUtil.getString(mContext, "room");
        List<RoomBean> mRoomList = RoomDaoUtil.getInstance().findRoomByName(mRoomName);
        if (mRoomList.size() == 0) {
            int mRoomId = RoomDaoUtil.getInstance().getRoomDao().queryAll().size();
            RoomBean roomBean = new RoomBean();
            roomBean.setRid(mRoomId);
            roomBean.setRoom_name(mRoomName);
            RoomDaoUtil.getInstance().getRoomDao().insert(roomBean);
        }

        String ds = CoderALiUtils.getAscii(mDeviceName);
        String dsCRC = ByteUtil.CRCmaker(ds);
        sendEquipmentDataAli.modifyEquipmentName(deviceId, ds + dsCRC);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendEquipmentDataAli.cancelIncreaseEq();
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.destroy();
        EventBus.getDefault().unregister(this);
    }
}
