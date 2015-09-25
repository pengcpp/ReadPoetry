package com.cpp.readpoetry.fragment;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.DisplayUtil;
import com.cpp.readpoetry.view.CircleBorderColorTextView;
import com.cpp.readpoetry.view.CircleTextView;
import it.neokree.materialnavigationdrawer.util.Utils;

/**
 * Created by Three. on 2015/9/23.
 * <p/>
 * CircleView
 */
public class CircleViewFragment extends BaseFragment {

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_view_circle_main;
    }

    @Override
    protected void onInitView() {
        CircleTextView circleTextView = (CircleTextView) containerView.findViewById(R.id.circle_text);
        circleTextView.setBackgroundColor(getResources().getColor(R.color.material_red));

        CircleBorderColorTextView circleBorderColorTextView = (CircleBorderColorTextView) containerView.findViewById(R.id.circle_border_text);
        circleBorderColorTextView.setBackgroundColor(getResources().getColor(R.color.material_blue));

        ImageView circleImage = (ImageView) containerView.findViewById(R.id.circle_image);
        Point photoSize = DisplayUtil.getUserPhotoSize(getResources());
        Bitmap photo = Utils.resizeBitmapFromResource(getResources(), R.drawable.ic_default_user, photoSize.x, photoSize.y);
        Drawable circularPhoto = new BitmapDrawable(getResources(), Utils.getCroppedBitmapDrawable(photo));
        circleImage.setBackgroundDrawable(circularPhoto);

    }
}
