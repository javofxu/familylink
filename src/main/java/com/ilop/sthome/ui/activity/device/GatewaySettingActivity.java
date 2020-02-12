package com.ilop.sthome.ui.activity.device;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aliyun.iot.aep.component.router.Router;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToEdit;
import com.example.common.base.OnCallBackToRefresh;
import com.example.xmpic.support.utils.UIFactory;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.event.EventAnswerOK;
import com.ilop.sthome.network.api.SendCommandAli;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.ui.activity.config.AddDeviceGuideActivity;
import com.ilop.sthome.ui.activity.main.MainActivity;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.ilop.sthome.ui.dialog.BaseEditDialog;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.bean.OTADeviceSimpleInfo;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.utils.tools.EmojiFilter;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityGatewaySettingBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2019/11/08.
 */

public class GatewaySettingActivity extends BaseActivity<ActivityGatewaySettingBinding> implements View.OnClickListener{
    private String TAG = GatewaySettingActivity.class.getSimpleName();
    private final int  DELETE_GATEWAY_SUCCESS = 0;
    private final int  RENAME_GATEWAY_SUCCESS = 1;
    private final int  GET_SCODE_SUCCESS = 3;
    private final int  GET_MESSAGE = 2;
    private int eqid;
    private String deviceName;
    private DeviceInfoBean deviceInfoBean;
    private DeviceAliDAO deviceAliDAO;
    private String nickName;
    private SendEquipmentDataAli sendEquipmentDataAli;
    private Bitmap mQrCodeBmp = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gateway_setting;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        eqid = getIntent().getIntExtra("deviceId",0);
        deviceName = getIntent().getStringExtra("deviceName");
        EventBus.getDefault().register(this);
        deviceAliDAO = new DeviceAliDAO(this);
        DeviceInfoBean deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,0);
        sendEquipmentDataAli = new SendEquipmentDataAli(this,deviceInfoBean);
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.tvGatewaySetTitle.setText(eqid==0 ? getString(R.string.ali_gateway_setting) : getString(R.string.ali_subdevice_setting));
        mDBind.deviceUnbind.setText(eqid==0 ? getString(R.string.ali_gateway_unbind) : getString(R.string.delete_equipment));
        if(eqid==0){
            mDBind.replaceeqid.setVisibility(View.GONE);
            deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName, eqid);
            mDBind.imgDeviceQRCode.setVisibility(deviceInfoBean.getOwned()==1 ? View.VISIBLE : View.GONE);
        }else {
            mDBind.location.setVisibility(View.GONE);
            mDBind.ota.setVisibility(View.GONE);
            mDBind.share.setVisibility(View.GONE);
            mDBind.imgDeviceQRCode.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.replaceeqid.setOnClickListener(this);
        mDBind.name.setOnClickListener(this);
        mDBind.ota.setOnClickListener(this);
        mDBind.share.setOnClickListener(this);
        mDBind.share.setOnClickListener(this);
        mDBind.ins.setOnClickListener(this);
        mDBind.deviceUnbind.setOnClickListener(this);
        mDBind.ivGatewayBack.setOnClickListener(view -> finish());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.name:
                BaseEditDialog mDialog = new BaseEditDialog(mContext, new OnCallBackToEdit() {
                    @Override
                    public void onConfirm(String name) {
                        if(!TextUtils.isEmpty(name)){
                            if(eqid==0){
                                nickName = name;
                                renameGateway(deviceInfoBean.getIotId(),nickName);
                            }else {
                                try {
                                    if(name.getBytes("GBK").length<=15){
                                        if(!EmojiFilter.containsEmoji(name)) {
                                            nickName = name;
                                            String ds = CoderALiUtils.getAscii(name);
                                            String dsCRC = ByteUtil.CRCmaker(ds);
                                            sendEquipmentDataAli.modifyEquipmentName(eqid, ds + dsCRC);
                                        }else {
                                            showToast(getString(R.string.name_contain_emoji));
                                        }
                                    }else{
                                        showToast(getString(R.string.name_is_too_long));
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            showToast(getString(R.string.name_is_null));
                        }
                        hideSoftKeyboard();
                    }

                    @Override
                    public void onCancel() {
                        hideSoftKeyboard();
                    }
                });
                mDialog.setTitleAndButton(getString(R.string.update_name), getString(R.string.cancel), getString(R.string.ok));
                mDialog.show();
                break;
            case R.id.ota:
                if(eqid==0){
                    deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,eqid);
                    OTADeviceSimpleInfo info = new OTADeviceSimpleInfo();
                    info.iotId = deviceInfoBean.getIotId();
                    info.deviceName = deviceInfoBean.getDeviceName();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(OTAConstants.OTA_KEY_DEVICESIMPLE_INFO, info);
                    Router.getInstance().toUrl(this, OTAConstants.MINE_URL_OTA, bundle);
                }
                break;
            case R.id.share:
                break;
            case R.id.location:
                break;
            case R.id.ins:
                UnitTools unitTools =new UnitTools(this);

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");

                    if("zh".equals(unitTools.readLanguage())){
                        String url = "";
                        if(eqid == 0){
                            url = SmartProduct.EE_SIMULATE_GATEWAY.getIns_url();
                        }else {
                            deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,eqid);
                            url = SmartProduct.getType(deviceInfoBean.getDevice_type()).getIns_url();
                        }

                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                    }else {
                        String url = "";
                        if(eqid == 0){
                            url = SmartProduct.EE_SIMULATE_GATEWAY.getIns_url_en();
                        }else {
                            deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,eqid);
                            url = SmartProduct.getType(deviceInfoBean.getDevice_type()).getIns_url_en();
                        }
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                    }

                break;
            case R.id.device_unbind:
                if(eqid==0){
                    deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,eqid);
                    String name = TextUtils.isEmpty(deviceInfoBean.getNickName())? getResources().getString(DevType.getType(deviceInfoBean.getProductKey()).getTypeStrId()):deviceInfoBean.getNickName();
                    BaseDialog baseDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                        @Override
                        public void onConfirm() {
                            unbind(deviceInfoBean.getIotId());
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    baseDialog.setMsg(String.format(getResources().getString(R.string.ali_gateay_unbind_hint), name));
                    baseDialog.show();
                }else {
                    DeviceAliDAO deviceAliDAO = new DeviceAliDAO(this);
                    DeviceInfoBean deviceInfoBean1 = deviceAliDAO.findByDeviceid(deviceInfoBean.getDeviceName(),0);
                    String name1 = TextUtils.isEmpty(deviceInfoBean1.getNickName())? getResources().getString(DevType.getType(deviceInfoBean1.getProductKey()).getTypeStrId()):deviceInfoBean1.getNickName();
                    String name = String.format(getResources().getString(R.string.ali_delete_sub_device_from_gateway),name1);
                    BaseDialog baseDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
                        @Override
                        public void onConfirm() {
                            sendEquipmentDataAli.deleteEquipment(eqid);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    baseDialog.setMsg(name);
                    baseDialog.show();
                }

                break;
            case R.id.replaceeqid:
                if(eqid>0){
                    Intent intent1 = new Intent(GatewaySettingActivity.this, AddDeviceGuideActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("deviceId", eqid);
                    bundle.putString("deviceName",deviceName);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.openfrombottom, R.anim.stay);
                }
                break;
        }
    }

    private void refresh(){
        deviceInfoBean = deviceAliDAO.findByDeviceid(deviceName,eqid);
        if(deviceInfoBean==null){
            finish();
        }else {
            if(eqid==0){
                String name = TextUtils.isEmpty(deviceInfoBean.getNickName())? getResources().getString(DevType.getType(deviceInfoBean.getProductKey()).getTypeStrId()):deviceInfoBean.getNickName();
                mDBind.name.setDetailText(name);
                if(deviceInfoBean.getOwned()==1)
                getscode(deviceInfoBean.getIotId());
            }else {
                String name = TextUtils.isEmpty(deviceInfoBean.getSubdeviceName())? (getResources().getString(SmartProduct.getType(deviceInfoBean.getDevice_type()).getTypeStrId())+deviceInfoBean.getDevice_ID()):deviceInfoBean.getSubdeviceName();
                mDBind.name.setDetailText(name);
            }

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DELETE_GATEWAY_SUCCESS:
                    deviceAliDAO.deleteByDeviceName(deviceName,0);
                    Intent intent = new Intent(GatewaySettingActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case RENAME_GATEWAY_SUCCESS:
                    deviceAliDAO.updateGatewayName(deviceName,nickName);
                    refresh();
                    break;
                case GET_MESSAGE:
                    dismissProgressDialog();
                    break;
                case GET_SCODE_SUCCESS:
                    String sqr = (String)msg.obj;
                    // 生成二维码
                    Bitmap qrCodeBmp = UIFactory.createCode(
                            sqr, 600, 0xff202020);
                    if ( null != qrCodeBmp ) {
                        if ( null != mQrCodeBmp ) {
                            mQrCodeBmp.recycle();
                        }
                        mQrCodeBmp = qrCodeBmp;
                        mDBind.imgDeviceQRCode.setImageBitmap(qrCodeBmp);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }

    private void unbind(String iotId){
        showProgressDialog();
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId",iotId);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/unbindAccountAndDev")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.2")
                .setAuthType("iotAuth")
                .setParams(maps);

        IoTRequest request = builder.build();

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                ALog.d(TAG, "onFailure");
                handler.sendEmptyMessage(GET_MESSAGE);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                handler.sendEmptyMessage(GET_MESSAGE);
                final int code = ioTResponse.getCode();
                final String localizeMsg = ioTResponse.getLocalizedMsg();
                if (code != 200) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GatewaySettingActivity.this, localizeMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Object data = ioTResponse.getData();
                if (data == null) {
                    return;
                }

                handler.sendEmptyMessage(DELETE_GATEWAY_SUCCESS);

            }
        });
    }

    private void renameGateway(String iotId,String nickName){
        showProgressDialog();
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId",iotId);
        maps.put("nickName",nickName);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/setDeviceNickName")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.2")
                .setAuthType("iotAuth")
                .setParams(maps);

        IoTRequest request = builder.build();

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                ALog.d(TAG, "onFailure");
                handler.sendEmptyMessage(GET_MESSAGE);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                handler.sendEmptyMessage(GET_MESSAGE);
                final int code = ioTResponse.getCode();
                final String localizeMsg = ioTResponse.getLocalizedMsg();
                if (code != 200) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GatewaySettingActivity.this, localizeMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Object data = ioTResponse.getData();
                if (data == null) {
                    return;
                }

                handler.sendEmptyMessage(RENAME_GATEWAY_SUCCESS);

            }
        });
    }

    @Subscribe
    public  void onEventMainThread(EventAnswerOK event) {
        Log.i(TAG, "onEventMainThread: "+ event.getData_str1()+" -- " +event.getData_str2());
        if (event.getData_str1().length() == 9) {
            int cmd = Integer.parseInt(event.getData_str1().substring(0, 4), 16);
            if (cmd == SendCommandAli.MODIFY_EQUIPMENT_NAME) {
                if ("OK".equals(event.getData_str2())) {
                    DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                    deviceInfoBean.setSubdeviceName(nickName);
                    deviceInfoBean.setDeviceName(deviceName);
                    deviceInfoBean.setDevice_ID(eqid);
                    deviceAliDAO.updateName(deviceInfoBean);
                    refresh();
                    hideSoftKeyboard();
                }
            }else if(cmd== SendCommandAli.DELETE_EQUIPMENT){
                if("OK".equals(event.getData_str2())){
                        DeviceAliDAO deviceAliDAO = new DeviceAliDAO(GatewaySettingActivity.this);
                        deviceAliDAO.deleteByDeviceName(deviceName,eqid);
                        finish();

                }else {
                    Toast.makeText(GatewaySettingActivity.this,getResources().getString(R.string.failed),Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void getscode(String iotId){
        Map<String, Object> maps = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(iotId);
        maps.put("iotIdList",list);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/generateShareQrCode")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.2")
                .setAuthType("iotAuth")
                .setParams(maps);

        IoTRequest request = builder.build();

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                ALog.d(TAG, "onFailure");
                handler.sendEmptyMessage(GET_MESSAGE);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                handler.sendEmptyMessage(GET_MESSAGE);
                final int code = ioTResponse.getCode();
                final String localizeMsg = ioTResponse.getLocalizedMsg();
                if (code != 200) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GatewaySettingActivity.this, localizeMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Object data = ioTResponse.getData();
                if (data == null) {
                    return;
                }

                try {
                    JSONObject jsonObject = (JSONObject) data;
                    String de = jsonObject.getString("qrKey");
                    Message message = Message.obtain();
                    message.obj = de;
                    message.what = GET_SCODE_SUCCESS;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
