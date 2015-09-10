package com.cpp.readpoetry.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.util.DisplayUtil;

/**
 * 使用说明： TopBar提供以下功能：
 * <p/>
 * a.topbar分为四个区域，分别是左，中，右2，右，其中左区一般是返回键; 中区包含标题和标题旁边的一个提示图标；右2和右提供程序功能。
 * <p/>
 * b. 中区标题栏的功能包括，显示标题，显示标题旁边的小图片（一般是向下的箭头）
 * <p/>
 * c. 左，右，右2使用同一布局，默认提供显示图片，显示文字，显示提示更新数量的
 * 红色小园框功能。右布局出于兼容老版本原因，提供显示progressbar功能
 * <p/>
 * d. 如果要改变各布局内部的因素，请调用相应布局的customize方法，则原来的所有布局 中子view都将隐藏，来提供customize实现空间
 * <p/>
 * 使用方法: 1. 初始化topbar使用showConfig()，除了返回键默认有返回以外，右和右2无默认图片
 * <p/>
 * 2.设置图片调用showButtonImage();可以统一设置也可以单个设置，看参数选择
 * <p/>
 * 3.设置文字调用showButtonText();可以统一设置也可以单个设置，看参数选择
 * <p/>
 * 4.设置新消息提醒调用showMessageCount(layoutId, messageCount, buttonText)
 * <p/>
 * 5.暂时不提供同时显示图片和背景的方法，如果有需求请自行调用getArea(int
 * layoutId),然后调用里面的getTag()方法，获取一个TopBarHolder,里面包含了要显示的Button和TextView
 * <p/>
 * 6. 如果想要自行定制图标的显示，调用customizeArea(int layoutId)
 */
public class TopActionBar extends RelativeLayout {

    /**
     * Resource ID...
     */
    protected int layoutResId = R.layout.top_actionbar_layout;

    /**
     * 标题栏左侧区域，显示返回按钮的
     */
    public static final int LEFT_AREA = 1;
    /**
     * 标题栏中部区域，显示标题文本
     */
    public static final int MIDDLE_AREA = 2;
    /**
     * 标题栏中间偏右的区域
     */
    public static final int RIGHT_AREA2 = 3;
    /**
     * 标题栏最右侧区域
     */
    public static final int RIGHT_AREA = 4;

    /**
     * 点击标题栏的Listener
     *
     * @author peter
     */
    public interface OnTopBarTitleClickListener {
        void onTitleClick(View view);
    }

    /**
     * 三个按钮的listener
     *
     * @author peter
     */
    public interface OnTopBarButtonClickListener {
        void onLeftButtonClick(View view);

        void onRightButton2Click(View view);

        void onRightButtonClick(View view);
    }

    public interface OnLeftButtonClickListener {
        void onLeftButtonClick(View view);
    }

    public interface OnRightButtonClickListener {
        void onRightButtonClick(View view);
    }


    /**
     * 一个viewholder，包含了leftlayout里面的button和textview
     *
     * @author Peter
     */
    public class TopBarHolder {

        public ImageButton imageButton; // 主要显示的ImageButton

        public TextView textView;// 如果有消息提示的话，就是这个了
    }

    private static final float TEXT_SIZE_LARGE = 15.0f;
    private static final float TEXT_SIZE_SMALL = 15.0f;
    private static final int ANIMATION_DURATION = 500;
    private static final int TEMP_VIEW_INDEX = 2;

    private Context context;
    private LayoutInflater inflater;
    private OnTopBarButtonClickListener topbarButtonListener;
    private OnLeftButtonClickListener leftButtonListener;
    private OnRightButtonClickListener rightButtonListener;
    private OnTopBarTitleClickListener topbarTitleListener;

    // 返回键处的stub
    private ViewStub leftStub; // 包含layout的stub来提高性能，下同
    private RelativeLayout leftLayout;// 外层的layout，下同

    // 最右侧的按钮stub
    private ViewStub rightStub;
    private RelativeLayout rightLayout;

    // 右2按钮stub
    private ViewStub rightStub2;
    private RelativeLayout rightLayout2;

    // 标题栏
    private TextView titleTextView;
    private RelativeLayout middleLayout;
    // 标题旁边的那个小玩意儿
    private ImageView titleSide;
    private View seperatorView;

