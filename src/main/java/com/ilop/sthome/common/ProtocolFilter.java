package com.ilop.sthome.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.ui.dialog.PrivacyPolicyDialog;
import com.ilop.sthome.utils.tools.ECPreferences;

/**
 * @author skygge
 * @date 2019-12-09.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：协议过滤器
 */
public class ProtocolFilter {

    private Context context;
    private Action action;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = ECPreferences.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!isReadProtocol()){
            PrivacyPolicyDialog mDialog = new PrivacyPolicyDialog(context, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    editor.putBoolean("isAgree", true);
                    editor.apply();
                    if (action!=null) getAction().doAction();
                    LiveDataBus.get().with("close_dialog").postValue(0);
                }

                @Override
                public void onCancel() {
                    editor.putBoolean("isAgree", false);
                    editor.apply();
                    LiveDataBus.get().with("close_dialog").postValue(0);
                }
            });
            mDialog.show();
        }else{
            if (action!=null) getAction().doAction();
        }
    }

    private boolean isReadProtocol(){
        SharedPreferences sharedPreferences = ECPreferences.getSharedPreferences();
        boolean isAgree = sharedPreferences.getBoolean("isAgree", false);
        return isAgree;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public interface Action {
        void doAction();
    }
}
