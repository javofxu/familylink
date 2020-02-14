package com.siterwell.familywellplus.databinding;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityLocalpicvideoBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.iv_record_back, 2);
        sViewsWithIds.put(R.id.show_pic, 3);
        sViewsWithIds.put(R.id.show_video, 4);
        sViewsWithIds.put(R.id.show_pic_line, 5);
        sViewsWithIds.put(R.id.show_video_line, 6);
        sViewsWithIds.put(R.id.vPager, 7);
    }
    // views
    @NonNull
    public final android.widget.ImageView ivRecordBack;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    public final android.widget.Button showPic;
    @NonNull
    public final android.widget.Button showPicLine;
    @NonNull
    public final android.widget.Button showVideo;
    @NonNull
    public final android.widget.Button showVideoLine;
    @NonNull
    public final android.widget.RelativeLayout toolbar;
    @NonNull
    public final com.ilop.sthome.view.CustomViewPager vPager;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLocalpicvideoBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.ivRecordBack = (android.widget.ImageView) bindings[2];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.showPic = (android.widget.Button) bindings[3];
        this.showPicLine = (android.widget.Button) bindings[5];
        this.showVideo = (android.widget.Button) bindings[4];
        this.showVideoLine = (android.widget.Button) bindings[6];
        this.toolbar = (android.widget.RelativeLayout) bindings[1];
        this.vPager = (com.ilop.sthome.view.CustomViewPager) bindings[7];
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
    public static ActivityLocalpicvideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityLocalpicvideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityLocalpicvideoBinding>inflate(inflater, com.siterwell.familywellplus.R.layout.activity_localpicvideo, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityLocalpicvideoBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityLocalpicvideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.siterwell.familywellplus.R.layout.activity_localpicvideo, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityLocalpicvideoBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityLocalpicvideoBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_localpicvideo_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityLocalpicvideoBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}