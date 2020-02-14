package com.ilop.sthome.mvp.model;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author skygge.
 * @Date on 2020-02-05.
 * @Dec:
 */
public class PersonalModel implements PersonalContract.IModel {

    @Override
    public void toGetUploadUrl(onModelCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("fileSize", 300888);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/living/user/avatar/upload/signature/get")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.0")
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
    public void toQueryUserInfo(String identifyId, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, Object> maps2 = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(identifyId);
        maps2.put("identityIds",list);
        maps.put("request",maps2);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/iotx/account/queryIdentityList")
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
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void toUpdateUserInfo(String identityId, String accountMetaV2, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        maps.put("identityId", identityId);
        maps.put("accountMetaV2", JSONObject.parseObject(accountMetaV2));
        map.put("request", maps);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/iotx/account/modifyAccount")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.5")
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
    public void toCancellationAccount(onModelCallBack callBack) {
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("account/unregister")
                .setScheme(Scheme.HTTPS)
                .setApiVersion("1.0.6")
                .setAuthType("iotAuth");
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
}
