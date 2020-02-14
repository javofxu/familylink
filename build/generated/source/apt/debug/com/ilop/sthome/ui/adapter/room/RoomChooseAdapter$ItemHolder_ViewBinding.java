// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.room;

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

public class RoomChooseAdapter$ItemHolder_ViewBinding implements Unbinder {
  private RoomChooseAdapter.ItemHolder target;

  @UiThread
  public RoomChooseAdapter$ItemHolder_ViewBinding(RoomChooseAdapter.ItemHolder target,
      View source) {
    this.target = target;

    target.mRoomName = Utils.findRequiredViewAsType(source, R.id.tv_room_name, "field 'mRoomName'", TextView.class);
    target.mRoomState = Utils.findRequiredViewAsType(source, R.id.iv_room_state, "field 'mRoomState'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomChooseAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRoomName = null;
    target.mRoomState = null;
  }
}
