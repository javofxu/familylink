package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDeviceChangePasswordBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_pwd_back, 1);
        sViewsWithIds.put(R.id.et_old_password, 2);
        sViewsWithIds.put(R.id.et_new_password, 3);
        sViewsWithIds.put(R.id.et_confirm_password, 4);
        sViewsWithIds.put(R.id.bt_change_password, 5);
    }
    // views
    @NonNull
    public final android.widget.Button btChangePassword;
    @NonNull
    public final com.example.common.view.CustomEditText etConfirmPassword;
    @NonNull
    public final com.example.common.view.CustomEditText etNewPassword;
    @NonNull
    public final com.example.common.view.CustomEditText etOldPassword;
    @NonNull
    public final android.widget.ImageView ivPwdBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceChangePasswordBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.btChangePassword = (android.widget.Button) bindings[5];
        this.etConfirmPassword = (com.example.common.view.CustomEditText) bindings[4];
        this.etNewPassword = (com.example.common.view.CustomEditText) bindings[3];
        this.etOldPassword = (com.example.common.view.CustomEditText) bindings[2];
        this.ivPwdBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
    public static ActivityDeviceChangePasswordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceChangePasswordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDeviceChangePasswordBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_device_change_password, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDeviceChangePasswordBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceChangePasswordBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_device_change_password, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDeviceChangePasswordBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDeviceChangePasswordBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_device_change_password_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDeviceChangePasswordBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}