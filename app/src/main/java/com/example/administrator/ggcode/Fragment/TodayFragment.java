package com.example.administrator.ggcode.Fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.administrator.ggcode.Activity.TodayDetailActivity;
import com.example.administrator.ggcode.Adapter.TodayAdapter;
import com.example.administrator.ggcode.Bean.TodayOfHistoryBean;
import com.example.administrator.ggcode.Commons.Constants;
import com.example.administrator.ggcode.R;
import com.example.administrator.ggcode.net.QClitent;
import com.example.administrator.ggcode.net.QNewsService;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class TodayFragment extends Fragment {
    @BindView(R.id.tb_today)
    Toolbar tbToday;
    @BindView (R.id.rv_today)
    RecyclerView rvToday;
    @BindView (R.id.fab)
    FloatingActionButton fab;

    //历史上今天的适配器
    private TodayAdapter adapter;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this,view);
        adapter=new TodayAdapter();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.LOLLIPOP){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fab!=null){
                    rvToday.scrollToPosition(0);
                }
            }
        });
        StaggeredGridLayoutManager ll=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvToday.setLayoutManager(ll);
        rvToday.setAdapter(adapter);
        rvToday.addOnItemTouchListener(new OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(),TodayDetailActivity.class);
                intent.putExtra("e_id",((TodayOfHistoryBean.ResultBean)adapter.getItem(position)).getE_id());
                getActivity().overridePendingTransition(R.anim.design_snackbar_in,R.anim.design_snackbar_out);
                getActivity().startActivity(intent);
            }
        });

        //获得当前的日期
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String params = month + "/" + day;

        //初次加载数据
        QClitent.getInstance().create(QNewsService.class, Constants.BASE_JUHE_URL)
                .getTodayOfHistoryData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryBean>() {
                    @Override
                    public void accept(TodayOfHistoryBean todayOfHistoryBean) throws Exception {
                        List<TodayOfHistoryBean.ResultBean> result = todayOfHistoryBean.getResult();
                        adapter.setNewData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

}
