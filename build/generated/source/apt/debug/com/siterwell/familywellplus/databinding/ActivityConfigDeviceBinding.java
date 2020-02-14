package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityConfigDeviceBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_gateway_back, 1);
        sViewsWithIds.put(R.id.sl_has_gateway, 2);
        sViewsWithIds.put(R.id.et_device_name, 3);
        sViewsWithIds.put(R.id.et_room_name, 4);
        sViewsWithIds.put(R.id.choose_room_list, 5);
        sViewsWithIds.put(R.id.choose_gateway_list, 6);
        sViewsWithIds.put(R.id.config_device_next, 7);
        sViewsWithIds.put(R.id.ll_no_gateway, 8);
        sViewsWithIds.put(R.id.iv_add_gateway, 9);
    }
    // views
    @NonNull
    public final android.support.v7.widget.RecyclerView chooseGatewayList;
    @NonNull
    public final android.support.v7.widget.RecyclerView chooseRoomList;
    @NonNull
    public final android.widget.Button configDeviceNext;
    @NonNull
    public final android.widget.EditText etDeviceName;
    @NonNull
    public final android.widget.EditText etRoomName;
    @NonNull
    public final android.widget.ImageView ivAddGateway;
    @NonNull
    public final android.widget.ImageView ivGatewayBack;
    @NonNull
    public final android.widget.LinearLayout llNoGateway;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ScrollView slHasGateway;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityConfigDeviceBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.chooseGatewayList = (android.support.v7.widget.RecyclerView) bindings[6];
        this.chooseRoomList = (android.support.v7.widget.RecyclerView) bindings[5];
        this.configDeviceNext = (android.widget.Button) bindings[7];
        this.etDeviceName = (android.widget.EditText) bindings[3];
        this.etRoomName = (android.widget.EditText) bindings[4];
        this.ivAddGateway = (android.widget.ImageView) bindings[9];
        this.ivGatewayBack = (android.widget.ImageView) bindings[1];
        this.llNoGateway = (android.widget.LinearLayout) bindings[8];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.slHasGateway = (android.widget.ScrollView) bindings[2];
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
    public static ActivityConfigDeviceBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConfigDeviceBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityConfigDeviceBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_config_device, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityConfigDeviceBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConfigDeviceBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_config_device, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityConfigDeviceBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityConfigDeviceBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_config_device_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityConfigDeviceBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}