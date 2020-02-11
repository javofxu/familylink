package com.ilop.sthome.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.siterwell.familywellplus.R;

/**
 * @author skygge
 * @date 2020-1-15.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：圆环进度盘
 */

public class ColorArcProgressBar extends View
{
    private final int DEGREE_PROGRESS_DISTANCE = dipToPx(8);//弧形与外层刻度的距离

    private int mWidth;
    private int mHeight;

    private int diameter = 500;  //直径
    private float centerX;  //圆心X坐标
    private float centerY;  //圆心Y坐标

    private Paint allArcPaint;
    private Paint progressPaint;
    private Paint vTextPaint;
    private Paint vSubTextPaint;
    private Paint vMaxHintPaint;
    private Paint vOffSetHintPaint;
    private Paint hintPaint;
    private Paint curSpeedPaint;
    private Paint seekThumbPaint;
    private Paint seekThumbInnerPaint;
    private Paint mRoundRect;

    private RectF bgRect;

    private ValueAnimator progressAnimator;
    private PaintFlagsDrawFilter mDrawFilter;
    private SweepGradient sweepGradient;//颜色渲染
    private Matrix rotateMatrix;

    private int[] colors = new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.RED};

    private float mTouchInvalidateRadius;//触摸失效半径,控件外层都可触摸,当触摸区域小于这个值的时候触摸失效

    private float startAngle   = 135;//开始角度(0°与控件X轴平行)
    private float sweepAngle   = 270;//弧形扫过的区域
    private float currentAngle = 0;
    private float lastAngle;

    private float offset = 5;
    private float maxValues     = 60;
    private float currentValues = 0;
    private float subCurrentValues = 0;
    private float bgArcWidth    = dipToPx(7);
    private float progressWidth = dipToPx(7);
    private float textSize      = dipToPx(48);
    private float subTextSize   = dipToPx(16);
    private float hintSize      = dipToPx(16);
    private float hintSizeMax   = dipToPx(14);
    private float longDegree    = dipToPx(14);//长刻度

    private int hintColor        = 0xff999999;
    private int bgArcColor       = 0xff111111;

    private String titleString;
    private String hintString;
    private RectF oval;

    private boolean isAutoTextSize = true;

    private float k;

    private OnSeekArcChangeListener listener;

    private boolean seekEnable;

    public ColorArcProgressBar(Context context)
    {
        super(context, null);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs)
    {
        super(context, attrs, 0);
        initConfig(context, attrs);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
        initView();
    }

    /**
     * 初始化布局配置
     *
     * @param context
     * @param attrs
     */
    private void initConfig(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar);

        int color_start = a.getColor(R.styleable.ColorArcProgressBar_start_color, Color.GREEN);
        int color_end = a.getColor(R.styleable.ColorArcProgressBar_end_color, color_start);

        bgArcColor = a.getColor(R.styleable.ColorArcProgressBar_bg_arc_color, 0xff111111);
        hintColor = a.getColor(R.styleable.ColorArcProgressBar_hint_color, 0xff999999);

        colors = new int[]{color_start, color_end};

        sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_sweep_angle, 270);
        bgArcWidth = a.getDimension(R.styleable.ColorArcProgressBar_bg_arc_width, dipToPx(10));
        progressWidth = a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10));

        seekEnable = a.getBoolean(R.styleable.ColorArcProgressBar_is_seek_enable, false);

        hintString = a.getString(R.styleable.ColorArcProgressBar_string_unit);
        titleString = a.getString(R.styleable.ColorArcProgressBar_string_title);

        currentValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 5);
        subCurrentValues = a.getFloat(R.styleable.ColorArcProgressBar_sub_current_value,10);
        maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 60);
        offset = a.getFloat(R.styleable.ColorArcProgressBar_min_value,5);
        setCurrentValues(currentValues);
        setMaxValues(maxValues);
        setSubCurrentValues(subCurrentValues);
        a.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.v("ColorArcProgressBar", "onSizeChanged: mWidth:" + mWidth + " mHeight:" + mHeight);

        diameter = (int) (Math.min(mWidth, mHeight) - 2 * (longDegree + DEGREE_PROGRESS_DISTANCE + progressWidth / 2));

        Log.v("ColorArcProgressBar", "onSizeChanged: diameter:" + diameter);

        //弧形的矩阵区域
        bgRect = new RectF();
        bgRect.top = longDegree + DEGREE_PROGRESS_DISTANCE + progressWidth / 2;
        bgRect.left = longDegree + DEGREE_PROGRESS_DISTANCE + progressWidth / 2;
        bgRect.right = diameter + (longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE);
        bgRect.bottom = diameter + (longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE);

        Log.v("ColorArcProgressBar", "initView: " + diameter);

        //圆心
        centerX = (2 * (longDegree + DEGREE_PROGRESS_DISTANCE + progressWidth / 2) + diameter) / 2;
        centerY = (2 * (longDegree + DEGREE_PROGRESS_DISTANCE + progressWidth / 2) + diameter) / 2;

        Log.i("AAA", "onSizeChanged: " + centerX + centerY);
        sweepGradient = new SweepGradient(centerX, centerY, colors, null);

        mTouchInvalidateRadius = Math.max(mWidth, mHeight) / 2 - longDegree - DEGREE_PROGRESS_DISTANCE - progressWidth * 2;

    }

    private void initView()
    {

        //整个弧形画笔
        allArcPaint = new Paint();
        allArcPaint.setAntiAlias(true);
        allArcPaint.setStyle(Paint.Style.STROKE);
        allArcPaint.setStrokeWidth(bgArcWidth);
        allArcPaint.setColor((bgArcColor));
        allArcPaint.setStrokeCap(Paint.Cap.ROUND);

        //当前进度的弧形画笔
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(Color.GREEN);

        //显示标题文字
        curSpeedPaint = new Paint();
        curSpeedPaint.setTextSize(hintSize);
        curSpeedPaint.setColor(hintColor);
        curSpeedPaint.setTextAlign(Paint.Align.CENTER);

        //内容显示文字
        vTextPaint = new Paint();
        vTextPaint.setTextSize(textSize);
        vTextPaint.setColor(Color.parseColor("#333333"));
        vTextPaint.setTextAlign(Paint.Align.CENTER);

        //内容显示第二行文字
        vSubTextPaint = new Paint();
        vSubTextPaint.setTextSize(subTextSize);
        vSubTextPaint.setColor(Color.parseColor("#333333"));
        vSubTextPaint.setTextAlign(Paint.Align.CENTER);

        //显示单位文字
        hintPaint = new Paint();
        hintPaint.setTextSize(hintSize);
        hintPaint.setColor(hintColor);
        hintPaint.setTextAlign(Paint.Align.CENTER);

        //画圆角矩形
        mRoundRect = new Paint();
        mRoundRect.setStyle(Paint.Style.FILL);//充满
        mRoundRect.setColor(Color.parseColor("#f1f5fe"));
        mRoundRect.setAntiAlias(true);// 设置画笔的锯齿效果

        vMaxHintPaint = new Paint();
        vMaxHintPaint.setTextSize(hintSizeMax);
        vMaxHintPaint.setColor(Color.parseColor("#ff9ca2a6"));
        vMaxHintPaint.setTextAlign(Paint.Align.CENTER);

        vOffSetHintPaint = new Paint();
        vOffSetHintPaint.setTextSize(hintSizeMax);
        vOffSetHintPaint.setColor(Color.parseColor("#ff9ca2a6"));
        vOffSetHintPaint.setTextAlign(Paint.Align.CENTER);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        rotateMatrix = new Matrix();

        seekThumbPaint = new Paint();
        seekThumbPaint.setAntiAlias(false);                       //设置画笔为无锯齿
        seekThumbPaint.setColor(Color.parseColor("#33a7ff"));                    //设置画笔颜色
        seekThumbPaint.setStyle(Paint.Style.FILL);                   //实心效果

        seekThumbInnerPaint = new Paint();
        seekThumbInnerPaint.setAntiAlias(false);                       //设置画笔为无锯齿
        seekThumbInnerPaint.setColor(Color.WHITE);                    //设置画笔颜色
        seekThumbInnerPaint.setStyle(Paint.Style.FILL);                   //实心效果
    }

    @SuppressLint({"DrawAllocation", "DefaultLocale"})
    @Override
    protected void onDraw(Canvas canvas)
    {
        //抗锯齿
        canvas.setDrawFilter(mDrawFilter);

        //整个弧
        canvas.drawArc(bgRect, startAngle, sweepAngle, false, allArcPaint);

        //设置渐变色
        rotateMatrix.setRotate(130, centerX, centerY);
        sweepGradient.setLocalMatrix(rotateMatrix);
        progressPaint.setShader(sweepGradient);

        //当前进度
        canvas.drawArc(bgRect, startAngle, currentAngle, false, progressPaint);

        canvas.drawText(titleString, centerX, centerY - textSize - 20, curSpeedPaint); //标题

        canvas.drawText(String.format("%.1f", currentValues), centerX, centerY, vTextPaint);

        oval = new RectF(centerX/2, centerY +100, centerX/2*3, centerY + 180);// 设置个新的长方形
        canvas.drawRoundRect(oval, 33, 33, mRoundRect);//第二个参数是x半径，第三个参数是y半径

        canvas.drawText(hintString, centerX-80, centerY + 155, curSpeedPaint);

        canvas.drawText(String.format("%.0f", subCurrentValues)+"℃", centerX+80, centerY + 155, vSubTextPaint);

        canvas.drawCircle(centerX + ((float) Math.cos( (startAngle+currentAngle)* Math.PI / 180) * (diameter/2)),centerY + ((float) Math.sin( (startAngle+currentAngle)* Math.PI / 180)* (diameter/2)),bgArcWidth+5f,seekThumbPaint);
        canvas.drawCircle(centerX + ((float) Math.cos( (startAngle+currentAngle)* Math.PI / 180) * (diameter/2)),centerY + ((float) Math.sin( (startAngle+currentAngle)* Math.PI / 180)* (diameter/2))-5,(bgArcWidth+5f)/3,seekThumbInnerPaint);

        canvas.drawText(String.format("%.0f", offset)+"℃",centerX-diameter/2-10,centerY+diameter/2-diameter/10,vOffSetHintPaint);
        canvas.drawText(String.format("%.0f", maxValues)+"℃",centerX+diameter/2+10,centerY+diameter/2-diameter/10,vMaxHintPaint);
        invalidate();

    }

    /**
     * 设置最大值
     *
     * @param maxValues
     */
    public void setMaxValues(float maxValues)
    {
        this.maxValues = maxValues;
        k = sweepAngle / (maxValues-offset);

    }

    public void setOffset(float offset){
        this.offset = offset;
    }

    /**
     * 设置当前值
     *
     * @param currentValues
     */
    public void setCurrentValues(float currentValues)
    {
        if(currentValues > maxValues)
        {
            currentValues = maxValues;
        }
        if(currentValues < offset)
        {
            currentValues = offset;
        }
        this.currentValues = currentValues;
        lastAngle = currentAngle;
        setAnimation(lastAngle, (currentValues-offset) * k, 200);
    }

    public void setSubCurrentValues(float subCurrentValues){

        this.subCurrentValues = subCurrentValues;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(seekEnable)
        {
            this.getParent().requestDisallowInterceptTouchEvent(true);//一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    onStartTrackingTouch();
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onStopTrackingTouch();
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    onStopTrackingTouch();
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }


    private void onStartTrackingTouch()
    {
        if(listener != null)
        {
            listener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch()
    {
        if(listener != null)
        {
            listener.onStopTrackingTouch(this);
        }
    }




    private void updateOnTouch(MotionEvent event)
    {
        boolean validateTouch = validateTouch(event.getX(), event.getY());
        if(!validateTouch)
        {
            return;
        }
        setPressed(true);
        double mTouchAngle = getTouchDegrees(event.getX(), event.getY());

        float progress = angleToProgress(mTouchAngle);
        Log.v("ColorArcProgressBar", "updateOnTouch: " + progress);
        onProgressRefresh(progress, true);
    }

    /**
     * 判断触摸是否有效
     *
     * @param xPos x
     * @param yPos y
     * @return is validate touch
     */
    private boolean validateTouch(float xPos, float yPos)
    {
        boolean validate = false;

        float x = xPos - centerX;
        float y = yPos - centerY;

        float touchRadius = (float) Math.sqrt(((x * x) + (y * y)));//触摸半径

        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2) - Math.toRadians(225));

        if(angle < 0)
        {
            angle = 360 + angle;
        }
//

        if(touchRadius > mTouchInvalidateRadius && (angle >= 0 && angle <= 280))//其实角度小于270就够了,但是弧度换成角度是不精确的,所以需要适当放大范围,不然有时候滑动不到最大值
        {
            validate = true;
        }

        Log.v("ColorArcProgressBar", "validateTouch: " + angle);
        return validate;
    }

    private double getTouchDegrees(float xPos, float yPos)
    {
        float x = xPos - centerX;//触摸点X坐标与圆心X坐标的距离
        float y = yPos - centerY;//触摸点Y坐标与圆心Y坐标的距离
        // Math.toDegrees convert to arc Angle

        //Math.atan2(y, x)以弧度为单位计算并返回点 y /x 的夹角，该角度从圆的 x 轴（0 点在其上，0 表示圆心）沿逆时针方向测量。返回值介于正 pi 和负 pi 之间。
        //触摸点与圆心的夹角- Math.toRadians(225)是因为我们希望0°从圆弧的起点开始,默认角度从穿过圆心的X轴开始
        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2) - Math.toRadians(225));

        if(angle < 0)
        {
            angle = 360 + angle;
        }
        Log.v("ColorArcProgressBar", "getTouchDegrees: " + angle);
