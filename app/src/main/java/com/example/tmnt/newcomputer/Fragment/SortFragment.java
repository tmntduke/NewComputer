package com.example.tmnt.newcomputer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tmnt.newcomputer.Adapter.SortAdapter;
import com.example.tmnt.newcomputer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tmnt on 2016/6/10.
 */
public class SortFragment extends Fragment {
    @Bind(R.id.sort_list)
    ListView mSortList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment_lay, container,false);
        ButterKnife.bind(this, view);

        SortAdapter adapter = new SortAdapter(getActivity(), getTitleSort(), getIconSort());
        mSortList.setAdapter(adapter);

        mSortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public List<String> getTitleSort() {
        List<String> list = new ArrayList<>();
        list.add("android");
        list.add("java");
        list.add("python");
        list.add("c#");
        return list;
    }

    public List<Integer> getIconSort() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.androidicon);
        list.add(R.drawable.javaicon);
        list.add(R.drawable.pythonicon);
        list.add(R.drawable.cicon);
        return list;
    }
}
