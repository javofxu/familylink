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

public class SetUpAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SetUpAdapter.ItemHolder target;

  @UiThread
  public SetUpAdapter$ItemHolder_ViewBinding(SetUpAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mName = Utils.findRequiredViewAsType(source, R.id.item_set_up_name, "field 'mName'", TextView.class);
    target.mState = Utils.findRequiredViewAsType(source, R.id.item_set_up_state, "field 'mState'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SetUpAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mName = null;
    target.mState = null;
  }
}
