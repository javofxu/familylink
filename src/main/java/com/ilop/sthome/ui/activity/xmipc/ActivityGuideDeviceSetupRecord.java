package com.ilop.sthome.ui.activity.xmipc;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.common.base.BaseActivity;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunLog;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.CloudStorage;
import com.example.xmpic.support.config.RecordParam;
import com.example.xmpic.support.config.RecordParamEx;
import com.example.xmpic.support.config.SimplifyEncode;
import com.example.xmpic.support.models.FunDevType;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.utils.DeviceConfigType;
import com.example.xmpic.support.utils.MyUtils;
import com.lib.SDKCONST;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceSetupRecordBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 许格 on 2019/11/21.
 */

public class ActivityGuideDeviceSetupRecord extends BaseActivity<ActivityDeviceSetupRecordBinding> implements OnFunDeviceOptListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {


    private FunDevice mFunDevice = null;

    /**
     * 本界面需要获取到的设备配置信息
     */
    private final String[] DEV_CONFIGS_FOR_CAMARA = {
            // 获取参数:SimplifyEncode -> audioEable
            SimplifyEncode.CONFIG_NAME,

            // 获取参数:RecordParam
            RecordParam.CONFIG_NAME,

            RecordParamEx.CONFIG_NAME,
    };

    private final String[] DEV_CONFIGS_FOR_CHANNELS = {
            // 获取参数:RecordParam
            RecordParam.CONFIG_NAME,
    };

    private String[] DEV_CONFIGS = DEV_CONFIGS_FOR_CHANNELS;

