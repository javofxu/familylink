package com.ilop.sthome.ui.adapter.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.data.greenDao.SceneBean;
import com.ilop.sthome.data.greenDao.SceneSwitchBean;
import com.ilop.sthome.utils.greenDao.SceneDaoUtil;
import com.ilop.sthome.utils.greenDao.SceneSwitchDaoUtil;
import com.siterwell.familywellplus.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2019-12-12.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description： 场景开关适配器
 */
public class SceneSwitchAdapter extends RecyclerView.Adapter<SceneSwitchAdapter.ItemHolder> {

    private Context mContext;
    private List<SceneBean> mList;
    private Map<Integer,String> mModel;
    private DeviceInfoBean equipmentBean;

    public SceneSwitchAdapter(Context mContext, DeviceInfoBean equipmentBean) {
        this.mContext = mContext;
        this.equipmentBean = equipmentBean;
        mModel = SceneDaoUtil.getInstance().findAllSceneByMap(equipmentBean.getDeviceName());
    }

    public void setList(List<SceneBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scene_switch, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SceneBean bean = mList.get(i);
        SceneSwitchBean mSwitch = SceneSwitchDaoUtil.getInstance().findSwitchByDeviceId(bean.getSid(), equipmentBean.getDevice_ID(), equipmentBean.getDeviceName());
        if(mSwitch==null){
            itemHolder.desImg.setImageResource(R.mipmap.scene_custom);
            itemHolder.desName.setText(mContext.getResources().getString(R.string.please_choose));
        }else {
            if(mSwitch.getDes_sid()==0){
                itemHolder.desImg.setImageResource(R.mipmap.scene_at_home);
                itemHolder.desName.setText(mContext.getResources().getString(R.string.home_mode));
            }else if(mSwitch.getDes_sid()==1){
                itemHolder.desImg.setImageResource(R.mipmap.scene_leave_home);
                itemHolder.desName.setText(mContext.getResources().getString(R.string.out_mode));
            }else if(mSwitch.getDes_sid()==2){
                itemHolder.desImg.setImageResource(R.mipmap.scene_sleep);
                itemHolder.desName.setText(mContext.getResources().getString(R.string.sleep_mode));
            }else{
                itemHolder.desImg.setImageResource(R.mipmap.scene_custom);
                itemHolder.desName.setText(mModel.get(mSwitch.getDes_sid()));
            }
        }

        if(bean.getSid()==0){
            itemHolder.srcImg.setImageResource(R.mipmap.scene_at_home);
            itemHolder.srcName.setText(mContext.getResources().getString(R.string.home_mode));
        }else if(bean.getSid()==1){
            itemHolder.srcImg.setImageResource(R.mipmap.scene_leave_home);
            itemHolder.srcName.setText(mContext.getResources().getString(R.string.out_mode));
        }else if(bean.getSid()==2){
            itemHolder.srcImg.setImageResource(R.mipmap.scene_sleep);
            itemHolder.srcName.setText(mContext.getResources().getString(R.string.sleep_mode));
        }else{
            itemHolder.srcImg.setImageResource(R.mipmap.scene_custom);
            itemHolder.srcName.setText(bean.getModleName());
        }
        itemHolder.desImg.setOnClickListener(view -> LiveDataBus.get().with("Switch_Mode").setValue(i));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.src_img)
        ImageView srcImg;
        @BindView(R.id.src_name)
        TextView srcName;
        @BindView(R.id.des_img)
        ImageView desImg;
        @BindView(R.id.des_name)
        TextView desName;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
