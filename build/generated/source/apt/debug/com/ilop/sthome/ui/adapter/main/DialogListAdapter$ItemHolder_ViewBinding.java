// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DialogListAdapter$ItemHolder_ViewBinding implements Unbinder {
  private DialogListAdapter.ItemHolder target;

  @UiThread
  public DialogListAdapter$ItemHolder_ViewBinding(DialogListAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.tvItemMsg = Utils.findRequiredViewAsType(source, R.id.tv_item_msg, "field 'tvItemMsg'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DialogListAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvItemMsg = null;
  }
}
