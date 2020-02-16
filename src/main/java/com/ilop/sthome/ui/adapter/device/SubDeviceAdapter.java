package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.network.api.SendEquipmentDataAli;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-02.
 * @Dec:
 */
public class SubDeviceAdapter extends RecyclerView.Adapter<SubDeviceAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> deviceList;
    private int isOpen;
    private refresh mRefresh;

    public SubDeviceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDeviceList(List<DeviceInfoBean> deviceList) {
        this.deviceList = deviceList;
    }

    public void setRefresh(refresh mRefresh) {
        this.mRefresh = mRefresh;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device_child, null);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        DeviceInfoBean device = deviceList.get(i);
        holder.childIcon.setImageResource(SmartProduct.getType(device.getDevice_type()).getDrawableResId());
        holder.childName.setText(TextUtils.isEmpty(device.getSubdeviceName())?(mContext.getString(SmartProduct.getType(device.getDevice_type()).getTypeStrId())+device.getDevice_ID()):device.getSubdeviceName());
        if(SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_THCHECK1
                ||SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_THCHECK2
                ||SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_THCHECK3){
            holder.childSwitch.setVisibility(View.INVISIBLE);
            String temp = device.getDevice_status().substring(4,6);
            String humidity = device.getDevice_status().substring(6,8);
            String temp2 = Integer.toBinaryString(Integer.parseInt(temp,16));
            String realT ;
            String realH ;
            if (temp2.length()==8){
                realT = "-"+ (128 - Integer.parseInt(temp2.substring(1),2));
            }else{
                realT = Integer.parseInt(temp2,2)+"";
            }

            if(Integer.parseInt(realT)>100 || Integer.parseInt(realT) < -40 || Integer.parseInt(humidity,16)<0 || Integer.parseInt(humidity,16)>100){
                realT = HistoryDataUtil.getAlert(mContext, device.getDevice_type(), device.getDevice_status());
                holder.childStatus.setText(realT);
            }else{
                realH = Integer.parseInt(humidity,16)+"";
                holder.childStatus.setText(mContext.getString(R.string.ali_temp)+realT+"℃"+" "+mContext.getString(R.string.hum)+realH+"%");
            }
            String status = HistoryDataUtil.getsnapshot(mContext, device.getDevice_type(),device.getDevice_status());
            if(mContext.getString(R.string.low_battery).equals(status)){
                holder.childLayout.setBackgroundResource(R.mipmap.icon_alarm_yellow);
            }else {
                holder.childLayout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            }
        }else {
            String status = HistoryDataUtil.getsnapshot(mContext, device.getDevice_type(), device.getDevice_status());
            if(SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_SOCKET1
                    ||SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_SOCKET2
                    ||SmartProduct.getType(device.getDevice_type())==SmartProduct.EE_DEV_SOCKET3){
                holder.childSwitch.setVisibility(View.VISIBLE);
                if(mContext.getString(R.string.open).equals(status)){
                    isOpen = 0;
                    holder.childSwitch.setImageResource(R.mipmap.icon_switch_on);
                }else {
                    isOpen = 1;
                    holder.childSwitch.setImageResource(R.mipmap.icon_switch_off);
                }
            }else {
                holder.childSwitch.setVisibility(View.GONE);
            }
            if(mContext.getString(R.string.cxalert7).equals(status)
                    ||mContext.getString(R.string.sthalert).equals(status)
                    ||mContext.getString(R.string.cxalert9).equals(status)
                    ||mContext.getResources().getStringArray(R.array.sos_signs)[0].equals(status)){
                holder.childLayout.setBackgroundResource(R.mipmap.icon_alarm_red);
            } else if(mContext.getString(R.string.low_battery).equals(status)
                    ||mContext.getString(R.string.cxalert2).equals(status)
                    ||mContext.getString(R.string.cxalert5).equals(status)){
                holder.childLayout.setBackgroundResource(R.mipmap.icon_alarm_yellow);
            }else {
                holder.childLayout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            }
            holder.childStatus.setText(status);
        }

        holder.childSwitch.setOnClickListener(v -> {
            DeviceInfoBean deviceInfoBean = DeviceDaoUtil.getInstance().findGatewayByDeviceName(device.getDeviceName());
            SendEquipmentDataAli sendEquipmentDataAli = new SendEquipmentDataAli(mContext, deviceInfoBean);
            sendEquipmentDataAli.sendEquipmentCommand(device.getDevice_ID(),"01"+(isOpen==1?"01":"00")+"0000");
        });
        holder.itemView.setOnClickListener(v -> mRefresh.clickItem(device));
        holder.itemView.setOnLongClickListener(v -> {
            mRefresh.longClickItem(device);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.child_icon)
        ImageView childIcon;
        @BindView(R.id.child_name)
        TextView childName;
        @BindView(R.id.child_status)
        TextView childStatus;
        @BindView(R.id.child_socket)
        ImageView childSwitch;
        @BindView(R.id.child_layout)
        RelativeLayout childLayout;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface refresh{

        void clickItem(DeviceInfoBean device);

        void longClickItem(DeviceInfoBean device);
    }
}
