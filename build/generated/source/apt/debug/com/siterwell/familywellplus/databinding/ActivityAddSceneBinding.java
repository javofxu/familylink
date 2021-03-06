package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityAddSceneBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_scene_back, 1);
        sViewsWithIds.put(R.id.tv_add_scene, 2);
        sViewsWithIds.put(R.id.et_scene_name, 3);
        sViewsWithIds.put(R.id.rv_color_list, 4);
        sViewsWithIds.put(R.id.ll_without, 5);
        sViewsWithIds.put(R.id.iv_add_gateway, 6);
        sViewsWithIds.put(R.id.scene_choose_gateway, 7);
    }
    // views
    @NonNull
    public final android.widget.EditText etSceneName;
    @NonNull
    public final android.widget.ImageView ivAddGateway;
    @NonNull
    public final android.widget.ImageView ivSceneBack;
    @NonNull
    public final android.widget.LinearLayout llWithout;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.support.v7.widget.RecyclerView rvColorList;
    @NonNull
    public final android.support.v7.widget.RecyclerView sceneChooseGateway;
    @NonNull
    public final android.widget.TextView tvAddScene;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddSceneBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.etSceneName = (android.widget.EditText) bindings[3];
        this.ivAddGateway = (android.widget.ImageView) bindings[6];
        this.ivSceneBack = (android.widget.ImageView) bindings[1];
        this.llWithout = (android.widget.LinearLayout) bindings[5];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvColorList = (android.support.v7.widget.RecyclerView) bindings[4];
        this.sceneChooseGateway = (android.support.v7.widget.RecyclerView) bindings[7];
        this.tvAddScene = (android.widget.TextView) bindings[2];
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
    public static ActivityAddSceneBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddSceneBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityAddSceneBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_add_scene, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityAddSceneBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddSceneBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_add_scene, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityAddSceneBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityAddSceneBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_add_scene_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityAddSceneBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}