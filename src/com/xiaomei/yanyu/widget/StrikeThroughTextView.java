package com.xiaomei.yanyu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class StrikeThroughTextView extends TextView{

    private Paint mPaint = new Paint();
    
    public StrikeThroughTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StrikeThroughTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrikeThroughTextView(Context context) {
        super(context);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#9b9b9b"));
        canvas.drawLine(getWidth(), 0, 0, getHeight(), mPaint);
    }

}
