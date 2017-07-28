package com.example.administrator.ggcode.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.administrator.ggcode.Adapter.ImageRcycleAdapter;
import com.example.administrator.ggcode.Bean.ImageBean;
import com.example.administrator.ggcode.Commons.Constants;
import com.example.administrator.ggcode.R;
import com.example.administrator.ggcode.net.QClitent;
import com.example.administrator.ggcode.net.QNewsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AboutFragment extends Fragment {

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.recyclerview)
    RecyclerView recycle;

    ImageRcycleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this,view);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        adapter=new ImageRcycleAdapter();
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);//设置RecyclerView item动画
        recycle.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("你点击了"+position);
            }
        });
        initData();
        return view;
    }

    private void initData() {
        QClitent.getInstance().create(QNewsService.class, Constants.BASE_IMAGE_URL)
                .getIamgeData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ImageBean.DataBean>() {
            @Override
            public void accept(ImageBean.DataBean imageBean) throws Exception {
                adapter.addData(imageBean);
                refreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.get(getContext()).clearMemory();//清除内存缓存
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getContext()).clearDiskCache();//清除磁盘缓存(此方法要求在子线程中进行)
            }
        }).start();
    }
}
