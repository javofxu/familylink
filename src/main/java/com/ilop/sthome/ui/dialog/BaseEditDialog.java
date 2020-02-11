package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.base.OnCallBackToEdit;
import com.siterwell.familywellplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-30.
 * @Dec:
 */
public class BaseEditDialog extends Dialog {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_eyes)
    ImageView mEyes;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;

    private Context mContext;
    private String mTitle;
    private String mMsg;
    private String mCancel;
    private String mConfirm;
    private boolean isPwd = false;
    private OnCallBackToEdit mCallBack;

    public BaseEditDialog(Context context, OnCallBackToEdit callBack) {
        super(context, R.style.window_background);
        this.mCallBack = callBack;
        this.mContext = context;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setTitleAndButton(String title, String cancel, String confirm) {
        this.mTitle = title;
        this.mConfirm = confirm;
        this.mCancel = cancel;
    }

    public void setMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public void setPwdText(boolean pwd){
        this.isPwd = pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        if (mMsg == null){
            tvTip.setVisibility(View.GONE);
        }else {
            tvTip.setText(mMsg);
        }
        tvTitle.setText(mTitle);
        if (mConfirm!=null) tvDialogConfirm.setText(mConfirm);
        if (mConfirm!=null) tvDialogCancel.setText(mCancel);

        mEyes.setVisibility(isPwd ? View.VISIBLE : View.GONE);
        etInput.setInputType(isPwd ? 129 : 128);

        mEyes.setOnClickListener(view -> {
            if (etInput.getInputType() == 128){
                mEyes.setImageResource(R.mipmap.icon_eye_close);
            }else {
                mEyes.setImageResource(R.mipmap.icon_eye_open);
            }
        });

        tvDialogConfirm.setOnClickListener(v -> {
            mCallBack.onConfirm(etInput.getText().toString());
            dismiss();
        });

        tvDialogCancel.setOnClickListener(v -> {
            mCallBack.onCancel();
            dismiss();
        });
    }


}
