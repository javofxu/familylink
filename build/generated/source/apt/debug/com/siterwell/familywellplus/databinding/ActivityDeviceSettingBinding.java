package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDeviceSettingBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_gateway_set_title, 1);
        sViewsWithIds.put(R.id.iv_gateway_back, 2);
        sViewsWithIds.put(R.id.imgDeviceQRCode, 3);
        sViewsWithIds.put(R.id.replaceeqid, 4);
        sViewsWithIds.put(R.id.name, 5);
        sViewsWithIds.put(R.id.ota, 6);
        sViewsWithIds.put(R.id.share, 7);
        sViewsWithIds.put(R.id.location, 8);
        sViewsWithIds.put(R.id.ins, 9);
        sViewsWithIds.put(R.id.device_unbind, 10);
    }
    // views
    @NonNull
    public final android.widget.Button deviceUnbind;
    @NonNull
    public final android.widget.ImageView imgDeviceQRCode;
    @NonNull
    public final com.ilop.sthome.view.SettingItem ins;
    @NonNull
    public final android.widget.ImageView ivGatewayBack;
    @NonNull
    public final com.ilop.sthome.view.SettingItem location;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.SettingItem name;
    @NonNull
    public final com.ilop.sthome.view.SettingItem ota;
    @NonNull
    public final com.ilop.sthome.view.SettingItem replaceeqid;
    @NonNull
    public final com.ilop.sthome.view.SettingItem share;
    @NonNull
    public final android.widget.TextView tvGatewaySetTitle;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceSettingBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.deviceUnbind = (android.widget.Button) bindings[10];
        this.imgDeviceQRCode = (android.widget.ImageView) bindings[3];
        this.ins = (com.ilop.sthome.view.SettingItem) bindings[9];
        this.ivGatewayBack = (android.widget.ImageView) bindings[2];
        this.location = (com.ilop.sthome.view.SettingItem) bindings[8];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (com.ilop.sthome.view.SettingItem) bindings[5];
        this.ota = (com.ilop.sthome.view.SettingItem) bindings[6];
        this.replaceeqid = (com.ilop.sthome.view.SettingItem) bindings[4];
        this.share = (com.ilop.sthome.view.SettingItem) bindings[7];
        this.tvGatewaySetTitle = (android.widget.TextView) bindings[1];
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
    public static ActivityDeviceSettingBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSettingBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDeviceSettingBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_device_setting, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDeviceSettingBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSettingBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_device_setting, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDeviceSettingBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceSettingBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_device_setting_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDeviceSettingBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}