package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.siterwell.familywellplus.R;

import java.util.List;

/**
 * Created by henry on 2019/4/18.
 */

public class DeviceShareAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder holder;
    private List<DeviceInfoBean> lists;

    public DeviceShareAdapter(Context context, List<DeviceInfoBean> lists){
        this.context = context;
        this.lists = lists;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public DeviceInfoBean getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        holder = null;
        final DeviceInfoBean eq = lists.get(position);
        if( convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_device_share,null);
            holder.device_img= convertView.findViewById(R.id.iv_image);
            holder.textView_title = convertView.findViewById(R.id.tv_name);
            holder.textView_status = convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(!TextUtils.isEmpty(eq.getProductKey())){

            holder.device_img.setImageResource(DevType.getType(eq.getProductKey()).getDrawableResId());
            if(TextUtils.isEmpty(eq.getNickName())){
                holder.textView_title.setText(DevType.getType(eq.getProductKey()).getTypeStrId());
            }else {
                holder.textView_title.setText(eq.getNickName());
            }

            holder.textView_status.setText(eq.getStatus()==1?context.getResources().getString(R.string.device_stauts_online):context.getResources().getString(R.string.device_stauts_offline));
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView device_img;
        TextView textView_title;
        TextView textView_status;
    }

    public void addAll(List<DeviceInfoBean> dataList) {
        this.lists.clear();
        this.lists.addAll(dataList);
        notifyDataSetChanged();
    }


}
