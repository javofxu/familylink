package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-14.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：网关中更换场景
 */
public class SceneChangeAdapter extends RecyclerView.Adapter<SceneChangeAdapter.ItemHolder> {

    private Context mContext;
    private List<SysModelAliBean> mList;

    public SceneChangeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SysModelAliBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_change_scene, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SysModelAliBean mBean = mList.get(i);
        switch (mBean.getSid()){
            case 0:
                itemHolder.mIcon.setImageResource(R.mipmap.scene_at_home);
                itemHolder.mName.setText(mContext.getString(R.string.home_mode));
                break;
            case 1:
                itemHolder.mIcon.setImageResource(R.mipmap.scene_leave_home);
                itemHolder.mName.setText(mContext.getString(R.string.out_mode));
                break;
            case 2:
                itemHolder.mIcon.setImageResource(R.mipmap.scene_sleep);
                itemHolder.mName.setText(mContext.getString(R.string.sleep_mode));
                break;
            case 3:
                itemHolder.mIcon.setImageResource(R.mipmap.scene_custom);
                itemHolder.mName.setText(mContext.getString(R.string.custom_scene));
                break;
            default:
                itemHolder.mIcon.setImageResource(R.mipmap.scene_custom);
                itemHolder.mName.setText(mBean.getModleName());
                break;
        }

        if (mBean.getChoice() == 1){
            itemHolder.mChange.setImageResource(R.mipmap.selected_48);
        }else {
            itemHolder.mChange.setImageResource(R.mipmap.unselected_48);
        }

        View.OnClickListener scene_change = view -> LiveDataBus.get().with("scene_change_list").setValue(mBean);
        itemHolder.mChange.setOnClickListener(scene_change);
        itemHolder.itemView.setOnClickListener(scene_change);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_scene_icon)
        ImageView mIcon;
        @BindView(R.id.tv_scene_name)
        TextView mName;
        @BindView(R.id.iv_scene_change)
        ImageView mChange;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
