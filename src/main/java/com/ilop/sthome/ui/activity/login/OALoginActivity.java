package com.ilop.sthome.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailRegisterCallback;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.ui.widget.NonMultiClickListener;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.aliyun.iot.aep.oa.OALanguageHelper;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.common.ProtocolFilter;
import com.ilop.sthome.utils.tools.ErrorTools;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;

import java.util.Locale;


public class OALoginActivity extends LoginActivity{

    private int type = 0;
    private String mLanguage;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initView();
        initListener();
    }

    @Override
    protected String getLayoutName() {
        return "activity_login_ali";
    }


    private void initialize() {
        Locale locale = getResources().getConfiguration().locale;//获得local对象
        String lan = locale.getLanguage();
        if("zh".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.SIMPLIFIED_CHINESE);
        }else if("en".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.US);
        }else if("fr".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.FRANCE);
        }else if("de".equals(lan)){
            OALanguageHelper.setLanguageCode(Locale.GERMANY);
        }else if("es".equals(lan)){
            OALanguageHelper.setLanguageCode(new Locale("es","ES"));
        }else {
            OALanguageHelper.setLanguageCode(Locale.US);
        }
        mLanguage = lan;
    }


    private void initView(){
        TRANSPARENT();
        ConfigManager.getInstance().setSupportOfflineLogin(true);
        mProgressBar = findViewById(R.id.login_loading);
        this.mToolBar.setVisibility(View.GONE);
        this.resetPasswordTV = this.findViewById(ResourceUtils.getRId(this, "reset_password"));
        if (this.resetPasswordTV != null) {
            this.resetPasswordTV.setOnClickListener(v -> {
                if("zh".equals(mLanguage)){
                    forgetPhonePassword();
                }else {
                    forgetMailPassword();
                }
            });
        }
        this.registerTV = this.findViewById(ResourceUtils.getRId(this, "register2"));
        if (this.registerTV != null) {
            this.registerTV.setOnClickListener(v -> {
                if("zh".equals(mLanguage)){
                    registerPhone();
                }else {
                    registerEmail();
                }
            });
        }
    }



    private void initListener(){
        this.loginIdEdit.getEditText().addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
                loginIdEdit.updateHistoryView(var1.toString());
                if(!TextUtils.isEmpty(var1.toString())){
                    String a = UnitTools.readUserPwd(OALoginActivity.this,var1.toString());
                    passwordEdit.getInputBoxWithClear().getEditText().setText(a);
                }
            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }
        });

        if(ConfigManager.getInstance().isSupportOfflineLogin()) {
            next.setOnClickListener(new NonMultiClickListener() {
                public void onMonMultiClick(View v) {
                    ProtocolFilter mFilter = new ProtocolFilter();
                    mFilter.setAction(() -> {
                        hideSoftKeyboard();
                        mProgressBar.setVisibility(View.VISIBLE);
                        OALoginActivity.this.login(v);
                    });
                    mFilter.setContext(OALoginActivity.this);
                }
            });
        } else {
            next.setOnClickListener(new NetworkCheckOnClickListener() {
                public void afterCheck(View v) {
                    OALoginActivity.this.login(v);
                }
            });
        }
    }

    private void registerPhone(){//手机注册
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showRegister(OALoginActivity.this, PhoneRegisterAliActivity.class, new LoginCallback() {
            @Override
            public void onSuccess(OpenAccountSession openAccountSession) {
                type = 1;
                LoginCallback callback = getLoginCallback();
                if (callback != null) {
                    callback.onSuccess(openAccountSession);
                }
                finishWithoutCallback();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void registerEmail(){//邮箱注册
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showEmailRegister(OALoginActivity.this,EmailRegisterAliActivity.class,getEmailRegisterCallback());
    }


    public void forgetPhonePassword() {//手机找回
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showResetPassword(this, PhoneResetAliActivity.class, new LoginCallback() {
            @Override
            public void onSuccess(OpenAccountSession openAccountSession) {
                type = 3;
                LoginCallback callback = getLoginCallback();
                if (callback != null) {
                    callback.onSuccess(openAccountSession);

                }
                finishWithoutCallback();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    public void forgetMailPassword() {//邮箱找回
        OpenAccountUIService openAccountUIService = OpenAccountSDK.getService(OpenAccountUIService.class);
        openAccountUIService.showEmailResetPassword(this, EmailResetAliActivity.class,this.getEmailResetPasswordCallback());
    }

    private EmailResetPasswordCallback getEmailResetPasswordCallback() {
        return new EmailResetPasswordCallback() {

            @Override
            public void onSuccess(OpenAccountSession session) {
                type = 4;
                LoginCallback callback = getLoginCallback();
                if (callback != null) {
                    callback.onSuccess(session);

                }
                finishWithoutCallback();
            }

            @Override
            public void onFailure(int code, String message) {

            }

            @Override
            public void onEmailSent(String email) {
                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
            }

        };
    }

    private EmailRegisterCallback getEmailRegisterCallback() {
        return new EmailRegisterCallback() {

            @Override
            public void onSuccess(OpenAccountSession session) {
                type = 2;
                LoginCallback callback = getLoginCallback();
                if (callback != null) {
                    callback.onSuccess(session);
                }
                finishWithoutCallback();
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEmailSent(String email) {
                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
            }

        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OauthService service = OpenAccountSDK.getService(OauthService.class);
        if (service != null) {
            service.authorizeCallback(requestCode, resultCode, data);
        }
    }

    @Override
    protected LoginCallback getLoginCallback() {
        mProgressBar.setVisibility(View.INVISIBLE);
        LoginCallback loginCallback = new LoginCallback() {
            @Override
            public void onSuccess(OpenAccountSession openAccountSession) {
                SpUtil.putString(getApplication(), "userId", openAccountSession.getUserId());
                OpenAccountUIServiceImpl._loginCallback.onSuccess(openAccountSession);
            }

            @Override
            public void onFailure(int i, String s) {
            }
        };
        return  type == 0? loginCallback : super.getLoginCallback();
    }

    @Override
    protected void onPwdLoginFail(int i, String s) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(OALoginActivity.this, ErrorTools.errorCode2Msg(this,i),Toast.LENGTH_LONG).show();
    }

    protected final void TRANSPARENT() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    protected void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = this.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}