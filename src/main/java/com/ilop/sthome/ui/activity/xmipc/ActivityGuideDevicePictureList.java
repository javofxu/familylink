package com.ilop.sthome.ui.activity.xmipc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.common.base.BaseActivity;
import com.example.common.view.refresh.CustomRefreshView;
import com.example.xmpic.support.FunPath;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.config.DevCmdOPSCalendar;
import com.example.xmpic.support.config.OPCompressPic;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunFileData;
import com.ilop.sthome.ui.adapter.camera.DeviceCameraPicAdapter;
import com.lib.IFunSDKResult;
import com.lib.MsgContent;
import com.lib.SDKCONST;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.lib.sdk.struct.H264_DVR_FINDINFO;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDevicePictureListBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author skygge
 * @date 2019-12-04.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：远程设备图片列表
 */

public class ActivityGuideDevicePictureList extends BaseActivity<ActivityDevicePictureListBinding> implements
        AdapterView.OnItemClickListener, OnFunDeviceOptListener,IFunSDKResult{
    private final String TAG = "PictureList";
    private FunDevice mFunDevice = null;
    private String mFileType = null;
    private H264_DVR_FINDINFO findInfo = null;
    private int pictureType = SDKCONST.SDK_File_Type.SDK_PIC_ALL;
    private List<FunFileData> mDatas = new ArrayList<FunFileData>();
    private DeviceCameraPicAdapter mDeviceCameraPicAdapter;
    private Calendar calendar;
    private boolean flag = true;
    private final int REQUEST_SELECT_DATE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_picture_list;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        mFileType = getIntent().getStringExtra("FILE_TYPE");
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if (null == funDevice) {
            finish();
            return;
        } else {
            mFunDevice = funDevice;
        }
        if (null == mFileType) mFileType = "jpg";
    }

    @Override
    protected void initView() {
        super.initView();
        FunPath.onCreateSptTempPath(mFunDevice.getSerialNo());
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        calendar = Calendar.getInstance();
        mDeviceCameraPicAdapter = new DeviceCameraPicAdapter(this, mDBind.pictureList.getRecyclerView(), mFunDevice, mDatas);
        mDBind.pictureList.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDBind.pictureList.getRecyclerView().setAdapter(mDeviceCameraPicAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onSearchPicture(calendar.getTime(), 0);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivPictureBack.setOnClickListener(view -> finish());
        mDBind.ivPictureTime.setOnClickListener(view -> {
            Intent intent = new Intent(this, DateSelectActivity.class);
            intent.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
            intent.putExtra("Date",calendar);
            startActivityForResult(intent,REQUEST_SELECT_DATE);
        });
        mDBind.pictureList.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                onSearchMorePicture(calendar.getTime(), 0);
                mDBind.pictureList.complete();
            }

            @Override
            public void onLoadMore() {
                onSearchMorePicture(calendar.getTime(), 1);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int type;
        FunFileData imageInfo = mDatas.get(position);

        if (imageInfo != null) {
            type = imageInfo.getFileType();
        } else {
            type = 0;
        }

        Intent it = null;
        if (type == SDKCONST.PicFileType.PIC_BURST_SHOOT
            || type == SDKCONST.PicFileType.PIC_TIME_LAPSE) {
            //it = new Intent(this, ActivityGuideDevicePicBrowser.class);
        } else {
            it = new Intent(this, ActivityGuideDeviceNormalPic.class);
        }
        it.putExtra("position", position);
        it.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
        startActivity(it);
    }

    private void onSearchPicture(Date date, int flag) {
        showProgressDialog();
        findInfo = new H264_DVR_FINDINFO();
        findInfo.st_1_nFileType = pictureType;
        initSearchInfo(findInfo, date, 0, flag);
        FunSupport.getInstance().requestDeviceFileList(mFunDevice, findInfo);
    }
    
    private void onSearchMorePicture(Date date, int flag){
    	findInfo = new H264_DVR_FINDINFO();
    	findInfo.st_1_nFileType = pictureType;
    	initSearchInfo(findInfo, date, 0, flag);
    	FunSupport.getInstance().requestDeviceFileList(mFunDevice, findInfo);
    }

    private void initSearchInfo(H264_DVR_FINDINFO info, Date date, int channel,
                                int flag) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        info.st_2_startTime.st_0_dwYear = c.get(Calendar.YEAR);
        info.st_2_startTime.st_1_dwMonth = c.get(Calendar.MONTH) + 1;
        info.st_2_startTime.st_2_dwDay = c.get(Calendar.DATE);
        if (flag == 0) {
        	info.st_2_startTime.st_3_dwHour = 0;
        	info.st_2_startTime.st_4_dwMinute = 0;
        	info.st_2_startTime.st_5_dwSecond = 0;
		}else {
			info.st_2_startTime.st_3_dwHour = c.get(Calendar.HOUR_OF_DAY);
			info.st_2_startTime.st_4_dwMinute = c.get(Calendar.MINUTE);
			info.st_2_startTime.st_5_dwSecond = c.get(Calendar.SECOND);
		}
        info.st_3_endTime.st_0_dwYear = c.get(Calendar.YEAR);
        info.st_3_endTime.st_1_dwMonth = c.get(Calendar.MONTH) + 1;
        info.st_3_endTime.st_2_dwDay = c.get(Calendar.DATE);
        info.st_3_endTime.st_3_dwHour = 23;
        info.st_3_endTime.st_4_dwMinute = 59;
        info.st_3_endTime.st_5_dwSecond = 59;
        info.st_0_nChannelN0 = channel;
    }


    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {
    	
    	if (configName.equals(DevCmdOPSCalendar.CONFIG_NAME)) {
			DevCmdOPSCalendar calendar = (DevCmdOPSCalendar) funDevice.getConfig(DevCmdOPSCalendar.CONFIG_NAME);
			List<String> dates = new ArrayList<>();
			for (int i = 0; i < calendar.getData().size(); i++) {
				dates.add(calendar.getData().get(i).getDispDate());
			}
			new AlertDialog.Builder(this)
			.setTitle("Dates")
			.setItems(dates.toArray(new String[dates.size()]), null)
			.setNegativeButton("OK", null)
			.show();
		}
    	mFunDevice.invalidConfig(DevCmdOPSCalendar.CONFIG_NAME);
    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceSetConfigSuccess(final FunDevice funDevice,
    		final String configName) {

    }

    @Override
    public void onDeviceSetConfigFailed(final FunDevice funDevice,
    		final String configName, final Integer errCode) {

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
        Log.i(TAG,"onDeviceFileListChanged1");
    }

    private void notifyDataSetChanged() {
        if (null != mDeviceCameraPicAdapter) {
            mDeviceCameraPicAdapter.notifyDataSetChanged();
            flag = true;
        }
    }
    
    private void notifyDataSetInvalidated() {
    	if (null != mDeviceCameraPicAdapter) {
    		mDeviceCameraPicAdapter.notifyDataSetChanged();
            flag = true;
    	}
    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {
        Log.i(TAG,"onDeviceFileListChanged2");
        dismissProgressDialog();

        if (null != funDevice && null != mFunDevice && funDevice.getId() == mFunDevice.getId()) {
        	if (flag) {
        		mDatas.clear();
	            for (H264_DVR_FILE_DATA data : datas) {
	                FunFileData funFileData = new FunFileData(data, new OPCompressPic());
	                mDatas.add(funFileData);
	            }
	            mFunDevice.mDatas = mDatas;
	            notifyDataSetInvalidated();
        	}else {
        		 for (H264_DVR_FILE_DATA data : datas) {
 	                FunFileData funFileData = new FunFileData(data, new OPCompressPic());
 	                mDatas.add(funFileData);
 	            }
 	
 	            mFunDevice.mDatas = mDatas;
 	            	notifyDataSetChanged();
			}
        	if (mDatas.size()>0){
                FunFileData data = mDatas.get(mDatas.size()-1);
                calendar.setTime(data.getBeginDate());
            } else {
                mDatas.size();
                showToast(getString(R.string.device_pic_list_empty));
                mDBind.pictureList.complete();
                mDBind.pictureList.setEmptyView(getString(R.string.device_pic_list_empty), 0);
            }
        }


    }

	@Override
	public int OnFunSDKResult(Message arg0, MsgContent arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onDeviceFileListGetFailed(FunDevice funDevice) {
		// TODO Auto-generated method stub
        Log.i(TAG,"onDeviceFileListGetFailed22");
        Toast.makeText(this,"No Files!!",Toast.LENGTH_SHORT).show();
        mDBind.pictureList.complete();
        mDBind.pictureList.setEmptyView(getString(R.string.device_pic_list_empty), 0);
        dismissProgressDialog();
	}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;

        if(requestCode==REQUEST_SELECT_DATE){
            int result_year = data.getExtras().getInt("year");
            int result_month = data.getExtras().getInt("month");
            int result_day   = data.getExtras().getInt("day");

            calendar.set(result_year, result_month, result_day);
            onSearchPicture(calendar.getTime(), 0);

        }

    }

    @Override
    protected void onDestroy() {
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
        if (null != mDeviceCameraPicAdapter) {
            mDeviceCameraPicAdapter.release();
        }
        super.onDestroy();
    }

}
