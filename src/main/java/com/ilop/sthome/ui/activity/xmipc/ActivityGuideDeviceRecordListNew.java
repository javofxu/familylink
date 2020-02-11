package com.ilop.sthome.ui.activity.xmipc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.common.base.BaseActivity;
import com.example.common.base.OnCallBackToRefresh;
import com.example.xmpic.support.FunError;
import com.example.xmpic.support.FunPath;
import com.example.xmpic.support.FunSupport;
import com.example.xmpic.support.OnFunDeviceOptListener;
import com.example.xmpic.support.OnFunDeviceRecordListener;
import com.example.xmpic.support.config.OPCompressPic;
import com.example.xmpic.support.models.FunDevRecordFile;
import com.example.xmpic.support.models.FunDevice;
import com.example.xmpic.support.models.FunFileData;
import com.example.xmpic.support.utils.UIFactory;
import com.ilop.sthome.ui.dialog.BaseIamgeDialog;
import com.ilop.sthome.ui.dialog.TipDialog;
import com.ilop.sthome.utils.DateUtil;
import com.ilop.sthome.utils.tools.SystemTintManager;
import com.ilop.sthome.utils.tools.UnitTools;
import com.ilop.sthome.view.FiterImageView;
import com.ilop.sthome.view.TimerHistoryHorizonScrollView;
import com.lib.SDKCONST;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.lib.sdk.struct.H264_DVR_FINDINFO;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityDeviceRecordListHorizonBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 许格 on 2019/11/19.
 */

