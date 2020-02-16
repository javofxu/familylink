package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by henry on 2019/4/17.
 */

public class AddSubDeviceAdapter extends RecyclerView.Adapter<AddSubDeviceAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;

    public AddSubDeviceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_add_sub_device, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        DeviceInfoBean bean = mList.get(i);
        itemHolder.mImg.setImageResource(SmartProduct.getType(bean.getDevice_type()).getDrawableResId());
        itemHolder.mName.setText(mContext.getString(SmartProduct.getType(bean.getDevice_type()).getTypeStrId()));

        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("addSubDevice").setValue(bean.getDevice_type()));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
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
