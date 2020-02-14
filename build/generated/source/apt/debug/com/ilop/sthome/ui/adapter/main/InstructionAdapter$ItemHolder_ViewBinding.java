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

public class InstructionAdapter$ItemHolder_ViewBinding implements Unbinder {
  private InstructionAdapter.ItemHolder target;

  @UiThread
  public InstructionAdapter$ItemHolder_ViewBinding(InstructionAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.iv_instruction_icon, "field 'mIcon'", ImageView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.tv_instruction_title, "field 'mTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InstructionAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mTitle = null;
  }
}
