package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.example.common.utils.MPreferencesUtil;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.SceneAliDAO;
import com.siterwell.familywellplus.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-19.
 * @Dec:
 */
public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.ItemHolder> {

    private Context mContext;
    private TypedArray mColors;
    private Map<String, String> mMap;
    private List<SysModelAliBean> mList;
    private SceneAliDAO mSceneAliDAO;
    private SceneAutoAdapter mAdapter;

    public SceneAdapter(Context mContext) {
        this.mContext = mContext;
        mSceneAliDAO = new SceneAliDAO(mContext);
        mAdapter = new SceneAutoAdapter(mContext);
        mColors = mContext.getResources().obtainTypedArray(R.array.chose_color);
        mMap = new HashMap<>();
    }

    public void setList(List<SysModelAliBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scene, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        SysModelAliBean mBean = mList.get(i);
        if(mBean.getSid()==0){
            holder.mTitle.setText(mContext.getString(R.string.home_mode));
            holder.mScene.setImageResource(mBean.getChoice() == 1 ? R.mipmap.icon_home_online : R.mipmap.icon_home_offline);
        }else if(mBean.getSid()==1){
            holder.mTitle.setText(mContext.getString(R.string.out_mode));
            holder.mScene.setImageResource(mBean.getChoice() == 1 ? R.mipmap.icon_leave_online : R.mipmap.icon_leave_offline);
        }else if(mBean.getSid()==2){
            holder.mTitle.setText(mContext.getString(R.string.sleep_mode));
            holder.mScene.setImageResource(mBean.getChoice() == 1 ? R.mipmap.icon_sleep_online : R.mipmap.icon_sleep_offline);
        }else {
            holder.mTitle.setText(mBean.getModleName());
            holder.mScene.setImageResource(mBean.getChoice() == 1 ? R.mipmap.icon_custom_online : R.mipmap.icon_custom_offline);
        }

        if (mBean.getChoice() == 1){
            holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.main_color));
            holder.mCheckbox.setImageResource(R.mipmap.icon_correct);
        }else {
            holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.item_not));
            holder.mCheckbox.setImageResource(R.mipmap.icon_error);
        }

        if (String.valueOf(1).equals(MPreferencesUtil.getHashMapData(mContext,"scene").get(String.valueOf(i)))){
            mMap.put(String.valueOf(i), String.valueOf(1));
            MPreferencesUtil.putHashMapData(mContext,"scene", mMap);
            holder.mArrow.setImageResource(R.mipmap.icon_down);
            holder.mSceneAuto.setVisibility(View.VISIBLE);
        }else {
            mMap.put(String.valueOf(i), String.valueOf(0));
            MPreferencesUtil.putHashMapData(mContext,"scene", mMap);
            holder.mArrow.setImageResource(R.mipmap.icon_right);
            holder.mSceneAuto.setVisibility(View.GONE);
        }

        int num = Integer.parseInt(mBean.getColor().substring(1,2));
        holder.mGatewayColor.setBackground(mColors.getDrawable(num));

        List<SceneAliBean> list = mSceneAliDAO.findAllAmBySid(mBean.getSid(), mBean.getDeviceName());
        mAdapter.setList(list);
        holder.mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRvList.setAdapter(mAdapter);

        holder.mArrow.setOnClickListener(v -> {
            if (String.valueOf(1).equals(MPreferencesUtil.getHashMapData(mContext,"scene").get(String.valueOf(i)))){
                mMap.put(String.valueOf(i), String.valueOf(0));
                MPreferencesUtil.putHashMapData(mContext, "scene", mMap);
                holder.mArrow.setImageResource(R.mipmap.icon_right);
                holder.mSceneAuto.setVisibility(View.GONE);
            }else {
                mMap.put(String.valueOf(i), String.valueOf(1));
                MPreferencesUtil.putHashMapData(mContext, "scene", mMap);
                holder.mArrow.setImageResource(R.mipmap.icon_down);
                holder.mSceneAuto.setVisibility(View.VISIBLE);
                mAdapter.setList(list);
                mAdapter.notifyDataSetChanged();
                holder.mAddScene.setVisibility(mBean.getChoice() == 1 ? View.VISIBLE : View.GONE);
            }
        });

        holder.itemView.setOnClickListener(v -> LiveDataBus.get().with("scene_itemClick").setValue(mBean));
        holder.mCheckbox.setOnClickListener(v -> LiveDataBus.get().with("scene_change").setValue(mBean));

        holder.itemView.setOnLongClickListener(v -> {
            LiveDataBus.get().with("scene_itemLongClick").setValue(mBean);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.arrow)
        ImageView mArrow;
        @BindView(R.id.scene)
        ImageView mScene;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.gatewaycolor)
        TextView mGatewayColor;
        @BindView(R.id.checkboxo)
        ImageView mCheckbox;
        @BindView(R.id.rv_scene_list)
        RecyclerView mRvList;
        @BindView(R.id.tv_add_scene)
        TextView mAddScene;
        @BindView(R.id.ll_scene_auto)
        LinearLayout mSceneAuto;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
