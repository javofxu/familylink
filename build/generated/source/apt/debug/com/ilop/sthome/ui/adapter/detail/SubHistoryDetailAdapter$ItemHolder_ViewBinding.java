// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.detail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubHistoryDetailAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SubHistoryDetailAdapter.ItemHolder target;

  @UiThread
  public SubHistoryDetailAdapter$ItemHolder_ViewBinding(SubHistoryDetailAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'mStatus'", CustomTextView.class);
    target.mTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'mTime'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubHistoryDetailAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mStatus = null;
    target.mTime = null;
  }
}
