// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AssessAdapter$ItemHolder_ViewBinding implements Unbinder {
  private AssessAdapter.ItemHolder target;

  @UiThread
  public AssessAdapter$ItemHolder_ViewBinding(AssessAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mIcon = Utils.findRequiredViewAsType(source, R.id.item_detection_icon, "field 'mIcon'", ImageView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.item_detection_title, "field 'mTitle'", CustomTextView.class);
    target.mLists = Utils.findRequiredViewAsType(source, R.id.item_detection_list, "field 'mLists'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AssessAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIcon = null;
    target.mTitle = null;
    target.mLists = null;
  }
}
