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

public class GatewayListAdapter$ItemHolder_ViewBinding implements Unbinder {
  private GatewayListAdapter.ItemHolder target;

  @UiThread
  public GatewayListAdapter$ItemHolder_ViewBinding(GatewayListAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_gateway_name, "field 'tvName'", TextView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tv_gateway_status, "field 'tvStatus'", TextView.class);
    target.ivStatus = Utils.findRequiredViewAsType(source, R.id.iv_gateway_status, "field 'ivStatus'", ImageView.class);
    target.mState = Utils.findRequiredViewAsType(source, R.id.iv_gateway_state, "field 'mState'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GatewayListAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvName = null;
    target.tvStatus = null;
    target.ivStatus = null;
    target.mState = null;
  }
}
