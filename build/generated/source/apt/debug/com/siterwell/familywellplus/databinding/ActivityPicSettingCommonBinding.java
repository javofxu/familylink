package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityPicSettingCommonBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_common_setting_back, 1);
        sViewsWithIds.put(R.id.updatename, 2);
        sViewsWithIds.put(R.id.dd, 3);
        sViewsWithIds.put(R.id.name, 4);
        sViewsWithIds.put(R.id.updatetime, 5);
        sViewsWithIds.put(R.id.ee, 6);
        sViewsWithIds.put(R.id.time, 7);
        sViewsWithIds.put(R.id.btnSwitchCameraFlip, 8);
        sViewsWithIds.put(R.id.btnSwitchCameraMirror, 9);
    }
    // views
    @NonNull
    public final android.widget.ImageButton btnSwitchCameraFlip;
    @NonNull
    public final android.widget.ImageButton btnSwitchCameraMirror;
    @NonNull
    public final android.widget.ImageView dd;
    @NonNull
    public final android.widget.ImageView ee;
    @NonNull
    public final android.widget.ImageView ivCommonSettingBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView name;
    @NonNull
    public final com.example.xmpic.support.widget.TimeTextView time;
    @NonNull
    public final android.widget.RelativeLayout updatename;
    @NonNull
    public final android.widget.RelativeLayout updatetime;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPicSettingCommonBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.btnSwitchCameraFlip = (android.widget.ImageButton) bindings[8];
        this.btnSwitchCameraMirror = (android.widget.ImageButton) bindings[9];
        this.dd = (android.widget.ImageView) bindings[3];
        this.ee = (android.widget.ImageView) bindings[6];
        this.ivCommonSettingBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (android.widget.TextView) bindings[4];
        this.time = (com.example.xmpic.support.widget.TimeTextView) bindings[7];
        this.updatename = (android.widget.RelativeLayout) bindings[2];
        this.updatetime = (android.widget.RelativeLayout) bindings[5];
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
    public static ActivityPicSettingCommonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingCommonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityPicSettingCommonBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_pic_setting_common, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingCommonBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingCommonBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_pic_setting_common, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityPicSettingCommonBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPicSettingCommonBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_pic_setting_common_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityPicSettingCommonBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}