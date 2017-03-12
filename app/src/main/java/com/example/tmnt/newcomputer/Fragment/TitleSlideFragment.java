package com.example.tmnt.newcomputer.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tmnt on 2016/6/7.
 */
public class TitleSlideFragment extends Fragment {

    private final static String TITLE = "title";
    private final static String TEXT = "text";
    private final static String RES = "resId";
    private final static String POSITION = "position";


    @Bind(R.id.main_iv_placeholder)
    ImageView mMainIvPlaceholder;
    @Bind(R.id.main_ll_title_container)
    LinearLayout mMainLlTitleContainer;
    @Bind(R.id.main_fl_title)
    FrameLayout mMainFlTitle;
    @Bind(R.id.main_abl_app_bar)
    AppBarLayout mMainAblAppBar;
    @Bind(R.id.main_tv_toolbar_title)
    TextView mMainTvToolbarTitle;
    @Bind(R.id.main_tb_toolbar)
    Toolbar mMainTbToolbar;
    @Bind(R.id.title1)
    TextView mTitle1;
    @Bind(R.id.text)
    TextView mText;
    @Bind(R.id.title_icon)
    CircleImageView mTitleIcon;

    private int position;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.85f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.55f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        position = getArguments().getInt(POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.title_slide_lay, container, false);
        ButterKnife.bind(this, view);
        mMainTbToolbar.setTitle("");

        if (position == 0) {
            mTitle1.setText("Android");
            mMainFlTitle.setBackgroundColor(getResources().getColor(R.color.android));
            mMainTbToolbar.setBackgroundColor(getResources().getColor(R.color.android));
            //mMainTbToolbar.setTitle("Android");
            mText.setText(getResources().getString(R.string.androidShow));
            mMainIvPlaceholder.setImageResource(R.drawable.zhuan_2);
            mTitleIcon.setImageResource(R.drawable.androidicon);
            mMainTvToolbarTitle.setText("Android");
        } else if (position == 1) {
            mMainFlTitle.setBackgroundColor(getResources().getColor(R.color.java));
            mMainTbToolbar.setBackgroundColor(getResources().getColor(R.color.java));
            //mMainTbToolbar.setTitle("java");
            mTitle1.setText("java");
            mMainTvToolbarTitle.setText("java");
            mText.setText(getResources().getString(R.string.javaShow));
            mMainIvPlaceholder.setImageResource(R.drawable.java);
            mTitleIcon.setImageResource(R.drawable.javaicon);
        } else if (position == 2) {
            mMainFlTitle.setBackgroundColor(getResources().getColor(R.color.python));
            mMainTbToolbar.setBackgroundColor(getResources().getColor(R.color.python));
            //mMainTbToolbar.setTitle("python");
            mTitle1.setText("python");
            mText.setText(getResources().getString(R.string.pythonShow));
            mMainIvPlaceholder.setImageResource(R.drawable.python);
            mTitleIcon.setImageResource(R.drawable.pythonicon);
            mMainTvToolbarTitle.setText("python");
        }

        mMainAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });

        initParallaxValues(); // 自动滑动效果



        return view;
    }

    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mMainIvPlaceholder.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mMainFlTitle.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mMainIvPlaceholder.setLayoutParams(petDetailsLp);
        mMainFlTitle.setLayoutParams(petBackgroundLp);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mMainTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mMainTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }

    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mMainLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mMainLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static TitleSlideFragment newInstance(int position) {
        TitleSlideFragment fragment = new TitleSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
