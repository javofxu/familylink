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
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-19.
 * @Dec:
 */
public class GatewayListAdapter extends RecyclerView.Adapter<GatewayListAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;
    private int mPosition = -1;

    public GatewayListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gateway_list, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        DeviceInfoBean device  = mList.get(i);
        if(TextUtils.isEmpty(device.getNickName())){
            holder.tvName.setText(DevType.getType(device.getProductKey()).getTypeStrId());
        }else {
            holder.tvName.setText(device.getNickName());
        }
        holder.tvStatus.setText(device.getStatus() == 1 ? mContext.getString(R.string.device_stauts_online) : mContext.getString(R.string.device_stauts_offline));
        holder.ivStatus.setImageResource(device.getStatus() == 1 ? R.drawable.device_status_normal : R.drawable.device_status_off_line);
        if (mPosition !=-1 ) {
            if (mPosition == i) {
                holder.mState.setImageResource(R.mipmap.selected_48);
            }else {
                holder.mState.setImageResource(R.mipmap.unselected_48);
            }
        }
        View.OnClickListener choose_room = view -> LiveDataBus.get().with("choose_gateway").setValue(i);
        holder.mState.setOnClickListener(choose_room);
        holder.itemView.setOnClickListener(choose_room);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gateway_name)
        TextView tvName;
        @BindView(R.id.tv_gateway_status)
        TextView tvStatus;
        @BindView(R.id.iv_gateway_status)
        ImageView ivStatus;
        @BindView(R.id.iv_gateway_state)
        ImageView mState;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
