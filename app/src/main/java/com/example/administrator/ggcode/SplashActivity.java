package com.example.administrator.ggcode;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.SPUtils;
import com.example.administrator.ggcode.Activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView (R.id.activity_splash)
    RelativeLayout activitySplash;
    private AnimatorSet set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SPUtils sp=new SPUtils("theme_id");
        int theme_id = sp.getInt("theme_id", R.style.AppTheme);
        setTheme(theme_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_solash);
        ButterKnife.bind(this);
        set = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ivSplash, "translationX", 600, 0);
        ObjectAnimator translationY = ObjectAnimator
                .ofFloat(ivSplash, "translationY", -100, 90, -80, 70, -60, 50);

        set.playTogether(translationX, translationY);
        set.setDuration(2000);
        addListener();
    }

    private void addListener() {
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
