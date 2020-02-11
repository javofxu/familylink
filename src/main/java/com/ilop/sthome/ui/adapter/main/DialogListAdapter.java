package com.ilop.sthome.ui.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.ItemHolder> {

    private Context mContext;
    private String[] mText;
    private onCallBack mCallBack;

    public DialogListAdapter(Context mContext, String[] mText) {
        this.mContext = mContext;
        this.mText = mText;
    }

    public void setCallBack(onCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.tvItemMsg.setText(mText[i]);
        itemHolder.itemView.setOnClickListener(view -> mCallBack.callBack(i));
    }

    @Override
    public int getItemCount() {
        return mText == null ? 0 : mText.length;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_msg)
        TextView tvItemMsg;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onCallBack{
        void callBack(int i);
    }
}
