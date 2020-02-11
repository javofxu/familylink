package com.ilop.sthome.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BaseFragment;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.Localfile;
import com.ilop.sthome.ui.adapter.camera.LocalPicAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.FragmentIpcPicBinding;

import java.io.File;
import java.util.List;

/**
 * Created by 许格 on 2019/12/7.
 */

public class LocalPicFragment extends BaseFragment<FragmentIpcPicBinding> {
    private LocalPicAdapter mPicAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ipc_pic;
    }

    @Override
    protected void initView() {
        super.initView();
        mPicAdapter = new LocalPicAdapter(mContext);
        mDBind.picList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.picList.setAdapter(mPicAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        LiveDataBus.get().with("local_pic", List.class).observe(this, list -> {
            mPicAdapter.setLists(list);
        });
        LiveDataBus.get().with("local_pic_adapter", Localfile.class).observe(this, integer -> {
            File file=new File(integer.getFilepath());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(mContext, "com.siterwell.familywellplus.fileprovider", file);//file即为所要共享的文件的file
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(photoURI, "image/*");
            startActivity(intent);
        });
    }

}
