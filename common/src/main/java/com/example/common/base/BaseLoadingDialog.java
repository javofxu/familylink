package com.example.common.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.common.R;

/**
 * @author skygge
 * @date 2020-01-02.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class BaseLoadingDialog extends Dialog {

    private Context mContext;

    public BaseLoadingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_view);
        setCanceledOnTouchOutside(false);
        RelativeLayout layout = findViewById(R.id.loading_view);
        layout.getBackground().setAlpha(60);
        getWindow().setGravity(Gravity.BOTTOM|Gravity.CENTER);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.y = 200;
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        getWindow().setDimAmount(0);
    }
}
