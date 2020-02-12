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

public class ChoseColorAdapter$ItemHolder_ViewBinding implements Unbinder {
  private ChoseColorAdapter.ItemHolder target;

  @UiThread
  public ChoseColorAdapter$ItemHolder_ViewBinding(ChoseColorAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mImage = Utils.findRequiredViewAsType(source, R.id.cc_image_color, "field 'mImage'", TextView.class);
    target.mTick = Utils.findRequiredViewAsType(source, R.id.iv_color_tick, "field 'mTick'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChoseColorAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;
    target.mTick = null;
  }
}
