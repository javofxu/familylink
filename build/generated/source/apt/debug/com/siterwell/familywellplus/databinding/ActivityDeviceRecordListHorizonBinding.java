package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDeviceRecordListHorizonBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.layoutPlayWnd, 1);
        sViewsWithIds.put(R.id.funRecVideoView, 2);
        sViewsWithIds.put(R.id.devname, 3);
        sViewsWithIds.put(R.id.layoutVideoControl, 4);
        sViewsWithIds.put(R.id.btnPlay, 5);
        sViewsWithIds.put(R.id.btnvoice, 6);
        sViewsWithIds.put(R.id.btnCapture, 7);
        sViewsWithIds.put(R.id.btnScreenRatio, 8);
        sViewsWithIds.put(R.id.loading, 9);
        sViewsWithIds.put(R.id.layoutTop, 10);
        sViewsWithIds.put(R.id.tv_record_title, 11);
        sViewsWithIds.put(R.id.iv_record_back, 12);
        sViewsWithIds.put(R.id.iv_calendar, 13);
        sViewsWithIds.put(R.id.layoutVideoControl2, 14);
        sViewsWithIds.put(R.id.btnPlay2, 15);
        sViewsWithIds.put(R.id.btnvoice2, 16);
        sViewsWithIds.put(R.id.btnCapture2, 17);
        sViewsWithIds.put(R.id.btnScreenRatio2, 18);
        sViewsWithIds.put(R.id.lsit, 19);
        sViewsWithIds.put(R.id.lv, 20);
        sViewsWithIds.put(R.id.player, 21);
        sViewsWithIds.put(R.id.scroll_view, 22);
        sViewsWithIds.put(R.id.bottomthing, 23);
        sViewsWithIds.put(R.id.luxiangtime, 24);
        sViewsWithIds.put(R.id.bottomthing2, 25);
    }
    // views
    @NonNull
    public final android.widget.LinearLayout bottomthing;
    @NonNull
    public final android.widget.LinearLayout bottomthing2;
    @NonNull
    public final android.widget.ImageView btnCapture;
    @NonNull
    public final android.widget.ImageView btnCapture2;
    @NonNull
    public final android.widget.ImageView btnPlay;
    @NonNull
    public final android.widget.ImageView btnPlay2;
    @NonNull
    public final android.widget.ImageView btnScreenRatio;
    @NonNull
    public final android.widget.ImageView btnScreenRatio2;
    @NonNull
    public final android.widget.ImageView btnvoice;
    @NonNull
    public final android.widget.ImageView btnvoice2;
    @NonNull
    public final android.widget.TextView devname;
    @NonNull
    public final com.example.xmpic.support.widget.FunVideoView funRecVideoView;
    @NonNull
    public final android.widget.ImageView ivCalendar;
    @NonNull
    public final android.widget.ImageView ivRecordBack;
    @NonNull
    public final android.widget.RelativeLayout layoutPlayWnd;
    @NonNull
    public final android.widget.RelativeLayout layoutTop;
    @NonNull
    public final android.widget.LinearLayout layoutVideoControl;
    @NonNull
    public final android.widget.LinearLayout layoutVideoControl2;
    @NonNull
    public final android.widget.TextView loading;
    @NonNull
    public final android.widget.HorizontalScrollView lsit;
    @NonNull
    public final android.widget.TextView luxiangtime;
    @NonNull
    public final android.widget.LinearLayout lv;
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    public final android.widget.RelativeLayout player;
    @NonNull
    public final com.ilop.sthome.view.TimerHistoryHorizonScrollView scrollView;
    @NonNull
    public final android.widget.TextView tvRecordTitle;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceRecordListHorizonBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds);
        this.bottomthing = (android.widget.LinearLayout) bindings[23];
        this.bottomthing2 = (android.widget.LinearLayout) bindings[25];
        this.btnCapture = (android.widget.ImageView) bindings[7];
        this.btnCapture2 = (android.widget.ImageView) bindings[17];
        this.btnPlay = (android.widget.ImageView) bindings[5];
        this.btnPlay2 = (android.widget.ImageView) bindings[15];
        this.btnScreenRatio = (android.widget.ImageView) bindings[8];
        this.btnScreenRatio2 = (android.widget.ImageView) bindings[18];
        this.btnvoice = (android.widget.ImageView) bindings[6];
        this.btnvoice2 = (android.widget.ImageView) bindings[16];
        this.devname = (android.widget.TextView) bindings[3];
        this.funRecVideoView = (com.example.xmpic.support.widget.FunVideoView) bindings[2];
        this.ivCalendar = (android.widget.ImageView) bindings[13];
        this.ivRecordBack = (android.widget.ImageView) bindings[12];
        this.layoutPlayWnd = (android.widget.RelativeLayout) bindings[1];
        this.layoutTop = (android.widget.RelativeLayout) bindings[10];
        this.layoutVideoControl = (android.widget.LinearLayout) bindings[4];
        this.layoutVideoControl2 = (android.widget.LinearLayout) bindings[14];
        this.loading = (android.widget.TextView) bindings[9];
        this.lsit = (android.widget.HorizontalScrollView) bindings[19];
        this.luxiangtime = (android.widget.TextView) bindings[24];
        this.lv = (android.widget.LinearLayout) bindings[20];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.player = (android.widget.RelativeLayout) bindings[21];
        this.scrollView = (com.ilop.sthome.view.TimerHistoryHorizonScrollView) bindings[22];
        this.tvRecordTitle = (android.widget.TextView) bindings[11];
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
    public static ActivityDeviceRecordListHorizonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceRecordListHorizonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDeviceRecordListHorizonBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_device_record_list_horizon, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDeviceRecordListHorizonBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceRecordListHorizonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_device_record_list_horizon, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDeviceRecordListHorizonBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceRecordListHorizonBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_device_record_list_horizon_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDeviceRecordListHorizonBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}