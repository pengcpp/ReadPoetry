package com.cpp.readpoetry.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.FeedBackFragment;
import com.cpp.readpoetry.fragment.FeedFragment;
import com.cpp.readpoetry.fragment.FunctionFragment;
import com.cpp.readpoetry.fragment.HotFragment;
import com.cpp.readpoetry.util.DisplayUtil;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.util.Utils;

/**
 * 主容器
 */
public class MainActivity extends MaterialNavigationDrawer {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        closeDrawer();
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);

    }

    @Override
    public void init(Bundle savedInstanceState) {

        // set header background image
        setDrawerHeaderImage(R.drawable.ic_navigation_pic);

//        setUsername(getString(R.string.app_name_cn));
        setUserEmail(getString(R.string.default_des_two));

        Point photoSize = DisplayUtil.getUserPhotoSize(getResources());
        Bitmap photo = Utils.resizeBitmapFromResource(getResources(), R.drawable.ic_default_user, photoSize.x, photoSize.y);
        Drawable circularPhoto = new BitmapDrawable(getResources(), Utils.getCroppedBitmapDrawable(photo));
        setFirstAccountPhoto(circularPhoto);

        setAccountListener(new MaterialAccountListener() {
            @Override
            public void onAccountOpening(MaterialAccount materialAccount) {
                openDrawer();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

            @Override
            public void onChangeAccount(MaterialAccount materialAccount) {

            }
        });

        // create sections no icon & with icon
//        this.addSection(newSection("热门", new FragmentIndex()));
//        this.addSection(newSection("反馈意见",new FragmentIndex()));
        this.addSection(newSection(getString(R.string.drawer_section1), R.drawable.ic_track_changes_white, new HotFragment()).setSectionColor(Color.parseColor("#F44336")));
        this.addSection(newSection(getString(R.string.drawer_section2), R.drawable.ic_backup_white, new FeedBackFragment()).setSectionColor(Color.parseColor("#2196F3")));
        this.addSection(newSection(getString(R.string.drawer_section3), R.drawable.ic_camera_alt, new FeedFragment()).setSectionColor(Color.parseColor("#5AB963")));
        this.addSection(newSection(getString(R.string.drawer_section4), R.drawable.ic_settings_white, new FunctionFragment()).setSectionColor(Color.parseColor("#FF9800")));
//        this.addSection(newSection(getString(R.string.drawer_section4), R.drawable.ic_search_white, new Intent(MainActivity.this, SettingActivity.class)));

        // create bottom section
//        this.addBottomSection(newSection("Bottom Section", R.drawable.ic_settings_black_24dp, new Intent(this, Settings.class)));


    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    overridePendingTransition(R.anim.activity_right_enter, 0);
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为了让Toolbar的Menu有作用，这里的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce
                || this.getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, getString(R.string.double_click_quit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }

        }, 2000);
    }
}
