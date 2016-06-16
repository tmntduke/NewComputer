package com.example.tmnt.newcomputer.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tmnt.newcomputer.Activity.SortActivity;
import com.example.tmnt.newcomputer.Adapter.SortAdapter;
import com.example.tmnt.newcomputer.R;
import com.example.tmnt.newcomputer.Utils.Utils;

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
    private boolean isMoblie;
    private Snackbar snackbar;
    private Intent randomIntent;
    private Snackbar snackbar1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment_lay, container, false);
        ButterKnife.bind(this, view);

        SortAdapter adapter = new SortAdapter(getActivity(), getTitleSort(), getIconSort());
        adapter.notifyDataSetChanged();
        mSortList.setAdapter(adapter);

        mSortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Utils.isConnected(getActivity())) {


                    if (Utils.getNetWorkStatus(getActivity()) == 3 || Utils.getNetWorkStatus(getActivity()) == 4) {

                        snackbar = Snackbar.make(view, "you are  open the MobileConnected,are you continue", Snackbar.LENGTH_INDEFINITE).setAction("click", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isMoblie = true;
                                snackbar.dismiss();
                            }
                        });
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                        snackbar.show();


                    } else if (Utils.isWifiConnected(getActivity())) {
                        isMoblie = true;
                        randomIntent = new Intent(getActivity(), SortActivity.class);
                        switch (position) {
                            case 0:
                                turnNext(0);
                                break;
                            case 1:
                                turnNext(1);
                                break;

                            default:
                                break;
                        }

                    } else if (isMoblie) {
                        randomIntent = new Intent(getActivity(), SortActivity.class);
                        switch (position) {
                            case 0:
                                turnNext(0);
                                break;
                            case 1:
                                turnNext(1);
                                break;

                            default:
                                break;
                        }
                    }


                } else {

                    snackbar1 = Snackbar.make(view, "you are not open the network,click turn to setting", Snackbar.LENGTH_INDEFINITE).setAction("click", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.openSetting(getActivity());

                        }
                    });
                    snackbar1.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar1.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar1.show();
                }

            }

        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (snackbar1 != null) {
            snackbar1.dismiss();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public List<String> getTitleSort() {
        List<String> list = new ArrayList<>();
        list.add("选择");
        list.add("填空");
        return list;
    }

    public List<Integer> getIconSort() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.select);
        list.add(R.drawable.fill_blank);

        return list;
    }

    public void turnNext(int flag) {
        if (isMoblie) {
            randomIntent.putExtra("flag1", flag);
            startActivity(randomIntent);
        }

    }
}
