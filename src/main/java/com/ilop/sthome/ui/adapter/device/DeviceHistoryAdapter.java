package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.DeviceHistoryBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：设备历史记录
 */
public class DeviceHistoryAdapter extends RecyclerView.Adapter<DeviceHistoryAdapter.ItemHolder> {

    private Context mContext;
    private List<DeviceHistoryBean> mList;

    public DeviceHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<DeviceHistoryBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_device_history, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        DeviceHistoryBean history = mList.get(i);
        itemHolder.mMonth.setText(history.getMonth());
        itemHolder.mMum.setText(String.valueOf(history.getNumber()));
        HistoryDetailAdapter mAdapter = new HistoryDetailAdapter(mContext);
        mAdapter.setList(history.getList());
        itemHolder.mDetail.setLayoutManager(new LinearLayoutManager(mContext));
        itemHolder.mDetail.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_history_month)
        CustomTextView mMonth;
        @BindView(R.id.item_history_mum)
        TextView mMum;
        @BindView(R.id.item_history_detail)
        RecyclerView mDetail;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
