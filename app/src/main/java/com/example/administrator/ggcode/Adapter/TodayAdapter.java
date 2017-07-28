package com.example.administrator.ggcode.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.ggcode.Bean.TodayOfHistoryBean;
import com.example.administrator.ggcode.R;

import java.util.List;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Adapter
 * 作者名 ： g小志
 * 日期   ： 2017/7/26
 * 时间   ： 15:55
 * 功能   ：
 */

public class TodayAdapter extends BaseQuickAdapter<TodayOfHistoryBean.ResultBean,BaseViewHolder>{
    public TodayAdapter() {
        super(R.layout.item_today);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayOfHistoryBean.ResultBean item) {
        helper.setText(R.id.tv_today_title, item.getTitle());
        helper.setText(R.id.tv_today_date, item.getDate());
        helper.addOnClickListener(R.id.ll_today_detail);
    }
}
