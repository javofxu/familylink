package com.ilop.sthome.utils.adddevice;

import com.ilop.sthome.data.device.FoundDeviceListItem;
import com.ilop.sthome.data.device.SupportDeviceListItem;

import java.util.List;

public interface OnDeviceAddListener {


    void showToast(String message);


    void onSupportDeviceSuccess(List<SupportDeviceListItem> mSupportDeviceListItems);


    void onFilterComplete(List<FoundDeviceListItem> foundDeviceListItems);


}
