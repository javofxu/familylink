package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityPersonalBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_personal_back, 1);
        sViewsWithIds.put(R.id.tv_save_personal, 2);
        sViewsWithIds.put(R.id.user_img, 3);
        sViewsWithIds.put(R.id.iv_user_img, 4);
        sViewsWithIds.put(R.id.user_nickname, 5);
        sViewsWithIds.put(R.id.user_account, 6);
        sViewsWithIds.put(R.id.user_phone, 7);
        sViewsWithIds.put(R.id.user_update_phone, 8);
        sViewsWithIds.put(R.id.user_mail, 9);
        sViewsWithIds.put(R.id.user_update_email, 10);
    }
    // views
    @NonNull
    public final android.widget.ImageView ivPersonalBack;
    @NonNull
    public final android.widget.ImageView ivUserImg;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.TextView tvSavePersonal;
    @NonNull
    public final android.widget.TextView userAccount;
    @NonNull
    public final android.widget.ImageView userImg;
    @NonNull
    public final android.widget.EditText userMail;
    @NonNull
    public final android.widget.EditText userNickname;
    @NonNull
    public final android.widget.EditText userPhone;
    @NonNull
    public final android.widget.ImageView userUpdateEmail;
    @NonNull
    public final android.widget.ImageView userUpdatePhone;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPersonalBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.ivPersonalBack = (android.widget.ImageView) bindings[1];
        this.ivUserImg = (android.widget.ImageView) bindings[4];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvSavePersonal = (android.widget.TextView) bindings[2];
        this.userAccount = (android.widget.TextView) bindings[6];
        this.userImg = (android.widget.ImageView) bindings[3];
        this.userMail = (android.widget.EditText) bindings[9];
        this.userNickname = (android.widget.EditText) bindings[5];
        this.userPhone = (android.widget.EditText) bindings[7];
        this.userUpdateEmail = (android.widget.ImageView) bindings[10];
        this.userUpdatePhone = (android.widget.ImageView) bindings[8];
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
    public static ActivityPersonalBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPersonalBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityPersonalBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_personal, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityPersonalBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPersonalBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_personal, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityPersonalBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityPersonalBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_personal_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityPersonalBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}