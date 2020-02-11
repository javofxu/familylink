package com.ilop.sthome.ui.adapter.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.view.banner.ViewHolder;
import com.ilop.sthome.data.greenDao.CameraBean;
import com.ilop.sthome.ui.activity.xmipc.ActivityGuideDeviceAdd;
import com.ilop.sthome.utils.DeviceActivityUtil;
import com.ilop.sthome.utils.ViewFactoryUtil;
import com.siterwell.familywellplus.R;

public class ViewPagerHolder implements ViewHolder<CameraBean> {

    private ImageView mImageView;
    private LinearLayout mLinearLayout;
    private TextView mDesc;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_normal_banner,null);
        mImageView = view.findViewById(R.id.normal_banner_bg);
        mLinearLayout = view.findViewById(R.id.normal_banner);
        mDesc =  view.findViewById(R.id.page_desc);
        return view;
    }

    @Override
    public void onBind(Context context, int position, CameraBean data) {
        mLinearLayout.getBackground().setAlpha(100);
        if (data.getDeviceId()!=null){
            mImageView.setImageBitmap(ViewFactoryUtil.getImageViews(context, data.getDeviceId()));
        }else {
            mImageView.setImageResource(R.mipmap.u2);
        }
        mDesc.setText(data.getDeviceName());
        mImageView.setOnClickListener(view -> {
            if (data.getDeviceId() != null) {
                DeviceActivityUtil.startDeviceActivityByAli(context, data.getDeviceId(), data.getDeviceName());
            }else {
                context.startActivity(new Intent(context, ActivityGuideDeviceAdd.class));
            }
        });
    }
}
