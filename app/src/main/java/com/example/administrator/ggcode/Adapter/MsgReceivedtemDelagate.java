package com.example.administrator.ggcode.Adapter;

import com.example.administrator.ggcode.Bean.RobotMSGBean;
import com.example.administrator.ggcode.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Adapter
 * 作者名 ： g小志
 * 日期   ： 2017/7/27
 * 时间   ： 15:10
 * 功能   ： 接受适配器
 */

public class MsgReceivedtemDelagate implements ItemViewDelegate<RobotMSGBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_reveiver;
    }

    @Override
    public boolean isForViewType(RobotMSGBean item, int position) {
        return item.getType()==RobotMSGBean.MSG_RECEIVED;
    }

    @Override
    public void convert(ViewHolder holder, RobotMSGBean robotMSGBean, int position) {
        holder.setText(R.id.tv_msg_left, robotMSGBean.getMsg());
    }
}
