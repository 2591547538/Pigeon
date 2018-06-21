package com.pigeon.communication.privacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;


import java.util.List;

import android.content.Context;

import android.widget.Button;

import android.widget.TextView;

public class Ater extends BaseAdapter implements View.OnClickListener  {

    private List<String> lisst;
    private LayoutInflater flater;
    private Back back;

    public Ater(Context context, List<String> list,
                          Back b) {
        lisst= list;
        flater = LayoutInflater.from(context);
        back= b;
    }
    public interface Back {
        public void click(View v);
    }
    @Override
    public int getCount() {

        return lisst.size();
    }

    @Override
    public Object getItem(int i) {

        return lisst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder = null;
        if (view == null) {
            view = flater.inflate(R.layout.list_item, null);
            holder = new Holder();
            holder.textView = (TextView) view.findViewById(R.id.textView1);
            holder.button = (Button) view.findViewById(R.id.b1);
            holder.b = (Button) view.findViewById(R.id.b2);
            holder.b3 =(Button) view.findViewById(R.id.b3);
            holder.b4 =(Button) view.findViewById(R.id.b4);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.textView.setText(lisst.get(i));
        holder.b.setOnClickListener(this);
        holder.b.setTag(i);
        holder.b3.setOnClickListener(this);
        holder.b3.setTag(i);
        holder.b4.setOnClickListener(this);
        holder.b4.setTag(i);
        holder.button.setOnClickListener(this);
        holder.button.setTag(i);
        return view;
    }
    public class Holder {
        public TextView textView;
        public Button button;
        public Button b;
        public Button b3;
        public Button b4;
    }


    @Override
    public void onClick(View v) {
        back.click(v);
    }
}
