package com.ilop.sthome.ui.adapter.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.SubDeviceHistoryBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：子设备历史详情
 */
public class SubHistoryAdapter extends RecyclerView.Adapter<SubHistoryAdapter.ItemHolder> {

    private Context mContext;
    private List<SubDeviceHistoryBean> mList;

    public SubHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SubDeviceHistoryBean> mList) {
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
        SubDeviceHistoryBean history = mList.get(i);
        itemHolder.mMonth.setText(history.getMonth());
        itemHolder.mMum.setText(String.valueOf(history.getNumber()));
        SubHistoryDetailAdapter mAdapter = new SubHistoryDetailAdapter(mContext);
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
