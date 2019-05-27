package com.example.test.xyz;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<Data> {
    Context mContext;
    int mResource;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String n=getItem(position).getNam();
        String p=getItem(position).getPhon();
        Data d=new Data(n,p);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);
        TextView t=convertView.findViewById(R.id.textView1);
        TextView t2=convertView.findViewById(R.id.textView2);
        t.setText(n);
        t2.setText(p);
        return convertView;
    }


}
