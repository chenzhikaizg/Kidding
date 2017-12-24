package com.example.a.permissionone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/12/23.
 */

public class QQStepView extends View {
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;//20PX
    private int mStepTextSize;
    private int mStepTextColor;
    //总步数
    private int mStepMax ;
    //当前的步数
    private int mCurrentStep ;
  private   Paint mOutPaint,mInnerPaint,mTextPaint;



    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果

        //2.确定自定义属性编写，编写attrs。xml

        //3.在布局中使用

        //4.在自定义view中获取自定义属性
       TypedArray array =  context.obtainStyledAttributes(attrs,R.styleable.QQStepView);
       mOuterColor =  array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        array.recycle();
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);


        //5.onMeasure

        //6.onDraw 画外圆弧 内圆弧 文字


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中，可能会是wrap_content，还有可能是宽度高度不一致。
        //获取模式

        //宽高不一致，取最小的
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width>height ? height:width,width>height?height:width);
    }

    public synchronized void setStepMax(int step){
        mStepMax = step;

    }
    public synchronized  void setCurrentStep(int currentStep){
        mCurrentStep = currentStep;

        //不断绘制
          invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧

        int center = getWidth()/2;
        int radius = getWidth()/2-mBorderWidth/2;


       // RectF rectF = new RectF(center-radius,center-radius,center+radius,center+radius);
        RectF rectF = new RectF(mBorderWidth/2,mBorderWidth/2,getWidth()-mBorderWidth/2,getHeight()-mBorderWidth/2);

        canvas.drawArc(rectF,135,270,false,mOutPaint);
        //画内圆弧  百分比的 是用户传的
        if (mStepMax==0) return;
        float sweepAngle = (float) mCurrentStep/mStepMax;

        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);



        //画文字
        String stepText = mCurrentStep+"";
        Rect textBouns = new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBouns);
        //基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2+dy;
        int dx = getWidth()/2-textBouns.width()/2;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);

    }
}
