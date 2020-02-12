// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.device;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoryDetailAdapter$ItemHolder_ViewBinding implements Unbinder {
  private HistoryDetailAdapter.ItemHolder target;

  @UiThread
  public HistoryDetailAdapter$ItemHolder_ViewBinding(HistoryDetailAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mImage = Utils.findRequiredViewAsType(source, R.id.iv_history_image, "field 'mImage'", ImageView.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.tv_history_name, "field 'mName'", CustomTextView.class);
    target.mTime = Utils.findRequiredViewAsType(source, R.id.tv_history_time, "field 'mTime'", TextView.class);
    target.mStatus = Utils.findRequiredViewAsType(source, R.id.tv_history_status, "field 'mStatus'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryDetailAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;
    target.mName = null;
    target.mTime = null;
    target.mStatus = null;
  }
}
