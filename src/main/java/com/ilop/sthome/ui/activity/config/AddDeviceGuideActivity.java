package com.ilop.sthome.ui.activity.config;

import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;

import com.example.common.base.BasePActivity;
import com.example.common.utils.MediaPlayerUtil;
import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.event.EventRefreshDevice;
import com.ilop.sthome.mvp.contract.AddDeviceContract;
import com.ilop.sthome.mvp.present.AddDevicePresenter;
import com.ilop.sthome.network.api.SendCommandAli;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAddDeviceGuideBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2019-10-28.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：添加子设备(成功或失败)页面
 */

public class AddDeviceGuideActivity extends BasePActivity<AddDevicePresenter, ActivityAddDeviceGuideBinding> implements AddDeviceContract.IView {

    private AnimationDrawable mAnimation;
    private ProductGroup mConfig;
    private String deviceName;
    private int mDeviceId;
    private MediaPlayerUtil mMediaPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device_guide;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceId = getIntent().getIntExtra("deviceId",0);
        deviceName = getIntent().getStringExtra("deviceName");
        mConfig = (ProductGroup)getIntent().getSerializableExtra("guide");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new AddDevicePresenter(mContext, deviceName);
    }

    @Override
    protected void initView() {
        super.initView();
        mMediaPlayer = new MediaPlayerUtil(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        String mName;
        if(mDeviceId==0){
            mDBind.ivConfigGuide.setBackgroundResource(SmartProduct.getType(mConfig.getDevType()).getDrawGuideResId());
            mName = getString(mConfig.getTypeStrId());
            if(mConfig== ProductGroup.EE_DEV_LOCK){
                showGuideText(getString(R.string.ali_add_guide_lock_1), getString(R.string.ali_add_guide_lock_2));
            }else if(mConfig== ProductGroup.EE_TWO_CHANNEL_SOCKET1){
                showGuideText(getString(R.string.ali_add_switch_1), getString(R.string.ali_add_switch_2));
            }else if(mConfig== ProductGroup.EE_TEMP_OUTDOOR_SIREN3){
                showGuideText(getString(R.string.ali_add_shiwai_1), getString(R.string.ali_add_shiwai_2));
            }
            mPresent.onInsertDevice();
        }else {
            DeviceInfoBean deviceInfoBean = new DeviceAliDAO(mContext).findByDeviceid(deviceName,mDeviceId);
            mDBind.ivConfigGuide.setBackgroundResource(SmartProduct.getType(deviceInfoBean.getDevice_type()).getDrawGuideResId());
            mName = getResources().getString(R.string.replace_equipment);
            mPresent.onReplaceDevice(mDeviceId);
        }
        mDBind.tvAddSubTitle.setText(mName);
        mAnimation = (AnimationDrawable) mDBind.ivConfigGuide.getBackground();
        mDBind.ivConfigGuide.post(() -> mAnimation.start());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivSubBack.setOnClickListener(view -> {
            mPresent.onCancel();
            finish();
        });
    }

    @Subscribe
    public  void onEventMainThread(EventRefreshDevice event){
        if(mDeviceId == 0){
            mPresent.onAddSuccess(event);
        }else{
            if(mDeviceId==event.getDevice_id()){
                showToast(getString(R.string.replace_success));
                finish();
            }
        }
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event){
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME) {
                if ("OK".equals(event.getData_str2())) {
                    mPresent.onModifySuccess();
                }
            }
        }
    }

    @Override
    public void showGuideText(String text1, String text2) {
        mDBind.guideText1.setText(text1);
        mDBind.guideText2.setText(text2);
    }

    @Override
    public void showAddSuccess() {
        showToast(getString(R.string.add_success));
        mMediaPlayer.play("prompt");
    }

    @Override
    public void showAddFailed() {
        showToast(getString(R.string.failed));
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresent.onCancel();
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
