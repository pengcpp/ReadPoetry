package com.cpp.readpoetry.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.PortfolioData;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.view.percentlayout.PercentHeaderView;

import static com.cpp.readpoetry.config.GlobalConfig.REFRESH_FAIL;
import static com.cpp.readpoetry.config.GlobalConfig.REFRESH_OK;

/**
 * 反馈
 */
public class FeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private PercentHeaderView headerView;

    private SwipeRefreshLayout swipeLayout;
    private ListView mListView;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_feed_main;
    }

    @Override
    protected void onInitView() {

        swipeLayout = (SwipeRefreshLayout) containerView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        mListView = (ListView) containerView.findViewById(R.id.list_view);
        headerView = new PercentHeaderView(context);

        mListView.addHeaderView(headerView);
        mListView.setAdapter(null);

        headerView.setPortfolioData(new PortfolioData());
    }

    @Override
    protected void onInitListener() {
        headerView.setQuoraImageClickListener(new PercentHeaderView.OnQuoraImageClickListener() {
            @Override
            public void onQuoraImageClick(View view) {
                QuoraFragment.show(context);
            }
        });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                headerView.setPortfolioData(new PortfolioData());
            }
        }, 2000);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_OK:
                    swipeLayout.setRefreshing(false);
                    break;
                case REFRESH_FAIL:
                    break;
                default:
            }
        }
    };
}
