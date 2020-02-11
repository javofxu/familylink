package com.ilop.sthome.mvp.model;

import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.ilop.sthome.mvp.contract.SetUpContract;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public class SetUpModel implements SetUpContract.IModel {

    @Override
    public void getDeviceNoticeList(String iotId, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId", iotId);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/message/center/device/notice/list")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.5")
                .setAuthType("iotAuth")
                .setParams(maps);
        IoTRequest request = builder.build();
        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                callBack.onResponse(ioTResponse);
            }
        });
    }

    @Override
    public void setDeviceNoticeReminder(String iotId, boolean noticeEnabled, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId", iotId);
        maps.put("noticeEnabled", noticeEnabled);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/message/center/device/global/notice/set")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.5")
                .setAuthType("iotAuth")
                .setParams(maps);
        IoTRequest request = builder.build();
        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                callBack.onResponse(ioTResponse);
            }
        });
    }

    @Override
    public void deviceNoticeSet(String iotId, String eventId, boolean noticeEnabled, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId", iotId);
        maps.put("eventId", eventId);
        maps.put("noticeEnabled", noticeEnabled);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/message/center/device/notice/set")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.5")
                .setAuthType("iotAuth")
                .setParams(maps);
        IoTRequest request = builder.build();
        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                callBack.onResponse(ioTResponse);
            }
        });
    }
}
