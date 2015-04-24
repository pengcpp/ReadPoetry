package com.cpp.readpoetry.mockedActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.config.AppConfig;
import com.cpp.readpoetry.manager.AnimationManager;
import com.cpp.readpoetry.manager.SettingManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.cpp.readpoetry.util.Logger.logInfo;

/**
 * 欢迎界面
 * <p/>
 * Created by Three. on 2015/3/5.
 */
public class WelcomeActivity extends Activity {

    private ImageView welcomeImageView;

    private Timer timer = new Timer();

    private SettingManager mSettingManager;

    boolean initialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        mSettingManager = SettingManager.getInstance();

        initView();
        initUserData();

        onWelcomeEnd();
    }

    private void initView() {

        welcomeImageView = (ImageView) findViewById(R.id.welcome_image);

        int r = new Random().nextInt() % 2;
        logInfo("Random int -> " + r);
        int resId = (r == 0) ? R.drawable.ic_splash_1 : R.drawable.ic_splash_2;
        welcomeImageView.setImageResource(resId);

    }

    private void initUserData(){
        // TODO 读取数据库

        initialized = true;
    }


    private void onWelcomeEnd() {

        if (!initialized) {
            welcomeImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onWelcomeEnd();
                }
            }, 500);
        } else {
            if (timer != null) {
                timer.schedule(taskToDesktop, 500);
            }
        }
    }


    // 进入 Desktop TimerTask
    TimerTask taskToDesktop = new TimerTask() {
        public void run() {

            // 显示新特性，新功能介绍
            if (mSettingManager.getCurrentVersion() != AppConfig.getVersionCode()) {
                mSettingManager.setCurrentVersion(AppConfig.getVersionCode());
                // TODO 可有可无

//                finish();
//                return;
            }
            // 直接进入主界面
            {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                AnimationManager.overridePendingTransition(WelcomeActivity.this, false, AnimationManager.ActivityAnimationType.FADE_DEFAULT);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

}
