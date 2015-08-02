
package com.xiaomei.yanyu.leveltwo;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Goods.Mark;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsAdapter extends ArrayAdapter<Goods> {

    public GoodsAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView
                : LayoutInflater.from(getContext()).inflate(R.layout.item_goods_layout, parent,
                        false);

        Goods goods = getItem(position);

        ImageView icon = UiUtil.findImageViewById(itemView, R.id.icon);
        icon.setImageResource(R.drawable.goods_list_default);
        ImageLoader.getInstance().displayImage(goods.getFileUrl(), icon);
        UiUtil.findTextViewById(itemView, R.id.title).setText(goods.getTitle());
        UiUtil.findTextViewById(itemView, R.id.size).setText("销量" + goods.getSales());
        UiUtil.findTextViewById(itemView, R.id.hospital_name).setText(goods.getHospName());
        UiUtil.findTextViewById(itemView, R.id.price)
                .setText(getContext().getString(R.string.ren_ming_bi) + " " + goods.getPriceXm());
        UiUtil.findTextViewById(itemView, R.id.location).setText(goods.getCityName());
        UiUtil.findTextViewById(itemView, R.id.origin_price)
                .setText("原价" + goods.getPriceMarket() + "元");

        TextView mark1 = UiUtil.findTextViewById(itemView, R.id.tag_1);
        TextView mark2 = UiUtil.findTextViewById(itemView, R.id.tag_2);
        TextView mark3 = UiUtil.findTextViewById(itemView, R.id.tag_3);
        TextView[] markViews = new TextView[] {
                mark1, mark2, mark3
        };
        mark1.setVisibility(View.GONE);
        mark2.setVisibility(View.GONE);
        mark3.setVisibility(View.GONE);

        List<Goods.Mark> marks = goods.getMarks();
        int size = marks != null ? marks.size() : 0;
        for (int i = 0; i < size && i < 3; i++) {
            Mark mark = marks.get(i);
            TextView markView = markViews[i];
            markView.setVisibility(View.VISIBLE);
            markView.setBackground(getBackground(mark.getColor()));
            markView.setText(mark.getLabel());
        }

        return itemView;
    }

    private Drawable getBackground(String color) {
        GradientDrawable shapeDrawable;
        shapeDrawable = new GradientDrawable();
        shapeDrawable.setCornerRadius(8);
        shapeDrawable.setColor(Color.parseColor(color));
        return shapeDrawable;
    }
    
    public static class GoodsItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Goods goods = (Goods) parent.getItemAtPosition(position);
            String goodsId = goods.getId();
            GoodsDetailActivity.startActivity((Activity) view.getContext(), HttpUrlManager.GOODS_DETAIL_URL+"?goods_id=" + goodsId, goodsId);
        }
        
    }
}
