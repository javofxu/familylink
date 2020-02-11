package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-30.
 * @Dec:
 */
public class TipDialog extends Dialog {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;

    private String mMsg;
    private String mConfirm;
    private OnCallBackToRefresh mCallBack;
    private OnCallBackToCancel mCancel;

    public TipDialog(Context context, OnCallBackToRefresh callBack) {
        super(context, R.style.window_background);
        this.mCallBack = callBack;
    }

    public void setCancel(OnCallBackToCancel mCancel) {
        this.mCancel = mCancel;
    }

    public void setMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public void setConfirmMsg(String s){
        this.mConfirm = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        tvTip.setText(mMsg);

        if (mConfirm!=null) tvDialogConfirm.setText(mConfirm);

        tvDialogConfirm.setOnClickListener(v -> {
            mCallBack.onConfirm();
            dismiss();
        });

        tvDialogCancel.setOnClickListener(v -> {
            dismiss();
            if (mCancel!=null) mCancel.onCancel();
        });
    }

    public interface OnCallBackToRefresh{
        void onConfirm();
    }

    public interface OnCallBackToCancel{
        void onCancel();
    }
}
