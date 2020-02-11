package com.ilop.sthome.ui.activity.device;

import android.text.TextUtils;
import android.view.View;

import com.example.common.base.BasePActivity;
import com.example.common.utils.InputFilterUtil;
import com.ilop.sthome.mvp.contract.RoomContract;
import com.ilop.sthome.mvp.present.RoomPresenter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityRoomEditorBinding;

/**
 * @author skygge
 * @Date on 2020-02-11.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：房间编辑页面
 */
public class RoomEditorActivity extends BasePActivity<RoomPresenter, ActivityRoomEditorBinding> implements RoomContract.IView {

    private int mPosition;
    private boolean mIsEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_editor;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new RoomPresenter(mContext);
    }

    @Override
    protected void initialize() {
        super.initialize();
        mPosition = getIntent().getIntExtra("roomId", 0);
        mIsEdit = getIntent().getBooleanExtra("isEdit", false);
    }

    @Override
    protected void initView() {
        super.initView();
        InputFilterUtil.setEditTextInhibitInputSpaChat(mDBind.etRoomName);
        mDBind.btDeleteRoom.setVisibility(mIsEdit ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        if (mIsEdit){
            mPresent.getRoomInfo(mPosition);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivRoomBack.setOnClickListener(v -> finish());
        mDBind.tvRoomName1.setOnClickListener(v -> mDBind.etRoomName.setText(mDBind.tvRoomName1.getText().toString()));
        mDBind.tvRoomName2.setOnClickListener(v -> mDBind.etRoomName.setText(mDBind.tvRoomName2.getText().toString()));
        mDBind.tvRoomName3.setOnClickListener(v -> mDBind.etRoomName.setText(mDBind.tvRoomName3.getText().toString()));
        mDBind.tvRoomName4.setOnClickListener(v -> mDBind.etRoomName.setText(mDBind.tvRoomName4.getText().toString()));
        mDBind.tvSaveRoom.setOnClickListener(v -> {
            String roomName = mDBind.etRoomName.getText().toString();
            if (!TextUtils.isEmpty(roomName)){
                if (mIsEdit){
                    mPresent.onUpdateRoom(roomName);
                }else {
                    mPresent.onSaveRoom(roomName);
                }
            }
        });

        mDBind.btDeleteRoom.setOnClickListener(v -> mPresent.onDeleteRoom());
    }

    @Override
    public void showRoomName(String name) {
        mDBind.etRoomName.setText(name);
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void doSuccess() {
        finish();
    }
}
