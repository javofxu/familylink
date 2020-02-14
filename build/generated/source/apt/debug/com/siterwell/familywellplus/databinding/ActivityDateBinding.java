package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDateBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_select_date_back, 1);
        sViewsWithIds.put(R.id.iv_select_date_save, 2);
        sViewsWithIds.put(R.id.iv_left, 3);
        sViewsWithIds.put(R.id.iv_right, 4);
        sViewsWithIds.put(R.id.date_text, 5);
        sViewsWithIds.put(R.id.monthDateView, 6);
    }
    // views
    @NonNull
    public final android.widget.TextView dateText;
    @NonNull
    public final android.widget.ImageView ivLeft;
    @NonNull
    public final android.widget.ImageView ivRight;
    @NonNull
    public final android.widget.ImageView ivSelectDateBack;
    @NonNull
    public final android.widget.ImageView ivSelectDateSave;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.MonthDateView monthDateView;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDateBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.dateText = (android.widget.TextView) bindings[5];
        this.ivLeft = (android.widget.ImageView) bindings[3];
        this.ivRight = (android.widget.ImageView) bindings[4];
        this.ivSelectDateBack = (android.widget.ImageView) bindings[1];
        this.ivSelectDateSave = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.monthDateView = (com.ilop.sthome.view.MonthDateView) bindings[6];
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
    public static ActivityDateBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDateBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDateBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_date, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDateBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDateBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_date, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDateBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDateBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_date_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDateBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}