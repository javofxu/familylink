package com.ilop.sthome.ui.activity.device;

import android.text.TextUtils;
import android.util.Log;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.mvp.model.CommonModel;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceRenameBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author skygge
 * @date 2020-02-18.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备重命名
 */
public class DeviceRenameActivity extends BaseActivity<ActivityDeviceRenameBinding> {

    private int mDeviceId;
    private String mDeviceName;
    private CommonModelImpl mModel;
    private DeviceInfoBean mDevice;
    private SendEquipmentDataAli mSendEquipment;
    private String mNickName;
    private String mOldName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_rename;
    }

    @Override
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().register(this);
        mDeviceId = getIntent().getIntExtra("deviceId", 0);
        mDeviceName = getIntent().getStringExtra("deviceName");
        mOldName = getIntent().getStringExtra("nickName");
    }

    @Override
    protected void initView() {
        super.initView();
        mModel = new CommonModel();
        DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(mDeviceName);
        mSendEquipment = new SendEquipmentDataAli(mContext, deviceInfoBean);
        if (!TextUtils.isEmpty(mOldName)) mDBind.etDeviceName.setText(mOldName);
    }

    @Override
    protected void initData() {
        super.initData();
        mDevice = DeviceDaoUtil.getInstance().findByDeviceId(mDeviceName, mDeviceId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.tvSaveName.setOnClickListener(view -> {
            mNickName = mDBind.etDeviceName.getText().toString();
            hideSoftKeyboard();
            if (mDeviceId == 0){
                renameGateway(mDevice.getIotId(), mNickName);
            }else {
                String ds = CoderALiUtils.getAscii(mNickName);
                String dsCRC = ByteUtil.CRCmaker(ds);
                mSendEquipment.modifyEquipmentName(mDeviceId, ds + dsCRC);
            }
            showProgressDialog();
        });
        mDBind.ivRenameBack.setOnClickListener(view -> finish());
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        Log.i(TAG, "onEventMainThread: "+event.getData_str2());
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME) {
                if ("OK".equals(event.getData_str2())) {
                    mDevice.setSubdeviceName(mNickName);
                    DeviceDaoUtil.getInstance().getDeviceDao().update(mDevice);
                    dismissProgressDialog();
                    finish();
                }
            }
        }
    }

    private void renameGateway(String itoId, String nickName) {
        mModel.onRenameGateway(itoId, nickName, new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    DeviceDaoUtil.getInstance().updateGatewayName(mDeviceName, nickName);
                    dismissProgressDialog();
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
