// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.scene;

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

public class ChoseDeviceAdapter$ItemHolder_ViewBinding implements Unbinder {
  private ChoseDeviceAdapter.ItemHolder target;

  @UiThread
  public ChoseDeviceAdapter$ItemHolder_ViewBinding(ChoseDeviceAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mImg = Utils.findRequiredViewAsType(source, R.id.productImg, "field 'mImg'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.productName, "field 'mName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChoseDeviceAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImg = null;
    target.mName = null;
  }
}
