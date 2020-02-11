package com.example.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.common.R;


/**
 * @Author skygge.
 * @Date on 2019-08-24.
 * @Github https://github.com/javofxu
 * @Dec:自定义带删除和错误提示的EditText
 * @version: ${VERSION}.
 * @Update :
 */
public class CustomEditText extends RelativeLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private static final String TAG = CustomEditText.class.getSimpleName();
    private static final int HINT_ID = 11111;
    private static final int EDIT_ID = 22222;
    private static final int DELETE_ID = 33333;
    private static final int ERROR_ID = 4444;
    /**
     * 提示内容
     */
    private String inputHint;
    /**
     * 提示字体颜色
     */
    private int hintColor;
    /**
     * 提示字体大小
     */
    private float hintSize = 14;
    /**
     * 左边图标
     */
    private int drawable;
    /**
     * 图标padding
     */
    private int drawablePadding = 5;
    /**
     * 输入内容
     */
    private String inputContent;
    /**
     * 输入框Height
     */
    private int inputHeight = 5;
    /**
     * 输入框padding
     */
    private int inputPadding = 5;
    /**
     * 输入框paddingTop
     */
    private int inputPaddingTop = 5;
    /**
     * 输入框paddingBottom
     */
    private int inputPaddingBottom = 5;
    /**
     * 输入框paddingLeft
     */
    private int inputPaddingLeft = 5;
    /**
     * 输入框paddingRight
     */
    private int inputPaddingRight = 5;
    /**
     * 输入框背景
     */
    private Drawable inputBackground;
    /**
     * 输入字体大小
     */
    private float inputSize = 16;
    /**
     * 输入字体颜色
     */
    private int inputColor;
    /**
     * 按钮样式
     */
    private int rightButton;
    /**
     * 按钮类型
     */
    private int btnType;
    /**
     * 是否显示按钮
     */
    private int btnVisibility;
    /**
     * 重新获取焦点时，清除已输入内容
     */
    private boolean clearContentsFocus;

    /**
     * 默认闭眼状态显示密码不可见
     */
    private boolean eyesClose = true;
    /**
     * 错误内容
     */
    private String error;
    /**
     * 输入类型
     */
    private int inputType;
    /**
     * 当前对象
     */
    private Context mContext;
    /**
     * 提示text
     */
    private TextView hintView;
    /**
     * 错误text
     */
    private TextView errorView;
    /**
     * 输入框
     */
    private EditText editText;

    /**
     * 删除按钮
     */
    private ImageView delBtn;

    private TextWatcher textWatcher;
    private OnFocusChangeListener focusChangeListener;

    public CustomEditText(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);

        inputHeight = typedArray.getLayoutDimension(R.styleable.CustomEditText_inputHeight, LayoutParams.WRAP_CONTENT);

        inputHint = typedArray.getString(R.styleable.CustomEditText_inputHint) == null ? "请输入内容" : typedArray.getString(R.styleable.CustomEditText_inputHint);
        hintColor = typedArray.getColor(R.styleable.CustomEditText_hintColor, Color.RED);
        hintSize = px2sp(typedArray.getDimension(R.styleable.CustomEditText_hintSize, sp2px(hintSize)));
        drawable = typedArray.getResourceId(R.styleable.CustomEditText_drawable, -1);
        drawablePadding = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_drawablePadding, -1);
        inputContent = typedArray.getString(R.styleable.CustomEditText_inputContent);

        inputPadding = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_inputPadding, -1);
        inputPaddingTop = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_inputPaddingTop, -1);
        inputPaddingRight = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_inputPaddingRight, -1);
        inputPaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_inputPaddingBottom, -1);
        inputPaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.CustomEditText_inputPaddingLeft, -1);

        inputBackground = typedArray.getDrawable(R.styleable.CustomEditText_inputBackground);
        inputSize = px2sp(typedArray.getDimension(R.styleable.CustomEditText_inputSize, sp2px(inputSize)));
        inputColor = typedArray.getColor(R.styleable.CustomEditText_inputColor, Color.BLACK);
        rightButton = typedArray.getResourceId(R.styleable.CustomEditText_rightButton, -1);
        btnType = typedArray.getInt(R.styleable.CustomEditText_btnType, 1);
        btnVisibility = typedArray.getInt(R.styleable.CustomEditText_btnVisibility, 1);
        clearContentsFocus = typedArray.getBoolean(R.styleable.CustomEditText_clearContentsFocus, true);
        inputType = typedArray.getInt(R.styleable.CustomEditText_inputType, 0);
        typedArray.recycle();
        initView();
    }


    private void initView() {

        hintView = new TextView(mContext);
        hintView.setId(HINT_ID);
        errorView = new TextView(mContext);
        errorView.setId(ERROR_ID);
        editText = new EditText(mContext);
        editText.setId(EDIT_ID);
        delBtn = new ImageView(mContext);
        delBtn.setId(DELETE_ID);
        delBtn.setOnClickListener(this);

        LayoutParams hintViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        hintView.setText(inputHint);
        addView(hintView, hintViewParams);


        LayoutParams editTextParams = new LayoutParams(LayoutParams.MATCH_PARENT, inputHeight);
        editTextParams.addRule(RelativeLayout.BELOW, hintView.getId());
        addView(editText, editTextParams);

        LayoutParams delBtnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        delBtnParams.addRule(RelativeLayout.ALIGN_BOTTOM, editText.getId());
        delBtnParams.addRule(RelativeLayout.ALIGN_RIGHT, editText.getId());
        delBtnParams.addRule(RelativeLayout.ALIGN_TOP, editText.getId());
        delBtnParams.rightMargin = 10;
        delBtnParams.topMargin = 10;
        delBtnParams.bottomMargin = 10;
        delBtn.setImageResource(rightButton);
        delBtn.setPadding(20,20, 20, 20);
        addView(delBtn, delBtnParams);

        LayoutParams errorViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        errorViewParams.addRule(RelativeLayout.ALIGN_TOP, editText.getId());
        errorViewParams.addRule(RelativeLayout.ALIGN_BOTTOM, editText.getId());
        errorViewParams.addRule(RelativeLayout.LEFT_OF, delBtn.getId());
        errorView.setText("错误的输入");
        errorView.setTextColor(Color.RED);
        errorView.setGravity(Gravity.CENTER_VERTICAL);
        addView(errorView, errorViewParams);
        initConfig();
    }

    /**
     * 初始化配置
     */
    void initConfig() {
        errorView.setVisibility(GONE);
        hintView.setVisibility(GONE);

        hintView.setTextColor(hintColor);
        hintView.setTextSize(hintSize);
        hintView.setText(inputHint);
        hintView.setPadding(10, 0, 0, 0);

        if (drawable != -1) {

            Drawable drawable = ContextCompat.getDrawable(mContext, this.drawable);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            editText.setCompoundDrawables(drawable, null, null, null);
            editText.setCompoundDrawablePadding(drawablePadding);
        }

        if (inputPadding == -1) {
            editText.setPadding(inputPaddingLeft > editText.getPaddingLeft() ? inputPaddingLeft : editText.getPaddingLeft(), inputPaddingTop > editText.getPaddingTop() ? inputPaddingTop : editText.getPaddingTop(), inputPaddingRight > editText.getPaddingRight() ? inputPaddingRight : editText.getPaddingRight(), inputPaddingBottom > editText.getPaddingBottom() ? inputPaddingBottom : editText.getPaddingBottom());
        } else {
            editText.setPadding(inputPadding > editText.getPaddingLeft() ? inputPadding : editText.getPaddingLeft(), inputPadding > editText.getPaddingTop() ? inputPadding : editText.getPaddingTop(), inputPadding > editText.getPaddingRight() ? inputPadding : editText.getPaddingRight(), inputPadding > editText.getPaddingBottom() ? inputPadding : editText.getPaddingBottom());

        }
        editText.setTextColor(inputColor);
        editText.setTextSize(inputSize);
        editText.setHint(inputHint);
        editText.setMaxLines(1);
        editText.setSingleLine(true);

        editText.addTextChangedListener(this);
        if (inputBackground != null) {
            editText.setBackground(inputBackground);
        }else {
            editText.setBackground(null);
        }
        editText.setOnFocusChangeListener(this);

        delBtn.setVisibility(btnVisibility);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //强制高度为WRAP_CONTENT，不然有输入的时候会出现显示不全的bug
        getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 输入监听
     *
     * @param textWatcher
     */
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    /**
     * 设置输入框提示
     *
     * @param inputHint
     */
    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
        hintView.setText(inputHint);
        editText.setHint(inputHint);
    }

    /**
     * 设置有输入时，输入框上方提示文字颜色
     *
     * @param hintColor
     */
    public void setHintColor(int hintColor) {
        this.hintColor = hintColor;
        hintView.setTextColor(hintColor);
    }

    /**
     * 设置有输入时，输入框上方提示文字大小 单位：sp
     *
     * @param hintSize
     */
    public void setHintSize(float hintSize) {
        this.hintSize = hintSize;
        hintView.setTextSize(hintSize);

    }

    /**
     * 输入框输入类型
     * @param type
     */
    public void setInputType(int type){
        if (type == 0){
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if (type==1){
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else if (type==2){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    /**
     * 设置输入框左边图标
     *
     * @param id
     */
    public void setDrawable(int id) {
        this.drawable = id;
        Drawable drawable = ContextCompat.getDrawable(mContext, this.drawable);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        editText.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置输入框左边图标 Padding
     *
     * @param drawablePadding
     */
    public void setDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;
        editText.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 设置输入框内容
     *
     * @param inputContent
     */
    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
        editText.setText(inputContent);
    }

    /**
     * 获取输入框内容
     *
     * @return 输入框内容
     */
    public String getInputContent() {
        return editText.getText().toString().trim();
    }

    /**
     * 设置输入框高度
     *
     * @param inputHeight
     */
    public void setInputHeight(int inputHeight) {
        this.inputHeight = inputHeight;
    }

    /**
     * 设置输入框padding
     *
     * @param inputPadding
     */
    public void setInputPadding(int inputPadding) {
        this.inputPadding = inputPadding;
    }

    /**
     * 设置输入框 inputPaddingTop
     *
     * @param inputPaddingTop
     */
    public void setInputPaddingTop(int inputPaddingTop) {
        this.inputPaddingTop = inputPaddingTop;
    }

    /**
     * 设置输入框 inputPaddingBottom
     *
     * @param inputPaddingBottom
     */
    public void setInputPaddingBottom(int inputPaddingBottom) {
        this.inputPaddingBottom = inputPaddingBottom;
    }

    /**
     * 设置输入框 inputPaddingLeft
     *
     * @param inputPaddingLeft
     */
    public void setInputPaddingLeft(int inputPaddingLeft) {
        this.inputPaddingLeft = inputPaddingLeft;
    }

    /**
     * 设置输入框 inputPaddingRight
     *
     * @param inputPaddingRight
     */
    public void setInputPaddingRight(int inputPaddingRight) {
        this.inputPaddingRight = inputPaddingRight;
    }

    /**
     * 设置输入框背景
     *
     * @param drawable
     */
    public void setInputBackground(Drawable drawable) {
        this.inputBackground = drawable;
        editText.setBackground(inputBackground);
    }

    /**
     * 设置输入框背景
     *
     * @param resId
     */
    public void setInputBackgroundResource(int resId) {
        editText.setBackgroundResource(resId);
    }

    /**
     * 设置输入框背景
     *
     * @param color
     */
    public void setInputBackgroundColor(int color) {
        editText.setBackgroundColor(color);
    }

    /**
     * 设置输入框字体大小
     *
     * @param inputSize
     */
    public void setInputSize(float inputSize) {
        this.inputSize = inputSize;
        editText.setTextSize(inputSize);
    }

    /**
     * 设置输入框字体颜色
     *
     * @param inputColor
     */
    public void setInputColor(int inputColor) {
        this.inputColor = inputColor;
        editText.setTextColor(inputColor);
    }


    /**
     * 设置删除按钮显示隐藏 默认显示
     *
     * @param delBtnVisibility
     */
    public void setDelBtnVisibility(int delBtnVisibility) {
        this.btnVisibility = delBtnVisibility;
        delBtn.setVisibility(delBtnVisibility);
    }

    /**
     * 设置输入错误时，再次获取光标是否清除内容
     *
     * @param clearContentsFocus
     */
    public void setClearContentsFocus(boolean clearContentsFocus) {
        this.clearContentsFocus = clearContentsFocus;
    }

    public void setCursorVisible(boolean visible){
        editText.setCursorVisible(visible);
    }
    /**
     * 设置输入有误信息
     *
     * @param err
     */
    public void setError(String err) {
        if (err.length() <= 0) {
            return;
        }
        if (errorView.getVisibility() == GONE) {
            errorView.setVisibility(VISIBLE);
        }
        errorView.setText(err);
    }

    /**
     * 设置错误提示显示/隐藏
     *
     * @param visibility
     */
    public void setErrorVisibility(int visibility) {
        errorView.setVisibility(visibility);
    }

    /**
     * 获取输入框，进行更多的原生editText的设置
     * @return
     */
    public EditText getInput(){
        return editText;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        this.focusChangeListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case DELETE_ID:
                if (btnType==1){
                    editText.setText("");
                }else if (btnType==2){
                    if (eyesClose){
                        delBtn.setImageResource(R.mipmap.icon_eye_open);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Editable editable= editText.getText();
                        Selection.setSelection(editable, editable.length());
                        eyesClose = false;
                    }else {
                        delBtn.setImageResource(R.mipmap.icon_eye_close);
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Editable editable= editText.getText();
                        Selection.setSelection(editable, editable.length());
                        eyesClose = true;
                    }
                }
                break;

            default:

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textWatcher != null) {
            textWatcher.onTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(editable);
        }
        if (editable.length() > 0) {
            hintView.setVisibility(VISIBLE);
            if (errorView.getVisibility() == VISIBLE) {
                errorView.setVisibility(GONE);
            }
        } else {
            hintView.setVisibility(GONE);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (focusChangeListener != null) {
            focusChangeListener.onFocusChange(view, b);
        }
        if (b) {
            //获得焦点
            if (clearContentsFocus && errorView.getVisibility() == VISIBLE) {
                editText.setText("");
            } else {
                editText.setSelection(editText.getText().length());
            }
        } else {
            //失去焦点

        }
    }


    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, mContext.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public float px2sp(float pxVal) {
        return (pxVal / mContext.getResources().getDisplayMetrics().scaledDensity);
    }
}
