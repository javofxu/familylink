package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityPicSettingStorageBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_storage_back, 1);
        sViewsWithIds.put(R.id.porter_duff_view, 2);
        sViewsWithIds.put(R.id.percent, 3);
        sViewsWithIds.put(R.id.storage_has, 4);
        sViewsWithIds.put(R.id.storage_remain, 5);
        sViewsWithIds.put(R.id.stoprecord, 6);
        sViewsWithIds.put(R.id.cyclerecord, 7);
        sViewsWithIds.put(R.id.btnStorageFormat, 8);
    }
    // views
    @NonNull
    public final android.widget.Button btnStorageFormat;
    @NonNull
    public final android.widget.RadioButton cyclerecord;
    @NonNull
    public final android.widget.ImageView ivStorageBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView percent;
    @NonNull
    public final com.ilop.sthome.view.CustomWavesView porterDuffView;
    @NonNull
    public final android.widget.RadioButton stoprecord;
    @NonNull
    public final android.widget.TextView storageHas;
    @NonNull
    public final android.widget.TextView storageRemain;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPicSettingStorageBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.btnStorageFormat = (android.widget.Button) bindings[8];
        this.cyclerecord = (android.widget.RadioButton) bindings[7];
        this.ivStorageBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.percent = (android.widget.TextView) bindings[3];
        this.porterDuffView = (com.ilop.sthome.view.CustomWavesView) bindings[2];
        this.stoprecord = (android.widget.RadioButton) bindings[6];
        this.storageHas = (android.widget.TextView) bindings[4];
        this.storageRemain = (android.widget.TextView) bindings[5];
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
    public static ActivityPicSettingStorageBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingStorageBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityPicSettingStorageBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_pic_setting_storage, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingStorageBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingStorageBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_pic_setting_storage, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingStorageBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingStorageBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_pic_setting_storage_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityPicSettingStorageBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}