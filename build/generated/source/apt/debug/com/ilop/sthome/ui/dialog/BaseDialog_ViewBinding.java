// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseDialog_ViewBinding implements Unbinder {
  private BaseDialog target;

  @UiThread
  public BaseDialog_ViewBinding(BaseDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseDialog_ViewBinding(BaseDialog target, View source) {
    this.target = target;

    target.tvTip = Utils.findRequiredViewAsType(source, R.id.tv_tip, "field 'tvTip'", TextView.class);
    target.tvDialogCancel = Utils.findRequiredViewAsType(source, R.id.tv_dialog_cancel, "field 'tvDialogCancel'", TextView.class);
    target.tvDialogConfirm = Utils.findRequiredViewAsType(source, R.id.tv_dialog_confirm, "field 'tvDialogConfirm'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTip = null;
    target.tvDialogCancel = null;
    target.tvDialogConfirm = null;
  }
}
