package com.ilop.sthome.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.siterwell.familywellplus.R;

/**
 * @Author skygge.
 * @Date on 2019-10-04.
 * @Dec:
 */
public class RingProgressView extends View {
    private Context mContext;
    //内圆环颜色
    private int innerRoundColor;
    //外圆环颜色
    private int outerRoundColor;
    //绘制背景圆环的画笔
    private Paint mPaint;
    //绘制外面进度的圆环的画笔
    private Paint mProgressPaint;
    //绘制外面进度的圆环的画笔
    private Paint mTextPaint;
    //绘制中间重新测试按钮
    private Paint mRoundRect;

    //背景圆弧的绘制的宽度
    private int mRoundWidth = 48;
    //进度圆环的宽度
    private float mProgressRoundWidth = 48;
    //中间步数文字的大小
    private int mTextSize = 48;//单位 sp
    //圆环最大进度
    private int mMaxStep = 100;
    //圆环当前进度
    private float mCurrentStep = 0;

    private boolean mIsUpdate = false;

    private RectF roundRect;

    private Typeface mTypeface;

    private onClickCallBack callBack;

    public RingProgressView(Context context) {
        this(context, null);
        this.mContext = context;
        init();
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        init();
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void setCallBack(onClickCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽的尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);

        //对wrap_content这种模式进行处理
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = widthSize;
        }
        //以宽度为标准保存丈量结果
        setMeasuredDimension(widthSize/3*2, heightSize/3*2);
    }

    private void init() {
        innerRoundColor = ContextCompat.getColor(mContext, R.color.white);
        outerRoundColor = ContextCompat.getColor(mContext, R.color.main_color);
        mTypeface = ResourcesCompat.getFont(mContext, R.font.impact);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(innerRoundColor);
        mPaint.setAlpha(80);
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mPaint.setStrokeWidth(mRoundWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);// 抗锯齿效果
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(outerRoundColor);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mProgressPaint.setStrokeWidth(mProgressRoundWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);// 抗锯齿效果
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setTextSize(sp2px(mTextSize));

        mRoundRect = new Paint();
        mRoundRect.setStyle(Paint.Style.FILL);//充满
        mRoundRect.setColor(Color.parseColor("#6BC4FE"));
        mRoundRect.setAntiAlias(true);// 设置画笔的锯齿效果
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *  处理渐变色
         */
        //默认的渐变颜色数组:
        int[] mGradientColorArray = new int[]{ContextCompat.getColor(mContext, R.color.white) , ContextCompat.getColor(mContext, R.color.white)};
        int count = mGradientColorArray.length;
        int[] colors = new int[count];
        System.arraycopy(mGradientColorArray, 0, colors, 0, count);
        float[] positions = new float[count];
        positions[0] = 0.0f;
        positions[1] = 1.0f;
        SweepGradient shader = new SweepGradient(getWidth() / 2 - mRoundWidth / 2, getWidth() / 2 - mRoundWidth / 2, mGradientColorArray, positions);
        mProgressPaint.setShader(shader);

        if (mRoundWidth < mProgressRoundWidth) {
            RectF oval = new RectF(0 + mProgressRoundWidth / 2, 0 + mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2);
            //绘制背景圆环
            canvas.drawArc(oval, 135, 270, false, mPaint);

            //绘制进度圆环，绘制的角度最大不超过270°
            if (mCurrentStep * 1f / mMaxStep <= 1) {
                canvas.drawArc(oval, 0 + 135, mCurrentStep / mMaxStep * 270, false, mProgressPaint);
            } else {
                canvas.drawArc(oval, 0 + 135, 270, false, mProgressPaint);
            }
        } else {
            RectF oval = new RectF(0 + mRoundWidth / 2, 0 + mRoundWidth / 2, getWidth() - mRoundWidth / 2, getWidth() - mRoundWidth / 2);
            //绘制背景圆环
            canvas.drawArc(oval, 135, 270, false, mPaint);
            //绘制进度圆环
            if (mCurrentStep * 1f / mMaxStep <= 1) {
                canvas.drawArc(oval, 0 + 135, mCurrentStep / mMaxStep * 270, false, mProgressPaint);
            } else {
                canvas.drawArc(oval, 0 + 135, 270, false, mProgressPaint);
            }
        }

        //绘制中间的分数的文字
        Rect textRect = new Rect();
        String mShowText = (int) mCurrentStep + "";
        mTextPaint.setTextSize(sp2px(mTextSize));
        mTextPaint.setTypeface(mTypeface);
        mTextPaint.getTextBounds(mShowText, 0, mShowText.length(), textRect);
        canvas.drawText(mShowText, getWidth()/2 - textRect.width()/2, getWidth()/2 + textRect.height()/2, mTextPaint);

        //绘制标题的文字，在分数文字的上方
        String mRandText = mContext.getString(R.string.test_scores);
        mTextPaint.setTextSize(sp2px(14));
        mTextPaint.getTextBounds(mRandText, 0, mRandText.length(), textRect);
        canvas.drawText(mRandText, getWidth()/2 - textRect.width()/2, getWidth()/4 + textRect.height()/4, mTextPaint);

        if (mIsUpdate) {
            //绘制文字下方椭圆
            roundRect = new RectF(getWidth() / 4, getWidth() / 3 * 2, getWidth() / 2 + getWidth() / 4, getWidth() / 3 * 2 + textRect.height() * 2f);// 设置个新的长方形
            canvas.drawRoundRect(roundRect, 48, 48, mRoundRect);//第二个参数是x半径，第三个参数是y半径

            //绘制重新检测
            String mRoundText = mContext.getString(R.string.to_detect);
            mTextPaint.setTextSize(sp2px(14));
            mTextPaint.getTextBounds(mRoundText, 0, mRoundText.length(), textRect);
            canvas.drawText(mRoundText, getWidth() / 2 - textRect.width() / 2, getWidth() / 3 * 2 + textRect.height() * 1.25f, mTextPaint);
        }
    }

    public void setCurrentStep(float currentStep) {
        this.mCurrentStep = currentStep;
        //强制重绘，postInvalidate()可以在主线程也可以在分线程中执行
        this.mIsUpdate = true;
        postInvalidate();
    }

    public void setMaxProgress(int maxStep) {
        this.mMaxStep = maxStep;
    }

    public int getMaxtep() {
        return mMaxStep;
    }

    public float getCurrentStep() {
        return mCurrentStep;
    }

    /**
     * 将sp转换成px
     *
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    /**
     * 开始动态计步
     *
     * @param currentStep
     */
    public void startCountStep(final float currentStep) {
        //方法一：开一个分线程，动态改变进度的值，不断绘制达到进度变化的效果
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                setCurrentStep(0);
//                float changeProgress = currentStep;
//                for (float i = 0; i < changeProgress; i++) {
//                    setCurrentStep(getCurrentStep() + rate);
//                    SystemClock.sleep(20);
////                  invalidate();//invalidate()必须在主线程中执行，此处不能使用
////                  postInvalidate();//强制重绘，postInvalidate()可以在主线程也可以在分线程中执行
//                    changeProgress = changeProgress - rate;
//                }
//                //由于上面的循环结束时，可能计算后最终无法到达mCurrentProgress的值，所以在循环结束后，将mCurrentProgress重新设置
//                setCurrentStep(currentStep);
//            }
//        }).start();

        /**
         * 方法二
         */
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, currentStep);
        valueAnimator.setDuration(8000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float currentStep1 = (float) animation.getAnimatedValue();
            setCurrentStep((int) currentStep1);
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (roundRect.contains(event.getX(), event.getY())){
            if (callBack!=null){
                callBack.onClick();
            }
            return true;
        }
        return false;
    }

    public interface onClickCallBack{
        void onClick();
    }
}

