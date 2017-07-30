package com.example.administrator.ggcode.Activity;

import android.app.Application;

import com.blankj.utilcode.utils.Utils;



/**
 * Created by Administrator on 2017/7/23.
 */

public class MyApplicaction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
    }
}