    private String buttonTextCache;
    private Drawable buttonImgCache;

    private String title;

    private boolean isLeftShow;
    private int leftImgRes;
    private String leftText;

    private boolean isRightShow;
    private int rightImgRes;
    private String rightText;
    private String rightClassName;

    private boolean isRight2Show;
    private int right2ImgRes;
    private String right2Text;
    private String right2ClassName;

    /* TopActionBar */
    public TopActionBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TopActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    public TopActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        getAttrs(attrs);
        init();
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TopActionBar);
        try {
            title = ta.getString(R.styleable.TopActionBar_topbarTitle);
            isLeftShow = ta.getBoolean(R.styleable.TopActionBar_topbarIsLeftShow,
                    false);
            leftImgRes = ta.getResourceId(R.styleable.TopActionBar_topbarLeftImgRes,
                    R.drawable.icon_back);
            leftText = ta.getString(R.styleable.TopActionBar_topbarLeftText);
            if (!isLeftShow)
                leftImgRes = 0;

            isRightShow = ta.getBoolean(R.styleable.TopActionBar_topbarIsRightShow,
                    false);
            rightImgRes = ta.getResourceId(R.styleable.TopActionBar_topbarRightImgRes,
                    R.drawable.icon_setting);
            rightText = ta.getString(R.styleable.TopActionBar_topbarRightText);
            rightClassName = ta
                    .getString(R.styleable.TopActionBar_topbarRightClassName);
            if (!isRightShow)
                rightImgRes = 0;

            isRight2Show = ta.getBoolean(R.styleable.TopActionBar_topbarIsRight2Show,
                    false);
            right2ImgRes = ta.getResourceId(R.styleable.TopActionBar_topbarRight2ImgRes,
                    R.drawable.icon_share);
            right2Text = ta.getString(R.styleable.TopActionBar_topbarRight2Text);
            right2ClassName = ta
                    .getString(R.styleable.TopActionBar_topbarRight2ClassName);
            if (!isRight2Show)
                right2ImgRes = 0;

        } finally {
            ta.recycle();
        }
    }

    protected void init() {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId, this);
        middleLayout = (RelativeLayout) findViewById(R.id.middle_layout);
        seperatorView = findViewById(R.id.action_bar_seperator);
        titleTextView = (TextView) findViewById(R.id.topbar_title);
        titleSide = (ImageView) findViewById(R.id.topbar_side_icon);
        leftStub = (ViewStub) findViewById(R.id.left_layout);
        rightStub = (ViewStub) findViewById(R.id.right_layout);
        rightStub2 = (ViewStub) findViewById(R.id.right_layout2);

