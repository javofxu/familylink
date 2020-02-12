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

public class PrivacyPolicyDialog_ViewBinding implements Unbinder {
  private PrivacyPolicyDialog target;

  @UiThread
  public PrivacyPolicyDialog_ViewBinding(PrivacyPolicyDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PrivacyPolicyDialog_ViewBinding(PrivacyPolicyDialog target, View source) {
    this.target = target;

    target.tvMsg = Utils.findRequiredViewAsType(source, R.id.tv_tip, "field 'tvMsg'", TextView.class);
    target.tvDialogCancel = Utils.findRequiredViewAsType(source, R.id.tv_dialog_cancel, "field 'tvDialogCancel'", TextView.class);
    target.tvDialogConfirm = Utils.findRequiredViewAsType(source, R.id.tv_dialog_confirm, "field 'tvDialogConfirm'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PrivacyPolicyDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvMsg = null;
    target.tvDialogCancel = null;
    target.tvDialogConfirm = null;
  }
}
