package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityRoomEditorBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_room_back, 1);
        sViewsWithIds.put(R.id.tv_save_room, 2);
        sViewsWithIds.put(R.id.et_room_name, 3);
        sViewsWithIds.put(R.id.tv_room_name1, 4);
        sViewsWithIds.put(R.id.tv_room_name2, 5);
        sViewsWithIds.put(R.id.tv_room_name3, 6);
        sViewsWithIds.put(R.id.tv_room_name4, 7);
        sViewsWithIds.put(R.id.bt_delete_room, 8);
    }
    // views
    @NonNull
    public final android.widget.Button btDeleteRoom;
    @NonNull
    public final android.widget.EditText etRoomName;
    @NonNull
    public final android.widget.ImageView ivRoomBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView tvRoomName1;
    @NonNull
    public final android.widget.TextView tvRoomName2;
    @NonNull
    public final android.widget.TextView tvRoomName3;
    @NonNull
    public final android.widget.TextView tvRoomName4;
    @NonNull
    public final android.widget.TextView tvSaveRoom;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityRoomEditorBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.btDeleteRoom = (android.widget.Button) bindings[8];
        this.etRoomName = (android.widget.EditText) bindings[3];
        this.ivRoomBack = (android.widget.ImageView) bindings[1];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvRoomName1 = (android.widget.TextView) bindings[4];
        this.tvRoomName2 = (android.widget.TextView) bindings[5];
        this.tvRoomName3 = (android.widget.TextView) bindings[6];
        this.tvRoomName4 = (android.widget.TextView) bindings[7];
        this.tvSaveRoom = (android.widget.TextView) bindings[2];
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
    public static ActivityRoomEditorBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRoomEditorBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityRoomEditorBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_room_editor, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityRoomEditorBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRoomEditorBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_room_editor, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityRoomEditorBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityRoomEditorBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_room_editor_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityRoomEditorBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}