package com.example.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.common.R;
import com.example.common.utils.DisplayUtils;

/**
 * Created by 许格 on 2019/10/15.
 * 圆圈进度条控件
 */
public class RoundProgressView extends View {

    /**最外围的颜色值*/
    private int mOutRoundColor;
    /**中心圆的颜色值*/
    private int mCenterRoundColor;

    /**中心圆err的颜色值*/
    private int mCenterErrRoundColor ;
    /**进度的颜色*/
    private int mProgressRoundColor ;
    /**进度的背景颜色*/
    private int mProgressRoundBgColor ;

    /**进度的背景颜色2*/
    private int mProgressErrRoundBgColor;

    /**进度条的宽度*/
    private int mProgressWidth = 20;

    /**Err的宽度*/
    private int errWidth = 30;

    /***字体颜色*/
    private int mTextColor;
    private float mPencentTextSize = 100;

    private int mWidth,mHeight;
    private int mPaddingX;
    private int Screenwidth;

    private float mProgress = 0.5f;
    private float mMax = 1.0f;

    private Paint mPaint = new Paint();
    private boolean flag = false;

    public RoundProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RoundProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundProgressView(Context context) {
        super(context);
    }

    public void init(Context context, AttributeSet attrs){
        Screenwidth = DisplayUtils.getScreenWidth(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressView);
        mTextColor = typedArray.getColor(R.styleable.RoundProgressView_outRoundColor, Color.parseColor("#4C80F8"));
        mOutRoundColor = typedArray.getColor(R.styleable.RoundProgressView_outRoundColor, Color.parseColor("#FFFFFF"));
        mCenterRoundColor = typedArray.getColor(R.styleable.RoundProgressView_centerRoundColor, Color.parseColor("#e5f5fe"));
        mCenterErrRoundColor = typedArray.getColor(R.styleable.RoundProgressView_centerRoundColor, Color.parseColor("#e4e4e4"));
        mProgressRoundColor = typedArray.getColor(R.styleable.RoundProgressView_centerRoundColor, Color.parseColor("#4C80F8"));
        mProgressRoundBgColor = typedArray.getColor(R.styleable.RoundProgressView_centerRoundColor, Color.parseColor("#e5f5fe"));
        mProgressErrRoundBgColor = typedArray.getColor(R.styleable.RoundProgressView_centerRoundColor, Color.parseColor("#aaaaaa"));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        if(mWidth > mHeight){
            mPaddingX = (mWidth-mHeight)/2;
            mWidth = mHeight;
        }
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mOutRoundColor);
        RectF oval = new RectF(new Rect(mPaddingX, 0, mWidth+mPaddingX, mHeight));
        canvas.drawArc(oval, 0, 360, true, mPaint);

        if(!flag){
            int halfWidth = mWidth/4;
            mPaint.setStrokeWidth(mProgressWidth);
            mPaint.setColor(mProgressRoundBgColor);
            mPaint.setStyle(Paint.Style.STROKE);
            oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*3+mPaddingX, halfWidth*3));
            canvas.drawArc(oval, 0, 360, false, mPaint);

            mPaint.setColor(mProgressRoundColor);
            canvas.drawArc(oval, 0, 360, false, mPaint);

            halfWidth = mWidth/4;
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mCenterRoundColor);
            oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*3+mPaddingX, halfWidth*3));
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }else{
            int halfWidth = mWidth/4;
            mPaint.setStrokeWidth(mProgressWidth);
            mPaint.setColor(mProgressErrRoundBgColor);
            mPaint.setStyle(Paint.Style.STROKE);
            oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*3+mPaddingX, halfWidth*3));
            canvas.drawArc(oval, 0, 360, false, mPaint);


            halfWidth = mWidth/4;
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mCenterErrRoundColor);
            oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*3+mPaddingX, halfWidth*3));
            canvas.drawArc(oval, 0, 360, false, mPaint);
        }



        if(!flag){
            mPaint.reset();
            mPaint.setTextSize(mPencentTextSize*2);
            mPaint.setColor(mTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(Paint.Align.CENTER);
            String number = (int)(mProgress*100/mMax)+"";
            canvas.drawText(number, mWidth/2-mPencentTextSize/3*2, mHeight/2+mPencentTextSize/3*2, mPaint);

            float textWidth = mPaint.measureText(number);
            mPaint.setTextSize(mPencentTextSize*2);
            canvas.drawText("%", mWidth/2+textWidth/2, mHeight/2+mPencentTextSize/3*2, mPaint);
        }else{
            mPaint.reset();
            mPaint.setColor(mProgressErrRoundBgColor);
            mPaint.setStrokeWidth(errWidth);
            canvas.drawLine(Screenwidth/2-mPencentTextSize, mHeight/2-mPencentTextSize, Screenwidth/2+mPencentTextSize, mHeight/2+mPencentTextSize, mPaint);
            canvas.drawLine(Screenwidth/2-mPencentTextSize, mHeight/2+mPencentTextSize, Screenwidth/2+mPencentTextSize, mHeight/2-mPencentTextSize, mPaint);
        }

    }

    public void setMax(float mMax) {
        this.mMax = mMax;
    }

    public void setProgress(float mProgress) {
        flag = false;
        this.mProgress = mProgress;
        invalidate();
    }

    public void setErrStatus() {
        flag = true;
        this.mProgress = 0;
        invalidate();
    }

    public float getMax() {
        return mMax;
    }

    public float getProgress() {
        return mProgress;
    }

    //传入参数widthMeasureSpec、heightMeasureSpec 使其形状为正方形
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
