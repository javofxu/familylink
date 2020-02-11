package com.ilop.sthome.ui.adapter.scene;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.DeviceTrigger;
import com.ilop.sthome.data.enums.SmartProduct;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-24.
 * @Dec:
 */
public class TriggerOutAdapter extends RecyclerView.Adapter<TriggerOutAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;

    public TriggerOutAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> mList) {
        List<DeviceInfoBean> deviceList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if ("DELAY".equals(mList.get(i).getDevice_type())){
                mList.get(i+1).setIdentityAlias(mList.get(i).getDevice_status());
            }else {
                deviceList.add(mList.get(i));
            }
         }
        this.mList = deviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trigger, viewGroup, false);
        return new ItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        DeviceInfoBean deviceInfoBean = mList.get(i);
        itemHolder.mAction.setVisibility(View.VISIBLE);
            String[] mTrigger;
            String[] str;
            if (!TextUtils.isEmpty(deviceInfoBean.getIdentityAlias())){

                int minute = Integer.parseInt(deviceInfoBean.getIdentityAlias().substring(0,2));
                int second = Integer.parseInt(deviceInfoBean.getIdentityAlias().substring(2));
                String mDelay;
                String minuteDw = mContext.getString(R.string.device_setup_record_minute);
                String secondDw = mContext.getString(R.string.device_setup_record_second);
                if (minute == 0 && second == 0) {
                    mDelay = null;
                } else if (minute == 0 && second > 0) {
                    mDelay = second + secondDw;
                } else if (minute > 0 && second == 0) {
                    mDelay = minute + minuteDw;
                } else {
                    mDelay = minute + minuteDw + second + secondDw;
                }
                itemHolder.mDelay.setVisibility(View.VISIBLE);
                itemHolder.mDelay.setText(mContext.getString(R.string.delay) + mDelay);
            }
            if (!TextUtils.isEmpty(deviceInfoBean.getProductKey())) {
                itemHolder.mImg.setImageResource(DevType.getType(deviceInfoBean.getProductKey()).getDrawableResId());
                if (TextUtils.isEmpty(deviceInfoBean.getNickName())) {
                    itemHolder.mName.setText(DevType.getType(deviceInfoBean.getProductKey()).getTypeStrId());
                } else {
                    itemHolder.mName.setText(deviceInfoBean.getNickName());
                }
                mTrigger = mContext.getResources().getStringArray(DeviceTrigger.getType("GATEWAY").getCode());
                str = mContext.getResources().getStringArray(DeviceTrigger.getType("GATEWAY").getState());
                for (int j = 0; j < mTrigger.length; j++) {
                    if (mTrigger[j].equals(deviceInfoBean.getDevice_status())){
                        itemHolder.mAction.setText(str[j]);
                        break;
                    }
                }
            }else {
                itemHolder.mImg.setImageResource(SmartProduct.getType(deviceInfoBean.getDevice_type()).getDrawableResId());
                if (TextUtils.isEmpty(deviceInfoBean.getSubdeviceName())) {
                    if (SmartProduct.EE_SIMULATE_TIMER == SmartProduct.getType(deviceInfoBean.getDevice_type())
                            || SmartProduct.EE_SIMULATE_CLICK == SmartProduct.getType(deviceInfoBean.getDevice_type())
                            || SmartProduct.EE_SIMULATE_PHONE == SmartProduct.getType(deviceInfoBean.getDevice_type())
                            || SmartProduct.EE_SIMULATE_DELAY == SmartProduct.getType(deviceInfoBean.getDevice_type())
                            || SmartProduct.EE_SIMULATE_GATEWAY == SmartProduct.getType(deviceInfoBean.getDevice_type())) {
                        itemHolder.mName.setText(mContext.getString(SmartProduct.getType(deviceInfoBean.getDevice_type()).getTypeStrId()));
                    } else {
                        itemHolder.mName.setText(mContext.getString(SmartProduct.getType(deviceInfoBean.getDevice_type()).getTypeStrId()) + deviceInfoBean.getDevice_ID());
                    }
                } else {
                    itemHolder.mName.setText(deviceInfoBean.getSubdeviceName());
                }
                if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_TWO_CHANNEL_SOCKET)).contains(deviceInfoBean.getDevice_type())) {
                    mTrigger = mContext.getResources().getStringArray(R.array.socket_channel);
                    str = mContext.getResources().getStringArray(R.array.socket_actions);
                    if ("0101".equals(deviceInfoBean.getDevice_status().substring(0,4))){
                        itemHolder.mAction.setText(mTrigger[0] + str[0]);
                    }else if ("0100".equals(deviceInfoBean.getDevice_status().substring(0,4))){
                        itemHolder.mAction.setText(mTrigger[0] + str[1]);
                    }else if ("0202".equals(deviceInfoBean.getDevice_status().substring(0,4))){
                        itemHolder.mAction.setText(mTrigger[1] + str[0]);
                    }else if ("0200".equals(deviceInfoBean.getDevice_status().substring(0,4))){
                        itemHolder.mAction.setText(mTrigger[1] + str[1]);
                    }
                }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_TEMP_CONTROL)).contains(deviceInfoBean.getDevice_type())){
                    if ("1E800000".equals(deviceInfoBean.getDevice_status())){
                        itemHolder.mAction.setText(mContext.getResources().getStringArray(R.array.guard_actions)[1]);
                    }else {
                        itemHolder.mAction.setText(mContext.getResources().getStringArray(R.array.guard_actions)[0]);
                    }
                }else if ("PHONE".equals(deviceInfoBean.getDevice_type())){
                    itemHolder.mAction.setVisibility(View.GONE);
                } else {
                    mTrigger = mContext.getResources().getStringArray(DeviceTrigger.getType(deviceInfoBean.getDevice_type()).getCode());
                    str = mContext.getResources().getStringArray(DeviceTrigger.getType(deviceInfoBean.getDevice_type()).getState());
                    for (int j = 0; j < mTrigger.length; j++) {
                        if (mTrigger[j].equals(deviceInfoBean.getDevice_status())){
                            itemHolder.mAction.setText(str[j]);
                            break;
                        }
                    }
                }
        }

        itemHolder.itemView.setOnClickListener(view -> LiveDataBus.get().with("output_onClick").setValue(deviceInfoBean));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_device_delay)
        TextView mDelay;
        @BindView(R.id.item_trigger_img)
        ImageView mImg;
        @BindView(R.id.item_trigger_name)
        TextView mName;
        @BindView(R.id.item_trigger_action)
        TextView mAction;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
