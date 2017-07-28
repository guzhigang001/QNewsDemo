package com.example.administrator.ggcode.Adapter;

import android.content.Context;

import com.example.administrator.ggcode.Bean.RobotMSGBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import java.util.List;


/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Adapter
 * 作者名 ： g小志
 * 日期   ： 2017/7/27
 * 时间   ： 15:08
 * 功能   ：
 */

public class RobotAdapter extends MultiItemTypeAdapter<RobotMSGBean> {
    private Context            context;
    private List<RobotMSGBean> datas;
    public RobotAdapter(Context context, List<RobotMSGBean> datas) {
        super(context, datas);
        this.context = context;
        this.datas = datas;
    }

    public void addDataToAdapter(RobotMSGBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
    }
}
