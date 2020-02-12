// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.scene;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SceneChangeAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SceneChangeAdapter.ItemHolder target;

  @UiThread
  public SceneChangeAdapter$ItemHolder_ViewBinding(SceneChangeAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.iv_scene_icon, "field 'mIcon'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.tv_scene_name, "field 'mName'", TextView.class);
    target.mChange = Utils.findRequiredViewAsType(source, R.id.iv_scene_change, "field 'mChange'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneChangeAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mName = null;
    target.mChange = null;
  }
}
