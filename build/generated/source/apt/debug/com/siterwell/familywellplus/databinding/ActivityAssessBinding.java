package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAssessBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.assess_back, 1);
        sViewsWithIds.put(R.id.assess_scroll, 2);
        sViewsWithIds.put(R.id.assess_ring, 3);
        sViewsWithIds.put(R.id.assess_msg, 4);
        sViewsWithIds.put(R.id.assess_list, 5);
        sViewsWithIds.put(R.id.assess_empty, 6);
        sViewsWithIds.put(R.id.assess_start, 7);
    }
    // views
    @NonNull
    public final android.widget.ImageView assessBack;
    @NonNull
    public final android.widget.LinearLayout assessEmpty;
    @NonNull
    public final android.support.v7.widget.RecyclerView assessList;
    @NonNull
    public final android.widget.TextView assessMsg;
    @NonNull
    public final com.ilop.sthome.view.RingProgressView assessRing;
    @NonNull
    public final android.widget.ScrollView assessScroll;
    @NonNull
    public final android.widget.Button assessStart;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAssessBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.assessBack = (android.widget.ImageView) bindings[1];
        this.assessEmpty = (android.widget.LinearLayout) bindings[6];
        this.assessList = (android.support.v7.widget.RecyclerView) bindings[5];
        this.assessMsg = (android.widget.TextView) bindings[4];
        this.assessRing = (com.ilop.sthome.view.RingProgressView) bindings[3];
        this.assessScroll = (android.widget.ScrollView) bindings[2];
        this.assessStart = (android.widget.Button) bindings[7];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
    public static ActivityAssessBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAssessBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAssessBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_assess, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAssessBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAssessBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_assess, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAssessBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAssessBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_assess_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAssessBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}