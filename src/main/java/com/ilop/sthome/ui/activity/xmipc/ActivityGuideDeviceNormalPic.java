package com.ilop.sthome.ui.activity.xmipc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.basic.G;
import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunPath;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceFileListener;
import com.example.xmpic.support.config.DevCmdOPFileQueryJP;
import com.example.xmpic.support.config.DevCmdOPSCalendar;
import com.example.xmpic.support.config.SameDayPicInfo;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunFileData;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceNormalPicBinding;

import java.io.File;


public class ActivityGuideDeviceNormalPic extends BaseActivity<ActivityDeviceNormalPicBinding> implements OnFunDeviceFileListener {

    private FunDevice mFunDevice = null;
    private int mPosition = 0;
    private FunFileData mImageInfo = null;
    private boolean isPreview = false;
    private boolean isFromSportCamera = false;
    private String path = FunPath.getTempPicPath();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_normal_pic;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        mFunDevice = FunSupport.getInstance().findDeviceById(devId);

        mPosition = getIntent().getIntExtra("position", 0);
        isPreview = getIntent().getBooleanExtra("preview", false);
        isFromSportCamera = getIntent().getBooleanExtra("fromSportCamera", false);
    }

    @Override
    protected void initView() {
        super.initView();
        FunSupport.getInstance().registerOnFunDeviceFileListener(this);
        tryToLoadPic();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivPictureBack.setOnClickListener(view -> finish());
        mDBind.btnDownload.setOnClickListener(view -> downloadToPhone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FunSupport.getInstance().removeOnFunDeviceFileListener(this);
    }


    private void tryToLoadPic(){
        showProgressDialog();
        if (isPreview) {
            DevCmdOPFileQueryJP cmdOPFileQueryJP = (DevCmdOPFileQueryJP)
                    mFunDevice.getConfig(DevCmdOPFileQueryJP.CONFIG_NAME);
            mImageInfo = cmdOPFileQueryJP.getFileData().get(mPosition);
        } else if (isFromSportCamera) {
            DevCmdOPSCalendar opsCalendar = (DevCmdOPSCalendar) mFunDevice.checkConfig(DevCmdOPSCalendar.CONFIG_NAME);
            int pos = mPosition;
            for (SameDayPicInfo picInfo : opsCalendar.getData()) {
                if (pos >= 0 && pos < picInfo.getPicNum()) {
                    mImageInfo = picInfo.getPicData(pos);
                    break;
                } else if (pos >= picInfo.getPicNum()) {
                    pos -= picInfo.getPicNum();
                }
            }
        } else {
            mImageInfo = mFunDevice.mDatas.get(mPosition);
        }

        byte[] data = null;
        if (mImageInfo != null) {
            data = G.ObjToBytes(mImageInfo.getFileData());
        } else {
            showToast("Error");
            finish();
            return;
        }

        if (FunPath.isFileExists(path) >= 0) {
            FunPath.deleteFile(path);
        }

        FunSupport.getInstance().requestDeviceDownloadByFile(mFunDevice, data, path, mPosition);
    }

    private void downloadToPhone() {
        String fileName = mImageInfo.getBeginDateStr() + "_" + mImageInfo.getBeginTimeStr() + ".jpg";
        String newPath = FunPath.PATH_PHOTO + File.separator + fileName;
        if (mImageInfo != null && FunPath.isFileExists(path) > 0) {
            File oldFile = new File(path);
            File newFile = new File(newPath);
            if (oldFile.renameTo(newFile)) {
                String str = getString(R.string.device_sport_camera_download_success);
                showToast(str + FunPath.PATH_PHOTO);
            }
        } else {
            if (FunPath.isFileExists(newPath) > 0) {
                showToast(getString(R.string.device_sport_camera_pic_existed));
            } else{
                showToast(getString(R.string.device_sport_camera_load_first));
            }
        }
    }

    @Override
    public void onDeviceFileDownCompleted(FunDevice funDevice, String path, int nSeq) {
        dismissProgressDialog();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        mDBind.picNormalImg.setImageBitmap(bitmap);
    }

    @Override
    public void onDeviceFileDownProgress(int totalSize, int progress, int nSeq) {

    }

    @Override
    public void onDeviceFileDownStart(boolean isStartSuccess, int nSeq) {

    }
}