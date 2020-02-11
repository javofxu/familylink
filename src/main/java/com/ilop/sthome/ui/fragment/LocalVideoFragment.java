package com.ilop.sthome.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BaseFragment;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.Localfile;
import com.ilop.sthome.ui.adapter.camera.LocalVideoAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentIpcVideoBinding;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class LocalVideoFragment extends BaseFragment<FragmentIpcVideoBinding> {

    private LocalVideoAdapter mVideoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ipc_video;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoAdapter = new LocalVideoAdapter(mContext);
        mDBind.videoList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.videoList.setAdapter(mVideoAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        LiveDataBus.get().with("local_video", List.class).observe(this, list -> {
            mVideoAdapter.setLists(list);
        });
        LiveDataBus.get().with("local_video_adapter", Localfile.class).observe(this, integer -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            String type = "video/*";
            Uri uri = Uri.fromFile(new File(integer.getFilepath()));
            intent.setDataAndType(uri, type);
            startActivity(intent);
        });
    }

}
