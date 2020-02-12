// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.scene;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SceneChildAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SceneChildAdapter.ItemHolder target;

  @UiThread
  public SceneChildAdapter$ItemHolder_ViewBinding(SceneChildAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.scene_icon, "field 'mIcon'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.scene_child_name, "field 'mName'", CustomTextView.class);
    target.mMore = Utils.findRequiredViewAsType(source, R.id.scene_child_more, "field 'mMore'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.scene_child_bg, "field 'mLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneChildAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mName = null;
    target.mMore = null;
    target.mLayout = null;
  }
}
