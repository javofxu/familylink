package com.ilop.sthome.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.example.common.base.OnCallBackToRefresh;
import com.ilop.sthome.ui.activity.main.ExplainActivity;
import com.ilop.sthome.ui.activity.mine.WebViewActivity;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author skygge
 * @date 2019-12-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：隐私政策弹出框
 */
public class PrivacyPolicyDialog extends Dialog {

    @BindView(R.id.tv_tip)
    TextView tvMsg;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_confirm)
    TextView tvDialogConfirm;

    private Context mContext;
    private String privacy_agreement;
    private OnCallBackToRefresh mCallBack;

    public PrivacyPolicyDialog(Context context, OnCallBackToRefresh callBack) {
        super(context, R.style.window_background);
        this.mContext = context;
        this.mCallBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_policy);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        initData();
        setMsg(mContext.getString(R.string.login_protocol), tvMsg);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        UnitTools unitTools = new UnitTools(mContext);
        try {
            String url = CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_PRIVACY_AGREE, "");
            JSONObject jsonConfig = new JSONObject(url);
            JSONObject json2 = jsonConfig.getJSONObject("url");
            if (json2.has(unitTools.readLanguage())) {
                privacy_agreement = json2.getString(unitTools.readLanguage());
            } else {
                privacy_agreement = json2.getString("default");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvDialogCancel.setOnClickListener(view -> {
            if (mCallBack != null) mCallBack.onCancel();
            dismiss();
        });

        tvDialogConfirm.setOnClickListener(view -> {
            if (mCallBack != null) mCallBack.onConfirm();
            dismiss();
        });
    }

    /**
     *
     * @param content   文字内容
     * @param textView  加载文字的textview
     */

    private void setMsg(String content, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int i = content.indexOf(getContext().getResources().getString(R.string.login_protocol_1));//截取文字开始的下标
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //点击后的操作
                Intent intent = new Intent();
                intent.setClass(mContext, ExplainActivity.class);
                intent.putExtra("mid", 666);
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getContext().getResources().getColor(R.color.main_color));       //设置文字颜色
                ds.setUnderlineText(true);      //设置下划线//根据需要添加
            }
        }, i, i +getContext().getResources().getString(R.string.login_protocol_1).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int i2 = content.indexOf(getContext().getResources().getString(R.string.login_protocol_3));//截取文字开始的下标
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra("agree", privacy_agreement);
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getContext().getResources().getColor(R.color.main_color));       //设置文字颜色
                ds.setUnderlineText(true);      //设置下划线//根据需要添加
            }
        }, i2, i2 +getContext().getResources().getString(R.string.login_protocol_3).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
