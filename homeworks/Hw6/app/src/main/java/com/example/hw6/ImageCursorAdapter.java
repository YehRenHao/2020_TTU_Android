package com.example.hw6;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageCursorAdapter extends CursorAdapter {

    public ImageCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME_FIELD)));
        TextView sexual = (TextView) view.findViewById(R.id.sex);
        sexual.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SEXUAL_FIELD)));
        TextView address = (TextView) view.findViewById(R.id.address);
        address.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ADDRESS_FIELD)));
        byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(DBOpenHelper.IMAGE_FIELD));
        ImageView image = (ImageView) view.findViewById(R.id.image);
        if (imageByte != null) {
            // 因為資料庫裡的圖片是byte[]格式，要轉檔回去
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }
    }
}