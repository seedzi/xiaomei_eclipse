package com.xiaomei.widget;

import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GoodsGrade extends View {
    
    private Rect rect = new Rect();
    
    private static Bitmap itemIcon = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.pinglun_star);
    
    private static Bitmap itemIconChecked = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.pinglun_star_yipin);
    
    private int itemIconHeight = itemIcon.getHeight();
    
    private int itemIconWidth = itemIcon.getWidth();
    
    private Paint mPaint = new Paint();
    
    private int mGrade = 0;

    public GoodsGrade(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public GoodsGrade(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public GoodsGrade(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, itemIcon.getHeight());
    }
    
    public void setGrade(int grade){
        mGrade = grade;
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();
        int itemWidth = layoutWidth/5;
        rect.left = 0 + (itemWidth - itemIconWidth)/2;
//        rect.right = rect.left + itemIconWidth;
        rect.top = 0 + (layoutHeight - itemIconHeight)/2;
//        rect.bottom = rect.top + itemIconHeight;
        mGrade -- ;
        if(mGrade>=0)
            canvas.drawBitmap(itemIconChecked, rect.left , rect.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect.left , rect.top, mPaint);
        
        mGrade -- ;
        rect.left = rect.left + itemWidth;
        if(mGrade>=0)
            canvas.drawBitmap(itemIconChecked, rect.left , rect.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect.left , rect.top, mPaint);
        
        mGrade -- ;
        rect.left = rect.left + itemWidth;
        if(mGrade>=0){
            canvas.drawBitmap(itemIconChecked, rect.left , rect.top, mPaint);
        }else{
            canvas.drawBitmap(itemIcon, rect.left , rect.top, mPaint);
        }
        
        mGrade -- ;
        rect.left = rect.left + itemWidth;
        if(mGrade>=0){
            canvas.drawBitmap(itemIconChecked, rect.left , rect.top, mPaint);
        }else{
            canvas.drawBitmap(itemIcon, rect.left , rect.top, mPaint);
        }
        
        mGrade -- ;
        rect.left = rect.left + itemWidth;
        if(mGrade>=0){
            canvas.drawBitmap(itemIconChecked, rect.left , rect.top, mPaint);
        }else{
            canvas.drawBitmap(itemIcon, rect.left , rect.top, mPaint);
        }
    }

}
