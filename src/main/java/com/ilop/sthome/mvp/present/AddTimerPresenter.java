package com.ilop.sthome.mvp.present;

import android.content.Context;

import com.example.common.mvp.BasePresenterImpl;
import com.ilop.sthome.data.bean.SysModelAliBean;
import com.ilop.sthome.data.db.SysmodelAliDAO;
import com.ilop.sthome.mvp.contract.AddTimerContract;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.siterwell.familywellplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-21.
 * @Dec:
 */
public class AddTimerPresenter extends BasePresenterImpl<AddTimerContract.IView> implements AddTimerContract.IPresent {

    private Context mContext;
    private List<SysModelAliBean> mSysList;
    private ArrayList<String> items_hour;
    private ArrayList<String> items_min;
    private ArrayList<String> mSysNameList;
    private String deviceName;

    private SysmodelAliDAO mSysModelDAO;

    public AddTimerPresenter(Context mContext, String deviceName) {
        this.mContext = mContext;
        this.deviceName = deviceName;
        mSysList = new ArrayList<>();
        items_hour = new ArrayList<>();
        items_min = new ArrayList<>();
        mSysNameList = new ArrayList<>();
        mSysModelDAO = new SysmodelAliDAO(mContext);
    }

    @Override
    public void getWeightList() {
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
        mSysNameList.clear();
        mSysList = mSysModelDAO.findAllSys(deviceName);
        for(SysModelAliBean sysModelBean : mSysList){
            if(sysModelBean.getSid()==0){
                mSysNameList.add(mContext.getString(R.string.home_mode));
            }else if(sysModelBean.getSid()==1){
                mSysNameList.add(mContext.getString(R.string.out_mode));
            }else if(sysModelBean.getSid()==2){
                mSysNameList.add(mContext.getString(R.string.sleep_mode));
            }else{
                mSysNameList.add(sysModelBean.getModleName());
            }
        }
        mView.getListForUi(items_hour, items_min, mSysNameList);
    }

    @Override
    public String getTimerStringFromContent(HashMap<Integer, Boolean> week) {
        byte f = 0x00;
        for(int i=0;i<week.size();i++){
            if(week.get(i)){
                f =   (byte)((0x02 << i) | f);
            }
        }
        String wek = ByteUtil.convertByte2HexString(f);
        return wek;
    }

    @Override
    public HashMap<Integer, Boolean> CheckRepeat(List<String> weekList) {
        HashMap<Integer, Boolean>  isSelected = new HashMap<Integer, Boolean>();
        for (int j = 0; j < 7; j++) {
            isSelected.put(j,false);
        }
        for(int i=0;i<weekList.size();i++){
            byte week_byte= ByteUtil.hexStr2Bytes(weekList.get(i))[0];
            byte f;
            for(int j=0;j<7;j++){
                f =   (byte)((0x02 << j) & week_byte);
                if(f!=0){
                    isSelected.put(j,true);
                }
            }
        }
        return isSelected;
    }

    @Override
    public int getTid(List<Integer> list) {
        if(list.size()==0){
            return 0;
        }else {
            if (list.size() == 1) {
                if (list.get(0) == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                int m = 0;
                for (int i = 0; i < list.size() - 1; i++) {
                    if (i == 0) {
                        int d = list.get(i);
                        if (d != 0) {
                            m = 0;
                            break;
                        } else {
                            if ((list.get(i) + 1) < list.get(i + 1)) {
                                m = list.get(i) + 1;
                                break;
                            } else {
                                m = i + 2;
                            }
                        }
                    } else {
                        if ((list.get(i) + 1) < list.get(i + 1)) {
                            m = list.get(i) + 1;
                            break;
                        } else {
                            m = i + 2;
                        }
                    }
                }
                return m;
            }
        }
    }
}
