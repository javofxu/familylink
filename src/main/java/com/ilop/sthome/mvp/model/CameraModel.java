package com.ilop.sthome.mvp.model;

import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.ilop.sthome.mvp.contract.CameraContract;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraModel implements CameraContract.IModel {

    @Override
    public void toAddCamera(String deviceId, String name, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","name");
        maps.put("attrValue",deviceId);
        mapList.add(maps);
        Map<String, Object> maps2 = new HashMap<>();
        maps2.put("attrKey","displayName");
        maps2.put("attrValue",name);
        mapList.add(maps2);
        Map<String, Object> map = new HashMap<>();
        map.put("attrList", mapList);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/virtual/user/create")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.4")
                .setAuthType("iotAuth")
                .setParams(map);

        IoTRequest request = builder.build();

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void toDeleteCamera(String userId, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("virtualUserId", userId);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/virtual/user/delete")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.4")
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
    public void toUpdateCamera(String userId, String newName, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","displayName");
        maps.put("attrValue", newName);
        mapList.add(maps);
        Map<String, Object> map = new HashMap<>();
        map.put("virtualUserId", userId);
        map.put("opType", 2);
        map.put("attrList", mapList);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/virtual/user/update")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.4")
                .setAuthType("iotAuth")
                .setParams(map);

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
