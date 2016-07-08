package com.example.tmnt.newcomputer.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.tmnt.newcomputer.Activity.ExamActivity;
import com.example.tmnt.newcomputer.Activity.TitleSlideActivity;
import com.example.tmnt.newcomputer.Adapter.HomeAdapter;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.UIModel.UIQuestionData;
import com.example.tmnt.newcomputer.ViewHolder.MainViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 显示主页
 * Created by tmnt on 2016/6/6.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.homeList)
    RecyclerView mHomeList;
//    @Bind(R.id.convenientBanner)
//    ConvenientBanner mConvenientBanner;

    private QuestionDAO mDAO;
    private int count;
    public static final String COUNT = "count";
    public static final String ISLOAD = "isLoad";

    public static final String SRCOLL = "SRCOLL";
    public static final String FLAG = "flag";
    private HomeAdapter adapter;

    private static final String TAG = "HomeFragment";

    private int index;
    private boolean load;

    private float scroll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterAnmition();
        mDAO = new QuestionDAO(getActivity());
        load = getArguments().getBoolean(ISLOAD);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_lay, container, false);
        ButterKnife.bind(this, view);
        mHomeList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new HomeAdapter(getQuestionData(), showConvenientBanner(), getActivity());
        adapter.notifyDataSetChanged();
        mHomeList.setAdapter(adapter);

//        mConvenientBanner.setPages(new CBViewHolderCreator() {
//            @Override
//            public Object createHolder() {
//                return new MainViewHolder();
//            }
//        }, showConvenientBanner())
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);


        adapter.showDragWhenLoad(load);
        adapter.setOnItemCardClickListener(new HomeAdapter.OnItemCardClickListener() {
            @Override
            public void longClickEvent(View view, int position) {

            }

            @Override
            public void itemClickEvent(View view, int position) {
                Intent intent = new Intent(getActivity(), ExamActivity.class);
                switch (position) {
                    case 1:
                        intent.putExtra(FLAG, 1);
                        break;
                    case 2:
                        intent.putExtra(FLAG, 2);
                        break;
                    case 3:
                        count = mDAO.queryModelCount() + 1;
                        mDAO.updateModelCount(count);
                        intent.putExtra(FLAG, 3);
                        break;
                    case 4:
                        intent.putExtra(FLAG, 4);
                        break;
                }
                startActivity(intent);
            }
        });


        mHomeList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        MainViewHolder.setOnClickImageListener(new MainViewHolder.OnClickImageListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), TitleSlideActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("position", 0);
                        break;
                    case 1:
                        intent.putExtra("position", 1);
                        break;
                    case 2:
                        intent.putExtra("position", 2);
                        break;
                }
                String transitionName = getString(R.string.share);
                ActivityOptions transitionActivityOptions = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, transitionActivityOptions.toBundle());

                } else {
                    startActivity(intent);
                }
            }

        });


        return view;
    }


    public static HomeFragment getIntance(boolean isLoad) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ISLOAD, isLoad);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG, "onResume: " + adapter);
        //adapter.setSrollo(1500);
//        Log.i(TAG, "onResume: "+ HomeListViewHolder.convenientBanner);
//        HomeListViewHolder.convenientBanner.startTurning(1500);

        //mConvenientBanner.startTurning(1500);
    }

    @Override
    public void onPause() {
        super.onPause();
        // mConvenientBanner.stopTurning();
        // HomeListViewHolder.convenientBanner.stopTurning();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public List<UIQuestionData> getQuestionData() {
        List<UIQuestionData> list = new ArrayList<>();
        list.add(new UIQuestionData(R.drawable.createnew80, "顺序练习", "按照题目顺序进行联系"));
        list.add(new UIQuestionData(R.drawable.design80, "随机练习", "按照题目随机练习"));
        list.add(new UIQuestionData(R.drawable.doughnutchart80, "模拟考试", "按照题目真实进行练习"));
        list.add(new UIQuestionData(R.drawable.content80, "待加强", "重新做错误的题"));
        return list;
    }

    public List<Integer> showConvenientBanner() {
        List<Integer> mList = new ArrayList<>();
        mList.add(R.drawable.zhuan_3);
        mList.add(R.drawable.java);
        mList.add(R.drawable.python);
        return mList;
    }

    public void setEnterAnmition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Explode explode = new Explode();
            explode.setDuration(1500);
            getActivity().getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(1500);
            getActivity().getWindow().setReturnTransition(fade);
        }

    }

}
