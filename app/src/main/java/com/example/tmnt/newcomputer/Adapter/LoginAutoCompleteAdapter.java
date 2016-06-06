package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.tmnt.newcomputer.R;

import java.util.List;

/**
 * Created by tmnt on 2016/6/4.
 */
public class LoginAutoCompleteAdapter extends ArrayAdapter {

    private List<String> mList;
    private Context mContext;

    public LoginAutoCompleteAdapter(Context context, int resource, List<String>list) {
        super(context, resource, list);
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_autocomplete_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.tv_autocomplete);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText((CharSequence) getItem(position));
        return view;
    }


    class ViewHolder {
        TextView text;
    }
}