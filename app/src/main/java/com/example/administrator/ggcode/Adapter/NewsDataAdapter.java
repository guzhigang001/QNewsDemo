package com.example.administrator.ggcode.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.ggcode.Bean.NewsDataBean;
import com.example.administrator.ggcode.R;



/**
 * Created by gg小志 on 2017/7/25.BaseViewHolder开源库解析网址http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0417/4154.html
 */

public class NewsDataAdapter extends BaseQuickAdapter<NewsDataBean.ResultBean.DataBean, BaseViewHolder> {
    public NewsDataAdapter() {
        super(R.layout.item_news_detail);
    }

    @Override
    protected void convert(BaseViewHolder holper, NewsDataBean.ResultBean.DataBean item) {

        holper.setText(R.id.tv_news_detail_title,item.getTitle());
        holper.setText(R.id.tv_news_detail_author_name, item.getAuthor_name());
        holper.setText(R.id.tv_news_detail_date, item.getDate());
        holper.addOnClickListener(R.id.ll_news_detail);
        Glide.with(mContext).
                load(item.getThumbnail_pic_s()).
                placeholder(R.mipmap.ic_error).
                error(R.mipmap.ic_error).
                crossFade().
                centerCrop().
                into((ImageView) holper.getView(R.id.iv_news_detail_pic));
    }

}
