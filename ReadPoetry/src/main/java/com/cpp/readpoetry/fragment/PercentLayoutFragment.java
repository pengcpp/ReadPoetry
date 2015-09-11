package com.cpp.readpoetry.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.PortfolioData;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.Methods;
import com.cpp.readpoetry.view.CircleTextView;
import com.cpp.readpoetry.view.percentlayout.PercentHeaderView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * PercentLayout
 */
public class PercentLayoutFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private PercentHeaderView headerView;

    private SwipeRefreshLayout swipeLayout;
    private ListView mListView;
    private ItemAdapter itemAdapter;

    PortfolioData portfolioData = new PortfolioData();

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

        itemAdapter = new ItemAdapter();
        mListView.setAdapter(itemAdapter);
        itemAdapter.setItemDataList(portfolioData.fundList);
        headerView.setPortfolioData(portfolioData);
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

    /**
     * Adapter...
     */
    class ItemAdapter extends BaseAdapter {

        private List<PortfolioData.FundItemData> itemDataList = new ArrayList<PortfolioData.FundItemData>();

        public ItemAdapter() {
        }

        @Override
        public int getCount() {
            return itemDataList.size();
        }

        @Override
        public Object getItem(int i) {
            return itemDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final PortfolioData.FundItemData fundItemData = itemDataList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_main_fund, null);
                viewHolder = new ViewHolder();
                viewHolder.circleTextView = (CircleTextView) convertView.findViewById(R.id.circle_text);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.title_text);
                viewHolder.valueText = (TextView) convertView.findViewById(R.id.value_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.circleTextView.setBackgroundColor(fundItemData.color);
            viewHolder.circleTextView.setText(fundItemData.investType.replace("型", ""));
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
            ObjectAnimator.ofPropertyValuesHolder(viewHolder.circleTextView, scaleX, scaleY).setDuration(500).start();
            viewHolder.titleText.setText(fundItemData.fundName);
            ObjectAnimator.ofFloat(viewHolder.titleText, "alpha", 0f, 1f).setDuration(600).start();
            viewHolder.valueText.setText("资产占比:  " + Methods.doubleFormat(fundItemData.proportion) + "%");
            ObjectAnimator.ofFloat(viewHolder.valueText, "alpha", 0f, 1f).setDuration(600).start();
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            return convertView;
        }

        public void setItemDataList(List<PortfolioData.FundItemData> itemDataList) {
            this.itemDataList = itemDataList;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        public CircleTextView circleTextView;
        public TextView titleText;
        public TextView valueText;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        headerView.setPortfolioData(new PortfolioData());
                        itemAdapter.setItemDataList(portfolioData.fundList);
                    }
                });
            }
        }, 1500);
    }
}
