package com.example.administrator.weatherGuZhiGang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.administrator.weatherGuZhiGang.gson.Forecast;
import com.example.administrator.weatherGuZhiGang.gson.Weather;
import com.example.administrator.weatherGuZhiGang.service.AutoUpdateService;
import com.example.administrator.weatherGuZhiGang.util.HttpUtil;
import com.example.administrator.weatherGuZhiGang.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private ScrollView weatherLayout;

    private Button navButton,navButton02;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private String API_KEY="&"+"key=4efe9c7d94634b469df87ab280f4c23b";

    private ImageView bingImage;

    private String mWeatherId;

    public SwipeRefreshLayout swipeRefresh;

    public DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//系统栏设置透明
        }
        //初始化控件
        weatherLayout= (ScrollView) findViewById(R.id.weather_layout);
        titleCity= (TextView) findViewById(R.id.title_city);
        titleUpdateTime= (TextView) findViewById(R.id.title_update_time);
        degreeText= (TextView) findViewById(R.id.degree_text);
        weatherInfoText= (TextView) findViewById(R.id.weather_info_text);
        forecastLayout= (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText= (TextView) findViewById(R.id.aqi_text);
        pm25Text= (TextView) findViewById(R.id.pm25_text);
        comfortText= (TextView) findViewById(R.id.comfort_text);
        carWashText= (TextView) findViewById(R.id.car_wash_text);
        sportText= (TextView) findViewById(R.id.sport_text);
        bingImage= (ImageView) findViewById(R.id.bing_pic_img);
        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton= (Button) findViewById(R.id.nav_button02);
        navButton02= (Button) findViewById(R.id.nav_button);
        navButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        swipeRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.GRAY);
        swipeRefresh.setProgressViewEndTarget(false,150);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
        if (weatherString!=null){
            //有缓存时直接解析天气数据
            Weather weather= Utility.HandleWeatherResponse(weatherString);
            mWeatherId=weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            //无缓存是服务器查询天气
            mWeatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        String imgUrl=preferences.getString("bing_pic",null);
        if (imgUrl!=null){
            Glide.with(this).load(imgUrl).into(bingImage);
        }else {
            loadBingPic();
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingImage);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息
     * @param weatherId 天气ID
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl="https://free-api.heweather.com/v5/weather?city="+weatherId+API_KEY;
        Log.i(TAG, "weatherUrl: "+weatherUrl);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final Weather weather=Utility.HandleWeatherResponse(responseText);
                Log.i(TAG, "weather: "+weather);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather!=null&&"ok".equals(weather.status)){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();

                            showWeatherInfo(weather);

                        }else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }

                });
            }


        });

    }


    /**
     * 处理并显示Weather实体类中的数据
     */

    public void showWeatherInfo(Weather weather) {
        String cityName=weather.basic.cityName;
        String undateTime=weather.basic.update.updateTime.split(" ")[1];
        String degree=weather.now.temperature+"℃";
        String weatherInfo=weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(undateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for(Forecast f:weather.forecastList){
            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(f.date);
            infoText.setText(f.more.info);
            maxText.setText(f.temperature.max);
            minText.setText(f.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi!=null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent=new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
}
