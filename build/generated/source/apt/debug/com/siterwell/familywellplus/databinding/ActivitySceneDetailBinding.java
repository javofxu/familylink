package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivitySceneDetailBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.scene_mode_name, 1);
        sViewsWithIds.put(R.id.iv_scene_back, 2);
        sViewsWithIds.put(R.id.tv_scene_setting, 3);
        sViewsWithIds.put(R.id.scene_has_auto, 4);
        sViewsWithIds.put(R.id.scene_recycle, 5);
        sViewsWithIds.put(R.id.add_auto, 6);
        sViewsWithIds.put(R.id.scene_without_auto, 7);
        sViewsWithIds.put(R.id.scene_add_auto, 8);
    }
    // views
    @NonNull
    public final android.widget.Button addAuto;
    @NonNull
    public final android.widget.ImageView ivSceneBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.ImageView sceneAddAuto;
    @NonNull
    public final android.widget.LinearLayout sceneHasAuto;
    @NonNull
    public final android.widget.TextView sceneModeName;
    @NonNull
    public final android.support.v7.widget.RecyclerView sceneRecycle;
    @NonNull
    public final android.widget.LinearLayout sceneWithoutAuto;
    @NonNull
    public final android.widget.TextView tvSceneSetting;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySceneDetailBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.addAuto = (android.widget.Button) bindings[6];
        this.ivSceneBack = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.sceneAddAuto = (android.widget.ImageView) bindings[8];
        this.sceneHasAuto = (android.widget.LinearLayout) bindings[4];
        this.sceneModeName = (android.widget.TextView) bindings[1];
        this.sceneRecycle = (android.support.v7.widget.RecyclerView) bindings[5];
        this.sceneWithoutAuto = (android.widget.LinearLayout) bindings[7];
        this.tvSceneSetting = (android.widget.TextView) bindings[3];
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
    public static ActivitySceneDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySceneDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivitySceneDetailBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_scene_detail, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivitySceneDetailBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySceneDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_scene_detail, null, false), bindingComponent);
    }
    @NonNull
    public static ActivitySceneDetailBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivitySceneDetailBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_scene_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivitySceneDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}