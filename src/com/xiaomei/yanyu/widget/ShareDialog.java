package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class ShareDialog extends PopupDialog {

    private View mContent;

    private OnClickListener mCancelClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public ShareDialog(Context context) {
        mContent = LayoutInflater.from(context).inflate(R.layout.share_dialog_content, null);
        initConentView();
        initPopupWindow(mContent);
    }

    private void initConentView() {
        GridView grid = (GridView)mContent.findViewById(R.id.grid);
        grid.setAdapter(new ShareDialogAdapter());

        mContent.findViewById(R.id.cancel).setOnClickListener(mCancelClickListener);
        mContent.setOnClickListener(mCancelClickListener);
    }

    public static class ShareDialogAdapter extends BaseAdapter {

        private static ShareDialogEntry[] entries = new ShareDialogEntry[] {
                ShareDialogEntry.WECHAT_SHARE, ShareDialogEntry.WECHAT_MOMENT,
                ShareDialogEntry.QQ_SHARE, ShareDialogEntry.QZONE_SHARE,
                ShareDialogEntry.SINA_WEIBO,
        };

        @Override
        public int getCount() {
            return entries.length;
        }

        @Override
        public ShareDialogEntry getItem(int position) {
            return entries[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView
                    : LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.share_dialog_grid_item, parent, false);
            UiUtil.findTextViewById(itemView, android.R.id.text1)
                    .setText(getItem(position).titleRes);
            UiUtil.findImageViewById(itemView, R.id.icon)
                    .setImageResource(getItem(position).iconRes);
            return itemView;
        }

    }

    public static enum ShareDialogEntry {
        WECHAT_SHARE(R.string.title_wechat_share, R.drawable.ic_wechat_share),
        WECHAT_MOMENT(R.string.title_wechat_moment, R.drawable.ic_wechat_moment),
        QQ_SHARE(R.string.title_qq_share, R.drawable.ic_qq_share),
        QZONE_SHARE(R.string.title_qzone_share, R.drawable.ic_qzone_share),
        SINA_WEIBO(R.string.title_sina_weibo, R.drawable.ic_sina_weibo);

        public final int titleRes;

        public final int iconRes;

        ShareDialogEntry(int titleRes, int iconRes) {
            this.titleRes = titleRes;
            this.iconRes = iconRes;
        }
    }
}

