package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

/**
 * 机构列表的星星
 */
public class StarsView extends GoodsGrade {
	
    

	public StarsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public StarsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StarsView(Context context) {
		super(context);
		init();
	}
	
	private void  init(){
		itemIcon = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.jg_xx_gary);
		itemIconChecked = BitmapFactory.decodeResource(XiaoMeiApplication.getInstance().getResources(), R.drawable.jg_xx);
		itemIconHeight = itemIcon.getHeight();
		itemIconWidth = itemIcon.getWidth();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(6*itemIcon.getWidth(), /*(int)2*/itemIcon.getHeight());
	}
	
	

}
