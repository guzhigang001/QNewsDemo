package com.example.administrator.ggcode.Activity;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.utils.Utils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


/**
 * Created by Administrator on 2017/7/23.
 */

public class MyApplicaction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
           mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("myToken", "onSuccess: --------------"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
