package com.ilop.sthome.ui.adapter.scene;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.SceneAliBean;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-11-8.
 * @Dec:
 */
public class DefaultAutoAdapter extends RecyclerView.Adapter<DefaultAutoAdapter.ItemHolder> {

    private Context mContext;
    private List<SceneAliBean> mList;
    private List<SceneAliBean> mBeanList = new ArrayList<>();
    private HashMap<Integer, Boolean> isSelected = new HashMap<>();

    public DefaultAutoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SceneAliBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setSelected(List<Integer> integerList) {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                isSelected.put(i,false);
                for(int j=0;j<integerList.size();j++){
                    if(mList.get(i).getMid() == integerList.get(j)){
                        isSelected.put(i,true);
                        break;
                    }
                }
            }
        }
    }

    public List<SceneAliBean> getSceneBeanList(){
        return mBeanList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_default_auto, viewGroup, false);
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

        if (!isSelected.isEmpty()) {
            if (isSelected.get(i)){
                itemHolder.mCorrect.setChecked(true);
                mBeanList.add(bean);
            }else {
                itemHolder.mCorrect.setChecked(false);
            }
        }

        itemHolder.mCorrect.setOnClickListener(view -> {
            if (itemHolder.mCorrect.isChecked()){
                mBeanList.add(bean);
            }else {
                mBeanList.remove(bean);
            }
            LiveDataBus.get().with("change_scene_auto").setValue(0);
        });

        itemHolder.itemView.setOnClickListener(v -> LiveDataBus.get().with("auto_item_click").setValue(bean));

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.auto_name)
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
        @BindView(R.id.auto_done)
        CheckBox mCorrect;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
