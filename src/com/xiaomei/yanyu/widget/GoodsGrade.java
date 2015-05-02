package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GoodsGrade extends View {
    
    private Rect rect1 = new Rect();
    
    private Rect rect2 = new Rect();
    private Rect rect3 = new Rect();
    private Rect rect4 = new Rect();
    private Rect rect5 = new Rect();
    
    private static Bitmap itemIcon = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.pinglun_star);
    
    private static Bitmap itemIconChecked = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.pinglun_star_yipin);
    
    private int itemIconHeight = itemIcon.getHeight();
    
    private int itemIconWidth = itemIcon.getWidth();
    
    private Paint mPaint = new Paint();
    
    private int mGrade = 5;

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
    public int getGrade(){
    	return mGrade;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        android.util.Log.d("111", "mGrade = " + mGrade);
        int grade = mGrade;
//        super.onDraw(canvas);
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();
        int itemWidth = layoutWidth/5;
        rect1.left = 0 + (itemWidth - itemIconWidth)/2;
        rect1.top = 0 + (layoutHeight - itemIconHeight)/2;
        rect1.right = rect1.left + itemWidth;
        rect1.bottom = rect1.top + itemIconHeight;
        grade -- ;
        if(grade>=0)
            canvas.drawBitmap(itemIconChecked, rect1.left , rect1.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect1.left , rect1.top, mPaint);
        
        rect2.left = rect1.left + itemWidth;
        rect2.top = 0 + (layoutHeight - itemIconHeight)/2;
        rect2.right = rect2.left + itemWidth;
        rect2.bottom = rect2.top + itemIconHeight;
        grade -- ;
        if(grade>=0)
            canvas.drawBitmap(itemIconChecked, rect2.left , rect2.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect2.left , rect2.top, mPaint);
        
        rect3.left = rect2.left + itemWidth;
        rect3.top = 0 + (layoutHeight - itemIconHeight)/2;
        rect3.right = rect3.left + itemWidth;
        rect3.bottom = rect3.top + layoutHeight;
        grade -- ;
        if(grade>=0)
            canvas.drawBitmap(itemIconChecked, rect3.left , rect3.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect3.left , rect3.top, mPaint);
        
        rect4.left = rect3.left + itemWidth;
        rect4.top = 0 + (layoutHeight - itemIconHeight)/2;
        rect4.right = rect4.left + itemWidth;
        rect4.bottom = rect4.top + itemIconHeight;
        grade -- ;
        if(grade>=0)
            canvas.drawBitmap(itemIconChecked, rect4.left , rect4.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect4.left , rect4.top, mPaint);
        
        rect5.left = rect4.left + itemWidth;
        rect5.top = 0 + (layoutHeight - itemIconHeight)/2;
        rect5.right = rect5.left + itemWidth;
        rect5.bottom = rect5.top + itemIconHeight;
        grade -- ;
        if(grade>=0)
            canvas.drawBitmap(itemIconChecked, rect5.left , rect5.top, mPaint);
        else
            canvas.drawBitmap(itemIcon, rect5.left , rect5.top, mPaint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	int x = (int) event.getX();
    	int y = (int) event.getY();
    	android.util.Log.d("111", "x = " + x + ", y = " + y + ",rect1 = " + rect1);
    	switch (action) {
		case MotionEvent.ACTION_DOWN:
			if(rect1.contains(x,y))
				setGrade(1);
			else if (rect2.contains(x,y))
				setGrade(2);
			else if (rect3.contains(x,y))
				setGrade(3);
			else if (rect4.contains(x,y))
				setGrade(4);
			else if (rect5.contains(x,y))
				setGrade(5);
			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}
    	return super.onTouchEvent(event);
    }

}
