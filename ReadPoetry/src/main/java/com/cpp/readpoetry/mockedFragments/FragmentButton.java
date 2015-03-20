package com.cpp.readpoetry.mockedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.cpp.readpoetry.R;

/**
 * Created by neokree on 31/12/14.
 */
public class FragmentButton extends Fragment {

    private Context mContext;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hot_fragment_main, container, false);
        Button button = new Button(this.getActivity());
        button.setText("Click Me");
        button.setGravity(Gravity.CENTER);
        return button;

    }
}
