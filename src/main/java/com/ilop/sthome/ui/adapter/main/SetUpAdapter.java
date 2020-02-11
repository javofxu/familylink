package com.ilop.sthome.ui.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.AlarmNotice;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2020-02-06.
 * @Dec:
 */
public class SetUpAdapter extends RecyclerView.Adapter<SetUpAdapter.ItemHolder> {

    private Context mContext;
    private List<AlarmNotice> mList;

    public SetUpAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<AlarmNotice> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_set_up_notice, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        AlarmNotice alarm = mList.get(i);
        itemHolder.mName.setText(alarm.getEventName());
        itemHolder.mState.setImageResource(alarm.isNoticeEnabled() ? R.mipmap.btn_on_48 : R.mipmap.btn_off_48);

        itemHolder.mState.setOnClickListener(v -> LiveDataBus.get().with("alarm_notice").setValue(alarm));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_set_up_name)
        TextView mName;
        @BindView(R.id.item_set_up_state)
        ImageView mState;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
