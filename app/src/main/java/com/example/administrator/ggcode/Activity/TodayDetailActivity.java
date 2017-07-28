package com.example.administrator.ggcode.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.ggcode.Bean.TodayOfHistoryDetailBean;
import com.example.administrator.ggcode.Commons.Constants;
import com.example.administrator.ggcode.R;
import com.example.administrator.ggcode.net.QClitent;
import com.example.administrator.ggcode.net.QNewsService;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TodayDetailActivity extends AppCompatActivity {
    @BindView(R.id.image_toolbar)
    Toolbar tbToday;
    @BindView (R.id.CollapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarToday;
    @BindView (R.id.appbar_layout)
    AppBarLayout appBar;
    @BindView (R.id.content_tv)
    TextView tvTodayContent;
    @BindView(R.id.title_image)
    ImageView title_image;
    @BindView(R.id.fab_botton)
    FloatingActionButton fab_botton;
    private List<TodayOfHistoryDetailBean.ResultBean.PicUrlBean> picUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils spUtils=new SPUtils("theme_id");
        int theme_id=spUtils.getInt("theme_id");
        setTheme(theme_id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        /*************************** Activity 转场动画 ***************************/
        //具体操作见简书       http://www.jianshu.com/p/415a32976cc6
        // getWindow().setEnterTransition()是进入动画，与之对应的getWindow().setExitTransition()就是退出转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            switch ((int) (Math.random()*3)){
                case 0:
                    getWindow().setEnterTransition(new Explode());//有上自下
                    break;
                case 1:
                    getWindow().setEnterTransition(new Slide());//由下自上
                    break;
                case 2:
                    getWindow().setEnterTransition(new Fade());//渐变
                    break;

            }
        }
        setContentView(R.layout.activity_today_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.LOLLIPOP){
            fab_botton.setVisibility(View.GONE);
        }
        fab_botton.setColorFilter(Color.WHITE);
        fab_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypedValue typedValue = new  TypedValue();
                getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                fab_botton.setColorFilter(typedValue.data);

            }
        });
        String e_id=getIntent().getStringExtra("e_id");
        setSupportActionBar(tbToday);
        ActionBar actionBar=getSupportActionBar();

        if (tbToday!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        QClitent.getInstance().create(QNewsService.class, Constants.BASE_JUHE_URL)
                .getTodayOfHistoryDetailData(e_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryDetailBean>() {
                    @Override
                    public void accept(TodayOfHistoryDetailBean todayOfHistoryDetailBean) throws Exception {
                        // 成功获取到数据
                        if (todayOfHistoryDetailBean.getError_code() != 0) {
                            tbToday.setTitle("无结果");
                            tvTodayContent.setText("无结果");
                            return;
                        }
                        TodayOfHistoryDetailBean.ResultBean resultBean=todayOfHistoryDetailBean.getResult().get(0);
                        String content = resultBean.getContent();
                        String title   = resultBean.getTitle();
                        picUrl = resultBean.getPicUrl();
                        if (picUrl.size()!=0){
                            String url=picUrl.get(0).getUrl();
                            Glide.with(getApplicationContext()).load(url).into(title_image);
                        }else {
                            ToastUtils.showShortToast("暂无图片");
                        }

                        collapsingToolbarToday.setTitle(title);
                        collapsingToolbarToday.setCollapsedTitleGravity(Gravity.LEFT);
//                        collapsingToolbarToday.setCollapsedTitleTextColor();缩放后的颜色
                        collapsingToolbarToday.setExpandedTitleColor(Color.BLACK);//缩放前的颜色
                        tvTodayContent.setText(content);

                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
