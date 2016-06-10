package com.example.tmnt.newcomputer.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.tmnt.newcomputer.Activity.TitleSlideActivity;
import com.example.tmnt.newcomputer.Adapter.HomeAdapter;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.UIModel.UIQuestionData;
import com.example.tmnt.newcomputer.Utils.Utils;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterAnmition();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_lay, container, false);
        ButterKnife.bind(this, view);
        mHomeList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        HomeAdapter adapter = new HomeAdapter(getQuestionData(), showConvenientBanner(), getActivity());
        mHomeList.setAdapter(adapter);
        adapter.setOnItemCardClickListener(new HomeAdapter.OnItemCardClickListener() {
            @Override
            public void longClickEvent(View view, int position) {

            }

            @Override
            public void itemClickEvent(View view, int position) {
                Utils.showToast(getActivity(), "start");
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
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity());
                    startActivity(intent, transitionActivityOptions.toBundle());

                } else {
                    startActivity(intent);
                }
            }

        });


        return view;
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Explode explode = new Explode();
            explode.setDuration(2000);
            getActivity().getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getActivity().getWindow().setReturnTransition(fade);
        }

    }

}
