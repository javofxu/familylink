package com.ilop.sthome.ui.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-08.
 * @Dec: 检测
 */
public class DetectionAdapter extends RecyclerView.Adapter<DetectionAdapter.ItemHolderChild> {
    private static final String TAG = "DetectionAdapter";
    private Context mContext;
    private List<DeviceInfoBean> mList;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_ITEM_CHILD = 2;

    public DetectionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceInfoBean> deviceInfoBeans) {
        this.mList = deviceInfoBeans;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getDeviceName() == null){
            return TYPE_ITEM_CHILD;
        }else if (mList.get(position).getDeviceName() != null){
            return TYPE_ITEM;
        }
        return 0;
    }

    @NonNull
    @Override
    public ItemHolderChild onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_detection_child, viewGroup, false);
        return new ItemHolderChild(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolderChild itemHolderChild, int i) {
        DeviceInfoBean mBean= mList.get(i);
        itemHolderChild.mIcon.setImageResource(SmartProduct.getType(mBean.getDevice_type()).getDrawableResId());
        itemHolderChild.mName.setText(mContext.getString(SmartProduct.getType(mBean.getDevice_type()).getTypeStrId()));
        itemHolderChild.mTick.setText(mBean.getOwned() == 0 ? mContext.getString(R.string.not_installed) : mContext.getString(R.string.installed));
        itemHolderChild.mTick.setTextColor(mBean.getOwned() == 0 ? mContext.getResources().getColor(R.color.device_not_installed) : mContext.getResources().getColor(R.color.device_installed));
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ItemHolderChild extends RecyclerView.ViewHolder {

        @BindView(R.id.item_child_icon)
        ImageView mIcon;
        @BindView(R.id.item_child_name)
        TextView mName;
        @BindView(R.id.item_child_tick)
        TextView mTick;

        ItemHolderChild(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
