package com.example.i5630fnl_i3b47;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MyListAdapter extends ArrayAdapter<SubjectData>{
    private ArrayList<SubjectData> arrayList;
    private Context context;
    public MyListAdapter(Context context, ArrayList<SubjectData> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubjectData subjectData = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.mylist, null);
            TextView title = convertView.findViewById(R.id.title);
            TextView subtitle = convertView.findViewById(R.id.subtitle);
            ImageView image = convertView.findViewById(R.id.image);
            title.setText(subjectData.currency);
            subtitle.setText(subjectData.country);
            image.setImageResource(subjectData.image);
       }
        return convertView;
    }
}