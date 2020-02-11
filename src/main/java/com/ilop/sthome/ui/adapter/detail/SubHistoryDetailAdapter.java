package com.ilop.sthome.ui.adapter.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.greenDao.HistoryBean;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.siterwell.familywellplus.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-13.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SubHistoryDetailAdapter extends RecyclerView.Adapter<SubHistoryDetailAdapter.ItemHolder> {

    private Context mContext;
    private List<HistoryBean> mList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SubHistoryDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<HistoryBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_sub_history, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        HistoryBean mWarnBean = mList.get(i);
        Date date = new Date(mWarnBean.getTime() * 1000);
        itemHolder.mTime.setText(sdf.format(date));
        itemHolder.mStatus.setText(HistoryDataUtil.getAlert(mContext,mWarnBean.getDevice_type(),mWarnBean.getDevice_status()));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_status)
        CustomTextView mStatus;
        @BindView(R.id.tv_time)
        TextView mTime;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