//        angle -= mStartAngle;
        return angle;
    }

    private float angleToProgress(double angle)
    {
        float progress = 0f;
        float progress_init = (float) (valuePerDegree() * angle);
        String str = String.valueOf(valuePerDegree() * angle);//浮点变量a转换为字符串str
        int idx = str.lastIndexOf(".");//查找小数点的位置
        String strNum = str.substring(0,idx);//截取从字符串开始到小数点位置的字符串，就是整数部分
        int num = Integer.valueOf(strNum);//把整数部分通过Integer.valueof方法转换为数字

        float xiaoshu =  progress_init-(float) num;
        if(xiaoshu<0.5&&xiaoshu>=0){
            progress = num;
        }else {
            progress = num+0.5f;
        }



        progress = (progress < 0) ? 0 : progress;
        progress = (progress > (maxValues-offset)) ? (int) (maxValues - offset) : progress;
        return progress;
    }

    private float valuePerDegree()
    {
        return (maxValues-offset) / sweepAngle;
    }

    private void onProgressRefresh(float progress, boolean fromUser)
    {
        updateProgress(progress, fromUser);
    }

    private void updateProgress(float progress, boolean fromUser)
    {

        currentValues = progress+offset;

        if(listener != null)
        {
            listener.onProgressChanged(this, progress, fromUser);
        }

        currentAngle = progress / (maxValues-offset) * sweepAngle;//计算划过当前的角度

        lastAngle = currentAngle;

        invalidate();
    }


    /**
     * 设置整个圆弧宽度
     *
     * @param bgArcWidth
     */
    public void setArcWidth(int bgArcWidth)
    {
        this.bgArcWidth = bgArcWidth;
    }

    /**
     * 设置进度宽度
     *
     * @param progressWidth
     */
    public void setProgressWidth(int progressWidth)
    {
        this.progressWidth = progressWidth;
    }

    /**
     * 设置速度文字大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    /**
     * 设置单位文字大小
     *
     * @param hintSize
     */
    public void setHintSize(int hintSize)
    {
        this.hintSize = hintSize;
    }

    /**
     * 设置单位文字
     *
     * @param hintString
     */
    public void setUnit(String hintString)
    {
        this.hintString = hintString;
        invalidate();
    }

    /**
     * 设置直径大小
     *
     * @param diameter
     */
    public void setDiameter(int diameter)
    {
        this.diameter = dipToPx(diameter);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    private void setTitle(String title)
    {
        this.titleString = title;
    }


    /**
     * 为进度设置动画
     *
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current, int length)
    {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                currentAngle = (float) animation.getAnimatedValue();
                currentValues = currentAngle / k+offset;
            }
        });
        progressAnimator.start();
    }

    public void setSeekEnable(boolean seekEnable)
    {
        this.seekEnable = seekEnable;
    }

    public void setOnSeekArcChangeListener(OnSeekArcChangeListener listener)
    {
        this.listener = listener;
    }

    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    private int dipToPx(float dip)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 得到屏幕宽度
     *
     * @return
     */
    private int getScreenWidth()
    {
        WindowManager windowManager  = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public float getCurrentValues(){
        return currentValues;
    }

    public float getSubCurrentValues(){
        return subCurrentValues;
    }

    public interface OnSeekArcChangeListener
    {

        /**
         * Notification that the progress level has changed. Clients can use the
         * fromUser parameter to distinguish user-initiated changes from those
         * that occurred programmatically.
         *
         * @param seekArc  The SeekArc whose progress has changed
         * @param progress The current progress level. This will be in the range
         *                 0..max where max was set by
         *                 {@link ColorArcProgressBar#setMaxValues(float)} . (The default value for
         *                 max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(ColorArcProgressBar seekArc, float progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may
         * want to use this to disable advancing the SeekBar.
         *
         * @param seekArc The SeekArc in which the touch gesture began
         */
        void onStartTrackingTouch(ColorArcProgressBar seekArc);

        /**
         * Notification that the user has finished a touch gesture. Clients may
         * want to use this to re-enable advancing the SeekBar.
         *
         * @param seekArc The SeekArc in which the touch gesture began
         */
        void onStopTrackingTouch(ColorArcProgressBar seekArc);
    }
}
