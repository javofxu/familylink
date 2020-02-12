// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimerAdapter$ItemHolder_ViewBinding implements Unbinder {
  private TimerAdapter.ItemHolder target;

  @UiThread
  public TimerAdapter$ItemHolder_ViewBinding(TimerAdapter.ItemHolder target, View source) {
    this.target = target;

    target.tvTimeHour = Utils.findRequiredViewAsType(source, R.id.tv_time_hour, "field 'tvTimeHour'", TextView.class);
    target.tvTimeMin = Utils.findRequiredViewAsType(source, R.id.tv_time_min, "field 'tvTimeMin'", TextView.class);
    target.tvTimerMode = Utils.findRequiredViewAsType(source, R.id.tv_timer_mode, "field 'tvTimerMode'", TextView.class);
    target.tvWeekTime = Utils.findRequiredViewAsType(source, R.id.tv_week_time, "field 'tvWeekTime'", TextView.class);
    target.ibEnable = Utils.findRequiredViewAsType(source, R.id.ib_enable, "field 'ibEnable'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TimerAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTimeHour = null;
    target.tvTimeMin = null;
    target.tvTimerMode = null;
    target.tvWeekTime = null;
    target.ibEnable = null;
  }
}
