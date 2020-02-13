package com.ilop.sthome.mvp.model;

import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.ilop.sthome.mvp.model.common.CommonModelImpl;
import com.ilop.sthome.mvp.model.common.onModelCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author skygge.
 * @Date on 2020-02-08.
 * @Dec:
 */
public class CommonModel implements CommonModelImpl {

    @Override
    public void onGetGatewayList(onModelCallBack callBack) {
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
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onRenameGateway(String iotId, String nickName, onModelCallBack callBack) {
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
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onUnbindGateway(String iotId, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("iotId", iotId);
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
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onQueryRoomList(int pageNo, int pageSize, onModelCallBack callBack) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",1);
        maps.put("pageSize",10);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/virtual/user/list")
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
    public void onCreateRoom(String roomId, String roomName, String gateway, String device, String camera, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","employeeID");
        maps.put("attrValue", roomId);
        mapList.add(maps);
        Map<String, Object> maps2 = new HashMap<>();
        maps2.put("attrKey","extNum");
        maps2.put("attrValue", roomName);
        mapList.add(maps2);
        Map<String, Object> maps3 = new HashMap<>();
        maps3.put("attrKey","homeAddress");
        maps3.put("attrValue", camera);
        mapList.add(maps3);
        Map<String, Object> maps4 = new HashMap<>();
        maps4.put("attrKey","companyAddress");
        maps4.put("attrValue", gateway);
        mapList.add(maps4);
        Map<String, Object> maps5 = new HashMap<>();
        maps5.put("attrKey","companyName");
        maps5.put("attrValue", device);
        mapList.add(maps5);
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
    public void onUpdateRoomName(String userId, String roomName, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","extNum");
        maps.put("attrValue",roomName);
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
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onUpdateRoomByGateway(String userId, String gatewayList, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","companyAddress");
        maps.put("attrValue",gatewayList);
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
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onUpdateRoomByCamera(String userId, String cameraList, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","homeAddress");
        maps.put("attrValue",cameraList);
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
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onUpdateRoomBySubDevice(String userId, String subDeviceList, onModelCallBack callBack) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        maps.put("attrKey","companyName");
        maps.put("attrValue",subDeviceList);
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
            public void onResponse(IoTRequest ioTRequest, IoTResponse response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void onDeleteRoom(String userId, onModelCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("virtualUserId", userId);
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setPath("/uc/virtual/user/delete")
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
}
