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

public class TriggerInAdapter$ItemHolder_ViewBinding implements Unbinder {
  private TriggerInAdapter.ItemHolder target;

  @UiThread
  public TriggerInAdapter$ItemHolder_ViewBinding(TriggerInAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mDelay = Utils.findRequiredViewAsType(source, R.id.tv_device_delay, "field 'mDelay'", TextView.class);
    target.mImg = Utils.findRequiredViewAsType(source, R.id.item_trigger_img, "field 'mImg'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.item_trigger_name, "field 'mName'", TextView.class);
    target.mAction = Utils.findRequiredViewAsType(source, R.id.item_trigger_action, "field 'mAction'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TriggerInAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mDelay = null;
    target.mImg = null;
    target.mName = null;
    target.mAction = null;
  }
}
