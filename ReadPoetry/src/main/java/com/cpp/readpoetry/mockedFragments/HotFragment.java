package com.cpp.readpoetry.mockedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cpp.readpoetry.R;

/**
 * Created by Three. on 2015/3/20.
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private Context mContext;

    private View mView;

    private SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hot_fragment_main, container, false);

        mContext = getActivity();

        initView();
        return mView;
    }

    private void initView(){

        swipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);


    }

    /** SwipeRefreshLayout.OnRefreshListener **/
    @Override
    public void onRefresh() {

    }
}
