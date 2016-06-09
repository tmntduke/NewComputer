package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.Model.Questions;
import com.example.tmnt.newcomputer.R;

import java.util.List;

/**
 * Created by tmnt on 2016/6/8.
 */
public class UserMsgAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public UserMsgAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        if (mList.size() != 0) {
            return mList.size();
        } else {
            return 1;
        }

    }

    @Override
    public Object getItem(int position) {
        if (mList.size() != 0) {
            return mList.get(position);
        } else {
            return 1;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        if (mList.size() != 0) {
            if (convertView == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.user_msg_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) view.findViewById(R.id.text);
                viewHolder.delete = (ImageView) view.findViewById(R.id.delete);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickSlideItemListener != null) {
                        mOnClickSlideItemListener.clickDelete();
                    }
                }
            });

            viewHolder.mTextView.setText(mList.get(position));
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.user_msg_empty, parent, false);
        }

        return view;
    }

    private OnClickSlideItemListener mOnClickSlideItemListener;

    public void setOnClickSlideItemListener(OnClickSlideItemListener mOnClickSlideItemListener) {
        this.mOnClickSlideItemListener = mOnClickSlideItemListener;
    }

    public interface OnClickSlideItemListener {
        void clickDelete();
    }

    class ViewHolder {
        public TextView mTextView;
        public ImageView delete;
        public LinearLayout content;
    }

}
