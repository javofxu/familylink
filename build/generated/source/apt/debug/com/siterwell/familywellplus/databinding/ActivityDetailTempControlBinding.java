package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDetailTempControlBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.detail_name, 1);
        sViewsWithIds.put(R.id.signalPosition, 2);
        sViewsWithIds.put(R.id.quantityPosition, 3);
        sViewsWithIds.put(R.id.quantity_desc, 4);
        sViewsWithIds.put(R.id.root, 5);
        sViewsWithIds.put(R.id.showStatus, 6);
        sViewsWithIds.put(R.id.setting_temp, 7);
        sViewsWithIds.put(R.id.btnConfirm, 8);
        sViewsWithIds.put(R.id.timer_mode, 9);
        sViewsWithIds.put(R.id.handle_mode, 10);
        sViewsWithIds.put(R.id.fangdong_mode, 11);
        sViewsWithIds.put(R.id.valve_status, 12);
        sViewsWithIds.put(R.id.valve_check, 13);
        sViewsWithIds.put(R.id.tongsuo_status, 14);
        sViewsWithIds.put(R.id.tongsuo_btn, 15);
        sViewsWithIds.put(R.id.window_status, 16);
        sViewsWithIds.put(R.id.window_check, 17);
        sViewsWithIds.put(R.id.detail_history, 18);
        sViewsWithIds.put(R.id.tab_details, 19);
        sViewsWithIds.put(R.id.detail_edit, 20);
    }
    // views
    @NonNull
    public final android.widget.TextView btnConfirm;
    @NonNull
    public final android.widget.LinearLayout detailEdit;
    @NonNull
    public final android.widget.LinearLayout detailHistory;
    @NonNull
    public final android.widget.TextView detailName;
    @NonNull
    public final android.widget.ImageView fangdongMode;
    @NonNull
    public final android.widget.ImageView handleMode;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView quantityDesc;
    @NonNull
    public final android.widget.ImageView quantityPosition;
    @NonNull
    public final android.view.View root;
    @NonNull
    public final com.ilop.sthome.view.ColorArcProgressBar settingTemp;
    @NonNull
    public final android.widget.TextView showStatus;
    @NonNull
    public final android.widget.ImageView signalPosition;
    @NonNull
    public final android.widget.LinearLayout tabDetails;
    @NonNull
    public final android.widget.ImageView timerMode;
    @NonNull
    public final android.widget.CheckBox tongsuoBtn;
    @NonNull
    public final android.widget.ImageView tongsuoStatus;
    @NonNull
    public final android.widget.CheckBox valveCheck;
    @NonNull
    public final android.widget.ImageView valveStatus;
    @NonNull
    public final android.widget.CheckBox windowCheck;
    @NonNull
    public final android.widget.ImageView windowStatus;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailTempControlBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds);
        this.btnConfirm = (android.widget.TextView) bindings[8];
        this.detailEdit = (android.widget.LinearLayout) bindings[20];
        this.detailHistory = (android.widget.LinearLayout) bindings[18];
        this.detailName = (android.widget.TextView) bindings[1];
        this.fangdongMode = (android.widget.ImageView) bindings[11];
        this.handleMode = (android.widget.ImageView) bindings[10];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.quantityDesc = (android.widget.TextView) bindings[4];
        this.quantityPosition = (android.widget.ImageView) bindings[3];
        this.root = (android.view.View) bindings[5];
        this.settingTemp = (com.ilop.sthome.view.ColorArcProgressBar) bindings[7];
        this.showStatus = (android.widget.TextView) bindings[6];
        this.signalPosition = (android.widget.ImageView) bindings[2];
        this.tabDetails = (android.widget.LinearLayout) bindings[19];
        this.timerMode = (android.widget.ImageView) bindings[9];
        this.tongsuoBtn = (android.widget.CheckBox) bindings[15];
        this.tongsuoStatus = (android.widget.ImageView) bindings[14];
        this.valveCheck = (android.widget.CheckBox) bindings[13];
        this.valveStatus = (android.widget.ImageView) bindings[12];
        this.windowCheck = (android.widget.CheckBox) bindings[17];
        this.windowStatus = (android.widget.ImageView) bindings[16];
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
    public static ActivityDetailTempControlBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailTempControlBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailTempControlBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_detail_temp_control, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDetailTempControlBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailTempControlBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_detail_temp_control, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDetailTempControlBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailTempControlBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_temp_control_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailTempControlBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}