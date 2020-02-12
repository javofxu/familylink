// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeekAdapter$ItemHolder_ViewBinding implements Unbinder {
  private WeekAdapter.ItemHolder target;

  @UiThread
  public WeekAdapter$ItemHolder_ViewBinding(WeekAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mWeek = Utils.findRequiredViewAsType(source, R.id.wek_item, "field 'mWeek'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WeekAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mWeek = null;
  }
}
