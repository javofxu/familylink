package com.ilop.sthome.ui.adapter.camera;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.Localfile;
import com.siterwell.familywellplus.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 许格 on 2019/12/2.
 */
public class LocalVideoAdapter extends RecyclerView.Adapter<LocalVideoAdapter.ItemHolder> {

    private Context context;
    private List<Localfile> lists;
    public final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LocalVideoAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<Localfile> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_device_camera_pic, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder viewHolder, int i) {
        Localfile eq = lists.get(i);
        viewHolder.imageView.setImageResource(R.mipmap.u2);
        viewHolder.textView_name.setText(eq.getFilename());
        viewHolder.textView_time.setText(yearFormat.format(new Date(Long.parseLong(eq.getModifytime())*1000)));
        viewHolder.itemView.setOnClickListener(view -> {
            LiveDataBus.get().with("local_video_adapter").setValue(eq);
        });
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_push_result_checked)
        ImageView imageView;
        @BindView(R.id.tv_push_result_event)
        TextView textView_name;
        @BindView(R.id.tv_push_result_time)
        TextView textView_time;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}