package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivitySetHumitureBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_back, 1);
        sViewsWithIds.put(R.id.tv_save_humiture, 2);
        sViewsWithIds.put(R.id.temp_style, 3);
        sViewsWithIds.put(R.id.temp_num, 4);
        sViewsWithIds.put(R.id.hum_style, 5);
        sViewsWithIds.put(R.id.hum_num, 6);
    }
    // views
    @NonNull
    public final com.ilop.sthome.view.WheelView humNum;
    @NonNull
    public final com.ilop.sthome.view.WheelView humStyle;
    @NonNull
    public final android.widget.ImageView ivBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final com.ilop.sthome.view.WheelView tempNum;
    @NonNull
    public final com.ilop.sthome.view.WheelView tempStyle;
    @NonNull
    public final android.widget.TextView tvSaveHumiture;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySetHumitureBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.humNum = (com.ilop.sthome.view.WheelView) bindings[6];
        this.humStyle = (com.ilop.sthome.view.WheelView) bindings[5];
        this.ivBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tempNum = (com.ilop.sthome.view.WheelView) bindings[4];
        this.tempStyle = (com.ilop.sthome.view.WheelView) bindings[3];
        this.tvSaveHumiture = (android.widget.TextView) bindings[2];
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
    public static ActivitySetHumitureBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetHumitureBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivitySetHumitureBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_set_humiture, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivitySetHumitureBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetHumitureBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_set_humiture, null, false), bindingComponent);
    }
    @NonNull
    public static ActivitySetHumitureBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySetHumitureBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_set_humiture_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivitySetHumitureBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}