package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DView.CircleImageView;
import com.example.tmnt.newcomputer.R;

import java.util.List;

/**
 * 分类信息
 * Created by tmnt on 2016/6/10.
 */
public class SortAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mStringList;
    private List<Integer> mList;

    public SortAdapter(Context context, List<String> stringList, List<Integer> list) {
        mContext = context;
        mStringList = stringList;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.sort_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mCircleImageView = (CircleImageView) view.findViewById(R.id.sort_icon);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.sort_title);
            viewHolder.mLinearLayout = (LinearLayout) view.findViewById(R.id.sort_item_lay);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mCircleImageView.setImageResource(mList.get(position));
        viewHolder.mTextView.setText(mStringList.get(position));

        //不同item 点击后的背景不同
        if (position == 0) {
            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.selecter_android);
        } else if (position == 1) {
            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.selector_java);
        } else if (position == 2) {
            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.selector_python);
        } else if (position == 3) {
            viewHolder.mLinearLayout.setBackgroundResource(R.drawable.seletor_c);
        }
        return view;
    }

    class ViewHolder {
        TextView mTextView;
        CircleImageView mCircleImageView;
        LinearLayout mLinearLayout;
    }
}
