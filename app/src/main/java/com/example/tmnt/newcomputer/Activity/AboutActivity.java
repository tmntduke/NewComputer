package com.example.tmnt.newcomputer.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DView.GuiUtils;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/6/14.
 */
public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.other_fab_circle)
    FloatingActionButton mOtherFabCircle;
    @Bind(R.id.other_tv_container)
    TextView mOtherTvContainer;
    @Bind(R.id.other_iv_close)
    ImageView mOtherIvClose;
    @Bind(R.id.relative)
    RelativeLayout mRelative;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(AboutActivity.this, true);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOtherPrimaryDark));
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
        setContentView(R.layout.about_lay);
        ButterKnife.bind(this);


        setupEnterAnimation(); // 入场动画
        //animationShow();
        setupExitAnimation();

        mOtherIvClose.setOnClickListener((v) ->
                onBackPressed()
        );
    }


    private void setupEnterAnimation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(AboutActivity.this).inflateTransition(R.transition.arc_transition);
            getWindow().setSharedElementEnterTransition(transition);
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {

                    transition.removeListener(this);
                    animationShow();

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            initViews();
        }

    }

    private void setupExitAnimation() {
        Fade fade = new Fade();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setReturnTransition(fade);
        }
        fade.setDuration(300);
    }


    private void animationShow() {
        GuiUtils.animateRevealShow(AboutActivity.this, mRelative, mOtherFabCircle.getWidth() / 2, R.color.colorOtherPrimaryDark, new GuiUtils.OnRevealAnimationListener() {
            @Override
            public void onRevealHide() {

            }

            @Override
            public void onRevealShow() {
                initViews();
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GuiUtils.animateRevealHide(AboutActivity.this, mRelative, mOtherFabCircle.getWidth() / 2, R.color.colorOtherPrimaryDark, true, new GuiUtils.OnRevealAnimationListener() {
                @Override
                public void onRevealHide() {
                    defaultBack();
                }

                @Override
                public void onRevealShow() {

                }
            });
        } else {
            defaultBack();
        }
    }

    private void defaultBack() {
        super.onBackPressed();
    }

    private void initViews() {
        new Handler(Looper.getMainLooper()).post(() -> {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            animation.setDuration(300);
            mOtherTvContainer.startAnimation(animation);
            mOtherIvClose.setAnimation(animation);
            mOtherIvClose.setVisibility(View.VISIBLE);
            mOtherTvContainer.setVisibility(View.VISIBLE);
        });
    }
}
