package com.ilop.sthome.ui.activity.config;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.data.bean.QuestionBean;
import com.ilop.sthome.ui.adapter.config.QuestionAdapter;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityQuestionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许格 on 2019/10/28.
 */

public class QuestionActivity extends BaseActivity<ActivityQuestionBinding> implements QuestionAdapter.OnRecyclerViewItemClickListener {
    private QuestionAdapter mAdapter;
    private List<QuestionBean> mQuestionList;
    private GridLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
    }

    @Override
    protected void initView() {
        super.initView();
        mQuestionList = new ArrayList<>();
        mLayoutManager=new GridLayoutManager(this,1, GridLayoutManager.VERTICAL,false);
        mDBind.questionList.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        String[] ques = getResources().getStringArray(R.array.question);
        String[] answer = getResources().getStringArray(R.array.question_detail);
        for(int i=0; i<ques.length; i++){
            QuestionBean questionBean = new QuestionBean();
            if(i==0){
                questionBean.setIsopen(true);
            }else{
                questionBean.setIsopen(false);
            }
            questionBean.setAnswer(answer[i]);
            questionBean.setQuestion(ques[i]);
            mQuestionList.add(questionBean);
        }
        mAdapter = new QuestionAdapter(this, mQuestionList);
        mDBind.questionList.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(this);
        mDBind.ivConfigFailBack.setOnClickListener(view -> finish());
    }


    @Override
    public void onItemClick(View view, int position) {
        if(mQuestionList.get(position).isopen()){
            mQuestionList.get(position).setIsopen(false);
        }else{
            mQuestionList.get(position).setIsopen(true);
        }
        mAdapter.Refresh(mQuestionList);
    }
}
