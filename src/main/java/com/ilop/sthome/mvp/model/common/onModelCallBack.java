package com.ilop.sthome.mvp.model.common;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;

public interface onModelCallBack {

    void onResponse(IoTResponse response);

    void onFailure(Exception e);

}
