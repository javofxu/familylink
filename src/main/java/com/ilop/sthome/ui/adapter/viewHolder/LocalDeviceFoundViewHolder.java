package com.ilop.sthome.ui.adapter.viewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.example.common.base.BaseLoadingDialog;
import com.ilop.sthome.app.MyApplication;
import com.ilop.sthome.data.device.FoundDevice;
import com.ilop.sthome.data.device.FoundDeviceListItem;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.ui.activity.config.BindAndUseActivity;
import com.siterwell.familywellplus.R;

/**
 * wifi ，网关设备
 */
public class LocalDeviceFoundViewHolder extends BaseViewHolder<FoundDevice> {

    private Context mContext;
    private ImageView iv_device_icon;
    private TextView tv_device_name;
    private Button btn_device_connect;
    private Intent intent;
    private BaseLoadingDialog dialog;

    public LocalDeviceFoundViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        iv_device_icon = itemView.findViewById(R.id.list_item_device_icon);
        tv_device_name = itemView.findViewById(R.id.list_item_device_name);
        btn_device_connect = itemView.findViewById(R.id.list_item_device_action);
    }


    @Override
    public void onBind(FoundDevice data, int position) {
        super.onBind(data, position);
        FoundDeviceListItem foundDeviceListItem = (FoundDeviceListItem) data;

        DevType devType = DevType.getType(foundDeviceListItem.getProductKey());

        iv_device_icon.setImageResource(devType.getDrawableResId());

        tv_device_name.setText(foundDeviceListItem.deviceName);

        if (foundDeviceListItem.discoveryType==DiscoveryType.CLOUD_ENROLLEE_DEVICE){
            btn_device_connect.setText(mContext.getResources().getString(R.string.btnConfirmTitle));
        }else{
            btn_device_connect.setText(mContext.getResources().getString(R.string.ali_bind));
        }

        final DeviceInfo deviceInfo =foundDeviceListItem.deviceInfo;

        btn_device_connect.setOnClickListener(view -> {
            dialog = new BaseLoadingDialog(mContext);
            dialog.show();
            Bundle bundle = new Bundle();
            bundle.putString("productKey", deviceInfo.productKey);
            bundle.putString("deviceName", deviceInfo.deviceName);
            intent  = new Intent(mContext, BindAndUseActivity.class);
            intent.putExtras(bundle);
            handler.sendEmptyMessageDelayed(1, 1500);
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
            mContext.startActivity(intent);
        }
    };

}
