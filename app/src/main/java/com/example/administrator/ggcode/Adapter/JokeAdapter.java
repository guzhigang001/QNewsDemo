package com.example.administrator.ggcode.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.ggcode.Bean.JokeBean;
import com.example.administrator.ggcode.R;

import java.util.List;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Adapter
 * 作者名 ： g小志
 * 日期   ： 2017/7/26
 * 时间   ： 12:57
 */

public class JokeAdapter extends BaseQuickAdapter<JokeBean.ResultBean.DataBean,BaseViewHolder>{


    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_joke_content,item.getContent());
        helper.setText(R.id.tv_joke_date,item.getUpdatetime());
    }
}
