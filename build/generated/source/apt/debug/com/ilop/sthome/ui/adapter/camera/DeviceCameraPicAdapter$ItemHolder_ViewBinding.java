// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.camera;

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

public class DeviceCameraPicAdapter$ItemHolder_ViewBinding implements Unbinder {
  private DeviceCameraPicAdapter.ItemHolder target;

  @UiThread
  public DeviceCameraPicAdapter$ItemHolder_ViewBinding(DeviceCameraPicAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.baseLayout = Utils.findRequiredViewAsType(source, R.id.rl_push_result_layout, "field 'baseLayout'", RelativeLayout.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.iv_push_result_checked, "field 'image'", ImageView.class);
    target.id = Utils.findRequiredViewAsType(source, R.id.tv_push_result_id, "field 'id'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.tv_push_result_time, "field 'time'", TextView.class);
    target.event = Utils.findRequiredViewAsType(source, R.id.tv_push_result_event, "field 'event'", TextView.class);
    target.status = Utils.findRequiredViewAsType(source, R.id.tv_push_result_status, "field 'status'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceCameraPicAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.baseLayout = null;
    target.image = null;
    target.id = null;
    target.time = null;
    target.event = null;
    target.status = null;
  }
}
