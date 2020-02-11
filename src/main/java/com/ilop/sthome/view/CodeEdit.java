package com.ilop.sthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.siterwell.familywellplus.R;

/**
 * Created by gc-0001 on 2017/2/9.
 */
public class CodeEdit extends LinearLayout implements View.OnClickListener{

    private EditText code_edit;
    private ImageView switch_btn;
    private boolean flag_cansee;


    public CodeEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_edit_code, this, true);

        code_edit = findViewById(R.id.et_phone);
        code_edit.setTypeface(Typeface.DEFAULT);
        code_edit.setCursorVisible(false);
        code_edit.setTransformationMethod(new PasswordTransformationMethod());
        switch_btn = findViewById(R.id.arrow);
        switch_btn.setOnClickListener(this);

        TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.code_switch_edit);

        setCodeShow(localTypedArray.getBoolean(R.styleable.code_switch_edit_code_can_see , true));
        setHintText(localTypedArray.getString(R.styleable.code_switch_edit_hint_text));
        localTypedArray.recycle();
    }

    public void setCodeShow(boolean flag){
        flag_cansee = flag;
        if(flag){
            code_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            code_edit.setSelection(code_edit.getText().toString().trim().length());
            switch_btn.setBackgroundResource(R.mipmap.show);
        }else{
            code_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            code_edit.setSelection(code_edit.getText().toString().trim().length());
            switch_btn.setBackgroundResource(R.mipmap.hide);
        }
    }

    public boolean getCansee(){
        return flag_cansee;
    }

    private void setHintText(String hint){
       getCodeEdit().setHint(hint);
    }

    public EditText getCodeEdit(){
        return code_edit;
    }

    public ImageView getSwitchBtn(){
        return switch_btn;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.arrow){
            if(getCansee()){
                setCodeShow(false);
            }else{
                setCodeShow(true);
            }
        }
    }
}
