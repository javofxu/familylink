package com.ilop.sthome.ui.adapter.room;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-09.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：房间选择适配器
 */
public class RoomChooseAdapter extends RecyclerView.Adapter<RoomChooseAdapter.ItemHolder> {

    private Context mContext;
    private List<RoomBean> mRoomList;
    private int mPosition = -1;

    public RoomChooseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setRoomList(List<RoomBean> mRoomList) {
        this.mRoomList = mRoomList;
        mPosition = -1;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_room, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        RoomBean roomBean = mRoomList.get(i);
        if (mPosition !=-1 ) {
            if (mPosition == i) {
                itemHolder.mRoomState.setImageResource(R.mipmap.selected_48);
            }else {
                itemHolder.mRoomState.setImageResource(R.mipmap.unselected_48);
            }
        }else {
            itemHolder.mRoomState.setImageResource(R.mipmap.unselected_48);
        }
        itemHolder.mRoomName.setText(roomBean.getRoom_name());
        View.OnClickListener choose_room = view -> LiveDataBus.get().with("choose_room").setValue(i);
        itemHolder.mRoomState.setOnClickListener(choose_room);
        itemHolder.itemView.setOnClickListener(choose_room);
    }

    @Override
    public int getItemCount() {
        return mRoomList == null ? 0 : mRoomList.size();
    }

     class ItemHolder extends RecyclerView.ViewHolder {

         @BindView(R.id.tv_room_name)
         TextView mRoomName;
         @BindView(R.id.iv_room_state)
         ImageView mRoomState;

         ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
