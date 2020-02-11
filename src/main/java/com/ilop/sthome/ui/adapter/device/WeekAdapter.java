package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siterwell.familywellplus.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-21.
 * @Dec:
 */
public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ItemHolder> {

    private Context mContext;
    private final String[] mWeeks;
    private HashMap<Integer, Boolean> isSelected;

    public WeekAdapter(Context mContext, byte msg) {
        this.mContext = mContext;
        mWeeks = mContext.getResources().getStringArray(R.array.week);
        init(msg);
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
        notifyDataSetChanged();
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    private void init(byte ds) {
        byte f;
        isSelected = new HashMap<>();
        for (int i = 0; i < mWeeks.length; i++) {
            isSelected.put(i, false);
        }
        for (int i = 0; i < mWeeks.length; i++) {
            f = (byte) ((0x02 << i) & ds);
            if (f != 0) {
                isSelected.put(i, true);
            }
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timer_week, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        String day = mWeeks[i];
        itemHolder.mWeek.setText(day);
        if (isSelected.get(i)){
            itemHolder.mWeek.setBackgroundResource(R.drawable.week_select);
        }else {
            itemHolder.mWeek.setBackgroundResource(R.drawable.week_select_normal);
        }
        itemHolder.mWeek.setOnClickListener(v -> {
            isSelected.put(i,!isSelected.get(i));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mWeeks == null ? 0 : mWeeks.length;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wek_item)
        TextView mWeek;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
