package com.ilop.sthome.ui.adapter.viewHolder;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilop.sthome.data.device.FoundDevice;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.ui.activity.config.ConfigGatewayActivity;
import com.siterwell.familywellplus.R;

/**
 * 支持添加的设备
 */
public class SupportDeviceItemViewHolder extends BaseViewHolder<FoundDevice> {


    private static String CODE = "link://router/connectConfig";


    private   ImageView iv_device_icon;
    private   TextView tv_device_name;
    private   Button btn_device_connect;


    public SupportDeviceItemViewHolder(View itemView) {
        super(itemView);
         iv_device_icon = itemView.findViewById(R.id.list_item_device_icon);
         tv_device_name = itemView.findViewById(R.id.list_item_device_name);
         btn_device_connect = itemView.findViewById(R.id.list_item_device_action);


    }


    @Override
    public void onBind(FoundDevice data, int position) {
        super.onBind(data, position);
        DevType devType = DevType.getType(data.getProductKey());

        iv_device_icon.setImageResource(devType.getDrawableResId());
        tv_device_name.setText(devType.getTypeStrId());
        btn_device_connect.setOnClickListener(view -> {
            if(DevType.getType(data.productKey)==DevType.EE_GATEWAY){
                Intent intent = new Intent(view.getContext(), ConfigGatewayActivity.class);
                view.getContext().startActivity(intent);
            }

        });
    }
}





