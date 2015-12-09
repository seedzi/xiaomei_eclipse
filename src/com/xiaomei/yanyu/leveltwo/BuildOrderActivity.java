package com.xiaomei.yanyu.leveltwo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.Payment.PayUtils;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;
/**
 * 生成订单页
 * @author huzhi
 */
public class BuildOrderActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{
    

    /*
    holder.iconIv = (ImageView) convertView.findViewById(R.id.icon);
    holder.titleTv = (TextView) convertView.findViewById(R.id.title);
    holder.sizeTv = (TextView) convertView.findViewById(R.id.size);
    holder.hospitalTv = (TextView) convertView.findViewById(R.id.hospital_name);
    holder.localTv = (TextView) convertView.findViewById(R.id.location);
    holder.priceTv = (TextView) convertView.findViewById(R.id.price);
    holder.localTv = (TextView) convertView.findViewById(R.id.location);
            holder.mark1 = (TextView) convertView.findViewById(R.id.tag_1);
                holder.mark2 = (TextView) convertView.findViewById(R.id.tag_2);
                holder.mark3 = (TextView) convertView.findViewById(R.id.tag_3);
*/
    public static void startActivity(Activity ac,String goodsId){
        Intent intent = new Intent(ac,BuildOrderActivity.class);
        intent.putExtra("goods_id", goodsId);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_out_no_change, R.anim.activity_slid_in_from_right);
    }
    
    private ImageView iconIv; 
    private TextView titleTv;
    private TextView sizeTv;
    private TextView hospitalTv;
    private TextView localTv;
    private TextView priceTv;
    private TextView mark1;
    private TextView mark2 ;
    private TextView mark3;
    private TextView priceMarketTv ;
    private View buildOrder;
    private View itemGoodsLayout;
    
    private String goodsId; //产品id
    private TitleBar mTitlebar;
    
    private EditText mUsername;
    private EditText mUserMobile;
    private EditText mUserPassport;
    
    private View mDiscountLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_layout);
        setupView();
        initData();
    }

    private void setupView(){
        mTitlebar = (TitleBar) findViewById(R.id.titlebar);
        mTitlebar.setTitle("生成订单");
        mTitlebar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
        mTitlebar.findViewById(R.id.edit).setVisibility(View.GONE);
        mTitlebar.findViewById(R.id.fav).setVisibility(View.GONE);
        mTitlebar.findViewById(R.id.share).setVisibility(View.GONE);
        mTitlebar.findViewById(R.id.build_order).setVisibility(View.VISIBLE);
        buildOrder = findViewById(R.id.build_order);
        buildOrder.setOnClickListener(this);
        
        itemGoodsLayout = findViewById(R.id.item_goods_layout);
        iconIv = (ImageView)itemGoodsLayout.findViewById(R.id.icon);
        titleTv = (TextView)itemGoodsLayout.findViewById(R.id.title);
        sizeTv = (TextView)itemGoodsLayout.findViewById(R.id.size);
        hospitalTv = (TextView)itemGoodsLayout.findViewById(R.id.hospital_name);
        localTv = (TextView)itemGoodsLayout.findViewById(R.id.location);
        priceTv = (TextView)itemGoodsLayout.findViewById(R.id.price);
        mark1 = (TextView)itemGoodsLayout.findViewById(R.id.tag_1);
        mark2 = (TextView)itemGoodsLayout.findViewById(R.id.tag_2);
        mark3 = (TextView)itemGoodsLayout.findViewById(R.id.tag_3);
        priceMarketTv = (TextView) findViewById(R.id.origin_price);
        
        TextView title = (TextView) findViewById(R.id.item1).findViewById(R.id.title);     
        title.setText("客户姓名");
        title = (TextView) findViewById(R.id.item2).findViewById(R.id.title);     
        title.setText("客户电话");
        title = (TextView) findViewById(R.id.item3).findViewById(R.id.title);     
        title.setText("护照号");
        mUsername = (EditText) findViewById(R.id.item1).findViewById(R.id.value);     
        mUserMobile = (EditText) findViewById(R.id.item2).findViewById(R.id.value);     
        mUserPassport = (EditText) findViewById(R.id.item3).findViewById(R.id.value);     
        
        goodsId = getIntent().getStringExtra("goods_id");
        
        mDiscountLayout = findViewById(R.id.discount_layout);
        mDiscountLayout.setOnClickListener(this);
    }
    
    private void initData(){
        mControl.getGoodsFromNetAsyn(goodsId);
    }

    // =========================================== CallBack =====================================================
    public void getGoodsFromNetAsynCallback(){
        UserInfo userInfo = UserUtil.getUser().getUserInfo();
        mUsername.setText(userInfo.getUsername());
        mUserMobile.setText(userInfo.getMobile());
        mUserPassport.setText(userInfo.getIdcard());
        
        Goods goods = mControl.getModel().getGoods();
        
        ImageLoader.getInstance().displayImage(goods.getFileUrl(),iconIv );
        titleTv .setText(goods.getTitle());
        sizeTv.setText("销量" + goods.getSales());
        hospitalTv.setText(goods.getHospName());
        priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
        localTv.setText(goods.getCityName());
        priceMarketTv.setText("原价"+goods.getPriceMarket()+"元");
        
        List<Goods.Mark> marks = goods.getMarks();
        int i = 0;
        GradientDrawable shapeDrawable  = null;
        if(marks!=null){
            for(Goods.Mark mark:marks){
                switch (i) {
                case 0:
                    mark1.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark1.setBackground(shapeDrawable);
                    mark1.setText(mark.getLabel());
                    break;
                case 1:
                    mark2.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark2.setBackground(shapeDrawable);
                    mark2.setText(mark.getLabel());
                    break;
                case 2:
                    mark3.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark3.setBackground(shapeDrawable);
                    mark3.setText(mark.getLabel());
                    break;
                default:
                    break;
                }
                i++;
            }
        }
    }

    public void getGoodsFromNetAsynExceptionCallback(){
        
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.build_order:
            if(!PayUtils.checkoutInputData(mUsername.getText().toString(),
                    mUserMobile.getText().toString(), 
                    mUserPassport.getText().toString())){
                Toast.makeText(this, "请您完整的输入您的信息", 0).show();
                return;
            }
            OrderDetailsActivity.startActivity(this, goodsId, mUsername
                    .getText().toString(), mUserMobile.getText().toString(),
                    mUserPassport.getText().toString());
            finish();
            break;
        case R.id.discount_layout:
            
            break;
        default:
            break;
        }
    }
    
    // =========================================== 业务 =====================================================

}
