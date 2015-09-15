package com.cpp.readpoetry.fragment.function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.third.easing.BaseEasingMethod;
import com.cpp.readpoetry.third.easing.Glider;
import com.cpp.readpoetry.third.easing.Skill;
import com.cpp.readpoetry.util.DisplayUtil;
import com.cpp.readpoetry.view.DrawView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Easing Animation
 *
 * @see {http://easings.net/zh-cn}
 */
public class EasingFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mEasingList;
    private EasingAdapter mAdapter;
    private View mTarget;

    private DrawView mHistory;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_easing_main;
    }

    @Override
    protected void onInitView() {
        mEasingList = (ListView) containerView.findViewById(R.id.easing_list);
        mEasingList.setOnItemClickListener(this);
        mAdapter = new EasingAdapter(context);
        mEasingList.setAdapter(mAdapter);
        mTarget = containerView.findViewById(R.id.target);
        mHistory = (DrawView) containerView.findViewById(R.id.history);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mHistory.clear();
        Skill s = (Skill) view.getTag();
        AnimatorSet set = new AnimatorSet();
        mTarget.setTranslationX(0);
        mTarget.setTranslationY(0);
        set.playTogether(
                Glider.glide(s, 1200, ObjectAnimator.ofFloat(mTarget, "translationY", 0, DisplayUtil.dp2px(context, -(160 - 3))),
                        new BaseEasingMethod.EasingListener() {
                            @Override
                            public void on(float time, float value, float start, float end, float duration) {
                                mHistory.drawPoint(time, duration, value - DisplayUtil.dp2px(context, 60));
                            }
                        })
        );
        set.setDuration(1200);
        set.start();
    }

    /**
     * Adapter...
     */
    class EasingAdapter extends BaseAdapter {

        private Context mContext;

        public EasingAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return Skill.values().length;
        }

        @Override
        public Object getItem(int i) {
            return Skill.values()[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Object o = getItem(i);
            BaseEasingMethod b = ((Skill) o).getMethod(1000);
            int start = b.getClass().getName().lastIndexOf(".") + 1;
            String name = b.getClass().getName().substring(start);
            View view1 = LayoutInflater.from(mContext).inflate(R.layout.list_item_easing, null);
            TextView textView = (TextView) view1.findViewById(R.id.list_item_text);
            textView.setText(name);
            view1.setTag(o);
            return view1;
        }
    }


}
