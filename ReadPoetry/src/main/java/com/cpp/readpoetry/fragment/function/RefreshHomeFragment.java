package com.cpp.readpoetry.fragment.function;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.activity.TerminalActivity;
import com.cpp.readpoetry.data.FuncItemData;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.fragment.refresh.RentalsStyleFragment;
import com.cpp.readpoetry.fragment.refresh.StoreHouseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Refresh
 */
public class RefreshHomeFragment extends BaseFragment {

    ListView mListView;

    private FuncAdapter mFuncAdapter;

    private List<FuncItemData> itemDataList = new ArrayList<>();

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_refresh_main;
    }

    @Override
    protected void onPreConfigured() {
        initBaseList();
    }

    @Override
    protected void onInitView() {
        mListView = (ListView) containerView.findViewById(R.id.list_view);

        mFuncAdapter = new FuncAdapter();
        mListView.setAdapter(mFuncAdapter);
        mFuncAdapter.setItemDataList(itemDataList);
    }

    void initBaseList() {
        itemDataList = new ArrayList<>();
        FuncItemData data;

        // PercentLayoutFragment
        data = new FuncItemData();
        data.title = "StoreHouse";
        data.showHeaderView = true;
        data.showDividingLine = true;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, StoreHouseFragment.class, null);
            }
        };
        itemDataList.add(data);

        // CarouselFragment
        data = new FuncItemData();
        data.title = "RentalsStyle";
        data.showDividingLine = true;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, RentalsStyleFragment.class, null);
            }
        };
        itemDataList.add(data);

    }

    /**
     * Adapter...
     */
    class FuncAdapter extends BaseAdapter {

        private List<FuncItemData> itemDataList = new ArrayList<>();

        public FuncAdapter() {
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

            final FuncItemData funcItemData = itemDataList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_single, null);
                viewHolder = new ViewHolder();
                viewHolder.divisionHeaderView = convertView.findViewById(R.id.header_division_view);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.title_text);
                viewHolder.valueText = (TextView) convertView.findViewById(R.id.value_text_text);
                viewHolder.divisionLine = convertView.findViewById(R.id.item_division_line);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.titleText.setText(funcItemData.title);
//            viewHolder.imageView.setImageResource(funcItemData.res);

            viewHolder.divisionHeaderView.setVisibility(funcItemData.showHeaderView ? View.VISIBLE : View.GONE);
            viewHolder.divisionLine.setVisibility(funcItemData.showDividingLine ? View.VISIBLE : View.GONE);

            if (funcItemData.onClickListener != null) {
                convertView.setOnClickListener(funcItemData.onClickListener);
            }

            return convertView;
        }

        public void setItemDataList(List<FuncItemData> itemDataList) {
            this.itemDataList = itemDataList;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {

        public View divisionHeaderView;
        public TextView titleText;
        public TextView valueText;
        public View divisionLine;
        public ImageView imageView;
    }
}