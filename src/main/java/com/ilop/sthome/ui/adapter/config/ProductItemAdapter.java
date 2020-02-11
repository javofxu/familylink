package com.ilop.sthome.ui.adapter.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.enums.SmartProduct;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-15.
 * @Dec:
 */
public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductHolder> {

    private Context mContext;
    private List<String> mList;

    public ProductItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_style, viewGroup, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int i) {
        String mDeviceType = mList.get(i);
        holder.ivProductIcon.setImageResource(SmartProduct.getType(mDeviceType).getDrawableResId());
        holder.tvProductName.setText(mContext.getString(SmartProduct.getType(mDeviceType).getTypeStrId()));
        holder.itemView.setOnClickListener(v -> LiveDataBus.get().with("product").setValue(mDeviceType));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_product_icon)
        ImageView ivProductIcon;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


