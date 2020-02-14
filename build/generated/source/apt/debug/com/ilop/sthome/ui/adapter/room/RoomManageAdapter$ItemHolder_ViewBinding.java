// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.room;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomManageAdapter$ItemHolder_ViewBinding implements Unbinder {
  private RoomManageAdapter.ItemHolder target;

  @UiThread
  public RoomManageAdapter$ItemHolder_ViewBinding(RoomManageAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mName = Utils.findRequiredViewAsType(source, R.id.item_room_name, "field 'mName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomManageAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mName = null;
  }
}
