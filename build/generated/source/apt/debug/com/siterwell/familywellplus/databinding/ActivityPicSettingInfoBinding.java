package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityPicSettingInfoBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_common_back, 1);
        sViewsWithIds.put(R.id.imgDeviceQRCode, 2);
        sViewsWithIds.put(R.id.sn, 3);
        sViewsWithIds.put(R.id.type, 4);
        sViewsWithIds.put(R.id.storage, 5);
        sViewsWithIds.put(R.id.net_type, 6);
        sViewsWithIds.put(R.id.expertsetting, 7);
        sViewsWithIds.put(R.id.net_status, 8);
        sViewsWithIds.put(R.id.reset, 9);
    }
    // views
    @NonNull
    public final android.widget.RelativeLayout expertsetting;
    @NonNull
    public final android.widget.ImageView imgDeviceQRCode;
    @NonNull
    public final android.widget.ImageView ivCommonBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView netStatus;
    @NonNull
    public final android.widget.TextView netType;
    @NonNull
    public final android.widget.Button reset;
    @NonNull
    public final android.widget.TextView sn;
    @NonNull
    public final android.widget.RelativeLayout storage;
    @NonNull
    public final android.widget.TextView type;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPicSettingInfoBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.expertsetting = (android.widget.RelativeLayout) bindings[7];
        this.imgDeviceQRCode = (android.widget.ImageView) bindings[2];
        this.ivCommonBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.netStatus = (android.widget.TextView) bindings[8];
        this.netType = (android.widget.TextView) bindings[6];
        this.reset = (android.widget.Button) bindings[9];
        this.sn = (android.widget.TextView) bindings[3];
        this.storage = (android.widget.RelativeLayout) bindings[5];
        this.type = (android.widget.TextView) bindings[4];
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
    public static ActivityPicSettingInfoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingInfoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityPicSettingInfoBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_pic_setting_info, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingInfoBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingInfoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_pic_setting_info, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingInfoBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingInfoBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_pic_setting_info_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityPicSettingInfoBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}