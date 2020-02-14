package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityMainBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.main_toolbar, 1);
        sViewsWithIds.put(R.id.main_add_scene, 2);
        sViewsWithIds.put(R.id.main_container, 3);
        sViewsWithIds.put(R.id.main_radio, 4);
        sViewsWithIds.put(R.id.main_device, 5);
        sViewsWithIds.put(R.id.main_scene, 6);
        sViewsWithIds.put(R.id.main_message, 7);
        sViewsWithIds.put(R.id.main_user_img, 8);
        sViewsWithIds.put(R.id.main_nick_name, 9);
        sViewsWithIds.put(R.id.main_login_name, 10);
        sViewsWithIds.put(R.id.main_person, 11);
        sViewsWithIds.put(R.id.main_instructions, 12);
        sViewsWithIds.put(R.id.main_assess, 13);
        sViewsWithIds.put(R.id.main_setting, 14);
        sViewsWithIds.put(R.id.main_about, 15);
        sViewsWithIds.put(R.id.main_login_out, 16);
    }
    // views
    @NonNull
    public final android.support.v4.widget.DrawerLayout drawerLayout;
    @NonNull
    public final android.widget.LinearLayout mainAbout;
    @NonNull
    public final android.widget.ImageView mainAddScene;
    @NonNull
    public final android.widget.LinearLayout mainAssess;
    @NonNull
    public final android.widget.FrameLayout mainContainer;
    @NonNull
    public final android.widget.RadioButton mainDevice;
    @NonNull
    public final android.widget.LinearLayout mainInstructions;
    @NonNull
    public final android.widget.TextView mainLoginName;
    @NonNull
    public final android.widget.Button mainLoginOut;
    @NonNull
    public final android.widget.RadioButton mainMessage;
    @NonNull
    public final com.example.common.view.CustomTextView mainNickName;
    @NonNull
    public final android.widget.LinearLayout mainPerson;
    @NonNull
    public final android.widget.RadioGroup mainRadio;
    @NonNull
    public final android.widget.RadioButton mainScene;
    @NonNull
    public final android.widget.LinearLayout mainSetting;
    @NonNull
    public final android.support.v7.widget.Toolbar mainToolbar;
    @NonNull
    public final android.widget.ImageView mainUserImg;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds);
        this.drawerLayout = (android.support.v4.widget.DrawerLayout) bindings[0];
        this.drawerLayout.setTag(null);
        this.mainAbout = (android.widget.LinearLayout) bindings[15];
        this.mainAddScene = (android.widget.ImageView) bindings[2];
        this.mainAssess = (android.widget.LinearLayout) bindings[13];
        this.mainContainer = (android.widget.FrameLayout) bindings[3];
        this.mainDevice = (android.widget.RadioButton) bindings[5];
        this.mainInstructions = (android.widget.LinearLayout) bindings[12];
        this.mainLoginName = (android.widget.TextView) bindings[10];
        this.mainLoginOut = (android.widget.Button) bindings[16];
        this.mainMessage = (android.widget.RadioButton) bindings[7];
        this.mainNickName = (com.example.common.view.CustomTextView) bindings[9];
        this.mainPerson = (android.widget.LinearLayout) bindings[11];
        this.mainRadio = (android.widget.RadioGroup) bindings[4];
        this.mainScene = (android.widget.RadioButton) bindings[6];
        this.mainSetting = (android.widget.LinearLayout) bindings[14];
        this.mainToolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.mainUserImg = (android.widget.ImageView) bindings[8];
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
    public static ActivityMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityMainBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_main, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityMainBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityMainBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_main, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityMainBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityMainBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_main_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityMainBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}