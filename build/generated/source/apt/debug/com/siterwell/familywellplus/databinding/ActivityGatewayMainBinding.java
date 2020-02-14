package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityGatewayMainBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_gateway_back, 1);
        sViewsWithIds.put(R.id.title, 2);
        sViewsWithIds.put(R.id.state_model, 3);
        sViewsWithIds.put(R.id.state_model_change, 4);
        sViewsWithIds.put(R.id.model_color, 5);
        sViewsWithIds.put(R.id.title_sceneMode, 6);
        sViewsWithIds.put(R.id.gateway_sub_device, 7);
        sViewsWithIds.put(R.id.gateway_sub_add, 8);
        sViewsWithIds.put(R.id.tab_history, 9);
        sViewsWithIds.put(R.id.tab_details, 10);
        sViewsWithIds.put(R.id.tab_setting, 11);
    }
    // views
    @NonNull
    public final android.widget.ImageView gatewaySubAdd;
    @NonNull
    public final com.example.common.view.refresh.CustomRefreshView gatewaySubDevice;
    @NonNull
    public final android.widget.ImageView ivGatewayBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.view.View modelColor;
    @NonNull
    public final android.widget.TextView stateModel;
    @NonNull
    public final android.widget.ImageView stateModelChange;
    @NonNull
    public final android.widget.LinearLayout tabDetails;
    @NonNull
    public final android.widget.LinearLayout tabHistory;
    @NonNull
    public final android.widget.LinearLayout tabSetting;
    @NonNull
    public final com.example.common.view.CustomTextView title;
    @NonNull
    public final android.widget.TextView titleSceneMode;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityGatewayMainBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.gatewaySubAdd = (android.widget.ImageView) bindings[8];
        this.gatewaySubDevice = (com.example.common.view.refresh.CustomRefreshView) bindings[7];
        this.ivGatewayBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.modelColor = (android.view.View) bindings[5];
        this.stateModel = (android.widget.TextView) bindings[3];
        this.stateModelChange = (android.widget.ImageView) bindings[4];
        this.tabDetails = (android.widget.LinearLayout) bindings[10];
        this.tabHistory = (android.widget.LinearLayout) bindings[9];
        this.tabSetting = (android.widget.LinearLayout) bindings[11];
        this.title = (com.example.common.view.CustomTextView) bindings[2];
        this.titleSceneMode = (android.widget.TextView) bindings[6];
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
    public static ActivityGatewayMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityGatewayMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityGatewayMainBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_gateway_main, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityGatewayMainBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityGatewayMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_gateway_main, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityGatewayMainBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityGatewayMainBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_gateway_main_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityGatewayMainBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}