//        setBackgroundResource(R.drawable.topbar_bg);
        this.setBackgroundImageRes(R.color.common_white);

        showConfig(title, isLeftShow, isRight2Show, isRightShow);

        initImageAndText(leftText, leftImgRes, LEFT_AREA);
        initImageAndText(rightText, rightImgRes, RIGHT_AREA);
        initImageAndText(right2Text, right2ImgRes, RIGHT_AREA2);
        setSeperatorShow(true);
    }

    private void initImageAndText(String text, int imgRes, int areaId) {
        if (!TextUtils.isEmpty(text)) {
            showButtonText(text, areaId);
        } else if (imgRes != 0) {
            showButtonImage(imgRes, areaId);
        }
    }

    public void setSeperatorShow(boolean isShow) {
        if (seperatorView != null) {
            if (isShow) {
                seperatorView.setVisibility(View.VISIBLE);
            } else {
                seperatorView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置标题，同时设置topbar包含的区域。如果要显示，则传true,否则传false
     * 初始化topbar的方法，必须调用，来设置topbar上面显示的元素 直接调用三个参数的那个
     *
     * @param title        标题
     * @param isLeftShow   返回键是否显示（默认显示为一个返回图片）
     * @param isRight2Show 标题右侧按钮是否显示
     * @param isRightShow  最右侧按钮是否显示
     */
    private void showConfig(String title, boolean isLeftShow,
                            boolean isRight2Show, boolean isRightShow) {
        this.title = title;
        this.setTitle(title);
        showConfig(isLeftShow, isRight2Show, isRightShow);
    }

    /**
     * 设置topbar包含的区域。如果要显示，则传true,否则传false
     *
     * @param isLeftShow   返回键是否显示（默认显示为一个返回图片）
     * @param isRight2Show 标题右侧按钮是否显示
     * @param isRightShow  最右侧按钮是否显示
     */
    private void showConfig(boolean isLeftShow, boolean isRight2Show,
                            boolean isRightShow) {

        if (isLeftShow) {
            initArea(LEFT_AREA);
        } else {
            if (leftLayout != null) {
                leftLayout.setVisibility(View.GONE);
            }
        }

        if (isRightShow) {
            initArea(RIGHT_AREA);
        } else {
            if (rightLayout != null) {
                rightLayout.setVisibility(View.GONE);
            }
        }

        if (isRight2Show) {
            initArea(RIGHT_AREA2);
        } else {
            if (rightLayout2 != null) {
                rightLayout2.setVisibility(View.GONE);
            }
        }

        this.isLeftShow = isLeftShow;
        this.isRight2Show = isRight2Show;
        this.isRightShow = isRightShow;
    }

    /**
     * 初始化标题栏按钮区域
     *
     * @param layoutId
     */
    private void initArea(final int layoutId) {

        RelativeLayout layout = null;
        ViewStub stub = null;
        switch (layoutId) {
            case LEFT_AREA:
                layout = leftLayout;
                stub = leftStub;
                break;
            case RIGHT_AREA:
                layout = rightLayout;
                stub = rightStub;
                break;
            case RIGHT_AREA2:
                layout = rightLayout2;
                stub = rightStub2;
                break;
            default:
                break;
        }

        if (layout == null) {
            layout = (RelativeLayout) stub.inflate();
            switch (layoutId) {
                case LEFT_AREA:
                    leftLayout = layout;
                    break;
                case RIGHT_AREA:
                    rightLayout = layout;
                    break;
                case RIGHT_AREA2:
                    rightLayout2 = layout;
                    break;
                default:
                    break;
            }
            final TopBarHolder holder = new TopBarHolder();
            layout.setTag(holder);
            holder.imageButton = (ImageButton) layout.findViewById(R.id.topbar_button);
            holder.imageButton.setVisibility(View.VISIBLE);
            holder.textView = (TextView) layout
                    .findViewById(R.id.topbar_message_count);
            holder.textView.setVisibility(View.GONE);
            holder.imageButton.setClickable(true);
            holder.imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLayoutClicked(layoutId);
                }
            });
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLayoutClicked(layoutId);
                }
            });
            holder.textView.setClickable(false);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据所提供的layoutId,获取相应的layout
     *
     * @param area 就是layoutId
     * @return 获取相应的layout(包括左，右2，右)
     */
    public RelativeLayout getArea(int area) {
        RelativeLayout tempLayout = null;
        switch (area) {
            case LEFT_AREA:
                if (leftLayout == null) {
                    initArea(LEFT_AREA);
                }
                tempLayout = leftLayout;
                break;
            case MIDDLE_AREA:
                titleTextView.setVisibility(View.GONE);
                titleSide.setVisibility(View.GONE);
                tempLayout = middleLayout;
                break;
            case RIGHT_AREA2:
                if (rightLayout2 == null) {
                    initArea(RIGHT_AREA2);
                }
                tempLayout = rightLayout2;
                break;
            case RIGHT_AREA:
                if (rightLayout == null) {
                    initArea(RIGHT_AREA);
                }
                tempLayout = rightLayout;
            default:
                break;
        }
        return tempLayout;
    }

    /**
     * 如果你要自定义topbar的某一个区域，请调用这个方法，并且传入下列参数的一个： TopBar.LEFT_AREA
     * TopBar.MIDDEL_AREA TopBar.RIGHT_AREA TopBar.RIGHT_AREA2
     *
     * @param area 相应的areaId
     * @return 相应的RelativeLayout
     */
    public RelativeLayout customizeArea(int area) {
        RelativeLayout tempLayout = getArea(area);
        TopBarHolder tempHolder = (TopBarHolder) tempLayout.getTag();
        tempHolder.imageButton.setVisibility(View.GONE);
        tempHolder.textView.setVisibility(View.GONE);
        return tempLayout;
    }

    /**
     * 设置相应的layout是否显示
     *
     * @param areaId
     * @param enable
     */
    public void showArea(int areaId, boolean enable) {
        RelativeLayout tempLayout = getArea(areaId);
        if (enable) {
            tempLayout.setVisibility(View.VISIBLE);
        } else {
            tempLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示消息的通用方法
     *
     * @param layoutId     指定要显示消息的区域
     * @param messageCount 要显示的消息数，<=0时候不显示
     * @param buttonText   显示消息数的提示信息
     */
    public void showMessageCount(int layoutId, int messageCount,
                                 String buttonText) {
        if (messageCount <= 0) {
            hideMessageCount(layoutId);
            return;
        }
        RelativeLayout layout = getArea(layoutId);
        layout.setVisibility(View.VISIBLE);
        layout.setBackgroundResource(R.color.common_gray);
        TopBarHolder tempHolder = (TopBarHolder) layout.getTag();
        buttonImgCache = tempHolder.imageButton.getBackground();
        tempHolder.imageButton.setBackgroundResource(android.R.color.transparent);
        tempHolder.textView.setText(messageCount + "");
        tempHolder.textView.setVisibility(View.VISIBLE);
        showMessageAnim(layoutId);
    }

    /**
     * 默认新消息的提示文字为“新消息”
     *
     * @param layoutId
     * @param messageCount
     */
    public void showMessageCount(int layoutId, int messageCount) {
        showMessageCount(layoutId, messageCount, "新消息");
    }

    /**
     * 隐藏新消息提示
     *
     * @param layoutId
     */
    @SuppressWarnings("deprecation")
    public void hideMessageCount(int layoutId) {
        RelativeLayout layout = getArea(layoutId);
        layout.setBackgroundResource(R.color.common_transparent);
        TopBarHolder tempHolder = (TopBarHolder) layout.getTag();

        // 这个地方目前是有点小bug，如果既有图片又有文字的话，将会显示图片不显示文字
        if (!TextUtils.isEmpty(buttonTextCache)) {
            tempHolder.imageButton.setBackgroundResource(R.color.common_transparent);
            buttonTextCache = "";
        }
        if (buttonImgCache != null) {
            tempHolder.imageButton.setBackgroundDrawable(buttonImgCache);
            buttonImgCache = null;
        }
        tempHolder.textView.setText("");
        tempHolder.textView.setVisibility(View.GONE);
    }

    public void showMessageCountLeft(int messageCount, String buttonText) {
        showMessageCount(LEFT_AREA, messageCount, buttonText);
    }

    public void showMessageCountRight2(int messageCount, String buttonText) {
        showMessageCount(RIGHT_AREA2, messageCount, buttonText);
    }

    public void showMessageCountRight(int messageCount, String buttonText) {
        showMessageCount(RIGHT_AREA, messageCount, buttonText);
    }

    public void isLeftNewMessageShow(boolean isShow) {
        if (isShow) {
            showNewMessage(LEFT_AREA);
        } else {
            hideNewMessage(LEFT_AREA);
        }
    }

    public void isRightNewMessageShow(boolean isShow) {
        if (isShow) {
            showNewMessage(RIGHT_AREA);
        } else {
            hideNewMessage(RIGHT_AREA);
        }
    }

    public void isRightNewMessage2Show(boolean isShow) {
        if (isShow) {
            showNewMessage(RIGHT_AREA2);
        } else {
            hideNewMessage(RIGHT_AREA2);
        }
    }

    public void showNewMessage(int layoutId) {
        RelativeLayout layout = getArea(layoutId);
        if (layout.getChildAt(TEMP_VIEW_INDEX) == null) {
            ImageView newMessage = new ImageView(context);
            newMessage.setBackgroundResource(R.drawable.icon_topbar_point);
            LayoutParams newMessageParam = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            newMessageParam.addRule(RelativeLayout.ABOVE, R.id.topbar_button);
            newMessageParam
                    .addRule(RelativeLayout.RIGHT_OF, R.id.topbar_button);
            newMessageParam.setMargins(-20, 0, 0, -20);
            newMessage.setLayoutParams(newMessageParam);
            layout.addView(newMessage, TEMP_VIEW_INDEX);
        } else {
            layout.getChildAt(TEMP_VIEW_INDEX).setVisibility(View.VISIBLE);
        }
    }

    public void hideNewMessage(int layoutId) {
        RelativeLayout layout = getArea(layoutId);

        if (layout.getChildAt(TEMP_VIEW_INDEX) != null) {
            layout.getChildAt(TEMP_VIEW_INDEX).setVisibility(View.GONE);
        }

    }

    private void showMessageAnim(int layoutId) {
        RelativeLayout layout = getArea(layoutId);
        TopBarHolder tempHolder = (TopBarHolder) layout.getTag();
        Animation fadeOut = new AlphaAnimation(1.0f, 0.2f);
        fadeOut.setRepeatMode(Animation.REVERSE);
        fadeOut.setRepeatCount(5);
        fadeOut.setDuration(ANIMATION_DURATION);
        tempHolder.imageButton.setAnimation(fadeOut);
        tempHolder.textView.setAnimation(fadeOut);
        fadeOut.start();

    }

    /**
     * 该方法调用后，button中显示的是图片
     */
    public void showButtonImage(int res1, int res2, int res3) {
        showButtonImage(res1, LEFT_AREA);
        showButtonImage(res2, RIGHT_AREA2);
        showButtonImage(res3, RIGHT_AREA);
    }

    /**
     * 设置单个button中的图片
     *
     * @param res      要显示的图片
     * @param layoutId 选取的buttonId
     */
    public void showButtonImage(int res, int layoutId) {
        RelativeLayout tempLayout = getArea(layoutId);
        tempLayout.setBackgroundResource(R.color.common_transparent);
        TopBarHolder holder = (TopBarHolder) tempLayout.getTag();
        if (res > 0) {
            tempLayout.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.VISIBLE);
            holder.imageButton.setImageResource(res);
            holder.textView.setVisibility(View.GONE);
        } else {
            holder.imageButton.setVisibility(View.GONE);
        }
    }

    /**
     * 设置单个button中的图片
     *
     * @param text     要显示的文字
     * @param layoutId 选取的buttonId
     */
    public void showButtonText(String text, int layoutId) {
        RelativeLayout tempLayout = getArea(layoutId);
        tempLayout.setBackgroundResource(R.color.common_transparent);
        TopBarHolder holder = (TopBarHolder) tempLayout.getTag();
        if (!TextUtils.isEmpty(text)) {
            tempLayout.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.GONE);
            holder.textView.setText(text);
            holder.textView.setVisibility(View.VISIBLE);
        }
    }

    public void showButtonText(String text, int layoutId, int colorId) {
        RelativeLayout tempLayout = getArea(layoutId);
        tempLayout.setBackgroundResource(R.color.common_transparent);
        TopBarHolder holder = (TopBarHolder) tempLayout.getTag();
        if (!TextUtils.isEmpty(text)) {
            tempLayout.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.GONE);
            holder.textView.setText(text);
            holder.textView.setTextColor(getContext().getResources().getColor(colorId));
            holder.textView.setVisibility(View.VISIBLE);
        }
    }

    public void showButtonText(String text, int layoutId, int colorId, int drawableResId) {
        RelativeLayout tempLayout = getArea(layoutId);
        tempLayout.setBackgroundResource(R.color.common_transparent);
        TopBarHolder holder = (TopBarHolder) tempLayout.getTag();
        if (!TextUtils.isEmpty(text)) {
            tempLayout.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.GONE);
            holder.textView.setText(text);
            Drawable drawable = context.getResources().getDrawable(drawableResId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.textView.setCompoundDrawables(drawable, null, null, null);
            holder.textView.setCompoundDrawablePadding(DisplayUtil.dp2px(context, 5));
            holder.textView.setTextColor(getContext().getResources().getColor(colorId, null));
            holder.textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * show left button
     */
    public void showLeftButton(boolean isShow) {
        RelativeLayout tempLayout = getArea(LEFT_AREA);
        if (isShow) {
            leftLayout.setVisibility(View.VISIBLE);
        } else {
            leftLayout.setVisibility(View.GONE);
        }
    }

    /**
     * show right button
     */
    public void showRightButton(boolean isShow) {
        RelativeLayout tempLayout = getArea(RIGHT_AREA);
        if (isShow) {
            rightLayout.setVisibility(View.VISIBLE);
        } else {
            rightLayout.setVisibility(View.GONE);
        }
    }

    /**
     * show right2 button
     */
    public void showRight2Button(boolean isShow) {
        RelativeLayout tempLayout = getArea(RIGHT_AREA2);
        if (isShow) {
            rightLayout2.setVisibility(View.VISIBLE);
        } else {
            rightLayout2.setVisibility(View.GONE);
        }
    }

    /**
     * 该方法调用后，button中显示的是文字
     */
    public void showButtonText(String text1, String text2, String text3) {
        showButtonText(text1, LEFT_AREA);
        showButtonText(text2, RIGHT_AREA2);
        showButtonText(text3, RIGHT_AREA);
    }

    /**
     * 设置标题栏旁的按钮是否显示
     */
    public void showTitleSide(boolean enable) {
        if (enable) {
            titleSide.setVisibility(View.VISIBLE);
        } else {
            titleSide.setVisibility(View.GONE);
        }

    }

    /**
     * 设置标题栏旁的按钮的图片
     */
    public void setTitleSideImg(int resId) {
        if (resId > 0) {
            titleSide.setVisibility(View.VISIBLE);
            titleSide.setBackgroundResource(resId);
        }
    }

    public TextView getTitleView() {
        return titleTextView;
    }

    public String getTitle() {
        return titleTextView.getText().toString();
    }

    public void setTitle(CharSequence title) {
        titleTextView.setText(title);
    }

    public void setTitle(CharSequence title, int color) {
        titleTextView.setText(title);
        titleTextView.setTextColor(color);
    }

    public void setTitleCanClick() {
        titleTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                performTitleClick(arg0);
            }
        });
        showTitleSide(true);
    }

    /**
     * 设置topbar的通用背景
     *
     * @param resId resource id
     */
    public void setBackgroundImageRes(int resId) {
        this.setBackgroundResource(resId);
    }

    /**
     * 获取topbar的标题点击事件
     */
    public OnTopBarTitleClickListener getTitleClickListener() {
        return topbarTitleListener;
    }

    /**
     * 设置topbar标题的点击事件
     */
    public void setTitleClickListener(OnTopBarTitleClickListener mTListener) {
        this.topbarTitleListener = mTListener;
    }

    /**
     * 获取topbar的三个按钮点击事件
     */
    public void setButtonClickListener(OnTopBarButtonClickListener mListener) {
        this.topbarButtonListener = mListener;
    }

    public void setLeftButtonClickListener(OnLeftButtonClickListener mListener) {
        this.leftButtonListener = mListener;
    }

    public void setRightButtonClickListener(OnRightButtonClickListener mListener) {
        this.rightButtonListener = mListener;
    }

    /**
     * 获取topbar的三个按钮点击事件
     */
    public OnTopBarButtonClickListener getTopBarButtonClickListener() {
        return topbarButtonListener;
    }

    private void onLayoutClicked(int layoutId) {
        switch (layoutId) {
            case LEFT_AREA:
                performLeftButtonClick(leftLayout);
                break;
            case RIGHT_AREA:
                performRightButtonClick(rightLayout);
                break;
            case RIGHT_AREA2:
                performRightButton2Click(rightLayout2);
                break;
            default:
                break;
        }
    }

    private void performTitleClick(View view) {
        if (topbarTitleListener != null) {
            topbarTitleListener.onTitleClick(view);
        }
    }

    private void performLeftButtonClick(View view) {
        if (topbarButtonListener != null) {
            topbarButtonListener.onLeftButtonClick(view);
        } else if (leftButtonListener != null) {
            leftButtonListener.onLeftButtonClick(view);
        } else {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }

    }

    private void performRightButton2Click(View view) {
        if (topbarButtonListener != null) {
            topbarButtonListener.onRightButton2Click(view);
        } else {
            if (!TextUtils.isEmpty(right2ClassName)) {
                Intent intent = new Intent();
                intent.setClassName(context, right2ClassName);
                context.startActivity(intent);
            }
        }

    }

    private void performRightButtonClick(View view) {
        if (topbarButtonListener != null) {
            topbarButtonListener.onRightButtonClick(view);
        } else if (rightButtonListener != null) {
            rightButtonListener.onRightButtonClick(view);
        } else {
            if (!TextUtils.isEmpty(rightClassName)) {
                Intent intent = new Intent();
                intent.setClassName(context, rightClassName);
                context.startActivity(intent);
            }
        }
    }

}
