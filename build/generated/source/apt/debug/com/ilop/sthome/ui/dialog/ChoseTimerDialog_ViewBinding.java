// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChoseTimerDialog_ViewBinding implements Unbinder {
  private ChoseTimerDialog target;

  @UiThread
  public ChoseTimerDialog_ViewBinding(ChoseTimerDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChoseTimerDialog_ViewBinding(ChoseTimerDialog target, View source) {
    this.target = target;

    target.ivSetTimer = Utils.findRequiredViewAsType(source, R.id.iv_set_timer, "field 'ivSetTimer'", ImageView.class);
    target.timerHour = Utils.findRequiredViewAsType(source, R.id.timer_minute, "field 'timerHour'", WheelView.class);
    target.timerMinute = Utils.findRequiredViewAsType(source, R.id.timer_second, "field 'timerMinute'", WheelView.class);
    target.weekTrigger = Utils.findRequiredViewAsType(source, R.id.week_trigger, "field 'weekTrigger'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChoseTimerDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivSetTimer = null;
    target.timerHour = null;
    target.timerMinute = null;
    target.weekTrigger = null;
  }
}
