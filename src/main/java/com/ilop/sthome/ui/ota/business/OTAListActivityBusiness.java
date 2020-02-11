package com.ilop.sthome.ui.ota.business;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.business.listener.OTAListActivityBusinessListener;

import java.util.HashMap;
import java.util.Map;

public class OTAListActivityBusiness {
    private IoTAPIClient mIoTAPIClient;
    private OTAListActivityBusinessListener mListener;

    public OTAListActivityBusiness(Handler handler) {
        mListener = new OTAListActivityBusinessListener(handler);

        IoTAPIClientFactory factory = new IoTAPIClientFactory();
        mIoTAPIClient = factory.getClient();
    }

    private IoTRequestBuilder getBaseIoTRequestBuilder() {
        IoTRequestBuilder builder = new IoTRequestBuilder();
        builder.setAuthType(OTAConstants.APICLIENT_IOTAUTH)
            .setApiVersion(OTAConstants.APICLIENT_VERSION);
        return builder;
    }

    /**
     * 请求ota列表
     * @param houseId
     */
    public void requestOTAList(@Nullable String houseId) {
        if (null == mIoTAPIClient) {
            return;
        }


        Map<String, Object> requestMap = new HashMap<>();
        if (null != houseId) {
            requestMap.put("houseId", houseId);
        } else {
            requestMap.put("houseId", "");
        }

        IoTRequest ioTRequest = getBaseIoTRequestBuilder()
            .setPath(OTAConstants.APICLIENT_PATH_QUERYOTADEVICELIST)
            .setParams(requestMap)
            .build();
        mIoTAPIClient.send(ioTRequest, mListener);
    }
}