public class ActivityGuideDeviceRecordListNew extends BaseActivity<ActivityDeviceRecordListHorizonBinding> implements View.OnClickListener,OnFunDeviceRecordListener
        ,OnFunDeviceOptListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener
        ,MediaPlayer.OnErrorListener, TimerHistoryHorizonScrollView.ScrollViewListener {

    private static String TAG = ActivityGuideDeviceRecordListNew.class.getName();
    private List<Map<String,Object>> playlist;
    private List<FunFileData> files;
    private FunFileData currfiles;
    private SystemTintManager systemTintManager;
    private final int REQUEST_SELECT_DATE  = 1;
    private final int MESSAGE_AUTO_HIDE_CONTROL_BAR = 0x103;
    private final int MESSAGE_TOAST_SCREENSHOT_PREVIEW = 0x104;
    private final int MESSAGE_SETVIDEOAT = 0x105;
    private final int MESSAGE_SETVIDEOIN = 0x106;
    private final int MESSAGE_REFRESH_PROGRESS = 0x107;
    private FunDevice mFunDevice = null;
    private Calendar calendar;
    private boolean srcoll_status = false; //若为true表示进度条在被手指滑动
    private float curr = -1f;  //若为true表示进度条在中间；并且不是同一段录像
    private AtomicBoolean isSounded = new AtomicBoolean(true);
    private final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    private int PER_ITEM_SCROLL_WIDTH = 0;
    private String mCameraName;
    private TipDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_record_list_horizon;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        mCameraName = getIntent().getStringExtra("FUN_DEVICE_NAME");
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if (devId==0) {
            funDevice = FunSupport.getInstance().mCurrDevice;
        }
        if (null == funDevice) {
            finish();
        } else {
            mFunDevice = funDevice;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        calendar = Calendar.getInstance();
        playlist = new ArrayList<>();
        files = new ArrayList<>();
        systemTintManager = new SystemTintManager(this);
        mDBind.luxiangtime.setVisibility(View.GONE);
        mDBind.loading.setVisibility(View.GONE);
        mDBind.scrollView.setHandler(handler);
        PER_ITEM_SCROLL_WIDTH = 20 + UnitTools.getScreenWidth(this)/3;
        // 1. 注册录像文件搜索结果监听 - 在搜索完成后以回调的方式返回
        FunSupport.getInstance().registerOnFunDeviceRecordListener(this);
        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mDBind.devname.setText(mCameraName);
        showAsPortrait();
        onSearchFile();
        FunPath.onCreateSptTempPath(mFunDevice.getSerialNo());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivRecordBack.setOnClickListener(view -> finish());
        mDBind.ivCalendar.setOnClickListener(this);
        mDBind.scrollView.setOnScrollStateChangedListener(this);
        mDBind.funRecVideoView.setOnPreparedListener(this);
        mDBind.funRecVideoView.setOnErrorListener(this);
        mDBind.funRecVideoView.setOnCompletionListener(this);
        mDBind.funRecVideoView.setOnTouchListener(new VideoTouch());

        mDBind.btnPlay.setOnClickListener(this);
        mDBind.btnvoice.setOnClickListener(this);
        mDBind.btnCapture.setOnClickListener(this);
        mDBind.btnScreenRatio.setOnClickListener(this);
        mDBind.btnPlay2.setOnClickListener(this);
        mDBind.btnvoice2.setOnClickListener(this);
        mDBind.btnCapture2.setOnClickListener(this);
        mDBind.btnScreenRatio2.setOnClickListener(this);
    }


      @SuppressLint("HandlerLeak")
      Handler handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              switch (msg.what){
                  case MESSAGE_AUTO_HIDE_CONTROL_BAR:
                      hideVideoControlBar();
                  break;
                  case MESSAGE_TOAST_SCREENSHOT_PREVIEW:
                      String path = (String) msg.obj;
                      toastScreenShotPreview(path);
                  break;
                  case MESSAGE_SETVIDEOAT:
                      currfiles = (FunFileData)msg.obj;
                      playRecordVideoByFile(currfiles);
                      break;
                  case MESSAGE_REFRESH_PROGRESS:
                      refreshProgress();
                      resetProgressInterval();
                  break;
                  case MESSAGE_SETVIDEOIN:
                      Map<String,Object> data = (Map<String,Object>)msg.obj;
                      FunFileData currfiles2= (FunFileData)data.get("video");
                      float  curr1 = (float)data.get("current");
                      if(currfiles!=null && currfiles2.getBeginTimeStr().equals(currfiles.getBeginTimeStr())){
                          seekRecordVideo(currfiles2,curr1);
                      }else{
                          currfiles = currfiles2;
                          curr = curr1;
                          playRecordVideoByFile(currfiles2);
                      }
                  break;
              }
          }
      };


    private void intVideoHorizon(){
        mDBind.lv.removeAllViews();
        for (int i=0;i<files.size();i++){
            RelativeLayout view2 = new RelativeLayout(this);
            LinearLayout.LayoutParams layoutParams3= new LinearLayout.LayoutParams(UnitTools.getScreenWidth(this)/3, UnitTools.getScreenWidth(this)*2/9);
            layoutParams3.leftMargin = 10;
            layoutParams3.rightMargin = 10;
            view2.setLayoutParams(layoutParams3);

            mDBind.lv.addView(view2);

            FiterImageView imageView2 = new FiterImageView(this);
            RelativeLayout.LayoutParams layoutParams4= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
            imageView2.setLayoutParams(layoutParams4);

            if(currfiles.getBeginTimeStr().equals(files.get(i).getBeginTimeStr())){
                imageView2.setImageResource(R.mipmap.u2);
            }else{
                imageView2.setImageResource(R.mipmap.u2);
            }


            imageView2.setTag(i);
            view2.addView(imageView2);

            TextView textView2 = new TextView(this);
            RelativeLayout.LayoutParams layoutParams5= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams5.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            textView2.setLayoutParams(layoutParams5);
            textView2.setText(files.get(i).getBeginTimeStr());
            Log.i(TAG, "intVideoHorizon: "+files.get(i).getBeginTimeStr());
            view2.addView(textView2);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int d = (int)view.getTag();
                    for(int i=0;i<files.size();i++){
                        ((FiterImageView)((RelativeLayout)mDBind.lv.getChildAt(i)).getChildAt(0)).setImageResource(R.mipmap.u2);
                    }

                    ((FiterImageView)view).setImageResource(R.mipmap.u2);
                    Message message = new Message();
                    message.what = MESSAGE_SETVIDEOAT;
                    message.obj = files.get(d);
                    handler.sendMessageDelayed(message, 10);
                }
            });
        }
    }

    /**
     * 显示截图成功对话框
     * @param path
     */
    private void toastScreenShotPreview(final String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        BaseIamgeDialog mDialog = new BaseIamgeDialog(mContext, new OnCallBackToRefresh() {
            @Override
            public void onConfirm() {
                File file = new File(path);
                File imgPath = new File(FunPath.PATH_CAPTURE_TEMP + File.separator+mFunDevice.getDevSn()+File.separator + file.getName());
                if (imgPath.exists()) {
                    String d = String.format(getResources().getString(R.string.device_socket_capture_save_success),path);
                    showToast(d);
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);   //, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                Uri uri = Uri.fromFile(new File(path));
                intent.setData(uri);
                sendBroadcast(intent);
            }

            @Override
            public void onCancel() {
                FunPath.deleteFile(path);
                showToast(getString(R.string.device_socket_capture_delete_success));
            }
        });
        mDialog.setImageAndButton(bitmap, getString(R.string.delete), getString(R.string.save));
        mDialog.show();
    }

    private void hideVideoControlBar() {
        if (mDBind.layoutVideoControl.getVisibility() != View.GONE) {
            TranslateAnimation ani = new TranslateAnimation(0, 0, 0, UIFactory.dip2px(this, 42));
            ani.setDuration(200);
            mDBind.layoutVideoControl.startAnimation(ani);
            mDBind.layoutVideoControl.setVisibility(View.GONE);
        }

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏情况下,顶部标题栏也隐藏
            TranslateAnimation ani = new TranslateAnimation(0, 0, 0, -UIFactory.dip2px(this, 48));
            ani.setDuration(200);
            mDBind.layoutTop.startAnimation(ani);
            mDBind.layoutTop.setVisibility(View.GONE);
        }

        // 隐藏后清空自动隐藏的消息
        handler.removeMessages(MESSAGE_AUTO_HIDE_CONTROL_BAR);
    }

    /**
     * 视频截图,并延时一会提示截图对话框
     */
    private void tryToCapture() {
        if (!mDBind.funRecVideoView.isPlaying()) {
            showToast(getString(R.string.media_capture_failure_need_playing));
            return;
        }

        final String path = mDBind.funRecVideoView.captureImage(mFunDevice.getDevSn());	//图片异步保存
        if (!TextUtils.isEmpty(path)) {
            Message message = new Message();
            message.what = MESSAGE_TOAST_SCREENSHOT_PREVIEW;
            message.obj = path;
            handler.sendMessageDelayed(message, 400);			//此处延时一定时间等待图片保存完成后显示，也可以在回调成功后显示
        }
    }


    private void onSearchFile() {
        showProgressDialog();
        int[] time = {calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE)};
        H264_DVR_FINDINFO info = new H264_DVR_FINDINFO();
        info.st_1_nFileType = SDKCONST.FileType.SDK_RECORD_ALL;
        info.st_2_startTime.st_0_dwYear = time[0];
        info.st_2_startTime.st_1_dwMonth = time[1];
        info.st_2_startTime.st_2_dwDay = time[2];
        info.st_2_startTime.st_3_dwHour = 0;
        info.st_2_startTime.st_4_dwMinute = 0;
        info.st_2_startTime.st_5_dwSecond = 0;
        info.st_3_endTime.st_0_dwYear = time[0];
        info.st_3_endTime.st_1_dwMonth = time[1];
        info.st_3_endTime.st_2_dwDay = time[2];
        info.st_3_endTime.st_3_dwHour = 23;
        info.st_3_endTime.st_4_dwMinute = 59;
        info.st_3_endTime.st_5_dwSecond = 59;
        info.st_0_nChannelN0 = mFunDevice.CurrChannel;
        FunSupport.getInstance().requestDeviceFileList(mFunDevice, info);

        mDBind.tvRecordTitle.setText(df.format(calendar.getTime()));

    }


    private void seekRecordVideo(FunFileData funFileData, float curr) {
        if ( null != mDBind.funRecVideoView && null != funFileData) {

            float startt = (float) DateUtil.getSecondInDay(funFileData.getBeginTimeStr()) * TimerHistoryHorizonScrollView.TOTAL_PROCESS /86400f;
            float endt   = (float) DateUtil.getSecondInDay(funFileData.getEndTimeStr()) * TimerHistoryHorizonScrollView.TOTAL_PROCESS /86400f;

            int seekposbyfile = (int)((curr-startt)*100f/(endt-startt));
            Log.i(TAG,"seekRecordVideo+++++++++seekposbyfile:"+seekposbyfile);
            mDBind.funRecVideoView.seekbyfile(seekposbyfile);
        }
    }

    private void playRecordVideoByFile(FunFileData recordFile) {
        try {
            if(recordFile!=null && recordFile.getFileData()!=null){
                mDBind.funRecVideoView.stopPlayback();
                mDBind.loading.setVisibility(View.VISIBLE);
                mDBind.btnPlay.setImageResource(R.mipmap.icon_play_on);
                mDBind.btnPlay2.setImageResource(R.mipmap.icon_play_on);
                mDBind.funRecVideoView.playRecordByFile(mFunDevice.getDevSn(), recordFile.getFileData(), mFunDevice.CurrChannel);
                mDBind.funRecVideoView.setMediaSound(isSounded.get());
                int start= DateUtil.getSecondInDay(recordFile.getBeginTimeStr()) * TimerHistoryHorizonScrollView.TOTAL_PROCESS / 86400;
                mDBind.scrollView.smoothScrollTo(start,0);

                for(int i=0;i<files.size();i++){
                    if(recordFile.getBeginTimeStr().equals(files.get(i).getBeginTimeStr())){
                        mDBind.lsit.smoothScrollTo(PER_ITEM_SCROLL_WIDTH * i,0);

                        for(int j=0;j<files.size();j++){
                            ((FiterImageView)((RelativeLayout)mDBind.lv.getChildAt(j)).getChildAt(0)).setImageResource(R.mipmap.u2);
                        }

                        ((FiterImageView)((RelativeLayout)mDBind.lv.getChildAt(i)).getChildAt(0)).setImageResource(R.mipmap.u2);

                        break;
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.btnScreenRatio2:
             case R.id.btnScreenRatio:
                 switchOrientation();
                 break;
             case R.id.iv_calendar:
                 Intent intent = new Intent(this, DateSelectActivity.class);
                 intent.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
                 intent.putExtra("Date",calendar);
                 startActivityForResult(intent,REQUEST_SELECT_DATE);
                 break;
             case R.id.btnCapture:
             case R.id.btnCapture2:
                 tryToCapture();
                 break;
             case R.id.btnvoice:
             case R.id.btnvoice2:
                 if(isSounded.get()){
                     isSounded.set(false);
                     mDBind.btnCapture.setImageResource(R.mipmap.icon_is_voice);
                     mDBind.btnCapture2.setImageResource(R.mipmap.icon_is_voice);
                     mDBind.funRecVideoView.setMediaSound(false);
                 }
                 else{
                     isSounded.set(true);
                     mDBind.btnCapture.setImageResource(R.mipmap.icon_no_voice);
                     mDBind.btnCapture2.setImageResource(R.mipmap.icon_no_voice);
                     mDBind.funRecVideoView.setMediaSound(true);
                 }
                 break;
             case R.id.btnPlay:
             case R.id.btnPlay2:
                 if(mDBind.funRecVideoView.isPlaying()){
                     mDBind.btnPlay.setImageResource(R.mipmap.icon_play);
                     mDBind.btnPlay2.setImageResource(R.mipmap.icon_play);
                     if ( null != mDBind.funRecVideoView ) {
                         mDBind.funRecVideoView.stopPlayback();
                     }
                 }else{
                     mDBind.btnPlay.setImageResource(R.mipmap.icon_play_on);
                     mDBind.btnPlay2.setImageResource(R.mipmap.icon_play_on);
                     Message message = new Message();
                     message.what = MESSAGE_SETVIDEOAT;
                     message.obj = currfiles;
                     handler.sendMessageDelayed(message, 10);
                 }
                 break;

         }
    }


    /**
     * 切换视频全屏/小视频窗口(以切横竖屏切换替代)
     */
    private void switchOrientation() {
        // 横竖屏切换
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onBackPressed() {
        // 如果当前是横屏，返回时先回到竖屏
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        finish();
    }

    @Override
    protected void onResume() {
        systemTintManager.setStatusBarDarkMode(true,this);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;

        if(requestCode==REQUEST_SELECT_DATE){
            playlist.clear();
            files.clear();
            int result_year = data.getExtras().getInt("year");
            int result_month = data.getExtras().getInt("month");
            int result_day   = data.getExtras().getInt("day");

            calendar.set(result_year, result_month, result_day);
            onSearchFile();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 检测屏幕的方向：纵向或横向
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // 当前为横屏， 在此处添加额外的处理代码
            showAsLandscape();
        }else if(getResources().getConfiguration().orientation
                ==Configuration.ORIENTATION_PORTRAIT) {
            // 当前为竖屏， 在此处添加额外的处理代码
            showAsPortrait();
        }
        super.onConfigurationChanged(newConfig);
    }

    private void showAsLandscape() {
        systemTintManager.setStatusBarAlpha(0.0f);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 视频窗口全屏显示
        RelativeLayout.LayoutParams lpWnd = (RelativeLayout.LayoutParams) mDBind.layoutPlayWnd.getLayoutParams();
        lpWnd.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lpWnd.topMargin = 0;
        mDBind.layoutPlayWnd.setLayoutParams(lpWnd);

        // 上面标题半透明背景
        mDBind.layoutTop.setBackgroundColor(0x00000000);
        mDBind.layoutVideoControl.setVisibility(View.VISIBLE);
        mDBind.layoutVideoControl2.setVisibility(View.GONE);
        mDBind.bottomthing.setVisibility(View.GONE);
        mDBind.bottomthing2.setVisibility(View.GONE);
        showVideoControlBar();
    }

    private void showAsPortrait() {
        systemTintManager.setStatusBarAlpha(1.0f);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 还原上面标题栏背景
        mDBind.layoutTop.setBackgroundColor(getResources().getColor(R.color.white));

        // 视频显示为小窗口
        RelativeLayout.LayoutParams lpWnd = (RelativeLayout.LayoutParams) mDBind.layoutPlayWnd.getLayoutParams();
        lpWnd.height = (int)getResources().getDimension(R.dimen.funcview_height);
        lpWnd.topMargin = UIFactory.dip2px(this, 48);
        // lpWnd.addRule(RelativeLayout.BELOW, mLayoutTop.getId());
        mDBind.layoutPlayWnd.setLayoutParams(lpWnd);
        mDBind.layoutVideoControl.setVisibility(View.GONE);
        mDBind.layoutVideoControl2.setVisibility(View.VISIBLE);
        mDBind.bottomthing.setVisibility(View.VISIBLE);
        mDBind.bottomthing2.setVisibility(View.VISIBLE);
    }


    private void refreshPlayInfo() {

        int startTm = mDBind.funRecVideoView.getStartTime();
        int endTm = mDBind.funRecVideoView.getEndTime();
        Log.i("startTm","TTTT----" + startTm);
        Log.i("endTm","TTTT----" + endTm);
        if (startTm > 0 && endTm > startTm) {
            resetProgressInterval();
        } else {
            cleanProgressInterval();
        }
    }

    private void resetProgressInterval() {
        if ( null != handler ) {
            handler.removeMessages(MESSAGE_REFRESH_PROGRESS);
            handler.sendEmptyMessageDelayed(MESSAGE_REFRESH_PROGRESS, 500);
        }
    }

    private void cleanProgressInterval() {
        if ( null != handler ) {
            handler.removeMessages(MESSAGE_REFRESH_PROGRESS);
        }
    }

    private void refreshProgress() {
        int posTm = mDBind.funRecVideoView.getPosition();
        if ( posTm > 0 ) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            Log.i(TAG,"当前时间----" + sdf.format(new Date((long)posTm*1000)));
            if(null != mDBind.luxiangtime){
                mDBind.luxiangtime.setText(sdf.format(new Date((long)posTm*1000)));
            }

            if(mDBind.scrollView!=null && !srcoll_status){
                mDBind.scrollView.smoothScrollTo(DateUtil.getSecondInDay(sdf.format(new Date((long)posTm*1000))) * TimerHistoryHorizonScrollView.TOTAL_PROCESS / 86400,0);
            }
        }
    }



    @Override
    protected void onDestroy() {
        // 停止视频播放
        if ( null != mDBind.funRecVideoView ) {
            mDBind.funRecVideoView.stopPlayback();
        }
        // 5. 退出注销监听
        FunSupport.getInstance().removeOnFunDeviceRecordListener(this);
        FunSupport.getInstance().removeOnFunDeviceOptListener(this);
        if ( null != handler ) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int extra) {
        showToast(getString(R.string.media_play_error) + " : " +FunError.getErrorStr(extra));
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mDBind.loading.setVisibility(View.GONE);
        refreshPlayInfo();
        if(curr!=-1f){
            seekRecordVideo(currfiles,curr);
            curr = -1f;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(null != currfiles){
            for(int i=0;i<files.size();i++){
                if(currfiles.getBeginTimeStr().equals(files.get(i).getBeginTimeStr())){

                    if(i<(files.size()-1)){
                        currfiles = files.get(i+1);
                        playRecordVideoByFile(currfiles);
                    }

                    break;
                }
            }

        }

    }

    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {
    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceSetConfigSuccess(FunDevice funDevice, String configName) {

    }

    @Override
    public void onDeviceSetConfigFailed(FunDevice funDevice, String configName, Integer errCode) {

    }

    @Override
    public void onDeviceChangeInfoSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceChangeInfoFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceOptionSuccess(FunDevice funDevice, String option) {

    }

    @Override
    public void onDeviceOptionFailed(FunDevice funDevice, String option, Integer errCode) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice) {

    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {
        Log.i(TAG, "onDeviceFileListChanged: "+ datas.length);
            if (null != funDevice && null != mFunDevice && funDevice.getId() == mFunDevice.getId()) {

                if(datas.length==0){
                    dismissProgressDialog();
                    if(files.size()>0){
                        currfiles = files.get(0);
                        mDBind.lsit.setVisibility(View.VISIBLE);
                        intVideoHorizon();
                        mDBind.player.setVisibility(View.VISIBLE);
                        mDBind.luxiangtime.setVisibility(View.VISIBLE);
                        mDBind.scrollView.getHistoryView().setPlayList(playlist);
                        playRecordVideoByFile(currfiles);

                    }else{
                        showToast(getString(R.string.device_camera_video_list_empty));
                        mDBind.player.setVisibility(View.GONE);
                        mDBind.luxiangtime.setVisibility(View.GONE);
                        mDBind.lsit.setVisibility(View.GONE);
                    }
                }else{
                    for (H264_DVR_FILE_DATA data : datas) {
                        FunFileData funFileData = new FunFileData(data, new OPCompressPic());
                        float startt = (float)(DateUtil.getSecondInDay(funFileData.getBeginTimeStr())* TimerHistoryHorizonScrollView.TOTAL_PROCESS) /86400f;
                        float endt   = (float)(DateUtil.getSecondInDay(funFileData.getEndTimeStr()) * TimerHistoryHorizonScrollView.TOTAL_PROCESS) /86400f;
                        if("00:00:00".equals(funFileData.getEndTimeStr())){
                            endt = (float) TimerHistoryHorizonScrollView.TOTAL_PROCESS;
                        }
                        int type = funFileData.getFileType();
                        Map<String,Object> map = new HashMap<>();
                        map.put("start",startt);
                        map.put("end",endt);
                        map.put("type",type);
                        playlist.add(map);
                        files.add(funFileData);
                    }
                    dismissProgressDialog();

                    if(files.size()>0){
                        currfiles = files.get(0);
                        mDBind.lsit.setVisibility(View.VISIBLE);
                        intVideoHorizon();
                        mDBind.player.setVisibility(View.VISIBLE);
                        mDBind.luxiangtime.setVisibility(View.VISIBLE);
                        mDBind.scrollView.getHistoryView().setPlayList(playlist);
                        playRecordVideoByFile(currfiles);
                    }else{
                        showToast(getString(R.string.device_camera_video_list_empty));
                        mDBind.player.setVisibility(View.GONE);
                        mDBind.luxiangtime.setVisibility(View.GONE);
                        mDBind.lsit.setVisibility(View.GONE);
                    }
                }
        }
    }

    @Override
    public void onDeviceFileListGetFailed(FunDevice funDevice) {
        Log.i(TAG, "onDeviceFileListGetFailed: ");
    }

    @Override
    public void onRequestRecordListSuccess(List<FunDevRecordFile> files) {

    }

    @Override
    public void onRequestRecordListFailed(Integer errCode) {
        playlist.clear();
        files.clear();
        dismissProgressDialog();
        mDBind.player.setVisibility(View.GONE);
        mDBind.luxiangtime.setVisibility(View.GONE);
        mDBind.lsit.setVisibility(View.GONE);
        if(null!=mDBind.funRecVideoView){
            mDBind.funRecVideoView.stopPlayback();
        }
        if (mDialog !=null && !mDialog.isShowing()){
            mDialog = new TipDialog(mContext, () -> {});
            mDialog.setMsg(FunError.getErrorStr(errCode));
            mDialog.show();
        }
    }

    @Override
    public void onScrollChanged(TimerHistoryHorizonScrollView.ScrollType scrollType) {
        float scrollx;

        if(scrollType != TimerHistoryHorizonScrollView.ScrollType.IDLE){
            srcoll_status = true;
            return;
        }
        srcoll_status = false;
        if(mDBind.scrollView.getScrollX()<0){
            scrollx = 0f;
        }else if(mDBind.scrollView.getScrollX()> TimerHistoryHorizonScrollView.TOTAL_PROCESS){
            scrollx = (float) TimerHistoryHorizonScrollView.TOTAL_PROCESS;
        }else{
            scrollx = (float)mDBind.scrollView.getScrollX();
        }


        for(int i=0;i<files.size();i++){
            if(( (DateUtil.getSecondInDay(files.get(i).getBeginTimeStr())* TimerHistoryHorizonScrollView.TOTAL_PROCESS) /86400f)>scrollx){
                Message message = new Message();
                message.what = MESSAGE_SETVIDEOAT;
                message.obj = files.get(i);
                handler.sendMessageDelayed(message, 10);
                break;
            }else if((( (float)(DateUtil.getSecondInDay(files.get(i).getBeginTimeStr())* TimerHistoryHorizonScrollView.TOTAL_PROCESS) /86400f)<=scrollx && (( (float)(DateUtil.getSecondInDay(files.get(i).getEndTimeStr())* TimerHistoryHorizonScrollView.TOTAL_PROCESS) /86400f))>=scrollx)){
                Message message = new Message();
                message.what = MESSAGE_SETVIDEOIN;

                Map<String,Object> ds = new HashMap<>();
                ds.put("video", files.get(i));
                ds.put("current",scrollx);

                message.obj = ds;
                handler.sendMessageDelayed(message, 10);
                break;
            }
        }
    }

    private void showVideoControlBar() {
        if (mDBind.layoutVideoControl.getVisibility() != View.VISIBLE) {
            TranslateAnimation ani = new TranslateAnimation(0, 0, UIFactory.dip2px(this, 42), 0);
            ani.setDuration(200);
            mDBind.layoutVideoControl.startAnimation(ani);
            mDBind.layoutVideoControl.setVisibility(View.VISIBLE);
        }

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏情况下,顶部标题栏也动画显示
            TranslateAnimation ani = new TranslateAnimation(0, 0, -UIFactory.dip2px(this, 48), 0);
            ani.setDuration(200);
            mDBind.layoutTop.startAnimation(ani);
            mDBind.layoutTop.setVisibility(View.VISIBLE);
        } else {
            mDBind.layoutTop.setVisibility(View.VISIBLE);
        }

        // 显示后设置10秒后自动隐藏
        handler.removeMessages(MESSAGE_AUTO_HIDE_CONTROL_BAR);
        handler.sendEmptyMessageDelayed(MESSAGE_AUTO_HIDE_CONTROL_BAR, 10000);
    }


    private class VideoTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int iAction = event.getAction();
            if (iAction == MotionEvent.ACTION_UP || iAction == MotionEvent.ACTION_CANCEL) { // 弹起
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
                    // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    if (mDBind.layoutVideoControl.getVisibility() == View.VISIBLE) {
                        hideVideoControlBar();
                    } else {
                        showVideoControlBar();
                    }
                }
            }
            return false;
        }
    }
}
