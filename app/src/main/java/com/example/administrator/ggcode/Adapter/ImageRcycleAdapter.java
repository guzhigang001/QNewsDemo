package com.example.administrator.ggcode.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.ggcode.Bean.ImageBean;
import com.example.administrator.ggcode.R;


/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Adapter
 * 作者名 ： g小志
 * 日期   ： 2017/7/28
 * 时间   ： 14:36
 * 功能   ：
 */

public class ImageRcycleAdapter extends BaseQuickAdapter<ImageBean.ResultsBean,BaseViewHolder> {

    public ImageRcycleAdapter() {
        super(R.layout.item_recycle_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean.ResultsBean item) {
        Glide.with(mContext).load(item.getUrl()).centerCrop().
                error(R.drawable.ic_version_update).
                placeholder(R.mipmap.ic_error)
                .crossFade().into((ImageView) helper.getView(R.id.recycle_image));
    }


}
