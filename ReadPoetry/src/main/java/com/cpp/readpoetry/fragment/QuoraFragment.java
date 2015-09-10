package com.cpp.readpoetry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.activity.TerminalActivity;
import com.cpp.readpoetry.data.QuoraData;
import com.cpp.readpoetry.data.TestData;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.Methods;
import com.cpp.readpoetry.view.viewpagerindicator.UnderlinePageIndicator;
import com.cpp.readpoetry.view.viewpagerindicator.ViewPagerScroller;

import java.util.ArrayList;

/**
 * 测试问答
 */
public class QuoraFragment extends BaseFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private final String TAG = "InvestigateFragment";

    private TextView questionText;
    private TextView pageText;
    private ListView questionList;
    private QuoraAdapter quoraAdapter;

    private LinearLayout footerLayout;
    private Button lastQuestionButton;
    private Button completeButton;

    private ViewPager quoraViewpager;
    private UnderlinePageIndicator indicator;
    private QuoraPagerAdapter pageAdapter;

    private ArrayList<QuoraData> quoraDataList = TestData.initQuoraList();

    private int curSelectPos = 0;
    private int[] answerSelect = new int[10];//记录选择


    //------------------//
    public static void show(Context context) {
        TerminalActivity.showFragment(context, QuoraFragment.class, null);
    }

    @Override
    protected void onPreConfigured() {
        for (int i = 0; i < 10; i++) {
            answerSelect[i] = -1;
        }
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_quora_main;
    }

    @Override
    protected void onInitView() {

        questionText = (TextView) containerView.findViewById(R.id.question_text);
        pageText = (TextView) containerView.findViewById(R.id.page_text);
        questionList = (ListView) containerView.findViewById(R.id.question_list);
        quoraAdapter = new QuoraAdapter();
        questionList.setAdapter(quoraAdapter);

        footerLayout = (LinearLayout) containerView.findViewById(R.id.footer_layout);
        lastQuestionButton = (Button) containerView.findViewById(R.id.last_question_btn);
        completeButton = (Button) containerView.findViewById(R.id.complete_btn);
        footerLayout.setVisibility(View.GONE);

        pageAdapter = new QuoraPagerAdapter(getFragmentManager(), quoraDataList.size());
        quoraViewpager = (ViewPager) containerView.findViewById(R.id.quora_viewpager);
        quoraViewpager.setAdapter(pageAdapter);
        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.initViewPagerScroll(quoraViewpager);
        indicator = (UnderlinePageIndicator) containerView.findViewById(R.id.quora_indicator);
        indicator.setViewPager(quoraViewpager, 0);
        indicator.setFades(false);

        quoraAdapter.setQuoraData(quoraDataList.get(0), -1);
    }

    @Override
    protected void onInitListener() {
        questionList.setOnItemClickListener(this);
        lastQuestionButton.setOnClickListener(this);
        completeButton.setOnClickListener(this);
    }

    /* View.OnClickListener */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点上一题 Adapter set data
            case R.id.last_question_btn:
                curSelectPos--;
                setViewPagerCurSelectPos(curSelectPos);
                if (curSelectPos == 0) {
                    footerLayout.setVisibility(View.GONE);
                }
                if (curSelectPos == quoraDataList.size() - 1) {
                    questionList.setVisibility(View.VISIBLE);
                }
                lastQuestionButton.setBackgroundResource(R.drawable.quora_orange_button_selector);
                lastQuestionButton.setTextColor(getResources().getColor(R.color.white));
                completeButton.setVisibility(View.GONE);
                quoraAdapter.setQuoraData(quoraDataList.get(curSelectPos), answerSelect[curSelectPos]);
                break;

            case R.id.complete_btn:
                judgeLastQuora();
                break;

            default:
        }
    }

    void judgeLastQuora() {

        if (quoraAdapter.getSelection() == -1) {
            Methods.showToast("请选择最后一题，以便为您生成投资组合！");
            return;
        }

    }

    @Override
    @SuppressWarnings("deprecation")
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        footerLayout.setVisibility(View.VISIBLE);
        answerSelect[curSelectPos] = position;
        if (curSelectPos == quoraDataList.size() - 1) { // 最后一个
            quoraAdapter.setQuoraData(quoraDataList.get(curSelectPos), position);

        } else {
            curSelectPos++;// 去下一个
            setViewPagerCurSelectPos(curSelectPos);
            quoraAdapter.setQuoraData(quoraDataList.get(curSelectPos), answerSelect[curSelectPos]);
            if (curSelectPos == quoraDataList.size() - 1) {
                completeButton.setVisibility(View.VISIBLE);
                lastQuestionButton.setBackgroundResource(R.color.common_left_btn_white_bg);
                lastQuestionButton.setTextColor(getResources().getColor(R.color.common_orange_text));
            }
        }
    }

    void setViewPagerCurSelectPos(final int position) {
        quoraViewpager.setCurrentItem(position);
    }

    /**
     * ListView Adapter
     */
    private class QuoraAdapter extends BaseAdapter {

        private int selection = -1;

        private QuoraData quoraData = new QuoraData();

        public QuoraAdapter() {
        }

        @Override
        public int getCount() {
            return quoraData.quoraList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return quoraData.quoraList.size();
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_quora, null);
                viewHolder = new ViewHolder();
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.title_text);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (selection != -1 && selection == i) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
            viewHolder.titleText.setText(quoraData.quoraList.get(i));

            return convertView;
        }

        public void setQuoraData(QuoraData quoraData, int selection) {
            this.selection = selection;
            this.quoraData = quoraData;
            notifyDataSetChanged();
            questionText.setText(quoraData.quoraTitle);
            pageText.setText("Q" + (quoraData.position + 1));
        }

        public int getSelection() {
            return selection;
        }
    }


    private class ViewHolder {
        public TextView titleText;
        public CheckBox checkBox;
    }

    /**
     * ViewPager Adapter
     */
    class QuoraPagerAdapter extends FragmentPagerAdapter {

        private int pageCount;

        public QuoraPagerAdapter(FragmentManager fm, int pageCount) {
            super(fm);
            this.pageCount = pageCount;
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public Fragment getItem(int i) {
            return QuoraPagerFragment.newInstance();
        }
    }
}
