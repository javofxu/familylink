package com.ilop.sthome.mvp.present;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.example.common.mvp.BasePresenterImpl;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.SpUtil;
import com.google.gson.Gson;
import com.ilop.sthome.data.greenDao.UserInfoBean;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.model.PersonalModel;
import com.ilop.sthome.mvp.model.common.onModelCallBack;
import com.ilop.sthome.utils.greenDao.UserInfoDaoUtil;
import com.siterwell.familywellplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Author skygge.
 * @Date on 2020-02-05.
 * @Dec:
 */
public class PersonalPresenter extends BasePresenterImpl<PersonalContract.IView> implements PersonalContract.IPresent {

    private Context mContext;
    private String mUrl;
    private String mAvatarUrl;
    private UserInfoBean mUserInfoBean;
    private PersonalContract.IModel mModel;
    private Handler mHandler = new Handler();

    public PersonalPresenter(Context mContext) {
        this.mContext = mContext;
        mModel = new PersonalModel();
    }

    @Override
    public void getUserInfo() {
        mUserInfoBean = UserInfoDaoUtil.getInstance().getUserInfoDao().queryAll().get(0);
        mView.showNickName(mUserInfoBean.getNickName());
        mView.showLoginName(mUserInfoBean.getLoginName());
        mView.showPhone(mUserInfoBean.getPhone());
        mView.showEmail(mUserInfoBean.getEmail());
        mView.showUserImg(mUserInfoBean.getAvatarUrl());
    }

    @Override
    public void getAvatarUrlAddress(File file) {
        mModel.toGetUploadUrl(new onModelCallBack() {
            @Override
            public void onResponse(IoTResponse response) {
                if (response.getCode() == 200){
                    try {
                        Object data = response.getData();
                        JSONObject jsonObject = (JSONObject) data;
                        mUrl = "https://" + jsonObject.getString("host");
                        String accessKey = jsonObject.getString("accessKey");
                        String policy = jsonObject.getString("policy");
                        String Signature = jsonObject.getString("signature");
                        String key = jsonObject.getString("dir");
                        mAvatarUrl = mUrl + "/"+ key;
                        onUploadImage(mUrl, accessKey, policy, Signature, file, key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
            }
        });
    }

    @Override
    public void onUploadImage(String url, String accessKey, String policy, String Signature, File file, String key) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("OSSAccessKeyId", accessKey)
                .addFormDataPart("policy", policy)
                .addFormDataPart("Signature", Signature)
                .addFormDataPart("key", key)
                .addFormDataPart("file", file.getName(), image)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call= okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(()-> mView.showToastMsg(mContext.getString(R.string.failed)));
            }

            @Override
            public void onResponse(Call call, Response response) {
                String code = String.valueOf(response.code()).substring(0,1);
                if ("2".equals(code)){
                    mHandler.post(()-> {
                        LiveDataBus.get().with("upload_success").setValue(mAvatarUrl);
                        mView.showToastMsg(mContext.getString(R.string.uploaded_avatar));
                    });
                }
            }
        });
    }

    @Override
    public void onSaveUserInfo(String nickname, String phone, String email, String avatarUrl) {
        String app_key = SpUtil.getString(mContext, "app_key");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginName" , mUserInfoBean.getLoginName());
            jsonObject.put("phone" , phone);
            jsonObject.put("email" , email);
            jsonObject.put("nickName" , nickname);
            jsonObject.put("appKey" , app_key);
            jsonObject.put("avatarUrl" , avatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        mModel.toUpdateUserInfo(mUserInfoBean.getIdentityId(), json, new onModelCallBack() {
            @Override
            public void onFailure(Exception e) {
                mHandler.post(() -> mView.showToastMsg(mContext.getString(R.string.failed)));
            }

            @Override
            public void onResponse(IoTResponse response) {
                mHandler.post(() -> {
                    mView.showToastMsg(mContext.getString(R.string.success));
                    mView.finishActivity();
                });
            }
        });
    }

}
