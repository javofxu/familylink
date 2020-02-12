// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseListDialog_ViewBinding implements Unbinder {
  private BaseListDialog target;

  @UiThread
  public BaseListDialog_ViewBinding(BaseListDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseListDialog_ViewBinding(BaseListDialog target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_dialog_list, "field 'mRecyclerView'", RecyclerView.class);
    target.tvDialogCancel = Utils.findRequiredViewAsType(source, R.id.tv_dialog_cancel, "field 'tvDialogCancel'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseListDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.tvDialogCancel = null;
    target.tvTitle = null;
  }
}
