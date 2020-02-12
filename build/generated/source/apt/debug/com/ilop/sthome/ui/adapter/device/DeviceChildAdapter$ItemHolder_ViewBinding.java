// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

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

public class DeviceChildAdapter$ItemHolder_ViewBinding implements Unbinder {
  private DeviceChildAdapter.ItemHolder target;

  @UiThread
  public DeviceChildAdapter$ItemHolder_ViewBinding(DeviceChildAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.item_device_icon, "field 'mIcon'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.item_device_name, "field 'mName'", TextView.class);
    target.mType = Utils.findRequiredViewAsType(source, R.id.item_device_type, "field 'mType'", TextView.class);
    target.mStatus = Utils.findRequiredViewAsType(source, R.id.item_device_status, "field 'mStatus'", TextView.class);
    target.mColor = Utils.findRequiredView(source, R.id.item_device_color, "field 'mColor'");
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceChildAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mName = null;
    target.mType = null;
    target.mStatus = null;
    target.mColor = null;
  }
}
