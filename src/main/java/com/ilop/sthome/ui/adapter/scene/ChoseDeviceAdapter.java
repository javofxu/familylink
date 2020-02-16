package com.ilop.sthome.ui.adapter.scene;

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
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-23.
 * @Dec:
 */
public class ChoseDeviceAdapter extends RecyclerView.Adapter<ChoseDeviceAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mLists;

    public ChoseDeviceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLists(List<DeviceInfoBean> mLists) {
        this.mLists = mLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_sub_device, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        DeviceInfoBean eq = mLists.get(i);
        itemHolder.mImg.setPadding(20, 20, 20, 20);
        if (!TextUtils.isEmpty(eq.getProductKey())){
            itemHolder.mImg.setImageResource(DevType.getType(eq.getProductKey()).getDrawableResId());
            if (TextUtils.isEmpty(eq.getNickName())) {
                itemHolder.mName.setText(DevType.getType(eq.getProductKey()).getTypeStrId());
            } else {
                itemHolder.mName.setText(eq.getNickName());
            }
        }else {
            itemHolder.mImg.setImageResource(SmartProduct.getType(eq.getDevice_type()).getDrawableResId());
            if (TextUtils.isEmpty(eq.getSubdeviceName())) {
                if (SmartProduct.EE_SIMULATE_TIMER == SmartProduct.getType(eq.getDevice_type())
                        || SmartProduct.EE_SIMULATE_CLICK == SmartProduct.getType(eq.getDevice_type())
                        || SmartProduct.EE_SIMULATE_PHONE == SmartProduct.getType(eq.getDevice_type())
                        || SmartProduct.EE_SIMULATE_DELAY == SmartProduct.getType(eq.getDevice_type())
                        || SmartProduct.EE_SIMULATE_GATEWAY == SmartProduct.getType(eq.getDevice_type())) {
                    itemHolder.mName.setText(mContext.getString(SmartProduct.getType(eq.getDevice_type()).getTypeStrId()));
                } else {
                    itemHolder.mName.setText(mContext.getString(SmartProduct.getType(eq.getDevice_type()).getTypeStrId()) + eq.getDevice_ID());
                }

            } else {
                itemHolder.mName.setText(eq.getSubdeviceName());
            }
        }
        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("Physical_onClick").setValue(i));
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productImg)
        ImageView mImg;
        @BindView(R.id.productName)
        TextView mName;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
