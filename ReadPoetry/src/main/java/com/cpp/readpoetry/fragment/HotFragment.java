package com.cpp.readpoetry.fragment;

import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.adapter.ImageListAdapter;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.view.ImageListView;
import com.cpp.readpoetry.view.OnDetectScrollListener;

/**
 * Created by Three. on 2015/3/20.
 */
public class HotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnDetectScrollListener, AdapterView.OnItemClickListener {

    private final String TAG = "HotFragment";

    private SwipeRefreshLayout swipeLayout;
    private ImageListView imageListView;

    private Matrix imageMatrix;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_hot_main;
    }

    @Override
    protected void onInitView() {
        swipeLayout = (SwipeRefreshLayout) containerView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        imageListView = (ImageListView) containerView.findViewById(R.id.list_view);
        imageListView.setAdapter(new ImageListAdapter(context));
        imageListView.setOnDetectScrollListener(this);
        imageListView.setOnItemClickListener(this);
    }

    /**
     * SwipeRefreshLayout.OnRefreshListener *
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        }, 1500);
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
            imageMatrix.postTranslate(0f, 0.2f);
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
            imageMatrix.postTranslate(0f, -0.2f);
            imageView.setImageMatrix(imageMatrix);
            imageView.invalidate();
        }
    }

}
