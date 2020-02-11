package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-11.
 * @Dec: 首页网关列表
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceInfoBean> mList;
    private DeviceAliDAO mDeviceAliDAO;
    private List<DeviceInfoBean> mDeviceList;

    public DeviceAdapter(Context mContext) {
        this.mContext = mContext;
        mDeviceAliDAO = new DeviceAliDAO(mContext);
        mDeviceList = new ArrayList<>();
    }

    public void setList(List<DeviceInfoBean> DeviceInfoBeans) {
        this.mList = DeviceInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_device_gateway, null);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        DeviceInfoBean deviceInfo = mList.get(i);
        mDeviceList.clear();
        mDeviceList.add(deviceInfo);
        List<DeviceInfoBean> deviceInfoBeanList = mDeviceAliDAO.findAllSubDevice(deviceInfo.getDeviceName());
        mDeviceList.addAll(deviceInfoBeanList);
        DeviceChildAdapter mAdapter = new DeviceChildAdapter(mContext);
        holder.mRecycle.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.mRecycle.setAdapter(mAdapter);
        mAdapter.setList(mDeviceList);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_device_child_list)
        RecyclerView mRecycle;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
