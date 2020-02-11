package com.ilop.sthome.utils.tools;

import android.content.Context;

import com.siterwell.familywellplus.R;


/**
 * Created by jishu0001 on 2016/8/30.
 */
public class ErrorTools {
    private Context context;
    public ErrorTools(Context context){
        this.context = context;
    }


    /**
     * 错误码转换到错误信息！
     *
     * @param errorCode 错误码
     * @return 错误信息
     */
    public static String errorCode2Msg(Context context,int errorCode) {
        switch (errorCode) {
            case 4027:
                return context.getResources().getString(R.string.account_or_password_error);

          /*  case 400014:
                return "密码重置失败";*/
            default:
                // return String.valueOf(errorCode);
                return context.getResources().getString(R.string.server_exception_try_again) + errorCode;
        }
    }

}
