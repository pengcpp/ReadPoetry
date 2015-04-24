package com.cpp.readpoetry.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpp.readpoetry.R;
import com.cpp.readpoetry.util.DisplayUtil;

/**
 * Created by Three. on 2015/3/26.
 */
public class ImageListAdapter extends BaseAdapter {

    private Context mContext;

    public ImageListAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_layout, null, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            imageView.setScaleType(ImageView.ScaleType.MATRIX);

            mContext.getResources().getDrawable(R.drawable.pic_1);
            // hardcode the background image
            int srcId = 0;
            if (position % 4 == 0) {
                srcId = R.drawable.pic_1;
            } else if (position % 4 == 1) {
                srcId = R.drawable.pic_2;
            } else if (position % 4 == 2) {
                srcId = R.drawable.pic_3;
            } else {
                srcId = R.drawable.pic_4;
            }

//            Bitmap bitmap = DisplayUtil.drawableToBitmap(mContext.getResources().getDrawable(srcId));
//            int bmpWidth = bitmap.getWidth();
//            int bmpHeight = bitmap.getHeight();

            imageView.setImageResource(srcId);

            // since the image size is set to 400 x 300 we will hardcode the initial translation to the center for new item
            Matrix matrix = imageView.getImageMatrix();
            matrix.postTranslate(0, -100);
            imageView.setImageMatrix(matrix);

            // use the viewholder
            viewHolder = new ViewHolder();
            viewHolder.imageView = imageView;
            viewHolder.textView = textView;
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textView.setText("Row " + position);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

}
