// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubDeviceAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SubDeviceAdapter.ItemHolder target;

  @UiThread
  public SubDeviceAdapter$ItemHolder_ViewBinding(SubDeviceAdapter.ItemHolder target, View source) {
    this.target = target;

    target.childIcon = Utils.findRequiredViewAsType(source, R.id.child_icon, "field 'childIcon'", ImageView.class);
    target.childName = Utils.findRequiredViewAsType(source, R.id.child_name, "field 'childName'", TextView.class);
    target.childStatus = Utils.findRequiredViewAsType(source, R.id.child_status, "field 'childStatus'", TextView.class);
    target.childSwitch = Utils.findRequiredViewAsType(source, R.id.child_socket, "field 'childSwitch'", ImageView.class);
    target.childLayout = Utils.findRequiredViewAsType(source, R.id.child_layout, "field 'childLayout'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubDeviceAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.childIcon = null;
    target.childName = null;
    target.childStatus = null;
    target.childSwitch = null;
    target.childLayout = null;
  }
}
