package com.ilop.sthome.ui.ota.adapter.holder;

import android.view.View;

import com.ilop.sthome.ui.ota.base.BaseViewHolder;
import com.ilop.sthome.ui.ota.bean.OTADeviceSimpleInfo;
import com.ilop.sthome.ui.ota.view.MineOTAListItem;


public class DeviceViewHolder extends BaseViewHolder<OTADeviceSimpleInfo> {
    private MineOTAListItem mItem;

    public DeviceViewHolder(View itemView) {
        super(itemView);

        if (itemView instanceof MineOTAListItem) {
            mItem = (MineOTAListItem)itemView;
        }
    }

    @Override
    public void bindData(OTADeviceSimpleInfo data, boolean maybeLatest) {
        if (null == mItem) {
            return;
        }

        if (null == data) {
            return;
        }

        mItem.setTitle(data.deviceName);
        mItem.showDeviceImage(data.image);

        if (maybeLatest) {
            mItem.showUnderLine(false);
        }
    }
}
