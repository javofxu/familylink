package com.ilop.sthome.ui.adapter.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.view.CustomTextView;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.data.db.DeviceAliDAO;
import com.ilop.sthome.data.enums.DevType;
import com.ilop.sthome.data.enums.SmartProduct;
import com.ilop.sthome.data.greenDao.WarnBean;
import com.ilop.sthome.utils.HistoryDataUtil;
import com.siterwell.familywellplus.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2020-01-13.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.ItemHolder> {

    private Context mContext;
    private List<WarnBean> mList;
    private DeviceAliDAO mDeviceAliDAO;

    public HistoryDetailAdapter(Context mContext) {
        this.mContext = mContext;
        mDeviceAliDAO = new DeviceAliDAO(mContext);
    }

    public void setList(List<WarnBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_history_detail, viewGroup, false);
        return new ItemHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        WarnBean mWarnBean = mList.get(i);
        String name;
        DeviceInfoBean gateway = mDeviceAliDAO.findByDeviceid(mWarnBean.getDeviceName(),0);
        if(TextUtils.isEmpty(gateway.getNickName())){
            name = mContext.getResources().getString(DevType.getType(gateway.getProductKey()).getTypeStrId());
        }else {
            name = gateway.getNickName();
        }
        if(mWarnBean.getDevice_id()==0){
            itemHolder.mName.setText(name);
            itemHolder.mStatus.setText(HistoryDataUtil.getGatewayAlert(mContext, mWarnBean.getDevice_status()));
        }else {
            DeviceInfoBean deviceInfoBean = mDeviceAliDAO.findByDeviceid(mWarnBean.getDeviceName(),mWarnBean.getDevice_id());

            if(deviceInfoBean==null){
                itemHolder.mName.setText(mContext.getResources().getString(SmartProduct.getType(mWarnBean.getDevice_type()).getTypeStrId())+mWarnBean.getDevice_id());
            }else {
                if(TextUtils.isEmpty(deviceInfoBean.getSubdeviceName())){
                    itemHolder.mName.setText(mContext.getResources().getString(SmartProduct.getType(mWarnBean.getDevice_type()).getTypeStrId())+mWarnBean.getDevice_id());
                }else {
                    itemHolder.mName.setText(deviceInfoBean.getSubdeviceName());
                }
            }
            itemHolder.mImage.setImageResource(SmartProduct.getType(mWarnBean.getDevice_type()).getDrawableResId());
            itemHolder.mStatus.setText(HistoryDataUtil.getAlert(mContext,mWarnBean.getDevice_type(),mWarnBean.getDevice_status()));
        }
        itemHolder.mTime.setText(mWarnBean.getActivtiyTimeDetail());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_history_image)
        ImageView mImage;
        @BindView(R.id.tv_history_name)
        CustomTextView mName;
        @BindView(R.id.tv_history_time)
        TextView mTime;
        @BindView(R.id.tv_history_status)
        TextView mStatus;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
