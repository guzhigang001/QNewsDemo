package com.example.administrator.ggcode.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.ggcode.Commons.ActivityUtils;
import com.example.administrator.ggcode.Commons.LogUtils;
import com.example.administrator.ggcode.Fragment.AboutFragment;
import com.example.administrator.ggcode.Fragment.GIFFragment;
import com.example.administrator.ggcode.Fragment.JokeFragment;
import com.example.administrator.ggcode.Fragment.NewsFragment;
import com.example.administrator.ggcode.Fragment.RobotFragment;
import com.example.administrator.ggcode.Fragment.TodayFragment;
import com.example.administrator.ggcode.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity------>";
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView (R.id.nv_left)
    NavigationView nvLeft;
    @BindView (R.id.dl_activity_main)
    DrawerLayout dlActivityMain;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private NewsFragment newsFragment;      //新闻数据
    private JokeFragment jokeFragment;      //段子
    private RobotFragment robotFragment;    //机器人
    private AboutFragment aboutFragment;    //关于
    private TodayFragment todayFragment;    //历史上的今天
    private GIFFragment gifFragment;        //动态图

    private Fragment currentFragment;

    private ActivityUtils utils;
    private BottomBar bottomBar;
    private AlertDialog.Builder builder;

    private ImageView mIconImage;
    private BottomSheetDialog mDialog;
    private MyHandler         mHandler;

    private String cacheSize="" ;//内部缓存
    String Size="";//外部缓存
    private SPUtils mSPUtils;
    public static final int    SUCESS    = 0;
    public static final int    FAILED    = 1;
    Uri imageUri;

    public static final String FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() +
                    "/xiaweizi" + "/image_cache" +
                    "/camera.jpg";
    public static final String TEMP_PATH = Environment.getExternalStorageDirectory().getPath() +
            "/xiaweizi" + "/image_cache" +
            "/camera.jpg";

    class MyHandler extends Handler{
        WeakReference<MainActivity> mainActivity;

        public MyHandler(MainActivity activity) {
            mainActivity=new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCESS:
                    utils.showToast("清理成功");
                    break;
                case FAILED:
                    break;
                default:
                    break;
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils util     = new SPUtils("theme_id");
        int     theme_id = util.getInt("theme_id", R.style.AppTheme);
        setTheme(theme_id);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        //设置软键盘的模式为适应屏幕模式
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        retrieveFragment();
        ButterKnife.bind(this);//每个界面都需要绑定View否则@BindView标签将无效

        utils = new ActivityUtils(this);
        mSPUtils = new SPUtils("head");
        mHandler = new MyHandler(this);

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {

                cacheSize = FileUtils.getDirSize(getCacheDir());
                if (Environment.getExternalStorageState().equals(//getCacheDir()内部缓存目录(api>=24) getExternalCacheDir SDcard外部关联目录
                        Environment.MEDIA_MOUNTED)) {
                    Size=FileUtils.getDirSize(getExternalCacheDir());

                    Log.i(TAG, "run: --------------------------"+cacheSize+Size);

                }
            }
        }).start();

        /*************************** 左侧 侧滑菜单 设置头像图片 ***************************/
        mIconImage= (ImageView) nvLeft.getHeaderView(0).findViewById(R.id.icon_image);
        final ImageView ivBitm= (ImageView) nvLeft.getHeaderView(0).findViewById(R.id.iv_head_bg);
        if (!mSPUtils.getBoolean("has_head",false)){
            Glide.with(this).load("http://img.17gexing.com/uploadfile/2016/07/2/20160725115642623.gif").
                    asGif().centerCrop().into(mIconImage);
        }else {
            mIconImage.setImageBitmap(BitmapFactory.decodeFile(TEMP_PATH));
        }

        OkHttpUtils.get().url("http://guolin.tech/api/bing_pic").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Glide.with(MainActivity.this)
                        .load(response)
                        .crossFade()
                        .centerCrop()
                        .into(ivBitm);
            }
        });
        mIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog=new BottomSheetDialog(MainActivity.this);
                View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_dialog_pic_selector,null);
                TextView xiangji = (TextView) view.findViewById(R.id.tv_xiangji);
                TextView xiangce = (TextView) view.findViewById(R.id.tv_xiangce);
                xiangce.setOnClickListener(listener);
                xiangji.setOnClickListener(listener);
                mDialog.setContentView(view);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.show();
            }
        });

        /*************************** 底部bar 设置点击事件 ***************************/
        bottomBar= (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_news:
                        if (newsFragment==null)
                            newsFragment=new NewsFragment();
                        switchFragment(newsFragment);
                        nvLeft.setCheckedItem(R.id.nav_news);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_joke:
                        if (jokeFragment==null)
                            jokeFragment=new JokeFragment();
                        switchFragment(jokeFragment);
                        nvLeft.setCheckedItem(R.id.nav_duanzi);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_today:
                        if (todayFragment==null)
                            todayFragment=new TodayFragment();
                        switchFragment(todayFragment);
                        nvLeft.setCheckedItem(R.id.nav_today_of_history);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_robot:
                        if (robotFragment==null)
                            robotFragment=new RobotFragment();
                        switchFragment(robotFragment);
                        nvLeft.setCheckedItem(R.id.nav_robot);
                        closeDrawerLayout();
                        break;
                    case R.id.tab_about:
                        if (aboutFragment==null)
                            aboutFragment=new AboutFragment();
                        switchFragment(aboutFragment);
                        nvLeft.setCheckedItem(R.id.nav_other);
                        closeDrawerLayout();
                        break;
                }
            }
        });


        /*************************** 左侧 侧滑菜单 设置选择事件 ***************************/
        nvLeft.setCheckedItem(R.id.nav_news);
        nvLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_news:
                        bottomBar.selectTabAtPosition(0);//true表示是否显示动画
                        break;
                    case R.id.nav_duanzi:
                        bottomBar.selectTabAtPosition(1, true);
                        break;
                    case R.id.nav_today_of_history:
                        bottomBar.selectTabAtPosition(2, true);
                        break;
                    case R.id.nav_robot:
                        bottomBar.selectTabAtPosition(3, true);
                        break;
                    case R.id.nav_other:
                        bottomBar.selectTabAtPosition(4, true);
                        break;
                    case R.id.nav_clear_cache:
                        clearCache();//清除缓存
                        break;
                    case R.id.nav_version_update:
                        VersionUtils.updateVersion(MainActivity.this);
                        break;
                    case R.id.nav_change_theme:
                        alertChangeTheme();
                        break;
                    case R.id.nav_day_night:
                        SPUtils spUtils=new SPUtils("theme_id");

                        if (spUtils.getInt("theme_id",R.style.AppTheme)==R.style.AppTheme){
                            changeTheme(9);
                        }else {
                            Intent intent = getIntent();
                            spUtils.putInt("theme_id",R.style.AppTheme);
                            overridePendingTransition(R.anim.design_snackbar_in,R.anim.design_snackbar_out);
                            finish();
                            overridePendingTransition(R.anim.design_snackbar_in,R.anim.design_snackbar_out);
                            startActivity(intent);
                        }

                        break;
                    default:
                        break;
                }

                return false;
            }
        });
        //底部bar设置再次点击事件

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news:
                        break;
                    case R.id.tab_joke:
                        if (gifFragment == null) gifFragment = new GIFFragment();
                        switchFragment(gifFragment);
                        break;
                    case R.id.tab_robot:
                        break;
                }
            }
        });

    }

    private void changeTheme(int index) {
        int[] themes
                = new int[]{R.style.AppTheme, R.style.AppTheme_Blue, R.style.AppTheme_Green,
                R.style.AppTheme_Orange, R.style.AppTheme_Pink, R.style.AppTheme_Sky,
                R.style.AppTheme_Purple, R.style.AppTheme_PP, R.style.AppTheme_Yellow,
                R.style.AppTheme_Night};
        SPUtils spUtils=new SPUtils("theme_id");
        spUtils.putInt("theme_id",themes[index]);
        Intent intent = getIntent();
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        finish();
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        startActivity(intent);

    }

    /**
     * 动态换肤
     */
    private void alertChangeTheme() {
        View view=View.inflate(MainActivity.this,R.layout.item_change_theme,null);
        builder=new AlertDialog.Builder(this);
        builder.setView(view).show();
        view.findViewById(R.id.tv_red).setOnClickListener(listener);
        view.findViewById(R.id.tv_green).setOnClickListener(listener);
        view.findViewById(R.id.tv_blue).setOnClickListener(listener);
        view.findViewById(R.id.tv_orange).setOnClickListener(listener);
        view.findViewById(R.id.tv_pink).setOnClickListener(listener);
        view.findViewById(R.id.tv_sky).setOnClickListener(listener);
        view.findViewById(R.id.tv_purple).setOnClickListener(listener);
        view.findViewById(R.id.tv_pp).setOnClickListener(listener);
        view.findViewById(R.id.tv_yellow).setOnClickListener(listener);
    }

    long lastTime=0;
    /**
     * 2秒内连续点击 back 键，退出应用
     */
    @Override
    public void onBackPressed() {
       if (dlActivityMain.isDrawerOpen(Gravity.LEFT)){
           dlActivityMain.closeDrawers();
           return;
       }

        if (currentFragment!=newsFragment){
            bottomBar.selectTabAtPosition(0);
            return;
        }
        long currtTime=System.currentTimeMillis();
        if (currtTime-lastTime>2000){
            ToastUtils.showShortToast("再按一次退出");
            lastTime=currtTime;

        }else {
            moveTaskToBack(true);
        }
    }

    private void clearCache() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        if ("0.000B".equals(Size)||Size.isEmpty()||Size.length()==0){
            dialog.setTitle("确定要清除缓存？").setMessage("缓存大小:"+cacheSize).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            FileUtils.deleteDir(getCacheDir());
                            if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
                                FileUtils.deleteDir(getExternalCacheDir());
                            }

                            mHandler.sendEmptyMessage(SUCESS);
                        }
                    }).start();
                }
            }).setNegativeButton("取消",null).show();
                    }
        else {
            dialog.setTitle("确定要清除缓存？").setMessage("内部缓存:"+cacheSize).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            FileUtils.deleteDir(getCacheDir());
                            FileUtils.deleteFile(getCacheDir());
                            if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
                                FileUtils.deleteDir(getExternalCacheDir());
                                FileUtils.deleteFile(getExternalCacheDir());
                            }

                            mHandler.sendEmptyMessage(SUCESS);
                        }
                    }).start();
                }
            }).setNegativeButton("取消",null).show();
        }

    }

    private void closeDrawerLayout() {
        if (dlActivityMain.isDrawerOpen(Gravity.LEFT)){
            dlActivityMain.closeDrawers();
        }
    }

    /**
     * 切换Fragment的显示
     *
     * @param target 要切换的 Fragment
     */
    private void switchFragment(Fragment target) {
        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment==target)return;
        // 获得 Fragment 事务
        FragmentTransaction manager=getSupportFragmentManager().beginTransaction();

        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment!=null){
            manager.hide(currentFragment);
        }
        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (target.isAdded()){
            manager.show(target);
        }else {
            // 如果要显示的Fragment没有添加，就添加进去
            manager.add(R.id.fl_content,target,target.getClass().getName());
        }

        // 事务进行提交
        manager.commit();

        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment=target;

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_red:
                    changeTheme(0);
                    break;
                case R.id.tv_blue:
                    changeTheme(1);
                    break;
                case R.id.tv_green:
                    changeTheme(2);
                    break;
                case R.id.tv_orange:
                    changeTheme(3);
                    break;
                case R.id.tv_pink:
                    changeTheme(4);
                    break;
                case R.id.tv_sky:
                    changeTheme(5);
                    break;
                case R.id.tv_purple:
                    changeTheme(6);
                    break;
                case R.id.tv_pp:
                    changeTheme(7);
                    break;
                case R.id.tv_yellow:
                    changeTheme(8);
                    break;
                case R.id.tv_xiangji:
                    File outputImage=new File(getExternalCacheDir(),"camera.jpg");

                    try {
                        if (outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(MainActivity.this,"com.example.administrator.ggcode.fileprovider",outputImage);
                    }else {
                        // 指定调用相机拍照后照片存储路径
                        imageUri = Uri.parse(FILE_PATH);
                    }
                    //相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 1000);
                    mDialog.dismiss();
                    break;
                case R.id.tv_xiangce:
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else {
                        openAlbum();
                    }
                    mDialog.dismiss();
                    break;


            }
        }
    };

    private void openAlbum() {
        //相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1001);

    }

    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        newsFragment = (NewsFragment) manager.findFragmentByTag("NewsFragment");
        jokeFragment = (JokeFragment) manager.findFragmentByTag("JokeFragment");
        todayFragment = (TodayFragment) manager.findFragmentByTag("TodayFragment");
        robotFragment = (RobotFragment) manager.findFragmentByTag("RobotFragment");
        aboutFragment = (AboutFragment) manager.findFragmentByTag("AboutFragment");
        gifFragment = (GIFFragment) manager.findFragmentByTag("GifFragment");
    }

    /*************************** 机器人Fragment页面 点击空白处隐藏软键盘 ***************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.i("路径-------------------"+imageUri);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mIconImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1001:
                if (resultCode==RESULT_OK){
                    if (Build.VERSION.SDK_INT>=19){//判断版本号

                        //4.4以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }

                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri,则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析数字可是的id
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/punlic_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方法
            imagePath=getImagePath(uri,null);
        }else  if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }
    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            mIconImage.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"打开图片失败",Toast.LENGTH_SHORT).show();
        }
    }
    public String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        mSPUtils.putBoolean("has_head", true);
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        return path;
    }
    //底部bar设置再次点击事件


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(getApplicationContext(),"未授权相册",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
