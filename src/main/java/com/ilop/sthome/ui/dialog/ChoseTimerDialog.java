package com.ilop.sthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common.utils.LiveDataBus;
import com.ilop.sthome.data.bean.DeviceInfoBean;
import com.ilop.sthome.ui.adapter.device.WeekAdapter;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.view.WheelView;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-10-29.
 * @Dec:
 */
public class ChoseTimerDialog extends Dialog {

    private static final String TAG = "ChoseTimerDialog";

    @BindView(R.id.iv_set_timer)
    ImageView ivSetTimer;
    @BindView(R.id.timer_minute)
    WheelView timerHour;
    @BindView(R.id.timer_second)
    WheelView timerMinute;
    @BindView(R.id.week_trigger)
    RecyclerView weekTrigger;

    private Context mContext;
    private WeekAdapter mAdapter;
    private byte mWeekInt=0x00;
    private ArrayList<String> items_hour = new ArrayList<>();
    private ArrayList<String> items_min = new ArrayList<>();

    public ChoseTimerDialog(Context context) {
        super(context, R.style.window_background);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chose_timer);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        ivSetTimer.setOnClickListener(v -> {
            String setting_status = getTimerStringFromContent(mAdapter.getIsSelected(), timerHour.getCurrentItem(), timerMinute.getCurrentItem());
            Log.i(TAG, "initListener: "+setting_status);
            DeviceInfoBean device = new DeviceInfoBean();
            if(TextUtils.isEmpty(setting_status)){
                Toast.makeText(mContext,mContext.getString(R.string.set_first), Toast.LENGTH_SHORT).show();
                return;
            }else{
                if(setting_status.startsWith("00")){
                    Toast.makeText(mContext,mContext.getString(R.string.set_weekday), Toast.LENGTH_SHORT).show();
                    return;
                }
                device.setDevice_type("TIMER");
                device.setDevice_status(setting_status);
            }
            LiveDataBus.get().with("timer_condition").setValue(device);
            dismiss();
        });
    }

    private void initView() {
        timerMinute.setCyclic(true);
        timerHour.setCyclic(true);
        timerMinute.setAdapter(new NumberAdapter(items_min));
        timerHour.setAdapter(new NumberAdapter(items_hour));
        mAdapter = new WeekAdapter(mContext, mWeekInt);
        weekTrigger.setLayoutManager(new GridLayoutManager(mContext, 5));
        weekTrigger.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 24; i ++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_hour.add(item);
        }

        for (int i = 0; i < 60; i ++) {
            String item = String.valueOf(i);
            if (item.length() == 1) {
                item = "0" + item;
            }
            items_min.add(item);
        }
    }

    private String getTimerStringFromContent(HashMap<Integer, Boolean> week, int hour, int min) {
        byte f = 0x00;
        for(int i=0;i<week.size();i++){
            if(week.get(i)){
                f =   (byte)((0x02 << i) | f);
            }
        }
        String wek = ByteUtil.convertByte2HexString(f);
        String ds2 = hour<10?"0":"";
        String ds3 = min<10?"0":"";
        return wek + ds2 + hour + ds3 + min;
    }

    private class NumberAdapter extends WheelView.WheelArrayAdapter<String> {
        NumberAdapter(ArrayList<String> items) {
            super(items);
        }
    }

}
