package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.utils.LiveDataBus;
import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoseColorAdapter extends RecyclerView.Adapter<ChoseColorAdapter.ItemHolder> {

    private Context mContext;
    private TypedArray mColors;
    private String[] mString;
    private int mPosition = -1;

    public ChoseColorAdapter(Context mContext) {
        this.mContext = mContext;
        mString= mContext.getResources().getStringArray(R.array.gatewaycolor);
        mColors = mContext.getResources().obtainTypedArray(R.array.chose_color);
    }

    public void setColor(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_chose_color, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.mImage.setBackground(mColors.getDrawable(i));
        if (mPosition !=-1 ){
            if (mPosition == i){
                itemHolder.mTick.setVisibility(View.VISIBLE);
            }else {
                itemHolder.mTick.setVisibility(View.GONE);
            }
        }

        itemHolder.mImage.setOnClickListener(view -> {
            LiveDataBus.get().with("chose_color").setValue(i);
            Toast.makeText(mContext, mString[i], Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mColors.length();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cc_image_color)
        TextView mImage;
        @BindView(R.id.iv_color_tick)
        ImageView mTick;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
