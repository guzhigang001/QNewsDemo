package com.example.administrator.ggcode.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.ggcode.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {


    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.image_toobar)
    Toolbar toolbar;
    private String url;
    String fileName="";
    int i=0;
    Bitmap mBitmap;
    String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/QNews/Image/";
    private  Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case -1:
                    ToastUtils.showShortToast("保存失败");
                    break;
                case 1:
                    ToastUtils.showShortToast("无SD卡");
                    break;
                case 2:
                    ToastUtils.showShortToast("已保存");
                    break;
                case 3:
                    ToastUtils.showShortToast("保存成功");
                    break;

            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils util     = new SPUtils("theme_id");
        int     theme_id = util.getInt("theme_id", R.style.AppTheme);
        setTheme(theme_id);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
//        Calendar now = new GregorianCalendar();
//        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
//        fileName = simpleDate.format(now.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Intent intent=getIntent();
        String who=intent.getStringExtra("who");
        url=intent.getStringExtra("imageUrl");
        fileName=url.substring(50,60);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(who);

        }
      Glide.with(getApplicationContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
              mBitmap=resource;
              imageView.setImageBitmap(resource);
          }
      });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                i++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int num=svaeImage();
                        handler.sendEmptyMessage(num);
                    }
                }).start();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int svaeImage() {
        Log.i(TAG, "svaeImage: ================="+fileName);
        //获取内部存储状态
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {

            return 1;
        }
        try {
            File file = new File(dir);
            if(!file.exists())
                file.mkdirs();
            if (FileUtils.isFileExists(file.getPath()+"/"+fileName + ".jpg"))
                return 2;
            FileOutputStream out = new FileOutputStream(file.getPath()+"/"+fileName + ".jpg");
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            // MediaScannerReceiver是一个广播接收者，当它接收到特定的广播请求后，就会去扫描指定的文件，并根据文件信息将其添加到数据库中。
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(file.getPath()+"/"+fileName + ".jpg"));
            intent.setData(uri);
            sendBroadcast(intent);
            return 3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_image,menu);
        return true;
    }

    private static final String TAG = "ImageActivity";
}
