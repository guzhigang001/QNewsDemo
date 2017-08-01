package com.example.administrator.weatherGuZhiGang.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.administrator.weatherGuZhiGang.gson.Weather;
import com.example.administrator.weatherGuZhiGang.util.HttpUtil;
import com.example.administrator.weatherGuZhiGang.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    private String API_KEY="&"+"key=4efe9c7d94634b469df87ab280f4c23b";

    private static final String TAG = "AutoUpdateService";
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ----------------------------------------");
        updateWeather();
        updateVingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8*60*60 * 1000; // 这是8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 每日毕更新一张图片
     */
    private void updateVingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 更新天气
     */
    private void updateWeather() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
        if (weatherString!=null){
            //有缓存时直接解析天气数据
            Weather weather= Utility.HandleWeatherResponse(weatherString);
            String mWeatherId=weather.basic.weatherId;

            String weatherUrl="https://free-api.heweather.com/v5/weather?city="+mWeatherId+API_KEY;

            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responseText=response.body().string();
                    Weather weather=Utility.HandleWeatherResponse(responseText);
                    if (weather !=null&&"ok".equals(weather.status)){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",responseText);
                        editor.apply();
                    }

                }
            });
        }
    }
}
