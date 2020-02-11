package com.ilop.sthome.ui.ota.base;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;

import com.aliyun.iot.aep.sdk.framework.AApplication;

public abstract class BaseAdapter<T> extends Adapter<BaseViewHolder<T>> {
    protected LayoutInflater inflater;

    public BaseAdapter() {
        inflater = LayoutInflater.from(AApplication.getInstance());
    }
}
