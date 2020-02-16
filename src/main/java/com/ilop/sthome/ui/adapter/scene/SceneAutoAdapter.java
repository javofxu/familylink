package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.SceneModelUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-19.
 * @Dec:
 */
public class SceneAutoAdapter extends RecyclerView.Adapter<SceneAutoAdapter.ItemHolder> {

    private Context mContext;
    private List<SceneAliBean> mList;

    public SceneAutoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SceneAliBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scene_auto, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SceneAliBean bean = mList.get(i);
        itemHolder.mTitle.setText(bean.getName());

        List<DeviceInfoBean> inputList = bean.getInput(mContext, bean.getDeviceName());
        if(inputList.size()>0){
            DeviceInfoBean sceneAliBean =inputList.get(0);
            itemHolder.output1.setVisibility(View.VISIBLE);
            itemHolder.input1.setImageResource(SmartProduct.getType(sceneAliBean.getDevice_type()).getDrawableResId());
        }else {
            itemHolder.output1.setVisibility(View.INVISIBLE);
        }
        if(inputList.size()>1){
            itemHolder.input2.setVisibility(View.VISIBLE);
        }else{
            itemHolder.input2.setVisibility(View.INVISIBLE);
        }

        List<DeviceInfoBean> outputList = bean.getOutput(mContext,bean.getDeviceName());

        if(outputList.size()>=1){
            DeviceInfoBean sceneAliBean =outputList.get(0);
            itemHolder.output1.setVisibility(View.VISIBLE);
            itemHolder.output1.setImageResource(SmartProduct.getType(sceneAliBean.getDevice_type()).getDrawableResId());
        }else {
            itemHolder.output1.setVisibility(View.INVISIBLE);
        }

        if(outputList.size()>=2){
            DeviceInfoBean sceneAliBean =outputList.get(1);
            itemHolder.output2.setVisibility(View.VISIBLE);
            itemHolder.output2.setImageResource(SmartProduct.getType(sceneAliBean.getDevice_type()).getDrawableResId());
        }else {
            itemHolder.output2.setVisibility(View.INVISIBLE);
        }

        if(outputList.size()>=3){
            DeviceInfoBean sceneAliBean =outputList.get(2);
            itemHolder.output3.setVisibility(View.VISIBLE);
            itemHolder.output3.setImageResource(SmartProduct.getType(sceneAliBean.getDevice_type()).getDrawableResId());
        }else {
            itemHolder.output3.setVisibility(View.INVISIBLE);
        }

        if(outputList.size()>3){
            itemHolder.output4.setVisibility(View.VISIBLE);
        }else{
            itemHolder.output4.setVisibility(View.INVISIBLE);
        }

        SceneModelUtil sceneModelUtil = bean.getSp();
        if("ab".equals(sceneModelUtil.getClickAction()) || "AB".equals(sceneModelUtil.getClickAction())){
            itemHolder.done.setVisibility(View.VISIBLE);
        }else {
            itemHolder.done.setVisibility(View.GONE);
        }

        itemHolder.done.setOnClickListener(v -> LiveDataBus.get().with("auto_click").setValue(bean.getMid()));

        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("auto_item_click").setValue(bean));

        itemHolder.itemView.setOnLongClickListener(v -> {
            LiveDataBus.get().with("auto_long_click").setValue(bean);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.wode)
        TextView mTitle;
        @BindView(R.id.input1)
        ImageView input1;
        @BindView(R.id.input2)
        ImageView input2;
        @BindView(R.id.output1)
        ImageView output1;
        @BindView(R.id.output2)
        ImageView output2;
        @BindView(R.id.output3)
        ImageView output3;
        @BindView(R.id.output4)
        ImageView output4;
        @BindView(R.id.done)
        Button done;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
