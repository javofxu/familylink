package com.ilop.sthome.ui.adapter.camera;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.utils.ViewFactoryUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraChoseAdapter extends RecyclerView.Adapter<CameraChoseAdapter.ItemHolder> {

    private Context mContext;
    private String mCameraId;
    private List<CameraBean> mCameraList;

    public CameraChoseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCameraList(String id, List<CameraBean> mCameraList) {
        this.mCameraList = mCameraList;
        this.mCameraId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_camera_chose, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        CameraBean mCamera  = mCameraList.get(i);
        itemHolder.mImage.setImageBitmap(ViewFactoryUtil.getImageViews(mContext, mCamera.getDeviceId()));
        itemHolder.mName.setText(mCamera.getDeviceName());
        if (mCameraId.equals(mCamera.getDeviceId())){
            itemHolder.mName.setTextColor(mContext.getResources().getColorStateList(R.color.main_color));
            itemHolder.mPlay.setImageResource(R.mipmap.icon_play_on);
        }else{
            itemHolder.mName.setTextColor(mContext.getResources().getColorStateList(R.color.device_off));
            itemHolder.mPlay.setImageResource(R.mipmap.icon_play);
        }
        itemHolder.itemView.setOnClickListener(view -> LiveDataBus.get().with("chose_camera_click").setValue(mCamera));
    }

    @Override
    public int getItemCount() {
        return mCameraList == null ? 0 : mCameraList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_camera_bg)
        ImageView mImage;
        @BindView(R.id.item_camera_play)
        ImageView mPlay;
        @BindView(R.id.item_camera_name)
        TextView mName;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
