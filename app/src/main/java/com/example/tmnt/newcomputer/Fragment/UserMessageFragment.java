package com.example.tmnt.newcomputer.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmnt.newcomputer.Activity.AboutActivity;
import com.example.tmnt.newcomputer.Activity.ShowUIconActivity;
import com.example.tmnt.newcomputer.Activity.TotalActivity;
import com.example.tmnt.newcomputer.Activity.WrongListActivity;
import com.example.tmnt.newcomputer.Adapter.UserMsgItemAdapter;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/6/6.
 */
public class UserMessageFragment extends Fragment {

    private static final String QUERTION = "question";
    private static final String NAME = "name";
    public static final String PATH = "path";

    @Bind(R.id.user_meg_list)
    RecyclerView mUserMegList;
    @Bind(R.id.floatButton)
    FloatingActionButton mFloatButton;

    private ArrayList<String> mArrayList;
    private String user, path;

    private QuestionDAO mDAO;

    private static final String TAG = "UserMessageFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArrayList = getArguments().getStringArrayList(QUERTION);
        user = getArguments().getString(NAME);
        path = getArguments().getString(PATH);
        mDAO = QuestionDAO.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_message_lay, container, false);
        ButterKnife.bind(this, view);

//        UserMsgAdapter adapter = new UserMsgAdapter(getActivity().getApplicationContext(), mArrayList);
//
//        View view2 = inflater.inflate(R.layout.user_meg_list_header, null, false);
//
//
//        mUserMegList.addHeaderView(view2);
//
//
//        mUserMegList.setAdapter(adapter);
//
//        ImageView userIcon = (ImageView) view2.findViewById(R.id.user_msg_icon);
//        TextView userName = (TextView) view2.findViewById(R.id.user_msg_name);
//
//        userIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ShowUIconActivity.class);
//                intent.putExtra(PATH,path);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), userIcon, getString(R.string.share));
//                    startActivity(intent, options.toBundle());
//                }
//
//            }
//        });
//
//        userName.setText(user);
//        userIcon.setImageBitmap(ImageUtils.readBitMap(getActivity(), path));

        mUserMegList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        UserMsgItemAdapter adapter = new UserMsgItemAdapter(setIconMsg(), setTitleMsg()
//                , getActivity(), path, user, "一级等考", mDAO.queryUserIcon(user));

        UserMsgItemAdapter adapter = new UserMsgItemAdapter.Bulider(setIconMsg(), setTitleMsg(), getActivity())
                .setPath(path)
                .setName(user)
                .setSub("一级等考")
                .isIcons(mDAO.queryUserIcon(user))
                .bulid();


        adapter.notifyDataSetChanged();

        mUserMegList.setAdapter(adapter);

        adapter.setOnclickUserMsgListener(new UserMsgItemAdapter.OnclickUserMsgListener() {
            @Override
            public void onClickMsgItem(View v, int position) {
                switch (position) {
                    case 1:
                        Log.i(TAG, "onClickMsgItem: start");
                        Intent intent = new Intent(getActivity(), WrongListActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent1 = new Intent(getActivity(), TotalActivity.class);
                        startActivity(intent1);
                        break;

                }
            }

            @Override
            public void turnToShow(View v) {
                Intent intent = new Intent(getActivity(), ShowUIconActivity.class);
                intent.putExtra(PATH, user);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity()
                            , v, getString(R.string.share));
                    startActivity(intent, options.toBundle());
                }
            }
        });

        mFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(getActivity()
                            , mFloatButton, mFloatButton.getTransitionName());
                    startActivity(new Intent(getActivity(), AboutActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(getActivity(), AboutActivity.class));
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

    public static UserMessageFragment newInstance(ArrayList<String> list, String name, String iconPath) {
        UserMessageFragment fragment = new UserMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUERTION, list);
        bundle.putSerializable(NAME, name);
        bundle.putSerializable(PATH, iconPath);
        fragment.setArguments(bundle);
        return fragment;
    }

    public List<Integer> setIconMsg() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(R.drawable.delete80);
        list1.add(R.drawable.total);
        return list1;
    }

    public List<String> setTitleMsg() {
        List<String> list1 = new ArrayList<>();
        list1.add("我的错题");
        list1.add("统计");
        return list1;
    }

}
