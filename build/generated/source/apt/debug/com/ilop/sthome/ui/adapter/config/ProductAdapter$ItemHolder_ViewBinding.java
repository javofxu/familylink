// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.config;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductAdapter$ItemHolder_ViewBinding implements Unbinder {
  private ProductAdapter.ItemHolder target;

  @UiThread
  public ProductAdapter$ItemHolder_ViewBinding(ProductAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mName = Utils.findRequiredViewAsType(source, R.id.tv_product_type, "field 'mName'", TextView.class);
    target.mGrid = Utils.findRequiredViewAsType(source, R.id.rv_product_list, "field 'mGrid'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mName = null;
    target.mGrid = null;
  }
}
