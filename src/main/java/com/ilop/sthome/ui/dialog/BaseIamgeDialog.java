package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
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
public class BaseIamgeDialog extends Dialog {

    @BindView(R.id.iv_dialog_image)
    ImageView mImage;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;

    private Bitmap mBitmap;
    private String mCancel;
    private String mConfirm;
    private OnCallBackToRefresh mCallBack;

    public BaseIamgeDialog(Context context, OnCallBackToRefresh callBack) {
        super(context, R.style.window_background);
        this.mCallBack = callBack;
    }

    public void setImageAndButton(Bitmap bitmap, String cancel, String confirm) {
        this.mBitmap = bitmap;
        this.mConfirm = confirm;
        this.mCancel = cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        mImage.setImageBitmap(mBitmap);
        tvDialogConfirm.setText(mConfirm);
        tvDialogCancel.setText(mCancel);

        tvDialogConfirm.setOnClickListener(v -> {
            mCallBack.onConfirm();
            dismiss();
        });

        tvDialogCancel.setOnClickListener(v -> {
            mCallBack.onCancel();
            dismiss();
        });
    }


}
