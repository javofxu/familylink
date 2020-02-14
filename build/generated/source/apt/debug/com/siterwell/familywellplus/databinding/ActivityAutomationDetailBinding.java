package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAutomationDetailBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_automation_name, 1);
        sViewsWithIds.put(R.id.iv_back, 2);
        sViewsWithIds.put(R.id.tv_save_automation, 3);
        sViewsWithIds.put(R.id.trigger_add, 4);
        sViewsWithIds.put(R.id.trigger_list, 5);
        sViewsWithIds.put(R.id.executive_add, 6);
        sViewsWithIds.put(R.id.executive_list, 7);
        sViewsWithIds.put(R.id.bt_delete_automation, 8);
    }
    // views
    @NonNull
    public final android.widget.Button btDeleteAutomation;
    @NonNull
    public final android.widget.ImageView executiveAdd;
    @NonNull
    public final android.support.v7.widget.RecyclerView executiveList;
    @NonNull
    public final android.widget.ImageView ivBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ImageView triggerAdd;
    @NonNull
    public final android.support.v7.widget.RecyclerView triggerList;
    @NonNull
    public final android.widget.TextView tvAutomationName;
    @NonNull
    public final android.widget.TextView tvSaveAutomation;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAutomationDetailBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.btDeleteAutomation = (android.widget.Button) bindings[8];
        this.executiveAdd = (android.widget.ImageView) bindings[6];
        this.executiveList = (android.support.v7.widget.RecyclerView) bindings[7];
        this.ivBack = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.triggerAdd = (android.widget.ImageView) bindings[4];
        this.triggerList = (android.support.v7.widget.RecyclerView) bindings[5];
        this.tvAutomationName = (android.widget.TextView) bindings[1];
        this.tvSaveAutomation = (android.widget.TextView) bindings[3];
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
    public static ActivityAutomationDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAutomationDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAutomationDetailBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_automation_detail, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAutomationDetailBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAutomationDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_automation_detail, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAutomationDetailBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAutomationDetailBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_automation_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAutomationDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}