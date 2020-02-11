package com.ilop.sthome.ui.ota.business.listener;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.base.OTABaseBusinessListener;
import com.ilop.sthome.ui.ota.bean.OTADeviceSimpleInfo;

import java.util.List;

public class OTAListActivityBusinessListener extends OTABaseBusinessListener {

    public OTAListActivityBusinessListener(Handler handler) {
        super(handler);
    }

    /**
     * 服务端返回成功
     * @param ioTRequest  请求参数
     * @param ioTResponse 返回参数
     */
    @Override
    protected void onResponseSuccess(IoTRequest ioTRequest, String ioTResponse) {
        if (null == mHandler) {
            return;
        }

        if (OTAConstants.APICLIENT_PATH_QUERYOTADEVICELIST.equals(ioTRequest.getPath())) {
            try {
                List<OTADeviceSimpleInfo> infos = JSON.parseArray(ioTResponse, OTADeviceSimpleInfo.class);
                Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_LIST_SUCCESS, infos).sendToTarget();
            } catch (Exception e) {
                ALog.e(TAG, "parse OTADeviceSimpleInfo error", e);
                Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_LIST_FAILED).sendToTarget();
            }
        }
    }

    /**
     * 服务端返回异常
     * @param ioTRequest  请求参数
     * @param ioTResponse 返回参数
     */
    @Override
    protected void onResponseFailure(IoTRequest ioTRequest, String ioTResponse) {
        if (null == mHandler) {
            return;
        }

        Message.obtain(mHandler, OTAConstants.OTA_MESSAGE_RESQUEST_ERROR).sendToTarget();
    }

    /**
     * 本地请求错误
     * @param ioTRequest 请求参数
     * @param e          异常信息
     */
    @Override
    protected void onRequestFailure(IoTRequest ioTRequest, Exception e) {
        if (null == mHandler) {
            return;
        }

        Message.obtain(mHandler, OTAConstants.OTA_MESSAGE_RESQUEST_ERROR).sendToTarget();
    }
}
