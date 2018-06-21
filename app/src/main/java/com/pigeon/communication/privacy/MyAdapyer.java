package com.pigeon.communication.privacy;



import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapyer  extends BaseAdapter {
    private static final int MSG = 1;
    private static final int IMG = 2;

    private List<Object> data = new ArrayList<Object>();

    private Context context;
    private LayoutInflater inflater;

    public MyAdapyer(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public void addString(String string) {
        data.add(string);
    }




    @Override
    public int getItemViewType(int position) {
        int type;
        if (data.get(position) instanceof Bitmap) {
            type = IMG;
        } else {
            type = MSG;
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == MSG) {
            convertView = inflater.inflate(R.layout.itemmsg, null);
            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            tv.setText((String) data.get(position));
        } else if (type == IMG) {

        }
        return convertView;
    }
}

