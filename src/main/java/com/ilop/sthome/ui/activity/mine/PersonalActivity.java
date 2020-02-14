package com.ilop.sthome.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.base.BasePActivity;
import com.example.common.utils.LiveDataBus;
import com.facebook.drawee.generic.RoundingParams;
import com.ilop.sthome.mvp.contract.PersonalContract;
import com.ilop.sthome.mvp.present.PersonalPresenter;
import com.ilop.sthome.ui.activity.login.EmailResetAliActivity;
import com.ilop.sthome.ui.activity.login.PhoneResetAliActivity;
import com.ilop.sthome.utils.FileUtil;
import com.linchaolong.android.imagepicker.ImagePicker;
import com.linchaolong.android.imagepicker.cropper.CropImage;
import com.linchaolong.android.imagepicker.cropper.CropImageView;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPersonalBinding;

import java.io.File;
import java.util.Locale;

/**
 * @author skygge
 * @date 2020-01-07.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：个人中心页面
 */
public class PersonalActivity extends BasePActivity<PersonalPresenter, ActivityPersonalBinding> implements PersonalContract.IView {

    private String mImageUrl;
    private String mLanguage;
    private File mFile;
    private ImagePicker mImagePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Locale locale = getResources().getConfiguration().locale;
        mLanguage = locale.getLanguage();
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresent = new PersonalPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        mImagePicker = new ImagePicker();
        mImagePicker.setCropImage(true);
        mDBind.userUpdatePhone.setVisibility("zh".equals(mLanguage) ? View.VISIBLE : View.INVISIBLE);
        mDBind.userUpdateEmail.setVisibility("zh".equals(mLanguage) ? View.INVISIBLE : View.VISIBLE);
        mDBind.userUpdatePhone.setEnabled("zh".equals(mLanguage));
        mDBind.userUpdateEmail.setEnabled(!"zh".equals(mLanguage));
        mDBind.userPhone.setEnabled(!"zh".equals(mLanguage));
        mDBind.userMail.setEnabled("zh".equals(mLanguage));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresent.getUserInfo();
        LiveDataBus.get().with("upload_success", String.class).observe(this, s -> {
            String nickname = mDBind.userNickname.getText().toString();
            String phone = mDBind.userPhone.getText().toString();
            String email = mDBind.userMail.getText().toString();
            mPresent.onSaveUserInfo(nickname, phone, email, s);
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivPersonalBack.setOnClickListener(view -> finish());
        mDBind.tvSavePersonal.setOnClickListener(v -> {
            if (mFile != null){
                mPresent.getAvatarUrlAddress(mFile);
            }else {
                String nickname = mDBind.userNickname.getText().toString();
                String phone = mDBind.userPhone.getText().toString();
                String email = mDBind.userMail.getText().toString();
                mPresent.onSaveUserInfo(nickname, phone, email, mImageUrl);
            }
            showProgressDialog();
        });
        mDBind.userImg.setOnClickListener(v -> startChooser());
        mDBind.ivUserImg.setOnClickListener(v -> startChooser());
        mDBind.userUpdatePhone.setOnClickListener(v -> skipAnotherActivity(PhoneResetAliActivity.class));
        mDBind.userUpdateEmail.setOnClickListener(v -> skipAnotherActivity(EmailResetAliActivity.class));
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void startChooser() {
        // 启动图片选择器
        mImagePicker.startChooser(this, new ImagePicker.Callback() {
            // 选择图片回调
            @Override public void onPickImage(Uri imageUri) {

            }

            // 裁剪图片回调
            @Override public void onCropImage(Uri imageUri) {
                mFile = FileUtil.uriToFile(mContext, imageUri);
                Glide.with(mContext)
                        .load(imageUri)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(mDBind.userImg);
            }

            // 自定义裁剪配置
            @Override public void cropConfig(CropImage.ActivityBuilder builder) {
                builder
                        // 是否启动多点触摸
                        .setMultiTouchEnabled(false)
                        // 设置网格显示模式
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        // 圆形/矩形
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        // 调整裁剪后的图片最终大小
                        .setRequestedSize(960, 960)
                        // 宽高比
                        .setAspectRatio(1, 1);
            }

            // 用户拒绝授权回调
            @Override public void onPermissionDenied(int requestCode, String[] permissions,
                                                     int[] grantResults) {
            }
        });
    }


    @Override
    public void showNickName(String nickname) {
        if (nickname.contains("[{")){
            mDBind.userNickname.setText(null);
        }else {
            mDBind.userNickname.setText(nickname);
        }
    }

    @Override
    public void showLoginName(String loginName) {
        mDBind.userAccount.setText(loginName);
    }

    @Override
    public void showPhone(String phone) {
        mDBind.userPhone.setText(phone);
    }

    @Override
    public void showEmail(String email) {
        mDBind.userMail.setText(email);
    }

    @Override
    public void showUserImg(String path) {
        if (path!=null){
            mImageUrl = path;
            Glide.with(mContext)
                    .load(path)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(mDBind.userImg);
        }else {
            mImageUrl = "";
            mDBind.userImg.setImageResource(R.mipmap.head_default);
        }
    }

    @Override
    public void showToastMsg(String msg) {
        dismissProgressDialog();
        showToast(msg);
    }

    @Override
    public void finishActivity() {
        dismissProgressDialog();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
