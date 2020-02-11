package com.ilop.sthome.ui.activity.xmipc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.example.common.utils.LiveDataBus;
import com.example.xmpic.support.FunPath;
import com.ilop.sthome.data.bean.Localfile;
import com.ilop.sthome.ui.fragment.LocalPicFragment;
import com.ilop.sthome.ui.fragment.LocalVideoFragment;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityLocalpicvideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许格 on 2019/12/2.
 */

public class ActivityLocalPicVideo extends BaseActivity<ActivityLocalpicvideoBinding> implements View.OnClickListener{

    private List<Fragment> fragments;// Tab页面列表
    private int currIndex=0;
    private ProgressDialog mProgressDialog;
    private List<Localfile> local_files_pic = new ArrayList<>();
    private List<Localfile> local_files_video = new ArrayList<>();
    private String path_file; //文件路径
    private int pic_or_video = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_localpicvideo;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        path_file = getIntent().getStringExtra("path");
        pic_or_video = getIntent().getIntExtra("pic_or_video",0);
        if(TextUtils.isEmpty(path_file)){
            showToast(getString(R.string.name_is_null));
            finish();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        fragments = new ArrayList<>();
        LocalPicFragment localpicFragment = new LocalPicFragment();
        fragments.add(localpicFragment);
        LocalVideoFragment localVideoFragment = new LocalVideoFragment();
        fragments.add(localVideoFragment);
        mDBind.vPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
                fragments));
        if(pic_or_video == 0){
            mDBind.vPager.setCurrentItem(0);
        }else{
            mDBind.vPager.setCurrentItem(1);
        }
        mDBind.vPager.setOnPageChangeListener(new MyOnPageChangeListener());
        getImages();
    }

    @Override
    protected void initData() {
        super.initData();
        if(pic_or_video == 0){
            setButton(0);
        }else{
            setButton(1);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivRecordBack.setOnClickListener(view -> finish());
        mDBind.showPic.setOnClickListener(this);
        mDBind.showVideo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.show_pic:
                 mDBind.vPager.setCurrentItem(0,false);
                 break;
             case R.id.show_video:
                 mDBind.vPager.setCurrentItem(1,false);
                 break;
         }
    }


    /**
     * 定义适配器
     */
    class myPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null
                    : fragmentList.get(arg0);
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }


    private void setButton(int index){
        mDBind.showVideo.setBackgroundColor(getResources().getColor(R.color.mainGround));
        mDBind.showPic.setBackgroundColor(getResources().getColor(R.color.mainGround));
        mDBind.showPicLine.setBackgroundColor(getResources().getColor(R.color.mainGround));
        mDBind.showVideoLine.setBackgroundColor(getResources().getColor(R.color.mainGround));

        switch (index){
            case 0:
                mDBind.showPic.setBackgroundColor(getResources().getColor(R.color.text_color_selected));
                mDBind.showPicLine.setBackgroundColor(getResources().getColor(R.color.text_color_selected));
                break;
            case 1:
                mDBind.showVideo.setBackgroundColor(getResources().getColor(R.color.text_color_selected));
                mDBind.showVideoLine.setBackgroundColor(getResources().getColor(R.color.text_color_selected));
                break;
        }
    }


    /**
     * 为选项卡绑定监听器
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int index) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            currIndex = index;
            setButton(currIndex);
        }
    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showToast(getString(R.string.no_extern_storage));
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.loading_press));

        new Thread(() -> {
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = ActivityLocalPicVideo.this
                    .getContentResolver();
            // 只查询jpeg和png的图片
            Cursor  mCursor = mContentResolver.query(mImageUri, null,
                    "("+MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?"+ ") and "
                            + MediaStore.Images.Media.DATA +" like ?",
                    new String[] { "image/jpeg", "image/png" ,"%" + FunPath.PATH_CAPTURE_TEMP+File.separator+path_file+"%"},
                    MediaStore.Images.Media.DATE_MODIFIED  + " DESC");

            Log.i(TAG, mCursor.getCount() + "");
            while (mCursor.moveToNext())
            {
                // 获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                String modify = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                File flie = new File(path);
                Localfile localfile = new Localfile();
                localfile.setFilename(flie.getName());
                localfile.setFilepath(path);
                localfile.setModifytime(modify);
                local_files_pic.add(localfile);
                // 拿到第一张图片的路径
            }
            mCursor.close();
            // 通知Handler扫描图片完成
            mHandler.sendEmptyMessage(0x110);
        }).start();

    }

    /**
     * 利用ContentProvider扫描手机中的的录像
     */
    private void getVideos() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showToast(getString(R.string.no_extern_storage));
            return;
        }

        new Thread(() -> {
            Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = ActivityLocalPicVideo.this
                    .getContentResolver();
            // 只查询jpeg和png的图片
            Cursor  mCursor = mContentResolver.query(mImageUri, null,
                     MediaStore.Video.Media.DATA +" like ?",
                    new String[] {  "%" + FunPath.PATH_VIDEO+File.separator+path_file+"%"},
                    MediaStore.Video.Media.DATE_MODIFIED  + " DESC");

            Log.i(TAG, mCursor.getCount() + "");
            while (mCursor.moveToNext())
            {
                // 获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Video.Media.DATA));
                String modify = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                File flie = new File(path);
                Localfile localfile = new Localfile();
                localfile.setFilename(flie.getName());
                localfile.setFilepath(path);
                localfile.setModifytime(modify);
                local_files_video.add(localfile);
                // 拿到第一张图片的路径
            }
            mCursor.close();
            // 通知Handler扫描图片完成
            mHandler.sendEmptyMessage(0x111);
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what) {
                case 0x110:
                    getVideos();
                    break;
                case 0x111:
                    mProgressDialog.dismiss();
                    LiveDataBus.get().with("local_pic").setValue(local_files_pic);
                    LiveDataBus.get().with("local_video").setValue(local_files_video);
                    break;
            }

        }
    };

}
