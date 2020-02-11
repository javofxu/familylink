package com.ilop.sthome.ui.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilop.sthome.data.enums.ProductGroup;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：产品说明书适配器
 */

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ItemHolder> {

    private Context mContext;
    private List<ProductGroup> mList;

    public InstructionAdapter(Context context, List<ProductGroup> lists) {
        this.mContext = context;
        this.mList = lists;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_product_instruction, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        ProductGroup eq = mList.get(i);
        itemHolder.mIcon.setImageResource(eq.getDrawableResId());
        itemHolder.mTitle.setText(eq.getTypeStrId());
        itemHolder.itemView.setOnClickListener(view -> {
            UnitTools unitTools =new UnitTools(mContext);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");

            if("zh".equals(unitTools.readLanguage())){
                String url = eq.getIns_url();

                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
            }else {
                String url = eq.getIns_url_en();
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_instruction_icon)
        ImageView mIcon;
        @BindView(R.id.tv_instruction_title)
        TextView mTitle;
        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
