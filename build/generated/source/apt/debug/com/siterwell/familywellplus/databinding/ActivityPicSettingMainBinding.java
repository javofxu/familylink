package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityPicSettingMainBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_setting_back, 1);
        sViewsWithIds.put(R.id.common, 2);
        sViewsWithIds.put(R.id.codesetting, 3);
        sViewsWithIds.put(R.id.storage, 4);
        sViewsWithIds.put(R.id.expertsetting, 5);
        sViewsWithIds.put(R.id.tongyong, 6);
        sViewsWithIds.put(R.id.tuichu, 7);
    }
    // views
    @NonNull
    public final com.ilop.sthome.view.SettingItem codesetting;
    @NonNull
    public final com.ilop.sthome.view.SettingItem common;
    @NonNull
    public final com.ilop.sthome.view.SettingItem expertsetting;
    @NonNull
    public final android.widget.ImageView ivSettingBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.SettingItem storage;
    @NonNull
    public final com.ilop.sthome.view.SettingItem tongyong;
    @NonNull
    public final android.widget.Button tuichu;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPicSettingMainBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.codesetting = (com.ilop.sthome.view.SettingItem) bindings[3];
        this.common = (com.ilop.sthome.view.SettingItem) bindings[2];
        this.expertsetting = (com.ilop.sthome.view.SettingItem) bindings[5];
        this.ivSettingBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.storage = (com.ilop.sthome.view.SettingItem) bindings[4];
        this.tongyong = (com.ilop.sthome.view.SettingItem) bindings[6];
        this.tuichu = (android.widget.Button) bindings[7];
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
    public static ActivityPicSettingMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityPicSettingMainBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_pic_setting_main, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingMainBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_pic_setting_main, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingMainBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingMainBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_pic_setting_main_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityPicSettingMainBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}