package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityConnectNetBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_connect_back, 1);
        sViewsWithIds.put(R.id.pv_progress, 2);
        sViewsWithIds.put(R.id.tv_progress_num, 3);
        sViewsWithIds.put(R.id.iv_connect_error, 4);
        sViewsWithIds.put(R.id.tv_tip, 5);
        sViewsWithIds.put(R.id.fail_reason, 6);
        sViewsWithIds.put(R.id.retry, 7);
        sViewsWithIds.put(R.id.change_config, 8);
    }
    // views
    @NonNull
    public final android.widget.Button changeConfig;
    @NonNull
    public final android.widget.TextView failReason;
    @NonNull
    public final android.widget.ImageView ivConnectBack;
    @NonNull
    public final android.widget.ImageView ivConnectError;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ProgressBar pvProgress;
    @NonNull
    public final android.widget.Button retry;
    @NonNull
    public final android.widget.TextView tvProgressNum;
    @NonNull
    public final android.widget.TextView tvTip;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityConnectNetBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.changeConfig = (android.widget.Button) bindings[8];
        this.failReason = (android.widget.TextView) bindings[6];
        this.ivConnectBack = (android.widget.ImageView) bindings[1];
        this.ivConnectError = (android.widget.ImageView) bindings[4];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pvProgress = (android.widget.ProgressBar) bindings[2];
        this.retry = (android.widget.Button) bindings[7];
        this.tvProgressNum = (android.widget.TextView) bindings[3];
        this.tvTip = (android.widget.TextView) bindings[5];
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
    public static ActivityConnectNetBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConnectNetBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityConnectNetBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_connect_net, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityConnectNetBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConnectNetBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_connect_net, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityConnectNetBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConnectNetBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_connect_net_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityConnectNetBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}