package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAddTimerBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_add_device_back, 1);
        sViewsWithIds.put(R.id.iv_add_timer, 2);
        sViewsWithIds.put(R.id.hour, 3);
        sViewsWithIds.put(R.id.min, 4);
        sViewsWithIds.put(R.id.mode, 5);
        sViewsWithIds.put(R.id.weeklist, 6);
    }
    // views
    @NonNull
    public final com.ilop.sthome.view.WheelView hour;
    @NonNull
    public final android.widget.ImageView ivAddDeviceBack;
    @NonNull
    public final android.widget.ImageView ivAddTimer;
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.WheelView min;
    @NonNull
    public final com.ilop.sthome.view.WheelView mode;
    @NonNull
    public final android.support.v7.widget.RecyclerView weeklist;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddTimerBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.hour = (com.ilop.sthome.view.WheelView) bindings[3];
        this.ivAddDeviceBack = (android.widget.ImageView) bindings[1];
        this.ivAddTimer = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.min = (com.ilop.sthome.view.WheelView) bindings[4];
        this.mode = (com.ilop.sthome.view.WheelView) bindings[5];
        this.weeklist = (android.support.v7.widget.RecyclerView) bindings[6];
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
    public static ActivityAddTimerBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddTimerBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAddTimerBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_add_timer, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAddTimerBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddTimerBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_add_timer, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAddTimerBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddTimerBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_add_timer_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAddTimerBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}