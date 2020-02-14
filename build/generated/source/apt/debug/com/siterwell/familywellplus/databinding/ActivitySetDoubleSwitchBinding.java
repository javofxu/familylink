package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivitySetDoubleSwitchBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_set_title, 1);
        sViewsWithIds.put(R.id.iv_back, 2);
        sViewsWithIds.put(R.id.tv_save_switch, 3);
        sViewsWithIds.put(R.id.switch_is_output, 4);
        sViewsWithIds.put(R.id.action_minute, 5);
        sViewsWithIds.put(R.id.action_second, 6);
        sViewsWithIds.put(R.id.tv_set_action, 7);
        sViewsWithIds.put(R.id.item_dual_device, 8);
        sViewsWithIds.put(R.id.item_dual_device2, 9);
    }
    // views
    @NonNull
    public final com.ilop.sthome.view.WheelView actionMinute;
    @NonNull
    public final com.ilop.sthome.view.WheelView actionSecond;
    @NonNull
    public final com.ilop.sthome.view.WheelView itemDualDevice;
    @NonNull
    public final com.ilop.sthome.view.WheelView itemDualDevice2;
    @NonNull
    public final android.widget.ImageView ivBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.LinearLayout switchIsOutput;
    @NonNull
    public final android.widget.TextView tvSaveSwitch;
    @NonNull
    public final android.widget.TextView tvSetAction;
    @NonNull
    public final android.widget.TextView tvSetTitle;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySetDoubleSwitchBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.actionMinute = (com.ilop.sthome.view.WheelView) bindings[5];
        this.actionSecond = (com.ilop.sthome.view.WheelView) bindings[6];
        this.itemDualDevice = (com.ilop.sthome.view.WheelView) bindings[8];
        this.itemDualDevice2 = (com.ilop.sthome.view.WheelView) bindings[9];
        this.ivBack = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.switchIsOutput = (android.widget.LinearLayout) bindings[4];
        this.tvSaveSwitch = (android.widget.TextView) bindings[3];
        this.tvSetAction = (android.widget.TextView) bindings[7];
        this.tvSetTitle = (android.widget.TextView) bindings[1];
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
    public static ActivitySetDoubleSwitchBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetDoubleSwitchBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivitySetDoubleSwitchBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_set_double_switch, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivitySetDoubleSwitchBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetDoubleSwitchBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_set_double_switch, null, false), bindingComponent);
    }
    @NonNull
    public static ActivitySetDoubleSwitchBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetDoubleSwitchBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_set_double_switch_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivitySetDoubleSwitchBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}