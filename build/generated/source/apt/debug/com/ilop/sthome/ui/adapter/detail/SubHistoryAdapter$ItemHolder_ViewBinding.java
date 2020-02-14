// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.detail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubHistoryAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SubHistoryAdapter.ItemHolder target;

  @UiThread
  public SubHistoryAdapter$ItemHolder_ViewBinding(SubHistoryAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mMonth = Utils.findRequiredViewAsType(source, R.id.item_history_month, "field 'mMonth'", CustomTextView.class);
    target.mMum = Utils.findRequiredViewAsType(source, R.id.item_history_mum, "field 'mMum'", TextView.class);
    target.mDetail = Utils.findRequiredViewAsType(source, R.id.item_history_detail, "field 'mDetail'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubHistoryAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMonth = null;
    target.mMum = null;
    target.mDetail = null;
  }
}
