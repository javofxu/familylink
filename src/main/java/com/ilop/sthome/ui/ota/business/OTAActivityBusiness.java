package com.ilop.sthome.ui.ota.business;

import android.os.Handler;

import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.business.listener.OTAActivityBusinessListener;
import com.ilop.sthome.ui.ota.manager.OTAManager;

import java.util.HashMap;
import java.util.Map;


public class OTAActivityBusiness {
    private static final String TAG = "OTAActivityBusiness";

    private IoTAPIClient mIoTAPIClient;
    private OTAActivityBusinessListener mListener;
    private OTAManager mManager;

    public OTAActivityBusiness(Handler handler) {
        mListener = new OTAActivityBusinessListener(handler);

        IoTAPIClientFactory factory = new IoTAPIClientFactory();
        mIoTAPIClient = factory.getClient();
    }

    public void generateOTAManager(Handler handler, String iotId, String netType) {
        if (null != mManager) {
            return;
        }

        mManager = new OTAManager(handler, iotId, netType);
    }

    private IoTRequestBuilder getBaseIoTRequestBuilder() {
        IoTRequestBuilder builder = new IoTRequestBuilder();
        builder.setAuthType(OTAConstants.APICLIENT_IOTAUTH)
            .setApiVersion(OTAConstants.APICLIENT_VERSION);
        return builder;
    }

    /**
     * 请求ota详情
     */
    public void requestDeviceInfo() {
        if (null == mIoTAPIClient) {
            return;
        }

        if (mManager == null) {
            return;
        }

        mManager.queryOTAStatus();
    }

    /**
     * 请求升级状态
     */
    public void requestUpgradeStatus() {
        if (null != mManager) {
            mManager.queryOTAStatus();
        } else {
            ALog.e(TAG, "must call generateOTAManager() first");
        }
    }

    /**
     * 请求升级
     */
    public void requestUpgrade() {

        if (null != mManager) {
            mManager.startUpgrade();
        }
    }

    /**
     * 请求产品详情
     * @param iotId
     */
    public void requestProductInfo(String iotId) {

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("iotId",iotId);
        IoTRequest ioTRequest = getBaseIoTRequestBuilder()
            .setApiVersion("1.1.1")
            .setPath(OTAConstants.APICLIENT_PATH_QUERYPRODUCTINFO)
            .setParams(requestMap)
            .build();
        mIoTAPIClient.send(ioTRequest, mListener);
    }

    /**
     * 请求设备详情 (获取当前固件版本号)
     * @param iotId
     */
    public void requestDeviceDetailInfo(String iotId) {

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("iotId",iotId);
        IoTRequest ioTRequest = getBaseIoTRequestBuilder()
                .setApiVersion("1.0.2")
                .setPath(OTAConstants.APICLIENT_PATH_QUERYDEVICEDETAIL)
                .setParams(requestMap)
                .build();
        mIoTAPIClient.send(ioTRequest, mListener);
    }


    public void destroy() {
        if (null != mManager) {
            mManager.destroy();
            mManager = null;
        }
    }
}
