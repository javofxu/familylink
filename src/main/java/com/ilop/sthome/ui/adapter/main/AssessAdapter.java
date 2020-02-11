package com.ilop.sthome.ui.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.CheckDeviceBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2020-02-07.
 * @Dec:
 */
public class AssessAdapter extends RecyclerView.Adapter<AssessAdapter.ItemHolder> {

    private Context mContext;
    private List<CheckDeviceBean> mList;

    public AssessAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<CheckDeviceBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_detection, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        CheckDeviceBean mCheck = mList.get(i);
        itemHolder.mIcon.setImageResource(mCheck.getDeviceIcon());
        itemHolder.mTitle.setText(mCheck.getDeviceType());
        DetectionAdapter mAdapter = new DetectionAdapter(mContext);
        mAdapter.setList(mCheck.getDevice());
        itemHolder.mLists.setLayoutManager(new LinearLayoutManager(mContext));
        itemHolder.mLists.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_detection_icon)
        ImageView mIcon;
        @BindView(R.id.item_detection_title)
        CustomTextView mTitle;
        @BindView(R.id.item_detection_list)
        RecyclerView mLists;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
