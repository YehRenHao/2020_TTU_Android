package com.example.mid410606247;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] title;
    private final String[] subtitle;
    private final Integer[] image;

    public MyListAdapter(Activity context, String[] title, String[] subtitle, Integer[] image) {
        super(context, R.layout.mylist, title);

        this.context = context;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(title[position]);
        imageView.setImageResource(image[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}
