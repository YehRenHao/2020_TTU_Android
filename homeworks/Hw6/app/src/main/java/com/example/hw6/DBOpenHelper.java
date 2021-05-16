package com.example.hw6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String _TableName = "friend";
    final static String ID_FIELD = "_id";
    final static String NAME_FIELD = "NAME";
    final static String SEXUAL_FIELD = "SEX";
    final static String ADDRESS_FIELD = "ADDRESS";
    final static String IMAGE_FIELD = "IMAGE";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                ID_FIELD + " integer PRIMARY KEY AUTOINCREMENT, " +
                NAME_FIELD + " text," +
                SEXUAL_FIELD + " text, " +
                ADDRESS_FIELD + " text, " +
                IMAGE_FIELD + " binary )";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);
    }

    // 增加資料
    long add(String name, String sex, String address, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_FIELD, name);
        values.put(SEXUAL_FIELD, sex);
        values.put(ADDRESS_FIELD, address);
        values.put(IMAGE_FIELD, image);
        long result = db.insert(_TableName, null, values);

        return result;
    }

    // 更新資料
    long update(String name, String sexual, String address, byte[] image, String whereClause) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (name != null) values.put(NAME_FIELD, name);
        if (sexual != null) values.put(SEXUAL_FIELD, sexual);
        if (address != null) values.put(ADDRESS_FIELD, address);
        if (image != null) {
            values.put(IMAGE_FIELD, image);
            System.out.println("AFTER" + values);
        }

        long result = db.update(_TableName, values, whereClause, null);
        db.close();
        return result;
    }

    // 刪除資料
    int delete(String _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(_TableName, ID_FIELD + " =" + _id, null);
        db.close();
        return result;
    }

    //查詢所有資料
    Cursor selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        c = db.rawQuery("SELECT * FROM " + _TableName, null);
        return c;
    }

    //查詢某筆資料
    Cursor selectData(String id, String name, String sexual, String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        if (!id.equals("")){
            c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "_id='" + id + "'", null);
        }
        if (!name.equals("")) {
            c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "name='" + name + "'", null);
        } else if (!sexual.equals("")) {
            c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "sex = '" + sexual + "'", null);
        } else if (!address.equals("")) {
            c = db.rawQuery("SELECT * FROM " + _TableName + " WHERE " + "address = '" + address + "'", null);
        }
        return c;
    }
}