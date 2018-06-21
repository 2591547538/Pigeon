package com.pigeon.communication.privacy;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapterr extends BaseAdapter implements View.OnClickListener {


    private List<String> mContentList;
    private LayoutInflater mInflaterr;
    private Callback mCallback;


    public interface Callback {
        public void click(View v);
    }

    public Adapterr(Context context, List<String> contentList,
                          Callback callback) {
        mContentList = contentList;
        mInflaterr = LayoutInflater.from(context);
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflaterr.inflate(R.layout.listgroup, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView1gg);
            holder.b11 = (Button) convertView.findViewById(R.id.b11);
            holder.b22 = (Button) convertView.findViewById(R.id.b22);
            holder.b33 = (Button) convertView.findViewById(R.id.b33);
            holder.b44= (Button) convertView.findViewById(R.id.b44);
            holder.b55= (Button) convertView.findViewById(R.id.b55);
            holder.b66= (Button) convertView.findViewById(R.id.b66);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mContentList.get(position));
        holder.b11.setOnClickListener(this);
        holder.b11.setTag(position);
        holder.b22.setOnClickListener(this);
        holder.b22.setTag(position);
        holder.b33.setOnClickListener(this);
        holder.b33.setTag(position);
        holder.b44.setOnClickListener(this);
        holder.b44.setTag(position);

        holder.b55.setOnClickListener(this);
        holder.b55.setTag(position);

        holder.b66.setOnClickListener(this);
        holder.b66.setTag(position);
        return convertView;
    }
    public class ViewHolder {public TextView textView;
        public Button b11;
        public Button b22;
        public Button b33;
        public Button b44;
        public Button b55;
        public Button b66;

    }


    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}

