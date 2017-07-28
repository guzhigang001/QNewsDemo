package com.example.administrator.ggcode.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SPUtils;
import com.example.administrator.ggcode.Activity.MainActivity;
import com.example.administrator.ggcode.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends BaseFragment {

    @BindView(R.id.main_viewpager)
    ViewPager main_viewpager;
//    @BindView(R.id.news_toolbar)
//    Toolbar toolbar;
    int theme_id;
    @BindView(R.id.ic_menu)
    ImageView ic_menu;
    private String[]             types;         //顶部 tab 英文内容数组
    private String[]             typesCN;       //顶部 tab 中文内容数组
    private NewsViewPagerAadpter newsViewPagerAadpter;    //ViewPager 适配器
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);//每个界面都需要绑定View否则@BindView标签将无效
        types=getResources().getStringArray(R.array.news_type_cn);
        typesCN=getResources().getStringArray(R.array.news_type_en);
        //初始化ViewPager，设置适配器
        newsViewPagerAadpter=new NewsViewPagerAadpter(getActivity().getSupportFragmentManager());
        main_viewpager.setAdapter(newsViewPagerAadpter);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        }
        SPUtils spUtils=new SPUtils("theme_id");
        theme_id=spUtils.getInt("theme_id");
        MainActivity activity= (MainActivity) getActivity();
        final DrawerLayout drawerLayout= (DrawerLayout) activity.findViewById(R.id.dl_activity_main);
        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        /*************************** 顶部指示器数据加载 ***************************/
        MagicIndicator magicIndicator  = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        final MainActivity activity1= (MainActivity) getActivity();
        final TypedValue typedValue = new  TypedValue();
        activity1.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return types==null ?0:types.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView
                        = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);
                colorTransitionPagerTitleView.setSelectedColor(Color.RED);

                colorTransitionPagerTitleView.setText(typesCN[i]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        main_viewpager.setCurrentItem(i);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator=new LinePagerIndicator(context);
                indicator.setColors(typedValue.data);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;


            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator,main_viewpager);
        return view;
    }
//    /**
//     * 获取主题颜色
//     * @return
//     */
//    public int getColorPrimary(){
//        TypedValue typedValue = new  TypedValue();
//        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//        return typedValue.data;
//    }
    @Override
    public void fetchData() {

    }



    class NewsViewPagerAadpter extends FragmentStatePagerAdapter{

        public NewsViewPagerAadpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new NewsDetailFragment(types[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }
    }
}
