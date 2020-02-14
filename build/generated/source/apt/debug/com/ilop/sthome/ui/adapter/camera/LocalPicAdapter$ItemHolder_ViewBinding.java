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

public class LocalPicAdapter$ItemHolder_ViewBinding implements Unbinder {
  private LocalPicAdapter.ItemHolder target;

  @UiThread
  public LocalPicAdapter$ItemHolder_ViewBinding(LocalPicAdapter.ItemHolder target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.iv_push_result_checked, "field 'imageView'", ImageView.class);
    target.textView_name = Utils.findRequiredViewAsType(source, R.id.tv_push_result_event, "field 'textView_name'", TextView.class);
    target.textView_time = Utils.findRequiredViewAsType(source, R.id.tv_push_result_time, "field 'textView_time'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LocalPicAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.textView_name = null;
    target.textView_time = null;
  }
}
