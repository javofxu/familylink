package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDetailSocketBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.detail_icon, 1);
        sViewsWithIds.put(R.id.detail_name, 2);
        sViewsWithIds.put(R.id.signalPosition, 3);
        sViewsWithIds.put(R.id.detail_layout, 4);
        sViewsWithIds.put(R.id.showStatus, 5);
        sViewsWithIds.put(R.id.detail_test, 6);
        sViewsWithIds.put(R.id.operation, 7);
        sViewsWithIds.put(R.id.detail_history, 8);
        sViewsWithIds.put(R.id.tab_details, 9);
        sViewsWithIds.put(R.id.detail_edit, 10);
    }
    // views
    @NonNull
    public final android.widget.LinearLayout detailEdit;
    @NonNull
    public final android.widget.LinearLayout detailHistory;
    @NonNull
    public final android.widget.ImageView detailIcon;
    @NonNull
    public final android.view.View detailLayout;
    @NonNull
    public final com.example.common.view.CustomTextView detailName;
    @NonNull
    public final android.widget.LinearLayout detailTest;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ImageView operation;
    @NonNull
    public final android.widget.TextView showStatus;
    @NonNull
    public final android.widget.ImageView signalPosition;
    @NonNull
    public final android.widget.LinearLayout tabDetails;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailSocketBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.detailEdit = (android.widget.LinearLayout) bindings[10];
        this.detailHistory = (android.widget.LinearLayout) bindings[8];
        this.detailIcon = (android.widget.ImageView) bindings[1];
        this.detailLayout = (android.view.View) bindings[4];
        this.detailName = (com.example.common.view.CustomTextView) bindings[2];
        this.detailTest = (android.widget.LinearLayout) bindings[6];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.operation = (android.widget.ImageView) bindings[7];
        this.showStatus = (android.widget.TextView) bindings[5];
        this.signalPosition = (android.widget.ImageView) bindings[3];
        this.tabDetails = (android.widget.LinearLayout) bindings[9];
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
    public static ActivityDetailSocketBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailSocketBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailSocketBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_detail_socket, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDetailSocketBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailSocketBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_detail_socket, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDetailSocketBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailSocketBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_socket_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailSocketBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}