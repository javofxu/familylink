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
import com.example.xmpic.support.models.FunDevice;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by st on 2017/12/25.
 */

public class LocalNetIPCAdapter extends RecyclerView.Adapter<LocalNetIPCAdapter.ItemHolder> {

    private Context mContext;
    private List<FunDevice> mLists;

    public LocalNetIPCAdapter(Context context) {
        this.mContext = context;

    }

    public void setLists(List<FunDevice> mLists) {
        this.mLists = mLists;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_local_ipc, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        FunDevice eq = mLists.get(i);
        itemHolder.subMonitor.setImageResource(eq.getDevType().getDrawableResId());
        itemHolder.monitorName.setText(eq.getDevName());
        itemHolder.monitorSn.setText(eq.getDevSn());
        itemHolder.monitorIp.setText(eq.getDevIP());

        itemHolder.itemView.setOnClickListener(view -> LiveDataBus.get().with("local_net_ipc").setValue(i));
    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sub_monitor)
        ImageView subMonitor;
        @BindView(R.id.monitor_name)
        TextView monitorName;
        @BindView(R.id.monitor_sn)
        TextView monitorSn;
        @BindView(R.id.monitor_ip)
        TextView monitorIp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
