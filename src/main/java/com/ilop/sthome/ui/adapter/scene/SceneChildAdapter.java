package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.common.utils.LiveDataBus;
import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.ui.activity.scene.SceneDetailActivity;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2019-12-31.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class SceneChildAdapter extends RecyclerView.Adapter<SceneChildAdapter.ItemHolder> {

    private Context mContext;
    private TypedArray mColors;
    private List<SysModelAliBean> mList;

    public SceneChildAdapter(Context mContext) {
        this.mContext = mContext;
        mColors = mContext.getResources().obtainTypedArray(R.array.scene_color);
    }

    public void setList(List<SysModelAliBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scene_child, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SysModelAliBean mBean = mList.get(i);
        switch (mBean.getSid()){
            case 0:
                itemHolder.mName.setText(mContext.getString(R.string.home_mode));
                break;
            case 1:
                itemHolder.mName.setText(mContext.getString(R.string.out_mode));
                break;
            case 2:
                itemHolder.mName.setText(mContext.getString(R.string.sleep_mode));
                break;
            case 3:
                itemHolder.mName.setText(mContext.getString(R.string.custom_scene));
                break;
            default:
                itemHolder.mName.setText(mBean.getModleName());
                break;
        }
        int num = Integer.parseInt(mBean.getColor().substring(1,2));
        if (mBean.getChoice() == 1){
            itemHolder.mLayout.setBackground(mColors.getDrawable(num));
            if (num !=8) {
                itemHolder.mName.setTextColor(mContext.getResources().getColor(R.color.white));
                itemHolder.mMore.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.white)));
                switch (mBean.getSid()) {
                    case 0:
                        itemHolder.mIcon.setImageResource(R.mipmap.scene_at_home_white);
                        break;
                    case 1:
                        itemHolder.mIcon.setImageResource(R.mipmap.scene_leave_home_white);
                        break;
                    case 2:
                        itemHolder.mIcon.setImageResource(R.mipmap.scene_sleep_white);
                        break;
                    case 3:
                        itemHolder.mIcon.setImageResource(R.mipmap.scene_custom_white);
                        break;
                    default:
                        itemHolder.mIcon.setImageResource(R.mipmap.scene_custom_white);
                        break;
                }
            }
        }else {
            itemHolder.mLayout.setBackgroundResource(R.drawable.round_corner_bg);
            itemHolder.mName.setTextColor(mContext.getResources().getColor(R.color.black));
            itemHolder.mMore.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.black)));
            switch (mBean.getSid()){
                case 0:
                    itemHolder.mIcon.setImageResource(R.mipmap.scene_at_home);
                    break;
                case 1:
                    itemHolder.mIcon.setImageResource(R.mipmap.scene_leave_home);
                    break;
                case 2:
                    itemHolder.mIcon.setImageResource(R.mipmap.scene_sleep);
                    break;
                case 3:
                    itemHolder.mIcon.setImageResource(R.mipmap.scene_custom);
                    break;
                default:
                    itemHolder.mIcon.setImageResource(R.mipmap.scene_custom);
                    break;
            }
        }

        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("scene_change").setValue(mBean));
        itemHolder.mMore.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SceneDetailActivity.class);
            intent.putExtra("scene_id", mBean.getSid());
            intent.putExtra("deviceName", mBean.getDeviceName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scene_icon)
        ImageView mIcon;
        @BindView(R.id.scene_child_name)
        CustomTextView mName;
        @BindView(R.id.scene_child_more)
        ImageView mMore;
        @BindView(R.id.scene_child_bg)
        LinearLayout mLayout;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
