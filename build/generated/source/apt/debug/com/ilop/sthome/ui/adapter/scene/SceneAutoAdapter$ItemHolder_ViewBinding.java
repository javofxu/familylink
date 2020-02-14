// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.scene;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SceneAutoAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SceneAutoAdapter.ItemHolder target;

  @UiThread
  public SceneAutoAdapter$ItemHolder_ViewBinding(SceneAutoAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mTitle = Utils.findRequiredViewAsType(source, R.id.wode, "field 'mTitle'", TextView.class);
    target.input1 = Utils.findRequiredViewAsType(source, R.id.input1, "field 'input1'", ImageView.class);
    target.input2 = Utils.findRequiredViewAsType(source, R.id.input2, "field 'input2'", ImageView.class);
    target.output1 = Utils.findRequiredViewAsType(source, R.id.output1, "field 'output1'", ImageView.class);
    target.output2 = Utils.findRequiredViewAsType(source, R.id.output2, "field 'output2'", ImageView.class);
    target.output3 = Utils.findRequiredViewAsType(source, R.id.output3, "field 'output3'", ImageView.class);
    target.output4 = Utils.findRequiredViewAsType(source, R.id.output4, "field 'output4'", ImageView.class);
    target.done = Utils.findRequiredViewAsType(source, R.id.done, "field 'done'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneAutoAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.input1 = null;
    target.input2 = null;
    target.output1 = null;
    target.output2 = null;
    target.output3 = null;
    target.output4 = null;
    target.done = null;
  }
}
