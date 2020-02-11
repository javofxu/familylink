package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.common.base.OnCallBackToRefresh;
import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-30.
 * @Dec:
 */
public class BaseDialog extends Dialog {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;

    private String mMsg;
    private String mCancel;
    private String mConfirm;
    private boolean isGone = false;
    private OnCallBackToRefresh mCallBack;

    public BaseDialog(Context context) {
        super(context, R.style.window_background);
    }

    public BaseDialog(Context context, OnCallBackToRefresh callBack) {
        super(context, R.style.window_background);
        this.mCallBack = callBack;
    }

    public void setCancelVisibility(boolean isGone){
        this.isGone = isGone;
    }

    public void setTitleAndButton(String msg, String cancel, String confirm) {
        this.mMsg = msg;
        this.mConfirm = confirm;
        this.mCancel = cancel;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        tvDialogCancel.setVisibility(isGone ? View.GONE : View.VISIBLE);
        tvTip.setText(mMsg);
        if (!TextUtils.isEmpty(mCancel)) tvDialogCancel.setText(mCancel);
        if (!TextUtils.isEmpty(mConfirm)) tvDialogConfirm.setText(mConfirm);

        tvDialogConfirm.setOnClickListener(v -> {
            if (mCallBack!=null) mCallBack.onConfirm();
            dismiss();
        });

        tvDialogCancel.setOnClickListener(v -> {
            if (mCallBack!=null) mCallBack.onCancel();
            dismiss();
        });
    }


}
