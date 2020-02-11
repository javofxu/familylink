package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.ResolveAliTimer;
import com.ilop.sthome.data.bean.TimerGatewayAliBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-22.
 * @Dec:
 */
public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ItemHolder> {

    private Context mContext;
    private List<TimerGatewayAliBean> mList;

    public TimerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<TimerGatewayAliBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timer, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        TimerGatewayAliBean timer = mList.get(i);
        if(timer.getModeid()==0){
            holder.tvTimerMode.setText(mContext.getString(R.string.home_mode));
        }else if(timer.getModeid()==1){
            holder.tvTimerMode.setText(mContext.getString(R.string.out_mode));
        }else if(timer.getModeid()==2){
            holder.tvTimerMode.setText(mContext.getString(R.string.sleep_mode));
        }else{
            holder.tvTimerMode.setText(TextUtils.isEmpty(timer.getModename()) ? String.valueOf(timer.getModeid()) : timer.getModename());
        }
        holder.tvWeekTime.setText(ResolveAliTimer.getWeekinfo(timer.getWeek(), mContext));
        holder.tvTimeHour.setText(timer.getHour());
        holder.tvTimeMin.setText(timer.getMin());
        if(timer.getEnable()==1){
            holder.ibEnable.setImageResource(R.mipmap.icon_switch_on);
        }else{
            holder.ibEnable.setImageResource(R.mipmap.icon_switch_off);
        }

        holder.ibEnable.setOnClickListener(v -> LiveDataBus.get().with("timer_switch").setValue(i));

        holder.itemView.setOnClickListener(v -> LiveDataBus.get().with("timer_click").setValue(timer.getTimerid()));
        holder.itemView.setOnLongClickListener(v -> {
            LiveDataBus.get().with("timer_longClick").setValue(timer.getTimerid());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time_hour)
        TextView tvTimeHour;
        @BindView(R.id.tv_time_min)
        TextView tvTimeMin;
        @BindView(R.id.tv_timer_mode)
        TextView tvTimerMode;
        @BindView(R.id.tv_week_time)
        TextView tvWeekTime;
        @BindView(R.id.ib_enable)
        ImageButton ibEnable;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
