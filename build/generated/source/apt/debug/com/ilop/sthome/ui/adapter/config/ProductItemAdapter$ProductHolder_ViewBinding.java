// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.config;

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

public class ProductItemAdapter$ProductHolder_ViewBinding implements Unbinder {
  private ProductItemAdapter.ProductHolder target;

  @UiThread
  public ProductItemAdapter$ProductHolder_ViewBinding(ProductItemAdapter.ProductHolder target,
      View source) {
    this.target = target;

    target.ivProductIcon = Utils.findRequiredViewAsType(source, R.id.iv_product_icon, "field 'ivProductIcon'", ImageView.class);
    target.tvProductName = Utils.findRequiredViewAsType(source, R.id.tv_product_name, "field 'tvProductName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductItemAdapter.ProductHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivProductIcon = null;
    target.tvProductName = null;
  }
}
