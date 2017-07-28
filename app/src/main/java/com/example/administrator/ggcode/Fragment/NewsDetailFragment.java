package com.example.administrator.ggcode.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.administrator.ggcode.Adapter.NewsDataAdapter;
import com.example.administrator.ggcode.Bean.NewsDataBean;
import com.example.administrator.ggcode.Commons.Constants;
import com.example.administrator.ggcode.Commons.LogUtils;
import com.example.administrator.ggcode.Commons.ShareUtils;
import com.example.administrator.ggcode.R;
import com.example.administrator.ggcode.net.QClitent;
import com.example.administrator.ggcode.net.QNewsService;
import com.xiawei.webviewlib.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/25.
 */

public class NewsDetailFragment extends BaseFragment {

    @BindView(R.id.rv_new_detail)
    RecyclerView rvNewDetail;
    @BindView (R.id.srl)
    SwipeRefreshLayout srl;
    private NewsDataAdapter mAdapter;

    /**
     * 新闻数据类型
     */
    private String type;

    public NewsDetailFragment() {
    }

    public NewsDetailFragment(String type) {
        this.type = type;
    }
    @Override
    public void fetchData() {
        updateData();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_data_news,null);
        ButterKnife.bind(this,view);

        mAdapter=new NewsDataAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);//这是动画类型    默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        /*************************** 设置下拉刷新 ***************************/
        srl.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
        /*************************** recyclerView 初始化数据***************************/
        rvNewDetail.setAdapter(mAdapter);
        rvNewDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNewDetail.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShortToast("点击了ItemClick");

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                String title = mAdapter.getItem(position).getTitle();
                String url   = mAdapter.getItem(position).getUrl();
                ShareUtils.share(getActivity(), title + "\n" + url);
//                ToastUtils.showShortToast("ItemLongClick");
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.startUrl(getActivity(),
                                        ((NewsDataBean.ResultBean.DataBean) adapter.getItem(
                                               position)).getUrl());
//                ToastUtils.showShortToast("ItemChildClick");

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShortToast("点击了ChildLong");
            }
        });

        return view;
    }





    private void updateData() {
        srl.setRefreshing(true);
        LogUtils.i("type------------------------------------"+type);
        QClitent.getInstance().create(QNewsService.class, Constants.BASE_JUHE_URL).
                getNewsData(type).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<NewsDataBean>() {
            @Override
            public void accept(NewsDataBean newsDataBean) throws Exception {
                mAdapter.setNewData(newsDataBean.getResult().getData());
                srl.setRefreshing(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                srl.setRefreshing(false);
            }
        });
    }


}

