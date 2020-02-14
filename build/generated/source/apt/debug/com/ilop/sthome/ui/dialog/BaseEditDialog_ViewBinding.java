// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseEditDialog_ViewBinding implements Unbinder {
  private BaseEditDialog target;

  @UiThread
  public BaseEditDialog_ViewBinding(BaseEditDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseEditDialog_ViewBinding(BaseEditDialog target, View source) {
    this.target = target;

    target.etInput = Utils.findRequiredViewAsType(source, R.id.et_input, "field 'etInput'", EditText.class);
    target.mEyes = Utils.findRequiredViewAsType(source, R.id.iv_eyes, "field 'mEyes'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvTip = Utils.findRequiredViewAsType(source, R.id.tv_tip, "field 'tvTip'", TextView.class);
    target.tvDialogCancel = Utils.findRequiredViewAsType(source, R.id.tv_dialog_cancel, "field 'tvDialogCancel'", TextView.class);
    target.tvDialogConfirm = Utils.findRequiredViewAsType(source, R.id.tv_dialog_confirm, "field 'tvDialogConfirm'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseEditDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etInput = null;
    target.mEyes = null;
    target.tvTitle = null;
    target.tvTip = null;
    target.tvDialogCancel = null;
    target.tvDialogConfirm = null;
  }
}
