// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.main;

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

public class DetectionAdapter$ItemHolderChild_ViewBinding implements Unbinder {
  private DetectionAdapter.ItemHolderChild target;

  @UiThread
  public DetectionAdapter$ItemHolderChild_ViewBinding(DetectionAdapter.ItemHolderChild target,
      View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.item_child_icon, "field 'mIcon'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.item_child_name, "field 'mName'", TextView.class);
    target.mTick = Utils.findRequiredViewAsType(source, R.id.item_child_tick, "field 'mTick'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetectionAdapter.ItemHolderChild target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mName = null;
    target.mTick = null;
  }
}
