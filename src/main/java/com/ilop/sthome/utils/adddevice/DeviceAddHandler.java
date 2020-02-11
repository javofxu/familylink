package com.ilop.sthome.utils.adddevice;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ilop.sthome.data.device.FoundDeviceListItem;
import com.ilop.sthome.data.device.SupportDeviceListItem;

import java.util.ArrayList;
import java.util.List;

public class DeviceAddHandler extends Handler {


    private OnDeviceAddListener onDeviceAddListener;
    private DeviceAddBusiness deviceAddBusiness;


    public DeviceAddHandler(OnDeviceAddListener onDeviceAddListener) {
        super(Looper.getMainLooper());
        this.onDeviceAddListener = onDeviceAddListener;
        deviceAddBusiness = new DeviceAddBusiness(this);
    }


    public void getSupportDeviceListFromSever() {
        deviceAddBusiness.getSupportDeviceListFromSever();
    }

    public void filterDevice(List<FoundDeviceListItem> foundDeviceListItems, Context context) {
        deviceAddBusiness.filterDevice(foundDeviceListItems,context);
    }

    public void reset() {
        deviceAddBusiness.reset();
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            if (onDeviceAddListener == null) {
                return;
            }
            switch (msg.what) {
                case DeviceAddBusiness.MESSAGE_RESPONSE_SUPPORTDEVICE_FAILED:
                    Object message = msg.obj;
                    if (msg == null) {
                        onDeviceAddListener.onSupportDeviceSuccess(new ArrayList<>());
                    } else {
                        onDeviceAddListener.showToast(message.toString());
                    }
                    break;
                case DeviceAddBusiness.MESSAGE_RESPONSE_SUPPORTDEVICE_SUCCESS:
                    ArrayList<SupportDeviceListItem> mSupportDeviceListItems = (ArrayList<SupportDeviceListItem>) msg.obj;
                    onDeviceAddListener.onSupportDeviceSuccess(mSupportDeviceListItems);
                    break;
                case DeviceAddBusiness.MESSAGE_RESPONSE_FILTERDEVICE:
                    List<FoundDeviceListItem> foundDeviceListItems= (List<FoundDeviceListItem>) msg.obj;
                    onDeviceAddListener.onFilterComplete(foundDeviceListItems);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public void onDestory() {
        removeCallbacksAndMessages(null);
        onDeviceAddListener = null;
    }
}
