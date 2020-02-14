// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.scene;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SceneAdapter$ItemHolder_ViewBinding implements Unbinder {
  private SceneAdapter.ItemHolder target;

  @UiThread
  public SceneAdapter$ItemHolder_ViewBinding(SceneAdapter.ItemHolder target, View source) {
    this.target = target;

    target.mArrow = Utils.findRequiredViewAsType(source, R.id.arrow, "field 'mArrow'", ImageView.class);
    target.mScene = Utils.findRequiredViewAsType(source, R.id.scene, "field 'mScene'", ImageView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.title, "field 'mTitle'", TextView.class);
    target.mGatewayColor = Utils.findRequiredViewAsType(source, R.id.gatewaycolor, "field 'mGatewayColor'", TextView.class);
    target.mCheckbox = Utils.findRequiredViewAsType(source, R.id.checkboxo, "field 'mCheckbox'", ImageView.class);
    target.mRvList = Utils.findRequiredViewAsType(source, R.id.rv_scene_list, "field 'mRvList'", RecyclerView.class);
    target.mAddScene = Utils.findRequiredViewAsType(source, R.id.tv_add_scene, "field 'mAddScene'", TextView.class);
    target.mSceneAuto = Utils.findRequiredViewAsType(source, R.id.ll_scene_auto, "field 'mSceneAuto'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SceneAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mArrow = null;
    target.mScene = null;
    target.mTitle = null;
    target.mGatewayColor = null;
    target.mCheckbox = null;
    target.mRvList = null;
    target.mAddScene = null;
    target.mSceneAuto = null;
  }
}
