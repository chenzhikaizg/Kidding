package com.example.a.permissionone;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import static com.example.a.permissionone.FiveEightView.Direction.CIRCLE;
import static com.example.a.permissionone.FiveEightView.Direction.RECTANGLE;
import static com.example.a.permissionone.FiveEightView.Direction.TRIANGLE;

/**
 * Created by Administrator on 2017/12/24.
 */

public class FiveEightView  extends View{

    private int mTypeColor = Color.RED;
    private Paint mPaint;

    private Direction mDirection =CIRCLE;
    public enum Direction{
        CIRCLE,TRIANGLE,RECTANGLE
    }


    public FiveEightView(Context context) {
        this(context,null);
    }

    public FiveEightView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FiveEightView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.FiveEightView);
        mTypeColor = array.getColor(R.styleable.FiveEightView_typeColor, mTypeColor);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTypeColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width>height ? height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDirection == Direction.CIRCLE){
            drawCircle(mPaint,canvas);
        }else if (mDirection == TRIANGLE){
            drawPath(mPaint,canvas);

        }else if (mDirection == RECTANGLE){
            drawRect(mPaint,canvas);
        }



    }

    private void drawCircle(Paint paint ,Canvas canvas) {
        canvas.drawCircle(getWidth()/2,getHeight()/2,50,mPaint);

    }
    private void drawRect(Paint paint ,Canvas canvas) {

        Rect rect = new Rect(0,0,getWidth(),getHeight());
        canvas.drawRect(rect,mPaint);

    }
    private void drawPath(Paint paint ,Canvas canvas) {

        Path path = new Path();
        path.moveTo(getWidth()/2,0);
        path.lineTo(0,getHeight());
        path.lineTo(getWidth(),getHeight());
        path.close();
       canvas.drawPath(path,mPaint);

    }
    public void setType(Direction direction){
        this.mDirection = direction;
        invalidate();
    }

}
