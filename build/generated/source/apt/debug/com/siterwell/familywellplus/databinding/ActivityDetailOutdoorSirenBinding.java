package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDetailOutdoorSirenBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.go_back, 1);
        sViewsWithIds.put(R.id.detail_icon, 2);
        sViewsWithIds.put(R.id.detail_name, 3);
        sViewsWithIds.put(R.id.signalPosition, 4);
        sViewsWithIds.put(R.id.quantityPosition, 5);
        sViewsWithIds.put(R.id.quantity_desc, 6);
        sViewsWithIds.put(R.id.detail_layout, 7);
        sViewsWithIds.put(R.id.showStatus, 8);
        sViewsWithIds.put(R.id.detail_test, 9);
        sViewsWithIds.put(R.id.operation, 10);
        sViewsWithIds.put(R.id.volume, 11);
        sViewsWithIds.put(R.id.detail_history, 12);
        sViewsWithIds.put(R.id.tab_details, 13);
        sViewsWithIds.put(R.id.detail_edit, 14);
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
    public final android.widget.ImageView goBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.Button operation;
    @NonNull
    public final android.widget.TextView quantityDesc;
    @NonNull
    public final android.widget.ImageView quantityPosition;
    @NonNull
    public final android.widget.TextView showStatus;
    @NonNull
    public final android.widget.ImageView signalPosition;
    @NonNull
    public final android.widget.LinearLayout tabDetails;
    @NonNull
    public final android.widget.Button volume;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailOutdoorSirenBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.detailEdit = (android.widget.LinearLayout) bindings[14];
        this.detailHistory = (android.widget.LinearLayout) bindings[12];
        this.detailIcon = (android.widget.ImageView) bindings[2];
        this.detailLayout = (android.view.View) bindings[7];
        this.detailName = (com.example.common.view.CustomTextView) bindings[3];
        this.detailTest = (android.widget.LinearLayout) bindings[9];
        this.goBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.operation = (android.widget.Button) bindings[10];
        this.quantityDesc = (android.widget.TextView) bindings[6];
        this.quantityPosition = (android.widget.ImageView) bindings[5];
        this.showStatus = (android.widget.TextView) bindings[8];
        this.signalPosition = (android.widget.ImageView) bindings[4];
        this.tabDetails = (android.widget.LinearLayout) bindings[13];
        this.volume = (android.widget.Button) bindings[11];
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
    public static ActivityDetailOutdoorSirenBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailOutdoorSirenBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailOutdoorSirenBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_detail_outdoor_siren, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDetailOutdoorSirenBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailOutdoorSirenBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_detail_outdoor_siren, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDetailOutdoorSirenBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailOutdoorSirenBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_outdoor_siren_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailOutdoorSirenBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}