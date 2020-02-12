// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceAdapter$ItemHolder_ViewBinding implements Unbinder {
  private DeviceAdapter.ItemHolder target;

  @UiThread
  public DeviceAdapter$ItemHolder_ViewBinding(DeviceAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mRecycle = Utils.findRequiredViewAsType(source, R.id.item_device_child_list, "field 'mRecycle'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecycle = null;
  }
}
