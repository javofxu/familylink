package com.ilop.sthome.ui.ota.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.siterwell.familywellplus.R;

public class SimpleTopbar extends FrameLayout implements OnClickListener {
    private ImageView mBackArea;
    private TextView mTitle;

    private onBackClickListener mBackListener;

    public SimpleTopbar(@NonNull Context context) {
        super(context);
        initView();
    }

    public SimpleTopbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SimpleTopbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.ilop_ota_view_topbar, this);
        mBackArea = findViewById(R.id.iv_ota_back);
        mTitle = findViewById(R.id.tv_ota_title);

        mBackArea.setOnClickListener(this);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        if (title == null) {
            return;
        }
        mTitle.setText(title);
    }

    /**
     * 设置标题
     * @param resId
     */
    public void setTitle(int resId) {
        if (resId <= 0) {
            return;
        }
        mTitle.setText(resId);
    }

    public void setOnBackClickListener(onBackClickListener listener) {
        if (listener != null) {
            mBackListener = listener;
        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.iv_ota_back == v.getId()) {
            if (null != mBackListener) {
                mBackListener.onBackClick();
            }
        }
    }

    /**
     * 回退按钮监听
     */
    public interface onBackClickListener {
        void onBackClick();
    }
}
