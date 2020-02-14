// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.camera;

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

public class LocalNetIPCAdapter$ItemHolder_ViewBinding implements Unbinder {
  private LocalNetIPCAdapter.ItemHolder target;

  @UiThread
  public LocalNetIPCAdapter$ItemHolder_ViewBinding(LocalNetIPCAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.subMonitor = Utils.findRequiredViewAsType(source, R.id.sub_monitor, "field 'subMonitor'", ImageView.class);
    target.monitorName = Utils.findRequiredViewAsType(source, R.id.monitor_name, "field 'monitorName'", TextView.class);
    target.monitorSn = Utils.findRequiredViewAsType(source, R.id.monitor_sn, "field 'monitorSn'", TextView.class);
    target.monitorIp = Utils.findRequiredViewAsType(source, R.id.monitor_ip, "field 'monitorIp'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LocalNetIPCAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.subMonitor = null;
    target.monitorName = null;
    target.monitorSn = null;
    target.monitorIp = null;
  }
}
