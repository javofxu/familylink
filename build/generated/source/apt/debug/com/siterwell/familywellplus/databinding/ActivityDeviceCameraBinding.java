package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDeviceCameraBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_camera_top, 1);
        sViewsWithIds.put(R.id.tv_camera_title, 2);
        sViewsWithIds.put(R.id.iv_camera_back, 3);
        sViewsWithIds.put(R.id.iv_camera_setting, 4);
        sViewsWithIds.put(R.id.camera_scroll, 5);
        sViewsWithIds.put(R.id.rv_camera_list, 6);
        sViewsWithIds.put(R.id.ll_camera, 7);
        sViewsWithIds.put(R.id.layoutPlayWnd, 8);
        sViewsWithIds.put(R.id.funVideoView, 9);
        sViewsWithIds.put(R.id.textVideoStat, 10);
        sViewsWithIds.put(R.id.layout_recording, 11);
        sViewsWithIds.put(R.id.img_recording, 12);
        sViewsWithIds.put(R.id.layoutVideoControl, 13);
        sViewsWithIds.put(R.id.layoutVideoControl2, 14);
        sViewsWithIds.put(R.id.btnPlay, 15);
        sViewsWithIds.put(R.id.btnStop, 16);
        sViewsWithIds.put(R.id.btnStream, 17);
        sViewsWithIds.put(R.id.btnCapture, 18);
        sViewsWithIds.put(R.id.btnScreenRatio, 19);
        sViewsWithIds.put(R.id.btnVoiceTalk2, 20);
        sViewsWithIds.put(R.id.switch_fish_eye, 21);
        sViewsWithIds.put(R.id.setting_fish_eye, 22);
        sViewsWithIds.put(R.id.textStreamStat, 23);
        sViewsWithIds.put(R.id.up_direction, 24);
        sViewsWithIds.put(R.id.down_direction, 25);
        sViewsWithIds.put(R.id.left_direction, 26);
        sViewsWithIds.put(R.id.right_direction, 27);
        sViewsWithIds.put(R.id.layoutFunctionControl, 28);
        sViewsWithIds.put(R.id.btnDevCapture, 29);
        sViewsWithIds.put(R.id.btnVoiceTalk, 30);
        sViewsWithIds.put(R.id.btnDevRecord, 31);
        sViewsWithIds.put(R.id.vv_camera_full, 32);
    }
    // views
    @NonNull
    public final android.widget.ImageView btnCapture;
    @NonNull
    public final android.widget.LinearLayout btnDevCapture;
    @NonNull
    public final android.widget.LinearLayout btnDevRecord;
    @NonNull
    public final android.widget.ImageView btnPlay;
    @NonNull
    public final android.widget.ImageView btnScreenRatio;
    @NonNull
    public final android.widget.ImageView btnStop;
    @NonNull
    public final android.widget.TextView btnStream;
    @NonNull
    public final android.widget.LinearLayout btnVoiceTalk;
    @NonNull
    public final android.widget.RelativeLayout btnVoiceTalk2;
    @NonNull
    public final com.example.common.view.scroll.CustomScrollView cameraScroll;
    @NonNull
    public final android.widget.ImageView downDirection;
    @NonNull
    public final com.example.xmpic.support.widget.FunVideoView funVideoView;
    @NonNull
    public final android.widget.ImageView imgRecording;
    @NonNull
    public final android.widget.ImageView ivCameraBack;
    @NonNull
    public final android.widget.ImageView ivCameraSetting;
    @NonNull
    public final android.widget.LinearLayout layoutFunctionControl;
    @NonNull
    public final android.widget.RelativeLayout layoutPlayWnd;
    @NonNull
    public final android.widget.RelativeLayout layoutRecording;
    @NonNull
    public final android.widget.FrameLayout layoutVideoControl;
    @NonNull
    public final android.widget.LinearLayout layoutVideoControl2;
    @NonNull
    public final android.widget.ImageView leftDirection;
    @NonNull
    public final android.widget.LinearLayout llCamera;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ImageView rightDirection;
    @NonNull
    public final android.widget.RelativeLayout rlCameraTop;
    @NonNull
    public final android.support.v7.widget.RecyclerView rvCameraList;
    @NonNull
    public final com.example.xmpic.support.widget.FishEyeSettingPannel settingFishEye;
    @NonNull
    public final android.widget.ImageView switchFishEye;
    @NonNull
    public final android.widget.TextView textStreamStat;
    @NonNull
    public final android.widget.TextView textVideoStat;
    @NonNull
    public final android.widget.TextView tvCameraTitle;
    @NonNull
    public final android.widget.ImageView upDirection;
    @NonNull
    public final android.view.View vvCameraFull;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceCameraBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds);
        this.btnCapture = (android.widget.ImageView) bindings[18];
        this.btnDevCapture = (android.widget.LinearLayout) bindings[29];
        this.btnDevRecord = (android.widget.LinearLayout) bindings[31];
        this.btnPlay = (android.widget.ImageView) bindings[15];
        this.btnScreenRatio = (android.widget.ImageView) bindings[19];
        this.btnStop = (android.widget.ImageView) bindings[16];
        this.btnStream = (android.widget.TextView) bindings[17];
        this.btnVoiceTalk = (android.widget.LinearLayout) bindings[30];
        this.btnVoiceTalk2 = (android.widget.RelativeLayout) bindings[20];
        this.cameraScroll = (com.example.common.view.scroll.CustomScrollView) bindings[5];
        this.downDirection = (android.widget.ImageView) bindings[25];
        this.funVideoView = (com.example.xmpic.support.widget.FunVideoView) bindings[9];
        this.imgRecording = (android.widget.ImageView) bindings[12];
        this.ivCameraBack = (android.widget.ImageView) bindings[3];
        this.ivCameraSetting = (android.widget.ImageView) bindings[4];
        this.layoutFunctionControl = (android.widget.LinearLayout) bindings[28];
        this.layoutPlayWnd = (android.widget.RelativeLayout) bindings[8];
        this.layoutRecording = (android.widget.RelativeLayout) bindings[11];
        this.layoutVideoControl = (android.widget.FrameLayout) bindings[13];
        this.layoutVideoControl2 = (android.widget.LinearLayout) bindings[14];
        this.leftDirection = (android.widget.ImageView) bindings[26];
        this.llCamera = (android.widget.LinearLayout) bindings[7];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rightDirection = (android.widget.ImageView) bindings[27];
        this.rlCameraTop = (android.widget.RelativeLayout) bindings[1];
        this.rvCameraList = (android.support.v7.widget.RecyclerView) bindings[6];
        this.settingFishEye = (com.example.xmpic.support.widget.FishEyeSettingPannel) bindings[22];
        this.switchFishEye = (android.widget.ImageView) bindings[21];
        this.textStreamStat = (android.widget.TextView) bindings[23];
        this.textVideoStat = (android.widget.TextView) bindings[10];
        this.tvCameraTitle = (android.widget.TextView) bindings[2];
        this.upDirection = (android.widget.ImageView) bindings[24];
        this.vvCameraFull = (android.view.View) bindings[32];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityDeviceCameraBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceCameraBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDeviceCameraBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_device_camera, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDeviceCameraBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceCameraBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_device_camera, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDeviceCameraBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceCameraBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_device_camera_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDeviceCameraBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}