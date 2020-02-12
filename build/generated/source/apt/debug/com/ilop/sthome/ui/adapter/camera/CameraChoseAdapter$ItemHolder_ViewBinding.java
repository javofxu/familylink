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

public class CameraChoseAdapter$ItemHolder_ViewBinding implements Unbinder {
  private CameraChoseAdapter.ItemHolder target;

  @UiThread
  public CameraChoseAdapter$ItemHolder_ViewBinding(CameraChoseAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mImage = Utils.findRequiredViewAsType(source, R.id.item_camera_bg, "field 'mImage'", ImageView.class);
    target.mPlay = Utils.findRequiredViewAsType(source, R.id.item_camera_play, "field 'mPlay'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.item_camera_name, "field 'mName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CameraChoseAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;
    target.mPlay = null;
    target.mName = null;
  }
}
