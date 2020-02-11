package com.ilop.sthome.ui.activity.xmipc;

import android.util.Log;

import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.GeneralGeneral;
import com.example.xmpic.support.config.OPStorageManager;
import com.example.xmpic.support.config.StorageInfo;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.utils.FileUtils;
import com.example.xmpic.support.utils.MyUtils;
import com.ilop.sthome.ui.dialog.BaseDialog;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityPicSettingStorageBinding;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 许格 on 2019/11/14.
 */

public class ActivityGuideDeviceSetupStorage extends BaseActivity<ActivityPicSettingStorageBinding> implements OnFunDeviceOptListener {


    private final String TAG = ActivityGuideDeviceSetupStorage.class.getName();

    private FunDevice mFunDevice = null;
    private OPStorageManager mOPStorageManager;
    private float percent = 0l;
    private DecimalFormat df = new DecimalFormat("#");
    /**
     * 本界面需要获取到的设备配置信息
     */
    private final String[] DEV_CONFIGS = {
            // SD卡存储容量信息
            StorageInfo.CONFIG_NAME,

            // 录像满时停止录像或循环录像
            GeneralGeneral.CONFIG_NAME
    };

    // 设置配置信息的时候,由于有多个,通过下面的列表来判断是否所有的配置都设置完成了

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_setting_storage;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(false);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if ( null == funDevice ) {
            finish();
            return;
        }
        mFunDevice = funDevice;
    }


    @Override
    protected void initData() {
        super.initData();
        tryGetStorageConfig();// 获取报警配置信息
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 注册设备操作监听
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        mDBind.ivStorageBack.setOnClickListener(view -> finish());
        mDBind.cyclerecord.setOnClickListener(view -> trySetOverWrite(true));
        mDBind.stoprecord.setOnClickListener(view -> trySetOverWrite(false));
        mDBind.btnStorageFormat.setOnClickListener(view -> showFormatPartitionDialog());
    }


    private boolean isCurrentUsefulConfig(String configName) {
        for ( int i = 0; i < DEV_CONFIGS.length; i ++ ) {
            if ( DEV_CONFIGS[i].equals(configName) ) {
                return true;
            }
        }
        return false;
    }


    private void tryGetStorageConfig() {
        if ( null != mFunDevice ) {
            showProgressDialog();
            for ( String configName : DEV_CONFIGS ) {
                // 删除老的配置信息
                mFunDevice.invalidConfig(configName);
                // 重新搜索新的配置信息
                FunSupport.getInstance().requestDeviceConfig(
                        mFunDevice, configName);
            }
        }
    }

    private void trySetOverWrite(boolean overWrite) {
        GeneralGeneral generalInfo = (GeneralGeneral)mFunDevice.getConfig(GeneralGeneral.CONFIG_NAME);
        if ( null != generalInfo ) {
            if ( overWrite ) {
                //录像满时，循环录像
                generalInfo.setOverWrite(GeneralGeneral.OverWriteType.OverWrite);
            } else {
                //录像满时，停止录像
                generalInfo.setOverWrite(GeneralGeneral.OverWriteType.StopRecord);
            }

            showProgressDialog();

            FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, generalInfo);
        }
    }

    private void refreshStorageConfig() {
        StorageInfo storageInfo = (StorageInfo)mFunDevice.getConfig(StorageInfo.CONFIG_NAME);
        if ( null != storageInfo ) {
            int totalSpace = 0;
            int remainSpace = 0;
            List<StorageInfo.Partition> partitions = storageInfo.getPartitions();
            for ( StorageInfo.Partition partition : partitions ) {
                if ( partition.IsCurrent ) {
                    // 获取当前分区的大小
                    int partTotalSpace = MyUtils.getIntFromHex(partition.TotalSpace);
                    int partRemainSpace = MyUtils.getIntFromHex(partition.RemainSpace);


                    // 累加总大小
                    totalSpace += partTotalSpace;
                    remainSpace += partRemainSpace;
                }
            }
            Log.i(TAG,"totalSpace:"+totalSpace);
            Log.i(TAG,"remainSpace:"+remainSpace);
            if(totalSpace>0 || remainSpace > 0){
                percent = ((float) totalSpace / ((float)totalSpace+remainSpace));
                Log.i(TAG,"percent:"+percent);
                mDBind.porterDuffView.setPercent(percent);

                mDBind.percent.setText(df.format(percent*100)+"%");

                mDBind.storageHas.setText(FileUtils.FormetFileSize(totalSpace, 2));
                mDBind.storageRemain.setText(FileUtils.FormetFileSize(remainSpace, 2));
            }

        }

        GeneralGeneral generalInfo = (GeneralGeneral)mFunDevice.getConfig(GeneralGeneral.CONFIG_NAME);
        if ( null != generalInfo ) {
            if( generalInfo.getOverWrite() == GeneralGeneral.OverWriteType.OverWrite ) {
                mDBind.cyclerecord.setChecked(true);
            }
            else{
                mDBind.stoprecord.setChecked(true);
            }
        }
    }

    /**
     * 判断是否所有需要的配置都获取到了
     * @return
     */
    private boolean isAllConfigGetted() {
        for ( String configName : DEV_CONFIGS ) {
            if ( null == mFunDevice.getConfig(configName) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请求格式化指定的分区
     * @param iPartition
     * @return
     */
    private boolean requestFormatPartition(int iPartition) {
        StorageInfo storageInfo = (StorageInfo)mFunDevice.getConfig(StorageInfo.CONFIG_NAME);
        if ( null != storageInfo && iPartition < storageInfo.PartNumber ) {
            if ( null == mOPStorageManager ) {
                mOPStorageManager = new OPStorageManager();
                mOPStorageManager.setAction("Clear");
                mOPStorageManager.setSerialNo(0);
                mOPStorageManager.setType("Data");
            }

            mOPStorageManager.setPartNo(iPartition);

            return FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, mOPStorageManager);
        }

        return false;
    }

    private void showFormatPartitionDialog() {

        BaseDialog mDialog = new BaseDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                if ( requestFormatPartition(0) ) {
                    showProgressDialog();
                }
            }

            @Override
            public void onCancel() {

            }
        });
         mDialog.setMsg(getString(R.string.device_setup_storage_format_tip));
         mDialog.show();
    }


    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice,
                                         String configName, int nSeq) {
        if ( null != mFunDevice
                && funDevice.getId() == mFunDevice.getId()
                && isCurrentUsefulConfig(configName) ) {
            if ( isAllConfigGetted() ) {
                dismissProgressDialog();
            }

            refreshStorageConfig();
        }
    }


    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {
        dismissProgressDialog();
        showToast(FunError.getErrorStr(errCode));
    }


    @Override
    public void onDeviceSetConfigSuccess(final FunDevice funDevice,
                                         final String configName) {
        if ( null != mFunDevice
                && funDevice.getId() == mFunDevice.getId() ) {

            if ( OPStorageManager.CONFIG_NAME.equals(configName)
                    && null != mOPStorageManager ) {
                // 请求格式化下一个分区
                if ( !requestFormatPartition(mOPStorageManager.getPartNo() + 1) ) {

                    // 所有分区格式化完成之后,重新获取设备磁盘信息
                    tryGetStorageConfig();
                }
            } else if ( GeneralGeneral.CONFIG_NAME.equals(configName) ) {
                // 设置录像满时，选择停止录像或循环录像成功
                dismissProgressDialog();
                refreshStorageConfig();
            }
        }
    }

    @Override
    public void onDeviceSetConfigFailed(final FunDevice funDevice,
                                        final String configName, final Integer errCode) {
        showToast(FunError.getErrorStr(errCode));
        dismissProgressDialog();
    }

    @Override
    public void onDeviceChangeInfoSuccess(final FunDevice funDevice) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeviceChangeInfoFailed(final FunDevice funDevice, final Integer errCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeviceOptionSuccess(final FunDevice funDevice, final String option) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeviceOptionFailed(final FunDevice funDevice, final String option, final Integer errCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

    }


    @Override
    public void onDeviceFileListGetFailed(FunDevice funDevice) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        // 注销监听
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
        super.onDestroy();
    }
}
