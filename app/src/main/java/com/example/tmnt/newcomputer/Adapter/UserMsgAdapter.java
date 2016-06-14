package com.example.tmnt.newcomputer.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tmnt.newcomputer.DView.SlideView;
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

    public void deleteData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
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
                view = LayoutInflater.from(mContext).inflate(R.layout.wrong_list_lay, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) view.findViewById(R.id.text);
                viewHolder.delete = (ImageView) view.findViewById(R.id.delete);
                viewHolder.mSlideView = (SlideView) view.findViewById(R.id.wrong_contain);
                viewHolder.mTextViewNo = (TextView) view.findViewById(R.id.textNo);
                viewHolder.content = (LinearLayout) view.findViewById(R.id.mContentView);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.mTextViewNo.setText(String.valueOf(position + 1) + ".");
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickSlideItemListener != null) {
                        mOnClickSlideItemListener.clickDelete(v, mList.get(position), position);
                    }
                }
            });

            SharedPreferences.Editor editor = mContext.getSharedPreferences("isOpen", Context.MODE_PRIVATE).edit();
            editor.putBoolean("isOpen", viewHolder.mSlideView.isOpen()).commit();

                viewHolder.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnClickSlideItemListener != null) {
                            mOnClickSlideItemListener.onClickQurstionItem(v, position, viewHolder.mSlideView.isOpen());
                        }
                    }
                });

            viewHolder.mTextView.setText(mList.get(position));
        }
        return view;
    }

    private OnClickSlideItemListener mOnClickSlideItemListener;

    public void setOnClickSlideItemListener(OnClickSlideItemListener mOnClickSlideItemListener) {
        this.mOnClickSlideItemListener = mOnClickSlideItemListener;
    }

    public interface OnClickSlideItemListener {
        void clickDelete(View view, String question, int position);

        void recover(int position);

        void onClickQurstionItem(View v, int position, boolean isOpen);
    }

    class ViewHolder {
        public TextView mTextView;
        public ImageView delete;
        public LinearLayout content;
        public SlideView mSlideView;
        public TextView mTextViewNo;
    }

}
