package com.example.a.permissionone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/12/23.
 */

public class TextView  extends View {

    private String myTextBackground;
    private String myText;
    private int myTextSize = 15;
    private int myTextColor  = Color.BLUE;
    private Paint mPaint;
    public TextView(Context context) {
        this(context, null);

    }

    public TextView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);

        myText = typedArray.getString(R.styleable.MyTextView_MyTextText);
        myTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_MyTextSize,sp2px(myTextSize));
        myTextColor = typedArray.getColor(R.styleable.MyTextView_MyTextColor,myTextColor);

        typedArray.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(myTextSize);
        mPaint.setColor(myTextColor);
    }

    private int sp2px(int sp) {

        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //1.确定的值，这个时候不需要计算，给多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //2.给的是wrap_content，就要去计算了
        if (widthMode == MeasureSpec.AT_MOST){
            //计算的宽度 与字体的长度，大小有关
            Rect bounds= new Rect();
            mPaint.getTextBounds(myText,0,myText.length(),bounds);
            width = bounds.width()+getPaddingLeft()+getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST){
            //计算的宽度 与字体的长度，大小有关
            Rect bounds= new Rect();
            //获取文本的rect
            mPaint.getTextBounds(myText,0,myText.length(),bounds);
            height = bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        //设置控件的宽高
        setMeasuredDimension(width,height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画文字 text x y paint
        //x 就是开始的位置
        //y是基线
        //dy代表的是 高度的一半 baseline的距离
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        //bottom是正值 top是负值 bootom和top带标的是到baseLine的距离到文字底部的距离
        int dy = (fontMetrics.bottom - fontMetrics.top)/2-fontMetrics.bottom;
        int baseLine = getHeight()/2+dy;

        int x = getPaddingLeft();
        canvas.drawText(myText,x,baseLine,mPaint);


    }
}
