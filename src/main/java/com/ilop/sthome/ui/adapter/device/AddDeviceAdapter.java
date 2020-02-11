package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ilop.sthome.data.device.FoundDevice;
import com.ilop.sthome.data.device.FoundDeviceListItem;
import com.ilop.sthome.data.device.LocalDevciceTitle;
import com.ilop.sthome.data.device.LocalScanDeviceTilte;
import com.ilop.sthome.data.device.NoSupportDeviceTitle;
import com.ilop.sthome.data.device.SupportDeviceListItem;
import com.ilop.sthome.data.device.SupportDeviceTitle;
import com.ilop.sthome.ui.adapter.viewHolder.BaseViewHolder;
import com.ilop.sthome.ui.adapter.viewHolder.LocalDeviceFoundViewHolder;
import com.ilop.sthome.ui.adapter.viewHolder.SupportDeviceItemViewHolder;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceAdapter extends BaseRecycleViewAdapter<FoundDevice> {
    private static final String TAG = AddDeviceAdapter.class.getSimpleName();

    private static final int LOCAL_DEVICE = 0x10000002;
    private static final int SUPPORT_DEVICE_TITLE = 0x10000003;
    private static final int SUPPORT_DEVICE = 0x10000004;
    private static final int NOSUPPORT_DEVICE_TITLE = 0x10000005;
    private static final int SCAN_LOCAL_DEVICE_TITLE = 0x10000006;
    private static final int LOCAL_DEVICE_TITLE = 0x10000007;


    private LocalDevciceTitle localDevciceTitle;//本地设备title
    private LocalScanDeviceTilte localScanDeviceTilte;//扫描
    private SupportDeviceTitle supportDeviceTitle;//支持的设备title
    private NoSupportDeviceTitle noSupportDeviceTitle;//不支持设备title


    public AddDeviceAdapter(Context context) {
        super(context);
        localDevciceTitle = new LocalDevciceTitle();
        localScanDeviceTilte = new LocalScanDeviceTilte();
        supportDeviceTitle = new SupportDeviceTitle();
        noSupportDeviceTitle = new NoSupportDeviceTitle();
        mDatas.add(0, localScanDeviceTilte);
        mDatas.add(1, localDevciceTitle);
        mDatas.add(2, supportDeviceTitle);
    }


    public void resetLocalDevice() {
        int supportIndex = mDatas.indexOf(supportDeviceTitle);

        List<FoundDevice> foundDevices = new ArrayList<>(mDatas.subList(2, supportIndex));
        mDatas.removeAll(foundDevices);
        notifyItemRangeRemoved(2, foundDevices.size());
    }

    public void addLocalDevice(List<FoundDeviceListItem> foundDeviceListItems) {
        if (foundDeviceListItems == null) {
            return;
        }
        int supportIndex = mDatas.indexOf(supportDeviceTitle);
        mDatas.addAll(supportIndex, foundDeviceListItems);
        notifyItemRangeInserted(supportIndex, foundDeviceListItems.size());
    }


    public void setSupportDevces(List<SupportDeviceListItem> data) {
        if (data == null) {
            return;
        }
        int supportIndex = mDatas.indexOf(supportDeviceTitle);
        if (data.size() == 0) {
            mDatas.add(supportIndex + 1, noSupportDeviceTitle);
            notifyItemInserted(supportIndex + 1);
        } else {
            mDatas.addAll(data);
            notifyItemRangeInserted(supportIndex + 1, data.size());
        }
    }


    @Override
    public int getItemViewType(int position) {
        FoundDevice foundDevice = mDatas.get(position);
     if (foundDevice instanceof FoundDeviceListItem) {
            return LOCAL_DEVICE;
        } else if (foundDevice instanceof SupportDeviceTitle) {
            return SUPPORT_DEVICE_TITLE;
        } else if (foundDevice instanceof SupportDeviceListItem) {
            return SUPPORT_DEVICE;
        } else if (foundDevice instanceof NoSupportDeviceTitle) {
            return NOSUPPORT_DEVICE_TITLE;
        } else if (foundDevice instanceof LocalScanDeviceTilte) {
            return SCAN_LOCAL_DEVICE_TITLE;
        } else if (foundDevice instanceof LocalDevciceTitle) {
            return LOCAL_DEVICE_TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOCAL_DEVICE:
                return new LocalDeviceFoundViewHolder(inflater.inflate(R.layout.item_device_list, parent, false));
            case SUPPORT_DEVICE:
                return new SupportDeviceItemViewHolder(inflater.inflate(R.layout.item_device_list, parent, false));
            case SUPPORT_DEVICE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.deviceadd_support_device_title, parent, false));
            case NOSUPPORT_DEVICE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.deviceadd_no_support_device, parent, false));
            case SCAN_LOCAL_DEVICE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.deviceadd_local_scan_title, parent, false));
            case LOCAL_DEVICE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.deviceadd_local_device_title, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(mDatas.get(position), position);
    }

    class TitleViewHolder extends BaseViewHolder {
        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
