package com.cpp.readpoetry.mockedFragments;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.adapter.ImageListAdapter;
import com.cpp.readpoetry.view.ImageListView;
import com.cpp.readpoetry.view.OnDetectScrollListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Three. on 2015/3/20.
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        OnDetectScrollListener, AdapterView.OnItemClickListener {

    private Context mContext;

    private View mView;

    private SwipeRefreshLayout swipeLayout;
    private ImageListView imageListView;

    private Matrix imageMatrix;

    Timer timer;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hot_fragment_main, container, false);

        mContext = getActivity();
        timer = new Timer();

        initView();
        return mView;
    }

    private void initView() {

        swipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        imageListView = (ImageListView) mView.findViewById(R.id.list_view);
        imageListView.setAdapter(new ImageListAdapter(mContext));
        imageListView.setOnDetectScrollListener(this);
        imageListView.setOnItemClickListener(this);

    }

    /**
     * SwipeRefreshLayout.OnRefreshListener *
     */
    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
    }

    /**
     * AdapterView.OnItemClickListener *
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * OnDetectScrollListener *
     */
    @Override
    public void onUpScrolling() {
        int first = imageListView.getFirstVisiblePosition();
        int last = imageListView.getLastVisiblePosition();
        for (int i = 0; i < (last - first); i++) {
            ImageView imageView = ((ImageListAdapter.ViewHolder) imageListView.getChildAt(i).getTag()).imageView;
            imageMatrix = imageView.getImageMatrix();
            imageMatrix.postTranslate(0, -0.5f);
            imageView.setImageMatrix(imageMatrix);
            imageView.invalidate();
        }
    }

    @Override
    public void onDownScrolling() {
        int first = imageListView.getFirstVisiblePosition();
        int last = imageListView.getLastVisiblePosition();
        for (int i = 0; i < (last - first); i++) {
            ImageView imageView = ((ImageListAdapter.ViewHolder) imageListView.getChildAt(i).getTag()).imageView;
            imageMatrix = imageView.getImageMatrix();
            imageMatrix.postTranslate(0, 0.5f);
            imageView.setImageMatrix(imageMatrix);
            imageView.invalidate();
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {

        }
    };
}