    // 设置配置信息的时候,由于有多个,通过下面的列表来判断是否所有的配置都设置完成了
    private List<String> mSettingConfigs = new ArrayList<String>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_setup_record;
    }


    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if ( null == funDevice ) {
            finish();
            return;
        }
        mFunDevice = funDevice;
    }

    @Override
    protected void initView() {
        super.initView();
        String[] recordMode = getResources().getStringArray(R.array.device_setup_record_mode_values);
        ArrayAdapter<String> adapterRecordMode = new ArrayAdapter<>(this, R.layout.item_right_spinner, recordMode);
        adapterRecordMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDBind.luxiangtype.getSpinner().setAdapter(adapterRecordMode);
        Integer[] defValues = {1, 0, 2};
        mDBind.luxiangtype.getSpinner().setTag(defValues);
        mDBind.luxiangtype.getSpinner().setOnItemSelectedListener(this);

        try {
            if (mFunDevice.channel.nChnCount == 1) {
                DEV_CONFIGS = DEV_CONFIGS_FOR_CAMARA;
            }
        }catch (NullPointerException e){
            finish();
            return;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        // 获取报警配置信息
        tryGetRecordConfig();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivParamBack.setOnClickListener(view -> finish());
        mDBind.ivParamSetting.setOnClickListener(view -> trySaveRecordConfig());
        mDBind.yulu.getSeekBar().setOnSeekBarChangeListener(this);
        mDBind.luxiangshichang.getSeekBar().setOnSeekBarChangeListener(this);
        // 注册设备操作监听
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
    }

    private int getSpinnerValue(Spinner spinner) {
        Integer[] values = (Integer[])spinner.getTag();
        int position = spinner.getSelectedItemPosition();
        if ( position >= 0 && position < values.length ) {
            return values[position];
        }
        return 0;
    }

    private int getSpinnerPosition(Spinner spinner, int value) {
        Integer[] values = (Integer[])spinner.getTag();
        for ( int i = 0; i < values.length; i ++ ) {
            if ( values[i] == value ) {
                return i;
            }
        }
        return 0;
    }

    private boolean isCurrentUsefulConfig(String configName) {
        for ( int i = 0; i < DEV_CONFIGS.length; i ++ ) {
            if ( DEV_CONFIGS[i].equals(configName) ) {
                return true;
            }
        }
        return false;
    }

    private String getStringRecordMode(int i) {
        if (i == 0) {
            return "ClosedRecord";
        } else if (i == 1) {
            return "ManualRecord";
        } else {
            return "ConfigRecord";
        }
    }

    private int getIntRecordMode(String s) {
        if (s.equals("ClosedRecord")) {
            return 0;
        } else if (s.equals("ManualRecord")) {
            return 1;
        } else {
            return 2;
        }
    }


    private void refreshRecordConfig() {

        SimplifyEncode simplifyEncode = (SimplifyEncode)mFunDevice.getConfig(SimplifyEncode.CONFIG_NAME);
        if ( null != simplifyEncode ) {
            mDBind.luxiangauto.setChecked(simplifyEncode.mainFormat.AudioEnable);
        }

        RecordParam recordParam = (RecordParam)mFunDevice.getConfig(RecordParam.CONFIG_NAME);
        if ( null != recordParam ) {

            mDBind.yulu.setDetailText(recordParam.getPreRecordTime() + getString(R.string.device_setup_record_second));
            mDBind.yulu.getSeekBar().setProgress(recordParam.getPreRecordTime());
            mDBind.luxiangshichang.setDetailText(recordParam.getPacketLength() + getString(R.string.device_setup_record_minute));
            mDBind.luxiangshichang.getSeekBar().setProgress(recordParam.getPacketLength());

            if(getIntRecordMode(recordParam.getRecordMode()) == 2)
            {
                boolean bNoramlRecord = MyUtils.getIntFromHex(recordParam.mask[0][0]) == 7;
                FunLog.i("setup record", "TTT--->" + recordParam.recordMode + "bNoramlRecord"
                        + (bNoramlRecord ? 1 : 2));
                mDBind.luxiangtype.getSpinner().setSelection(getSpinnerPosition(mDBind.luxiangtype.getSpinner(), bNoramlRecord ? 1 : 2));
            }
            else
            {
                mDBind.luxiangtype.getSpinner().setSelection(getSpinnerPosition(mDBind.luxiangtype.getSpinner(), getIntRecordMode(recordParam.getRecordMode())));
            }
        }

    }

    private void tryGetRecordConfig() {
        if ( null != mFunDevice ) {

            showProgressDialog();

            for ( String configName : DEV_CONFIGS ) {

                // 删除老的配置信息
                mFunDevice.invalidConfig(configName);

                if(mFunDevice.getDevType() == FunDevType.EE_DEV_SMALLEYE || configName != CloudStorage.CONFIG_NAME)
                {
                    // 重新搜索新的配置信息
                    if (contains(DeviceConfigType.DeviceConfigCommon, configName)) {
                        FunSupport.getInstance().requestDeviceConfig(mFunDevice,
                                configName);
                    }else if (contains(DeviceConfigType.DeviceConfigByChannel, configName)) {
                        FunSupport.getInstance().requestDeviceConfig(mFunDevice, configName, mFunDevice.CurrChannel);
                    }
                }
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
                if(mFunDevice.getDevType() == FunDevType.EE_DEV_SMALLEYE || configName != CloudStorage.CONFIG_NAME)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void trySaveRecordConfig() {
        boolean beSimplifyEncodeChanged = false;
        SimplifyEncode simplifyEncode = (SimplifyEncode)mFunDevice.getConfig(SimplifyEncode.CONFIG_NAME);
        if ( null != simplifyEncode ) {

            if ( simplifyEncode.mainFormat.AudioEnable
                    != mDBind.luxiangauto.isChecked() ) {

                simplifyEncode.mainFormat.AudioEnable = mDBind.luxiangauto.isChecked();

                beSimplifyEncodeChanged = true;
            }
        }

        boolean beRecordParamChanged = false;
        RecordParam recordParam = (RecordParam)mFunDevice.getConfig(RecordParam.CONFIG_NAME);
        if ( null != recordParam )
        {
            if(mDBind.yulu.getSeekBar().getProgress() != recordParam.getPreRecordTime())
            {
                recordParam.setPreRecordTime(mDBind.yulu.getSeekBar().getProgress());
                beRecordParamChanged = true;
            }

            if(mDBind.luxiangshichang.getSeekBar().getProgress() != recordParam.getPacketLength())
            {
                recordParam.setPacketLength(mDBind.luxiangshichang.getSeekBar().getProgress());
                beRecordParamChanged = true;
            }

            int mode = getSpinnerValue(mDBind.luxiangtype.getSpinner());

//			if(!getStringRecordMode(mode).equals(recordParam.getRecordMode()))
//			{
            recordParam.recordMode = getStringRecordMode(mode == 1 ? 2 : mode);
            // 如果是联动配置的话，把普通录像去掉
            for (int i = 0; i < SDKCONST.NET_N_WEEKS; ++i) {
                recordParam.mask[i][0] = MyUtils.getHexFromInt(mode == 2 ? 6 : 7);
            }
            beRecordParamChanged = true;
//			}
        }


        if ( beSimplifyEncodeChanged
                || beRecordParamChanged) {
            showProgressDialog();

            // 保存SimplifyEncode
            if ( beSimplifyEncodeChanged ) {
                synchronized (mSettingConfigs) {
                    mSettingConfigs.add(simplifyEncode.getConfigName());
                }

                FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, simplifyEncode);
            }

            if ( beRecordParamChanged ) {
                synchronized (mSettingConfigs) {
                    mSettingConfigs.add(recordParam.getConfigName());
                }

                FunSupport.getInstance().requestDeviceSetConfig(mFunDevice, recordParam);
            }

        } else {
            Toast.makeText(this,getResources().getString(R.string.device_alarm_no_change),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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

            refreshRecordConfig();
        }
    }


    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

        Toast.makeText(this, FunError.getErrorStr(errCode),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDeviceSetConfigSuccess(final FunDevice funDevice,
                                         final String configName) {
        if ( null != mFunDevice
                && funDevice.getId() == mFunDevice.getId() ) {
            synchronized (mSettingConfigs) {
                mSettingConfigs.remove(configName);

                if ( mSettingConfigs.size() == 0 ) {
                    dismissProgressDialog();
                }
            }

            //refreshRecordConfig();
        }
    }


    @Override
    public void onDeviceSetConfigFailed(final FunDevice funDevice,
                                        final String configName, final Integer errCode) {
        Toast.makeText(this, FunError.getErrorStr(errCode),Toast.LENGTH_LONG).show();
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
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {


        if(seekBar.equals(mDBind.yulu.getSeekBar()))
        {
            mDBind.yulu.setDetailText(progress + getString(R.string.device_setup_record_second));
        }
        else if(seekBar.equals(mDBind.luxiangshichang.getSeekBar()))
        {
            mDBind.luxiangshichang.setDetailText(progress + getString(R.string.device_setup_record_minute));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onDeviceFileListGetFailed(FunDevice funDevice) {
        // TODO Auto-generated method stub

    }

    /**
     *  判断某个字符串是否存在于数组中
     *  用来判断该配置是否通道相关
     *  @param stringArray 原数组
     *  @param source 查找的字符串
     *  @return 是否找到
     */
    public static boolean contains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);

        // 利用list的包含方法,进行判断
        return tempList.contains(source);
    }


    @Override
    protected void onDestroy() {
        // 注销监听
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);

        super.onDestroy();
    }
}
