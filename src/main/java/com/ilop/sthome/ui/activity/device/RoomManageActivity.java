package com.ilop.sthome.ui.activity.device;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.greenDao.RoomBean;
import com.ilop.sthome.ui.adapter.room.RoomManageAdapter;
import com.ilop.sthome.utils.greenDao.RoomDaoUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityRoomManageBinding;

import java.util.List;

/**
 * @author skygge
 * @date 2020-01-10.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：房间管理页面
 */
public class RoomManageActivity extends BaseActivity<ActivityRoomManageBinding> {

    private RoomManageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_manage;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new RoomManageAdapter(mContext);
        mDBind.rvRoomList.setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.rvRoomList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        LiveDataBus.get().with("room_manage", Integer.class).observe(this, integer -> {
            Intent intent = new Intent(mContext, RoomEditorActivity.class);
            intent.putExtra("roomId", integer);
            intent.putExtra("isEdit", true);
            startActivity(intent);
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivRoomBack.setOnClickListener(v -> finish());
        mDBind.ivAddRoom.setOnClickListener(v -> skipAnotherActivity(RoomEditorActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<RoomBean> roomBeanList = RoomDaoUtil.getInstance().getRoomDao().queryAll();
        mAdapter.setList(roomBeanList);
    }
}
