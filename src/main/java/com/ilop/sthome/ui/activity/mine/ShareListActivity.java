package com.ilop.sthome.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.db.SceneAliDAO;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.ui.activity.main.QRCodeActivity;
import com.ilop.sthome.ui.activity.main.StartActivity;
import com.ilop.sthome.ui.adapter.device.DeviceShareAdapter;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityShareListBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 许格 on 2019/10/17.
 */

public class ShareListActivity extends BaseActivity<ActivityShareListBinding> {
    private static final String TAG = "ShareListActivity";
    private final int DELETE_GATEWAY_SUCCESS = 0;
    private final int BIND_CODE_GATEWAY_SUCCESS = 1;
    private final int GET_MESSAGE = 2;
    private final int REFRESH = 3;

    private DeviceShareAdapter mAdapter;
    private List<DeviceInfoBean> mDeviceInfoList;
    private DeviceAliDAO mDeviceAliDAO;
    private SysmodelAliDAO mSysModelAliDAO;
    private SceneAliDAO mSceneAliDAO;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_list;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
    }

    @Override
    protected void initView() {
        super.initView();
        mDeviceAliDAO = new DeviceAliDAO(this);
        mSceneAliDAO = new SceneAliDAO(this);
        mSysModelAliDAO = new SysmodelAliDAO(this);
        View empty = findViewById(R.id.empty);
        TextView textView_em  = empty.findViewById(R.id.tv_empty);
        textView_em.setText(getResources().getString(R.string.ali_device_empty));
        mDBind.eqList.setEmptyView(empty);
    }

    @Override
    protected void initData() {
        super.initData();
        mDeviceInfoList = mDeviceAliDAO.findAllWifiShareDevice();
        mAdapter = new DeviceShareAdapter(this, mDeviceInfoList);
        mDBind.eqList.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivShareBack.setOnClickListener(view -> finish());
        mDBind.ivShareAdd.setOnClickListener(view -> startActivityForResult(new Intent(ShareListActivity.this, QRCodeActivity.class),1));
        mDBind.eqList.setOnItemLongClickListener((adapterView, view, i, l) -> {
            DeviceInfoBean deviceInfoBean = mDeviceInfoList.get(i);
            alertToDelete(deviceInfoBean.getIotId(),deviceInfoBean.getDeviceName());
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDeviceInfoList = mDeviceAliDAO.findAllWifiShareDevice();
        mAdapter.addAll(mDeviceInfoList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void alertToDelete(String iotId,String deviceName){
        TipDialog dialog = new TipDialog(mContext, () -> unbind(iotId,deviceName));
        dialog.setMsg(getString(R.string.delete_or_not));
        dialog.show();
    }


    private void unbind(final String iotId,final String deviceName){
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
                            Toast.makeText(ShareListActivity.this, localizeMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Object data = ioTResponse.getData();
                if (data == null) {
                    return;
                }

                Message msg = Message.obtain();
                msg.what = DELETE_GATEWAY_SUCCESS;
                msg.obj = deviceName;
                handler.sendMessage(msg);
            }
        });
    }


    private void bindWithScode(final String key){
        showProgressDialog();
        Map<String, Object> maps = new HashMap<>();
        maps.put("qrKey",key);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/scanBindByShareQrCode")
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
                            Toast.makeText(ShareListActivity.this, localizeMsg, Toast.LENGTH_SHORT).show();
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
                    JSONArray jsonArray = jsonObject.getJSONArray("iotIdList");
                    String de = jsonArray.get(0).toString();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Message msg = Message.obtain();
                msg.what = BIND_CODE_GATEWAY_SUCCESS;
                handler.sendMessage(msg);

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DELETE_GATEWAY_SUCCESS:
                    String ds = (String)msg.obj;
                    DeviceAliDAO deviceAliDAO = new DeviceAliDAO(ShareListActivity.this);
                    deviceAliDAO.deleteByDeviceName(ds,0);
                    mDeviceInfoList = deviceAliDAO.findAllWifiShareDevice();
                    mAdapter.addAll(mDeviceInfoList);
                    break;
                case GET_MESSAGE:
                    dismissProgressDialog();
                    break;
                case BIND_CODE_GATEWAY_SUCCESS:
                    listByAccount();
                    break;
                case REFRESH:
                    deviceAliDAO = new DeviceAliDAO(ShareListActivity.this);
                    mDeviceInfoList = deviceAliDAO.findAllWifiShareDevice();
                    mAdapter.addAll(mDeviceInfoList);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;

        if(requestCode==1){
            String sn = data.getExtras().getString("SN");
            bindWithScode(sn);
        }
    }


    private void listByAccount() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("thingType","DEVICE");
        maps.put("pageNo",1);
        maps.put("pageSize",50);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/listBindingByAccount")
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
                handler.sendEmptyMessage(REFRESH);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                final int code = response.getCode();
                final String localizeMsg = response.getLocalizedMsg();
                if (code != 200) {
                    if(code==401){
                        LoginBusiness.logout(new ILogoutCallback() {
                            @Override
                            public void onLogoutSuccess() {
                                skipAnotherActivity(StartActivity.class);
                                finish();
                            }

                            @Override
                            public void onLogoutFailed(int i, String s) {
                                skipAnotherActivity(StartActivity.class);
                                finish();
                            }
                        });
                    }else {
                        handler.post(() -> showToast(localizeMsg));
                    }
                    return;
                }

                Object data = response.getData();
                if (data == null) {
                    return;
                }
                if (!(data instanceof JSONObject)) {
                    return;
                }
                try {
                    JSONObject jsonObject = (JSONObject) data;
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    List<DeviceInfoBean> deviceInfoBeanList = JSON.parseArray(jsonArray.toString(), DeviceInfoBean.class);

                    List<DeviceInfoBean> deviceInfoBeans = mDeviceAliDAO.findAllWifiDevice();

                    for(DeviceInfoBean deviceInfoBean : deviceInfoBeans){

                        boolean flag = false;
                        for(int i=0;i<deviceInfoBeanList.size();i++){
                            if(deviceInfoBean.getDeviceName().equals(deviceInfoBeanList.get(i).getDeviceName())){
                                flag = true;
                                break;
                            }
                        }

                        if(!flag){
                            mDeviceAliDAO.deleteByDeviceName(deviceInfoBean.getDeviceName(),0);
                        }

                    }


                    for (DeviceInfoBean deviceInfoBean:deviceInfoBeanList){
                        mDeviceAliDAO.insertGateway(deviceInfoBean);
                        SysModelAliBean sysModelAliBean = new SysModelAliBean();
                        sysModelAliBean.setDeviceName(deviceInfoBean.getDeviceName());
                        sysModelAliBean.setSid(0);
                        sysModelAliBean.setColor("F0");
                        sysModelAliBean.setModleName("");
                        sysModelAliBean.setChoice(1);
                        mSysModelAliDAO.addinit(sysModelAliBean);

                        SysModelAliBean sysModelAliBean1 = new SysModelAliBean();
                        sysModelAliBean1.setDeviceName(deviceInfoBean.getDeviceName());
                        sysModelAliBean1.setSid(1);
                        sysModelAliBean1.setModleName("");
                        sysModelAliBean1.setColor("F1");
                        mSysModelAliDAO.addinit(sysModelAliBean1);

                        SysModelAliBean sysModelAliBean2 = new SysModelAliBean();
                        sysModelAliBean2.setDeviceName(deviceInfoBean.getDeviceName());
                        sysModelAliBean2.setSid(2);
                        sysModelAliBean2.setColor("F2");
                        sysModelAliBean2.setModleName("");
                        mSysModelAliDAO.addinit(sysModelAliBean2);

                        SceneAliBean sceneBean = new SceneAliBean();
                        sceneBean.setDeviceName(deviceInfoBean.getDeviceName());
                        sceneBean.setMid(129);
                        sceneBean.setCode("");
                        sceneBean.setName("");
                        mSceneAliDAO.addinit(sceneBean);

                        SceneAliBean sceneBean2 = new SceneAliBean();
                        sceneBean2.setDeviceName(deviceInfoBean.getDeviceName());
                        sceneBean2.setMid(130);
                        sceneBean2.setCode("");
                        sceneBean2.setName("");
                        mSceneAliDAO.addinit(sceneBean2);

                        SceneAliBean sceneBean3 = new SceneAliBean();
                        sceneBean3.setDeviceName(deviceInfoBean.getDeviceName());
                        sceneBean3.setMid(131);
                        sceneBean3.setCode("");
                        sceneBean3.setName("");
                        mSceneAliDAO.addinit(sceneBean3);
                    }
                    handler.sendEmptyMessage(REFRESH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
