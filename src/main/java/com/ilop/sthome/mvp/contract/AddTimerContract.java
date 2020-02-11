package com.ilop.sthome.mvp.contract;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-21.
 * @Dec:
 */
public interface AddTimerContract {

    interface IView extends IBaseView{

        void getListForUi(ArrayList<String> hour, ArrayList<String> minute, ArrayList<String> scene);
    }

    interface IPresent extends IBasePresenter<IView>{

        void getWeightList();

        String getTimerStringFromContent (HashMap<Integer,Boolean> week);

        HashMap<Integer, Boolean> CheckRepeat(List<String> weekList);

        int getTid(List<Integer> list);


    }

}
