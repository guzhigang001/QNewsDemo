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
 * 时间   ： 15:11
 * 功能   ：发送适配器
 */

public class MsgSendItemDelagate implements ItemViewDelegate<RobotMSGBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_robot_send;
    }

    @Override
    public boolean isForViewType(RobotMSGBean item, int position) {
        return item.getType()==RobotMSGBean.MSG_SEND;
    }

    @Override
    public void convert(ViewHolder holder, RobotMSGBean robotMSGBean, int position) {
        holder.setText(R.id.tv_msg_right, robotMSGBean.getMsg());
    }
}
