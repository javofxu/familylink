package com.ilop.sthome.ui.ota.business.listener;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.ilop.sthome.ui.ota.OTAConstants;
import com.ilop.sthome.ui.ota.base.OTABaseBusinessListener;
import com.ilop.sthome.ui.ota.bean.OTADeviceDetailInfo;


public class OTAActivityBusinessListener extends OTABaseBusinessListener {
    public OTAActivityBusinessListener(Handler handler) {
        super(handler);
    }

    /**
     * 请求成功
     * @param ioTRequest  请求参数
     * @param ioTResponse 返回参数
     */
    @Override
    protected void onResponseSuccess(IoTRequest ioTRequest, String ioTResponse) {
        if (null == mHandler) {
            return;
        }

        if (OTAConstants.APICLIENT_PATH_QUERYSTATUSINFO.equals(ioTRequest.getPath())) {
            try {
                OTADeviceDetailInfo detailInfo = JSON.parseObject(ioTResponse, OTADeviceDetailInfo.class);
                Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_INFO_SUCCESS, detailInfo)
                    .sendToTarget();
            } catch (Exception e) {
                ALog.e(TAG, "parse detailInfo error", e);
                onResponseFailure(ioTRequest, ioTResponse);
            }
        } else if (OTAConstants.APICLIENT_PATH_QUERYPRODUCTINFO.equals(ioTRequest.getPath())) {
            JSONObject jsonObject = JSONObject.parseObject(ioTResponse);
            String netType = jsonObject.getString("netType");
            Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_PRODUCT_INFO_SUCCESS, netType)
                .sendToTarget();
        }else if(OTAConstants.APICLIENT_PATH_QUERYDEVICEDETAIL.equals(ioTRequest.getPath())){
            JSONObject jsonObject = JSONObject.parseObject(ioTResponse);
            String currentFirmwareVersion = jsonObject.getString("firmwareVersion");
            Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_DETAIL_INFO_SUCCESS, currentFirmwareVersion)
                    .sendToTarget();
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

        if (OTAConstants.APICLIENT_PATH_QUERYPRODUCTINFO.equals(ioTRequest.getPath())) {
            Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_PRODUCT_INFO_FAILED).sendToTarget();
        } else if (OTAConstants.APICLIENT_PATH_QUERYSTATUSINFO.equals(ioTRequest.getPath())) {
            Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_INFO_FAILED).sendToTarget();
        } else if(OTAConstants.APICLIENT_PATH_QUERYDEVICEDETAIL.equals(ioTRequest.getPath())){
            Message.obtain(mHandler, OTAConstants.MINE_MESSAGE_RESPONSE_OTA_DEVICE_DETAIL_INFO_FAILED).sendToTarget();
        }

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
