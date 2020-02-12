// Generated code from Butter Knife. Do not modify!
package com.ilop.sthome.ui.adapter.room;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.common.view.CustomTextView;
import com.siterwell.familywellplus.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoomAdapter$ItemHolder_ViewBinding implements Unbinder {
  private RoomAdapter.ItemHolder target;

  @UiThread
  public RoomAdapter$ItemHolder_ViewBinding(RoomAdapter.ItemHolder target, View source) {
    this.target = target;

    target.itemRoomName = Utils.findRequiredViewAsType(source, R.id.item_room_name, "field 'itemRoomName'", CustomTextView.class);
    target.itemRoomNum = Utils.findRequiredViewAsType(source, R.id.item_room_num, "field 'itemRoomNum'", TextView.class);
    target.itemRoomEdit = Utils.findRequiredViewAsType(source, R.id.item_room_edit, "field 'itemRoomEdit'", ImageView.class);
    target.itemRoomList = Utils.findRequiredViewAsType(source, R.id.item_room_list, "field 'itemRoomList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RoomAdapter.ItemHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.itemRoomName = null;
    target.itemRoomNum = null;
    target.itemRoomEdit = null;
    target.itemRoomList = null;
  }
}
