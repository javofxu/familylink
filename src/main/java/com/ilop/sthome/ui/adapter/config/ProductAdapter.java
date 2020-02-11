package com.ilop.sthome.ui.adapter.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilop.sthome.data.bean.ProductBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-14.
 * @Dec:
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {

    private Context mContext;
    private List<ProductBean> mDiagnoseList;
    private String[] mNames;
    private boolean isSubDevice;

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
        mNames = new String[]{mContext.getString(R.string.ali_wifi_product),
                mContext.getString(R.string.ali_alarm_product),
                mContext.getString(R.string.ali_theft_product),
                mContext.getString(R.string.ali_environment_product)};
    }

    public void setProduct(List<ProductBean> mProduct, boolean isSubDevice) {
        this.mDiagnoseList = mProduct;
        this.isSubDevice = isSubDevice;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_product_list, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.mName.setText(isSubDevice ? mNames[i+1] : mNames[i]);
        ProductItemAdapter mAdapter = new ProductItemAdapter(mContext);
        mAdapter.setList(mDiagnoseList.get(i).getList());
        itemHolder.mGrid.setLayoutManager(new GridLayoutManager(mContext, 4));
        itemHolder.mGrid.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mDiagnoseList == null ? 0 : mDiagnoseList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_product_type)
        TextView mName;
        @BindView(R.id.rv_product_list)
        RecyclerView mGrid;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
