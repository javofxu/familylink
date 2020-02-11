package com.ilop.sthome.ui.adapter.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @Date on 2020-02-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：房间管理页面
 */
public class RoomManageAdapter extends RecyclerView.Adapter<RoomManageAdapter.ItemHolder> {

    private Context mContext;
    private List<RoomBean> mList;

    public RoomManageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<RoomBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_room_manage, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        RoomBean roomBean = mList.get(i);
        itemHolder.mName.setText(roomBean.getRoom_name());
        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("room_manage").setValue(i));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_room_name)
        TextView mName;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
