package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ilop.sthome.ui.adapter.main.DialogListAdapter;
import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-30.
 * @Dec:
 */
public class BaseListDialog extends Dialog {

    @BindView(R.id.rv_dialog_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context mContext;
    private String mTitle;
    private String[] mMsg;
    private String mCancel;
    private onCallBack mCallBack;

    public BaseListDialog(Context context, onCallBack callBack) {
        super(context, R.style.window_background);
        this.mContext = context;
        this.mCallBack = callBack;
    }

    public void setMsgAndButton(String[] mMsg, String cancel) {
        this.mMsg = mMsg;
        this.mCancel = cancel;
    }

    public void setTitle(String msg){
        mTitle = msg;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_list);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        DialogListAdapter mAdapter = new DialogListAdapter(mContext, mMsg);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCallBack(i -> {
            dismiss();
            mCallBack.callBack(i);
        });
        tvDialogCancel.setText(mCancel);

        if (mTitle!=null) tvTitle.setText(mTitle);
        tvDialogCancel.setOnClickListener(v -> dismiss());
    }

    public interface onCallBack{
        void callBack(int i);
    }
}
