package com.xiaomei.yanyu.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.GoodsGrade;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MerchantAdapter extends ArrayAdapter<Merchant> {

    private DisplayImageOptions mImageOptions;

    public MerchantAdapter(Context context) {
        super(context, 0);
        mImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.merchant_image_default)
                .showImageOnLoading(R.drawable.merchant_image_default)
                .showImageOnFail(R.drawable.merchant_image_default).build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView :
            LayoutInflater.from(getContext()).inflate(R.layout.merchant_list_item, parent, false);

        final Merchant merchant = getItem(position);
        ImageView cover = UiUtil.findImageViewById(itemView, R.id.cover);
        ImageUtils.setViewPressState(cover);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.startActivity((Activity) v.getContext(), HttpUrlManager.MERCHANT_DETAIL_URL + "?hosp_id=" + merchant.getId(),merchant.getName(),merchant.getImageLarge());
            }
        });
        ImageLoader.getInstance().displayImage(merchant.getImageLarge(), cover, mImageOptions);
        UiUtil.findTextViewById(itemView, R.id.description).setText(merchant.getDescription());

        UiUtil.findTextViewById(itemView, R.id.name).setText(merchant.getName());
        UiUtil.findTextViewById(itemView, R.id.address).setText(merchant.getAddr());

        UiUtil.<GoodsGrade>findById(itemView, R.id.rate).setGrade(merchant.getRateService());

        UiUtil.findTextViewById(itemView, R.id.service).setText(getContext().getString(R.string.merchant_rate_service, merchant.getRateService()));
        UiUtil.findTextViewById(itemView, R.id.effect).setText(getContext().getString(R.string.merchant_rate_effect, merchant.getRateEffect()));
        UiUtil.findTextViewById(itemView, R.id.environment).setText(getContext().getString(R.string.merchant_rate_environment, merchant.getRateEnvironment()));
        return itemView;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public static class MerchantItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	Merchant merchant = (Merchant) parent.getItemAtPosition(position);
            GoodsDetailActivity.startActivity((Activity) view.getContext(), HttpUrlManager.MERCHANT_DETAIL_URL + "?hosp_id=" + id,merchant.getName(),merchant.getImageLarge());
        }
        
    }
}