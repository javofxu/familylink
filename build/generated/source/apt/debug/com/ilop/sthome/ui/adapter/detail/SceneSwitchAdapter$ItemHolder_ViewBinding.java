// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.detail;

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

public class SceneSwitchAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SceneSwitchAdapter.ItemHolder target;

  @UiThread
  public SceneSwitchAdapter$ItemHolder_ViewBinding(SceneSwitchAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.srcImg = Utils.findRequiredViewAsType(source, R.id.src_img, "field 'srcImg'", ImageView.class);
    target.srcName = Utils.findRequiredViewAsType(source, R.id.src_name, "field 'srcName'", TextView.class);
    target.desImg = Utils.findRequiredViewAsType(source, R.id.des_img, "field 'desImg'", ImageView.class);
    target.desName = Utils.findRequiredViewAsType(source, R.id.des_name, "field 'desName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneSwitchAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.srcImg = null;
    target.srcName = null;
    target.desImg = null;
    target.desName = null;
  }
}
