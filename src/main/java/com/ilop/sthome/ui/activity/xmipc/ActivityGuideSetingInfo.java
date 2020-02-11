package com.ilop.sthome.ui.activity.xmipc;

import android.graphics.Bitmap;
import android.os.Message;
import android.widget.Toast;

import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.JsonConfig;
import com.example.xmpic.support.config.StatusNetInfo;
import com.example.xmpic.support.config.SystemInfo;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.utils.UIFactory;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.lib.EDEV_JSON_ID;
import com.lib.EUIMSG;
import com.lib.FunSDK;
import com.lib.IFunSDKResult;
import com.lib.MsgContent;
import com.lib.sdk.bean.DefaultConfigBean;
import com.lib.sdk.bean.HandleConfigData;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPicSettingInfoBinding;

import org.json.simple.JSONObject;

/**
 * Created by 许格 on 2019/10/21.
 */

public class ActivityGuideSetingInfo extends BaseActivity<ActivityPicSettingInfoBinding> implements OnFunDeviceOptListener,IFunSDKResult {

    private final static String TAG = ActivityGuideSetingInfo.class.getName();
    private int id;
    private FunDevice mFunDevice;
    private Bitmap mQrCodeBmp = null;
    private DefaultConfigBean mdefault = null;
    private int mHandler;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_setting_info;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        id = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        mFunDevice = FunSupport.getInstance().findDeviceById(id);
        if ( null == mFunDevice ) {
            finish();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mdefault = new DefaultConfigBean();
        mHandler = FunSDK.RegUser(this);
    }

    @Override
    protected void initData() {
        super.initData();
        requestSystemInfo();
    }

    @Override
    protected void initListener() {
        super.initListener();
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        mDBind.ivCommonBack.setOnClickListener(view -> finish());
        mDBind.reset.setOnClickListener(view -> DeviceConfig());
    }


    /**
     * 请求获取系统信息:SystemInfo/Status.NatInfo
     */
    private void requestSystemInfo() {

        // 获取系统信息
        FunSupport.getInstance().requestDeviceConfig(
                mFunDevice, SystemInfo.CONFIG_NAME);

        // 获取NAT状态
        FunSupport.getInstance().requestDeviceConfig(
                mFunDevice, StatusNetInfo.CONFIG_NAME);
    }

    private void refreshSystemInfo() {
        if ( null != mFunDevice ) {
            SystemInfo systemInfo = (SystemInfo)mFunDevice.getConfig(SystemInfo.CONFIG_NAME);
            if ( null != systemInfo ) {

                // 序列号
                mDBind.sn.setText(systemInfo.getSerialNo());

                // 设备型号
                mDBind.type.setText(systemInfo.getHardware());

                // 设备连接方式
                mDBind.netType.setText(getResources().getString(getConnectTypeStringId(mFunDevice.getNetConnectType())));

                // 生成二维码
                Bitmap qrCodeBmp = UIFactory.createCode(
                        systemInfo.getSerialNo(), 600, 0xff202020);
                if ( null != qrCodeBmp ) {
                    if ( null != mQrCodeBmp ) {
                        mQrCodeBmp.recycle();
                    }
                    mQrCodeBmp = qrCodeBmp;
                    mDBind.imgDeviceQRCode.setImageBitmap(qrCodeBmp);
                }

            }

            StatusNetInfo netInfo = (StatusNetInfo)mFunDevice.getConfig(StatusNetInfo.CONFIG_NAME);
            if ( null != netInfo ) {
                if("Conneted".equals(netInfo.getNatStatus())){
                    mDBind.netStatus.setText(getResources().getString(R.string.connected));
                }else{
                    mDBind.netStatus.setText(getResources().getString(R.string.unconnected));
                }

            }
        }
    }

    // 0: p2p 1:转发 2IP直连
    private int getConnectTypeStringId(int netConnectType) {
        if ( netConnectType == 0 ) {
            return R.string.device_net_connect_type_p2p;
        } else if ( netConnectType == 1 ) {
            return R.string.device_net_connect_type_transmit;
        } else if ( netConnectType == 2 ) {
            return R.string.device_net_connect_type_ip;
        } else if ( netConnectType == 5) {
            return R.string.device_net_connect_type_rps;
        }

        return R.string.device_net_connect_type_unknown;
    }

    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {
        if ( null != mFunDevice
                && funDevice.getId() == mFunDevice.getId()
                && ( SystemInfo.CONFIG_NAME.equals(configName)
                || StatusNetInfo.CONFIG_NAME.equals(configName)) ) {
            refreshSystemInfo();
        }
    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {
        //Toast.makeText(this, FunError.getErrorStr(errCode),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {

    }

    @Override
    public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {
        Toast.makeText(this, FunError.getErrorStr(errCode),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeviceChangeInfoSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceChangeInfoFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceOptionSuccess(FunDevice funDevice, String option) {

    }

    @Override
    public void onDeviceOptionFailed(FunDevice funDevice, String option, Integer errCode) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

    }

    @Override
    public void onDeviceFileListGetFailed(FunDevice funDevice) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if ( null != mQrCodeBmp ) {
            mQrCodeBmp.recycle();
            mQrCodeBmp = null;
        }


        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
    }

    @Override
    public int OnFunSDKResult(Message msg, MsgContent msgContent) {
        dismissProgressDialog();
        switch (msg.what) {
            case EUIMSG.DEV_SET_JSON:
                if (msg.arg1 < 0) {
                    showToast(FunError.getErrorStr(msg.arg1));
                }else {
                    if (msgContent.str.equals(JsonConfig.OPERATION_DEFAULT_CONFIG)) {
                        JSONObject object = new JSONObject();
                        object.put("Action", "Reboot");
                        System.out.print("TTT--------->> " + object.toString());
                        FunSDK.DevCmdGeneral(mHandler, mFunDevice.devSn, EDEV_JSON_ID.OPMACHINE, JsonConfig.OPERATION_MACHINE, 1024, 5000,
                                HandleConfigData.getSendData(JsonConfig.OPERATION_MACHINE, "0x1", object).getBytes(), -1, 0);
                        showToast(getString(R.string.device_system_info_defaultconfigsucc));
                    }
                }
            break;
        }
        return 0;
    }

    private void DeviceConfig() {
        TipDialog mDialog = new TipDialog(mContext, () -> {
            showProgressDialog();
            mdefault.setAllConfig(1);
            FunSDK.DevSetConfigByJson(mHandler, mFunDevice.devSn, JsonConfig.OPERATION_DEFAULT_CONFIG, HandleConfigData.getSendData(JsonConfig.OPERATION_DEFAULT_CONFIG, "0x1", mdefault), -1, 20000, mFunDevice.getId());
        });
        mDialog.setMsg(getString(R.string.reset_to_default_set));
        mDialog.show();
    }

}
