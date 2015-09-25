package com.cpp.readpoetry.fragment.guide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.LogUtils;
import com.cpp.readpoetry.view.ColorAnimationView;
import com.cpp.readpoetry.view.TopActionBar;

/**
 *
 */
public class ColorAnimFragment extends BaseFragment {

    private static final String TAG = "ColorAnimFragment";

    private static final int[] resource = new int[]{R.drawable.welcome1, R.drawable.welcome4,
            R.drawable.welcome3, R.drawable.welcome4};

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_color_anim_main;
    }

    @Override
    protected void onInitView() {
        ((TopActionBar) containerView.findViewById(R.id.topbar)).setSeperatorShow(false);
        ((TopActionBar) containerView.findViewById(R.id.topbar)).setBackgroundImageRes(0);

        MyFragmentStatePager adapter = new MyFragmentStatePager(ColorAnimFragment.this.getFragmentManager());
        ColorAnimationView colorAnimationView = (ColorAnimationView) containerView.findViewById(R.id.ColorAnimationView);
        ViewPager viewPager = (ViewPager) containerView.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        colorAnimationView.setViewPager(viewPager, resource.length);
        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.i("TAG", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.i("TAG", "onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.i("TAG", "onPageScrollStateChanged");
            }
        });
        // Four : Also ,you can call this method like this:
        // colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);

    }

    public class MyFragmentStatePager extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(position);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public class MyFragment extends Fragment {

        private int position;

        public MyFragment(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resource[position]);
            return imageView;
        }
    }
}
