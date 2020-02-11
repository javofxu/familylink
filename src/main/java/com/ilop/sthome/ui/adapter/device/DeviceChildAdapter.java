package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-11.
 * @Dec:
 */
public class DeviceChildAdapter extends RecyclerView.Adapter<DeviceChildAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;
    private subDeviceCallBack mCallBack;

    public DeviceChildAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> deviceInfoBeans) {
        this.mList = deviceInfoBeans;
        notifyDataSetChanged();
    }

    public void setCallBack(subDeviceCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_device, null);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        DeviceInfoBean device = mList.get(i);
        if(!TextUtils.isEmpty(device.getProductKey())) {
            holder.mIcon.setImageResource(DevType.getType(device.getProductKey()).getDrawableResId());
            holder.mType.setText(mContext.getString(R.string.ali_gateway));
            if (TextUtils.isEmpty(device.getNickName())) {
                holder.mName.setText(DevType.getType(device.getProductKey()).getTypeStrId());
            } else {
                holder.mName.setText(device.getNickName());
            }
            if (device.getStatus() == 1) {
                holder.mStatus.setText(mContext.getString(R.string.device_stauts_online));
                holder.mColor.setBackgroundResource(R.drawable.device_status_normal);
            } else {
                holder.mStatus.setText(mContext.getString(R.string.device_stauts_offline));
                holder.mColor.setBackgroundResource(R.drawable.device_status_off_line);
            }
        }else {
            holder.mIcon.setImageResource(SmartProduct.getType(device.getDevice_type()).getDrawableResId());
            holder.mType.setText(SmartProduct.getType(device.getDevice_type()).getTypeStrId());
            holder.mName.setText(TextUtils.isEmpty(device.getSubdeviceName()) ? (mContext.getString(SmartProduct.getType(device.getDevice_type()).getTypeStrId()) + device.getDevice_ID()) : device.getSubdeviceName());
            if (SmartProduct.getType(device.getDevice_type()) == SmartProduct.EE_DEV_THCHECK1
                    || SmartProduct.getType(device.getDevice_type()) == SmartProduct.EE_DEV_THCHECK2
                    || SmartProduct.getType(device.getDevice_type()) == SmartProduct.EE_DEV_THCHECK3) {
                String temp = device.getDevice_status().substring(4, 6);
                String humidity = device.getDevice_status().substring(6, 8);
                String temp2 = Integer.toBinaryString(Integer.parseInt(temp, 16));
                String realT;
                String realH;
                if (temp2.length() == 8) {
                    realT = "-" + (128 - Integer.parseInt(temp2.substring(1), 2));
                } else {
                    realT = Integer.parseInt(temp2, 2) + "";
                }

                if (Integer.parseInt(realT) > 100 || Integer.parseInt(realT) < -40 || Integer.parseInt(humidity, 16) < 0 || Integer.parseInt(humidity, 16) > 100) {
                    realT = HistoryDataUtil.getAlert(mContext, device.getDevice_type(), device.getDevice_status());
                    holder.mStatus.setText(realT);
                } else {
                    realH = Integer.parseInt(humidity, 16) + "";
                    holder.mStatus.setText(realT + "℃" +"/"+ realH + "%RH");
                }
                String status = HistoryDataUtil.getsnapshot(mContext, device.getDevice_type(), device.getDevice_status());
                if (mContext.getString(R.string.low_battery).equals(status)) {
                    holder.mColor.setBackgroundResource(R.drawable.device_status_warn);
                } else if (mContext.getString(R.string.off_line).equals(status)){
                    holder.mColor.setBackgroundResource(R.drawable.device_status_off_line);
                }else {
                    holder.mColor.setBackgroundResource(R.drawable.device_status_normal);
                }
            } else {
                String status = HistoryDataUtil.getsnapshot(mContext, device.getDevice_type(), device.getDevice_status());
                if (mContext.getString(R.string.cxalert7).equals(status)
                        || mContext.getString(R.string.sthalert).equals(status)
                        || mContext.getString(R.string.cxalert9).equals(status)
                        || mContext.getResources().getStringArray(R.array.sos_signs)[0].equals(status)) {
                    holder.mColor.setBackgroundResource(R.drawable.device_status_alarm);
                } else if (mContext.getString(R.string.low_battery).equals(status)
                        || mContext.getString(R.string.cxalert2).equals(status)
                        || mContext.getString(R.string.cxalert5).equals(status)) {
                    holder.mColor.setBackgroundResource(R.drawable.device_status_warn);
                } else if (mContext.getString(R.string.off_line).equals(status)){
                    holder.mColor.setBackgroundResource(R.drawable.device_status_off_line);
                } else {
                    holder.mColor.setBackgroundResource(R.drawable.device_status_normal);
                }
                holder.mStatus.setText(status);
            }
        }
        holder.itemView.setOnClickListener(v -> {
            if (mCallBack == null){
                LiveDataBus.get().with("ChildItemClick").setValue(device);
            }else {
                mCallBack.onClick(device);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_device_icon)
        ImageView mIcon;
        @BindView(R.id.item_device_name)
        TextView mName;
        @BindView(R.id.item_device_type)
        TextView mType;
        @BindView(R.id.item_device_status)
        TextView mStatus;
        @BindView(R.id.item_device_color)
        View mColor;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface subDeviceCallBack{

        void onClick(DeviceInfoBean device);
    }
}
