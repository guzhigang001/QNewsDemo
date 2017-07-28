package com.example.administrator.ggcode.net;

import android.os.Build;
import android.support.compat.BuildConfig;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/7/25.
 */

public class QClitent {


    private static QClitent qClitent;
    private OkHttpClient.Builder mBuilder;

    public QClitent() {
        initSetting();
    }
    public static QClitent getInstance(){
        if (qClitent==null){
            synchronized (QClitent.class){
                qClitent=new QClitent();
            }
        }
        return qClitent;
    }
    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service,String baseUrl){
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");
        return new Retrofit.Builder().baseUrl(baseUrl).
                client(mBuilder.build()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build().
                create(service);

    }

    private <T> T checkNotNull(T service, String s) {
        if (service==null){
            throw new NullPointerException(s);
        }
        return service;
    }

    private void initSetting() {
        //初始化okHttp
        mBuilder=new OkHttpClient.Builder().
                connectTimeout(9, TimeUnit.SECONDS).//设置连接超时 9s
                readTimeout(10,TimeUnit.SECONDS);//设置读取超时 10s
        if (BuildConfig.DEBUG){//判断是否为debug模式 请求到的json字符串和查看log
            // 如果为 debug 模式，则添加日志拦截器
            HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(interceptor);

        }

    }
}
