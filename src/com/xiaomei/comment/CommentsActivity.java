package com.xiaomei.comment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.Order;
import com.xiaomei.comment.control.CommentControl;
import com.xiaomei.widget.GoodsGrade;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseActivity;

public class CommentsActivity extends AbstractActivity<CommentControl> implements View.OnClickListener{

    public static void startActivity4Result(Activity ac,Order order){
        Intent intent = new Intent(ac,CommentsActivity.class);
        intent.putExtra("order", order);
        ac.startActivityForResult(intent, 1);
    }
	
    public static void startActivity(Context context,Order order){
        Intent intent = new Intent(context,CommentsActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }
	@Deprecated
    public static void startActivity(Context context){
        Intent intent = new Intent(context,CommentsActivity.class);
        context.startActivity(intent);
    }
	
	@Deprecated
    public static void startActivity(Context context,String goodsId){
        Intent intent = new Intent(context,CommentsActivity.class);
        intent.putExtra("goods_id", goodsId);
        context.startActivity(intent);
    }
    
    private TitleBar mTitleBar;
    private TextView mOrderId;
    private TextView mUserName;
    private TextView mCreateTime;
    private ImageView mGoodsIcon;
    private TextView mGoodsName;
    private TextView mStatus;
    private TextView mOrderAmount;
    private GoodsGrade mGradeServer;
    private GoodsGrade mGradeEnvironment;
    private GoodsGrade mGradeEffect;
    private TextView mCommentButton;
    private EditText mCommentEdit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_layout);
        setUpViews();
        initData();
    }
    
    private void setUpViews(){
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.setTitle("评论");
        mTitleBar.setBackListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        mOrderId = (TextView) findViewById(R.id.order_id);
        mUserName = (TextView) findViewById(R.id.user_name);
        mCreateTime = (TextView) findViewById(R.id.create_time);
        mGoodsIcon = (ImageView) findViewById(R.id.goods_icon);
        mGoodsName = (TextView) findViewById(R.id.goods_name);
        mStatus = (TextView) findViewById(R.id.status);
        mOrderAmount = (TextView) findViewById(R.id.order_amount);
        mGradeServer = (GoodsGrade) findViewById(R.id.grade_server);
        mGradeEnvironment = (GoodsGrade) findViewById(R.id.grade_environment);
        mGradeEffect = (GoodsGrade) findViewById(R.id.grade_effect);
        
        mCommentEdit = (EditText) findViewById(R.id.comment_edit);
        mCommentButton = (TextView) findViewById(R.id.comment_button);
        mCommentButton.setOnClickListener(this);
    }
    
    private Order mOrder;
    private void initData(){
    	mOrder = (Order) getIntent().getSerializableExtra("order");
    	mOrderId.setText(mOrder.getDataList().getId());
    	mUserName.setText(mOrder.getDataList().getUsername());
    	ImageLoader.getInstance().displayImage(mOrder.getDataList().getGoodsImg(), mGoodsIcon);
    	mGoodsName.setText(mOrder.getDataList().getGoodsName());
    	mStatus.setText("交易完成");
    	mOrderAmount.setText(mOrder.getDataList().getOrderAmount());
    	
    	mGradeServer.setGrade(5);
    	mGradeEnvironment.setGrade(5);
    	mGradeEffect.setGrade(5);
    }
	@Override
	public void onClick(View v) {
		if(TextUtils.isEmpty(mCommentEdit.getText().toString()))
			Toast.makeText(this, "评论不能为空", 0).show();
		
		mControl.addComment(mOrder.getDataList().getId(), mOrder.getDataList()
				.getGoodsId(), mCommentEdit.getText().toString(), 
				String.valueOf(mGradeServer.getGrade()),
				String.valueOf(mGradeEnvironment.getGrade()), 
				String.valueOf(mGradeEffect.getGrade()));
	}
	
	// ==========================================  CallBack  ======================================================
	public void addCommentCallBack(){
		Toast.makeText(this, "评论完成", 0).show();
		setResult(RESULT_OK);
		finish();
	}
	
	public void addCommentExceptionCallBack(){
		Toast.makeText(this, "网络异常", 0).show();
	}
    
}
