package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDeviceSetupRecordBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_param_back, 1);
        sViewsWithIds.put(R.id.iv_param_setting, 2);
        sViewsWithIds.put(R.id.yulu, 3);
        sViewsWithIds.put(R.id.luxiangshichang, 4);
        sViewsWithIds.put(R.id.luxiangtype, 5);
        sViewsWithIds.put(R.id.luxiangauto, 6);
    }
    // views
    @NonNull
    public final android.widget.ImageView ivParamBack;
    @NonNull
    public final android.widget.ImageView ivParamSetting;
    @NonNull
    public final com.ilop.sthome.view.SettingIpcItem luxiangauto;
    @NonNull
    public final com.ilop.sthome.view.SettingIpcItem luxiangshichang;
    @NonNull
    public final com.ilop.sthome.view.SettingIpcItem luxiangtype;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.SettingIpcItem yulu;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceSetupRecordBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.ivParamBack = (android.widget.ImageView) bindings[1];
        this.ivParamSetting = (android.widget.ImageView) bindings[2];
        this.luxiangauto = (com.ilop.sthome.view.SettingIpcItem) bindings[6];
        this.luxiangshichang = (com.ilop.sthome.view.SettingIpcItem) bindings[4];
        this.luxiangtype = (com.ilop.sthome.view.SettingIpcItem) bindings[5];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.yulu = (com.ilop.sthome.view.SettingIpcItem) bindings[3];
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
    public static ActivityDeviceSetupRecordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSetupRecordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDeviceSetupRecordBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_device_setup_record, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDeviceSetupRecordBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSetupRecordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_device_setup_record, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDeviceSetupRecordBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSetupRecordBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_device_setup_record_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDeviceSetupRecordBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}