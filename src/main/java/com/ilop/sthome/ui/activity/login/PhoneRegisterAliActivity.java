package com.ilop.sthome.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.sdk.android.openaccount.ui.ui.RegisterActivity;
import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.ui.activity.main.ExplainActivity;
import com.ilop.sthome.ui.activity.mine.WebViewActivity;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by henry on 2019/4/16.
 */

public class PhoneRegisterAliActivity extends RegisterActivity {

    private TextView tv_pid;
    private CheckBox mCheckBox;
    private String privacy_agreement;
    private boolean isAgree = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        initData();
        initListener();
    }


    @Override
    protected String getLayoutName() {
        return "activity_phone_register_ali";
    }

    private void initView() {
        this.mToolBar.setVisibility(View.GONE);
        this.smsCodeInputBox.getSend().setText(getResources().getString(R.string.get_code));
        this.smsCodeInputBox.setSendText("get_code");
        this.mobileInputBox.setSupportForeignMobile(this,null,false);
        tv_pid = findViewById(R.id.tv_pid);
        mCheckBox = findViewById(R.id.register_box);
    }

    private void initData() {
        UnitTools unitTools = new UnitTools(this);
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
        setMsg(getString(R.string.login_protocol_2), tv_pid);

        RxTimerUtil.interval(1000, number -> this.next.setEnabled(isAgree));
    }

    private void initListener(){
        findViewById(R.id.iv_register_back).setOnClickListener(view -> finish());
        mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            isAgree = b;
            this.next.setEnabled(b);
        });
    }

    /**
     *
     * @param content   文字内容
     * @param textView  加载文字的textview
     */

    private void setMsg(String content, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int i = content.indexOf(getResources().getString(R.string.login_protocol_1));//截取文字开始的下标
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //点击后的操作
                Intent intent = new Intent();
                intent.setClass(PhoneRegisterAliActivity.this, ExplainActivity.class);
                intent.putExtra("mid", 666);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.main_color));       //设置文字颜色
                ds.setUnderlineText(true);      //设置下划线//根据需要添加
            }
        }, i, i +getResources().getString(R.string.login_protocol_1).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int i2 = content.indexOf(getResources().getString(R.string.login_protocol_3));//截取文字开始的下标
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent();
                intent.setClass(PhoneRegisterAliActivity.this, WebViewActivity.class);
                intent.putExtra("agree", privacy_agreement);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.main_color));       //设置文字颜色
                ds.setUnderlineText(true);      //设置下划线//根据需要添加
            }
        }, i2, i2 +getResources().getString(R.string.login_protocol_3).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
