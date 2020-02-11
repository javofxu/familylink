package com.ilop.sthome.ui.adapter.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.ui.adapter.device.DeviceChildAdapter;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2020-02-08.
 * @Dec:
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ItemHolder> {

    private Context mContext;
    private List<RoomBean> mList;

    public RoomAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<RoomBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_room, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        RoomBean roomBean = mList.get(i);
        List<DeviceInfoBean> mDevice = new ArrayList<>();
        if (roomBean.getGatewayList()!=null){
            mDevice.addAll(roomBean.getGatewayList());
        }
        if (roomBean.getCameraList()!=null){
            mDevice.addAll(roomBean.getCameraList());
        }
        if (roomBean.getSubDeviceList()!=null){
            mDevice.addAll(roomBean.getSubDeviceList());
        }
        itemHolder.itemRoomEdit.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        itemHolder.itemRoomName.setText(roomBean.getRoom_name());
        itemHolder.itemRoomNum.setText(mDevice.size() + mContext.getString(R.string.devices));
        DeviceChildAdapter mAdapter = new DeviceChildAdapter(mContext);
        itemHolder.itemRoomList.setLayoutManager(new GridLayoutManager(mContext, 2));
        itemHolder.itemRoomList.setAdapter(mAdapter);
        mAdapter.setList(mDevice);
        itemHolder.itemRoomEdit.setOnClickListener(v -> LiveDataBus.get().with("edit_room").setValue(0));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_room_name)
        CustomTextView itemRoomName;
        @BindView(R.id.item_room_num)
        TextView itemRoomNum;
        @BindView(R.id.item_room_edit)
        ImageView itemRoomEdit;
        @BindView(R.id.item_room_list)
        RecyclerView itemRoomList;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
