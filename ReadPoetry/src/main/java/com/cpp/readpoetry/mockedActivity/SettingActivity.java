package com.cpp.readpoetry.mockedActivity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.manager.AnimationManager;


/**
 * Created by Three. on 2015/3/6.
 */
public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.action_settings));

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinishActivity();
            }
        });
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        onFinishActivity();
    }


    private void onFinishActivity() {
        finish();
        AnimationManager.overridePendingTransition(SettingActivity.this,
                AnimationManager.ActivityAction.EXIT,
                AnimationManager.ActivityAnimationType.TRANSLATE_EXIT_TO_LEFT);
    }


}
