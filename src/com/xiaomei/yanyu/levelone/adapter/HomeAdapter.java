package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Section;
import com.xiaomei.yanyu.leveltwo.BeautifulRingDetailsActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.HomeStyle2;
import com.xiaomei.yanyu.leveltwo.WebViewActivity;
import com.xiaomei.yanyu.util.ScreenUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeAdapter extends BaseAdapter implements View.OnClickListener{

	private List<Section> mData;
	
	private LayoutInflater mInflater;
	
	private Context mContext;
	
	private View.OnClickListener mNavOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String url = (String) v.getTag();
			android.util.Log.d("111", "url = " + url);
			GoodsDetailActivity.startActivity((Activity)mContext, url);
		}
	};
	
	public HomeAdapter(List<Section> data,Context context,ImageLoader imageLoader){
		mData = data;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}
	
	public void setData(List<Section> data){
		mData = data;
	}
	
	public List<Section> getData(){
		return mData;
	}
	
	@Override
	public int getCount() {
		return mData == null ?0:mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		Section section = mData.get(position);
		Holder holder = null;
		if(convertView == null){
			holder = new Holder();
			switch (type) {
			case 0:  // 热点
				convertView = mInflater.inflate(R.layout.section_topic, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 1:  // 导航
				convertView = mInflater.inflate(R.layout.section_nav, null);
				convertView.findViewById(R.id.youhui).setOnClickListener(mNavOnClickListener);
				convertView.findViewById(R.id.youhui).setTag(section.getList().get(0).getUrl());
				convertView.findViewById(R.id.payment).setOnClickListener(mNavOnClickListener);
				convertView.findViewById(R.id.payment).setTag(section.getList().get(1).getUrl());
				convertView.findViewById(R.id.riji).setOnClickListener(mNavOnClickListener);
				convertView.findViewById(R.id.riji).setTag(section.getList().get(2).getUrl());
				convertView.findViewById(R.id.jiangli).setOnClickListener(mNavOnClickListener);
				convertView.findViewById(R.id.jiangli).setTag(section.getList().get(3).getUrl());
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 2:  // 商品精选		if(holder.commontNumView!=null && en)
				convertView = mInflater.inflate(R.layout.section_jingxuan, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 3:  // 分享
				convertView =  mInflater.inflate(R.layout.section_share, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			case 4:   //会员活动
				convertView =  mInflater.inflate(R.layout.section_vip, null);
				convertView.setOnClickListener(this);
				attachView2Holder(holder, (ViewGroup)convertView, 0, 0);
				break;
			default:
				break;
			}
			convertView.setTag(holder);
		}
		holder = (Holder) convertView.getTag();
		attachData2Holder(type, section, holder,convertView);	
		return convertView;
	}

	private void attachData2Holder(int type ,Section section ,Holder holder,View convertView){
		Section.Entity entity = null;
		if(section==null || holder == null)
			return;
		switch (type) {
		case 1: // 导航
			entity =  section.getList().get(0);
			if(entity!=null)
				holder.youhui.setTag(entity.getUrl());
			entity =  section.getList().get(1);
			if(entity!=null)
				holder.payment.setTag(entity.getUrl());
			entity =  section.getList().get(2);
			if(entity!=null)
				holder.riji.setTag(entity.getUrl());
			entity =  section.getList().get(3);
			if(entity!=null)
				holder.jiangli.setTag(entity.getUrl());
			holder.section_type = "nav";
			holder.viewcount = section.getViewCount();
			break;
		case 3:  //分享
			entity =  section.getList().get(0);
			android.util.Log.d("111", "entity = " + entity);
			if(holder.personIconView!=null && !TextUtils.isEmpty( entity.getNumcomment()))
				ImageLoader.getInstance().displayImage(entity.getAvator(), holder.personIconView); 
			if(holder.personnameView!=null && !TextUtils.isEmpty( entity.getNumfavorite()))
				holder.personnameView.setText(entity.getUsername());
			if(holder.timeView!=null && !TextUtils.isEmpty( entity.getDate()))
				holder.timeView.setText(entity.getDate());
			if(holder.imgView!=null && !TextUtils.isEmpty( entity.getImg())){
				holder.imgView.setImageResource(R.drawable.ring_default_img);
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
				holder.imgView.getLayoutParams().height = ScreenUtils.getScreenWidth(mContext)*516/720;
			}

//			if(holder.titleView!=null && !TextUtils.isEmpty( entity.getTitle()))
			android.util.Log.d("333", "title = " + entity.getTitle() );
				holder.titleView.setText(entity.getTitle());
			holder.bubleSize.setText(entity.getNumcomment());
			if(holder.favoriteNumView!=null && !TextUtils.isEmpty( entity.getNumfavorite()))
				holder.favoriteNumView.setText(entity.getNumfavorite());
				holder.url = entity.getUrl();
				holder.type = section.getType();
				
				holder.listData = section.getList_String();
				holder.des = section.getDes();
				holder.tilte = section.getTitle();
				holder.img_url = entity.getImg();
				holder.section_type = "share";
				holder.share_id = entity.getShareId();
				holder.viewcount = section.getViewCount();
			break;
		case 0: //首张图
			entity =  section.getList().get(0);
			if(holder.imgView!=null && !TextUtils.isEmpty(entity.getImg())){
				if(holder.root!=null){
					AbsListView.LayoutParams ll = new AbsListView.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							(int) (ScreenUtils.getScreenWidth(mContext) * 730 / 720)
									+ ScreenUtils.dip2px(mContext, 10));
					holder.root.setLayoutParams(ll);
				}
				if(holder.line != null){
					holder.line.getLayoutParams().height = ScreenUtils.dip2px(mContext, 10);
				}
				holder.imgView.setImageResource(R.drawable.home_default_img);
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
				holder.imgView.getLayoutParams().height = (int) (ScreenUtils.getScreenWidth(mContext)*730/720);
			}
			holder.url = entity.getUrl();
			holder.type = section.getType();
			
			holder.listData = section.getList_String();
			holder.des = section.getDes();
			holder.tilte = section.getTitle();
			holder.img_url = entity.getImg();
			holder.section_type = "topic";
			holder.viewcount = section.getViewCount();
			break;
		case 2: //商品精选
			entity =  section.getList().get(0);
			if(holder.imgView!=null && !TextUtils.isEmpty(entity.getImg())){
				holder.imgView.setImageResource(R.drawable.home_transverse_default_img);
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
				holder.imgView.getLayoutParams().width = ScreenUtils.getScreenWidth(mContext)*430/720;
			}
			holder.url = entity.getUrl();
			holder.type = section.getType();
			
			holder.listData = section.getList_String();
			holder.des = section.getDes();
			holder.tilte = section.getTitle();
			holder.img_url = entity.getImg();
			holder.section_type = "jingxua";
			holder.viewcount = section.getViewCount();
			break;
		case 4: //会员活动
			entity =  section.getList().get(0);
//			if(holder.titleView!=null && !TextUtils.isEmpty(section.getTitle()))
//				holder.titleView.setText(section.getTitle()); //TODO
			if(holder.imgView!=null && !TextUtils.isEmpty(entity.getImg())){
				holder.imgView.setImageResource(R.drawable.home_transverse_default_img);
				ImageLoader.getInstance().displayImage(entity.getImg(), holder.imgView);
				holder.imgView.getLayoutParams().height = (int) (ScreenUtils.getScreenWidth(mContext)*428/720);
			}
			holder.url = entity.getUrl();
			holder.type = section.getType();

			holder.listData = section.getList_String();
			holder.des = section.getDes();
			holder.tilte = section.getTitle();
			holder.img_url = entity.getImg();
			holder.section_type = "vip";
			holder.viewcount = section.getViewCount();
			break;
		default:
			break;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public int getItemViewType(int position) {
		Section section = mData.get(position);
		if(section.getKey().equals("topic"))
			return 0;
		if(section.getKey().equals("nav"))
			return 1;
		if(section.getKey().equals("jingxua"))
			return 2;
		if(section.getKey().equals("share"))
			return 3;
		if(section.getKey().equals("vip"))
			return 4;
		return 4;
	}
	
	
	private void attachView2Holder(Holder holder,ViewGroup vGroup,int imageWidth,int imageHeight){
		holder.personIconView =  (ImageView) vGroup.findViewById(R.id.person_icon);
		holder.personnameView = (TextView) vGroup.findViewById(R.id.person_name);
		holder.timeView = (TextView) vGroup.findViewById(R.id.time);
		holder.imgView = (ImageView) vGroup.findViewById(R.id.icon);
		holder.titleView = (TextView) vGroup.findViewById(R.id.title);
		holder.favoriteNumView = (TextView) vGroup.findViewById(R.id.like_size);
		
		holder.youhui = vGroup.findViewById(R.id.youhui);
		holder.payment = vGroup.findViewById(R.id.payment);
		holder.jiangli = vGroup.findViewById(R.id.jiangli);
		holder.riji = vGroup.findViewById(R.id.riji);
		holder.root = (ViewGroup) vGroup.findViewById(R.id.root);
		holder.line = vGroup.findViewById(R.id.line);
		holder.bubleSize = (TextView) vGroup.findViewById(R.id.buble_size);
		//TODO :处理图片大小
//		holder.favoriteNumView.getLayoutParams().width = imageWidth;
	}
	
	private static class Holder{
//		List<Item> itemList;
//		static class Item{
			private ImageView personIconView; //用户icon
			private TextView personnameView;  //用户名
			private TextView timeView;  //时间
			private ImageView imgView;  //icon图
			private TextView titleView; //标题
			private TextView favoriteNumView; //点赞
			private TextView bubleSize;
			private String url ;//详情页url
			private View youhui; //优惠
			private View payment; //赔付
			private View riji; //日记
			private View jiangli; //奖励
			private ViewGroup root;
			private View line; //分割线
			private String type; //区分点击的动作
			/*带过来的数据*/
			private String listData;
			private String tilte;
			private String des;
			private String img_url;
			private String section_type; //区分 分享 热点 等。。。
			private String share_id;//分享的id
			private String viewcount;//浏览记录
//		}
	}

	@Override
	public void onClick(View v) {
		Holder holder = (Holder) v.getTag();
		android.util.Log.d("222", "type = " + holder.type + ",data = " + holder.listData);
		android.util.Log.d("111", "holder.share_id = " + holder.share_id + ", holder.type = " + holder.type + ",holder.section_type = " + holder.section_type);
		if("0".equals(holder.type)){
			GoodsDetailActivity.startActivity((Activity)mContext, holder.url);
		}else if("1".equals(holder.type)){
			if("share".equals(holder.section_type) && !TextUtils.isEmpty(holder.share_id)){
				android.util.Log.d("111", "holder.share_id = " + holder.share_id);
				BeautifulRingDetailsActivity.startActivity((Activity)mContext, holder.share_id);
			}else{
				HomeStyle2.startActivity((Activity)mContext,holder.listData,holder.tilte,holder.des,holder.img_url,holder.viewcount);
			}
		}

	}
	
}
