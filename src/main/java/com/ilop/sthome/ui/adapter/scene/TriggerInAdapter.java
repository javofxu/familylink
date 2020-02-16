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
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.enums.DeviceTrigger;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.CoderALiUtils;
import com.siterwell.familywellplus.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-24.
 * @Dec:
 */
public class TriggerInAdapter extends RecyclerView.Adapter<TriggerInAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;

    public TriggerInAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> mList) {
        this.mList = mList;
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
        itemHolder.mImg.setImageResource(SmartProduct.getType(deviceInfoBean.getDevice_type()).getDrawableResId());
        if(TextUtils.isEmpty(deviceInfoBean.getSubdeviceName())){
            if(SmartProduct.EE_SIMULATE_TIMER==SmartProduct.getType(deviceInfoBean.getDevice_type())
                    ||SmartProduct.EE_SIMULATE_CLICK==SmartProduct.getType(deviceInfoBean.getDevice_type())
                    ||SmartProduct.EE_SIMULATE_PHONE==SmartProduct.getType(deviceInfoBean.getDevice_type())
                    ||SmartProduct.EE_SIMULATE_DELAY==SmartProduct.getType(deviceInfoBean.getDevice_type())
                    ||SmartProduct.EE_SIMULATE_GATEWAY==SmartProduct.getType(deviceInfoBean.getDevice_type())){
                itemHolder.mName.setText(mContext.getString(SmartProduct.getType(deviceInfoBean.getDevice_type()).getTypeStrId()));
            }else {
                itemHolder.mName.setText(mContext.getString(SmartProduct.getType(deviceInfoBean.getDevice_type()).getTypeStrId())+deviceInfoBean.getDevice_ID());
            }
        }else{
            itemHolder.mName.setText(deviceInfoBean.getSubdeviceName());
        }
        String[] mTrigger;
        String[] str;

        if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_DEV_BUTTON)).contains(deviceInfoBean.getDevice_type())){
            itemHolder.mAction.setText(mContext.getString(R.string.press));
        }else {
            if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_DEV_TH_CHECK)).contains(deviceInfoBean.getDevice_type())){
                mTrigger = mContext.getResources().getStringArray(R.array.thtrigger_method);
                str = mContext.getResources().getStringArray(R.array.thtrigger_style);
                if ("7F00FF".equals(deviceInfoBean.getDevice_status().substring(2))){
                    String num = hexToDec(deviceInfoBean.getDevice_status().substring(0,2));
                    int temp = Integer.parseInt(num);
                    if (Integer.parseInt(num)>128){
                        temp = temp - 256;
                    }
                    itemHolder.mAction.setText(mTrigger[0] + str[0] + temp + "℃");
                }else if ("D8".equals(deviceInfoBean.getDevice_status().substring(0, 2)) && "00FF".equals(deviceInfoBean.getDevice_status().substring(4))){
                    String num = hexToDec(deviceInfoBean.getDevice_status().substring(2,4));
                    int temp = Integer.parseInt(num);
                    if (Integer.parseInt(num)>128){
                        temp = temp - 256;
                    }
                    itemHolder.mAction.setText(mTrigger[0] + str[1] + temp + "℃");
                }else if ("D87F".equals(deviceInfoBean.getDevice_status().substring(0, 4)) && "FF".equals(deviceInfoBean.getDevice_status().substring(6))){
                    String num = hexToDec(deviceInfoBean.getDevice_status().substring(4,6));
                    int temp = Integer.parseInt(num);
                    itemHolder.mAction.setText(mTrigger[1] + str[0] + temp + "%");
                }else if ("D87F00".equals(deviceInfoBean.getDevice_status().substring(0, 6))){
                    String num = hexToDec(deviceInfoBean.getDevice_status().substring(6));
                    int temp = Integer.parseInt(num);
                    itemHolder.mAction.setText(mTrigger[1] + str[1] + temp + "%");
                }
            }else if(Arrays.asList(mContext.getResources().getStringArray(R.array.AA_TWO_CHANNEL_SOCKET)).contains(deviceInfoBean.getDevice_type())){
                mTrigger = mContext.getResources().getStringArray(R.array.socket_channel);
                str = mContext.getResources().getStringArray(R.array.socket_actions);
                for (int j = 0; j < mTrigger.length; j++) {
                    if (mTrigger[j].equals(deviceInfoBean.getDevice_status())){
                        itemHolder.mAction.setText(mTrigger[j]+str[j]);
                        break;
                    }
                }
            }else if ("TIMER".equals(deviceInfoBean.getDevice_type())){
                String init_state = deviceInfoBean.getDevice_status();
                String init_week = init_state.substring(0, init_state.length()-4);
                String week = CoderALiUtils.getWeekInfo(init_week, mContext);
                String hour = init_state.substring(init_state.length()-4,init_state.length()-2);
                String min = init_state.substring(init_state.length()-2);
                String time = hour + ":"+ min;
                itemHolder.mAction.setText(time + "\t\t" +week);
            }else if ("CLICK".equals(deviceInfoBean.getDevice_type())){
                itemHolder.mAction.setVisibility(View.GONE);
            }else {
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

        itemHolder.itemView.setOnClickListener(view -> LiveDataBus.get().with("input_onClick").setValue(deviceInfoBean));

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

    /**
     * 十六进制数据转换为十进制字符串数
     *
     * @param hex
     * @return
     */
    private String hexToDec(String hex) {
        int data = Integer.parseInt(hex, 16);
        return Integer.toString(data, 10);
    }
}
