package com.cpp.readpoetry;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.cpp.readpoetry.mockedActivity.Settings;
import com.cpp.readpoetry.mockedFragments.FragmentButton;
import com.cpp.readpoetry.mockedFragments.FragmentIndex;
import com.cpp.readpoetry.util.DisplayUtil;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.util.Utils;

/**
 * Created by neokree on 18/01/15.
 */
public class MainActivity extends MaterialNavigationDrawer {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);

    }

    @Override
    public void init(Bundle savedInstanceState) {

        // set header background image
        setDrawerHeaderImage(R.drawable.ic_navigation_pic);

        setUsername(getString(R.string.app_name_cn));
        setUserEmail(getString(R.string.default_des));

        Point photoSize = DisplayUtil.getUserPhotoSize(getResources());
        Bitmap photo = Utils.resizeBitmapFromResource(getResources(), R.drawable.ic_default_user, photoSize.x, photoSize.y);
        Drawable circularPhoto = new BitmapDrawable(getResources(), Utils.getCroppedBitmapDrawable(photo));
        setFirstAccountPhoto(circularPhoto);

        // create sections no icon & with icon
//        this.addSection(newSection("热门", new FragmentIndex()));
//        this.addSection(newSection("反馈意见",new FragmentIndex()));
        this.addSection(newSection("热门", R.drawable.ic_hot, new FragmentButton()).setSectionColor(Color.parseColor("#F44336")));
        this.addSection(newSection("反馈意见", R.drawable.ic_feedback, new FragmentButton()).setSectionColor(Color.parseColor("#2196F3")));
        this.addSection(newSection("提供素材", R.drawable.ic_mic_white_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#5AB963")));
        this.addSection(newSection("设置", R.drawable.ic_hotel_grey600_24dp, new FragmentButton()).setSectionColor(Color.parseColor("#FF9800")));

        // create bottom section
        this.addBottomSection(newSection("Bottom Section", R.drawable.ic_settings_black_24dp, new Intent(this, Settings.class)));
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
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

}
