package com.xiaomei.yanyu.widget.pullrefreshview;




import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomei.yanyu.R;


public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
	private Context mContext;

	private final ImageView mHeaderImage;
	private final ProgressBar mHeaderProgress;
	private final TextView mHeaderText;
	private TextView mHeaderTimeText;

	private String mPullLabel;
	private String mRefreshingLabel;
	private String mReleaseLabel;
	private String mPullTimeLable;
	
	private int currentMode;

	private final Animation mRotateAnimation, mResetRotateAnimation;

	public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel,
			String refreshingLabel, String pullTimeLable, TypedArray attrs) {
		super(context);
		mContext = context;
		currentMode = mode;
		ViewGroup header = (ViewGroup) LayoutInflater.from(mContext).
				inflate(R.layout.pull_to_refresh_header, this);
		mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		mHeaderImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		mHeaderProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
		mHeaderTimeText = (TextView) header.findViewById(R.id.pull_to_refresh_time_text);
		
//		ThemeSettingsHelper.setTextViewColor(mContext, mHeaderText, R.color.favorite_confirm_delete_color);
//		ThemeSettingsHelper.setTextViewColor(mContext, mHeaderTimeText, R.color.favorite_confirm_delete_color);

		final Interpolator interpolator = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(interpolator);
		mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mResetRotateAnimation.setInterpolator(interpolator);
		mResetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);

		mReleaseLabel = releaseLabel;
		mPullLabel = pullLabel;
		mRefreshingLabel = refreshingLabel;
		mPullTimeLable = pullTimeLable;

		switch (currentMode) {
			case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
				mHeaderImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
				break;
			case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			default:
                mHeaderImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
				break;
		}

		if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
			final int color = attrs.getColor(R.styleable.PullToRefresh_ptrHeaderTextColor, Color.BLACK);
			setTextColor(color);
		}
	}

	public void reset() {
		mHeaderText.setText(mPullLabel);
		mHeaderTimeText.setText(mPullTimeLable);
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.VISIBLE);
		mHeaderProgress.setVisibility(View.GONE);
	}

	public void releaseToRefresh() {
		mHeaderText.setText(mReleaseLabel);
		mHeaderTimeText.setText(mPullTimeLable);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mRotateAnimation);
	}

	public void setPullLabel(String pullLabel) {
		mPullLabel = pullLabel;
	}
	
	public void setPullTimeLable(String pullTimeLabel){
	    this.mPullTimeLable = pullTimeLabel;
	}

	public void refreshing() {
		mHeaderText.setText(mRefreshingLabel);
		mHeaderTimeText.setText(mPullTimeLable);
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.INVISIBLE);
		mHeaderProgress.setVisibility(View.VISIBLE);
	}
	public void setTimerefreshing() {
	
		mHeaderTimeText.setText(mPullTimeLable);
	}
	public void setRefreshingLabel(String refreshingLabel) {
		mRefreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		mReleaseLabel = releaseLabel;
	}

	public void pullToRefresh() {
		mHeaderText.setText(mPullLabel);
		mHeaderTimeText.setText(mPullTimeLable);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mResetRotateAnimation);
	}

	public void setTextColor(int color) {
		mHeaderText.setTextColor(color);
	}
	
	public void setHeaderTimeTextVisible(int visibility){
	    mHeaderTimeText.setVisibility(visibility);
	    switch (visibility){
	    case VISIBLE:
	        break;
	    case GONE:
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mHeaderText
                    .getLayoutParams();
            params.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mHeaderText.setLayoutParams(params);
            break;
	    }
	}

	public void applyTheme(){
//	    ThemeSettingsHelper.setTextViewColor(mContext, mHeaderText, R.color.favorite_confirm_delete_color);
//        ThemeSettingsHelper.setTextViewColor(mContext, mHeaderTimeText, R.color.favorite_confirm_delete_color);
        switch (currentMode) {
        case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
        	mHeaderImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
//            ThemeSettingsHelper.setImageViewSrc(mContext, mHeaderImage, R.drawable.pulltorefresh_up_arrow);
            break;
        case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
        default:
        	mHeaderImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
//            ThemeSettingsHelper.setImageViewSrc(mContext, mHeaderImage, R.drawable.pulltorefresh_down_arrow);
            break;
        }
	}
}
