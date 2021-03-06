package com.xiaomei.yanyu.widget;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.paveldudka.util.FastBlur;
import com.xiaomei.yanyu.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BlurImageView extends ImageView {

	public BlurImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BlurImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BlurImageView(Context context) {
		super(context);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
	    bm = FastBlur.blur(bm, this, getContext());
//		bm = ImageUtils.blurImages(bm, getContext());
		super.setImageBitmap(bm);
		mBitmap = bm;
	}
	
	private Bitmap mBitmap;
	@Override
	protected void onDetachedFromWindow() {
	    super.onDetachedFromWindow();
	    if(mBitmap!=null){
	        mBitmap.recycle();
	    }
	    mBitmap = null;
	    System.gc();
	}

}
