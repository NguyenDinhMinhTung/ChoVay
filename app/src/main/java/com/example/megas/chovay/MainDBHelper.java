package com.example.megas.chovay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by megas on 2018/04/28.
 */

public class MainDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATA";
    public static final String DB_TABLE_NAME = "main";
    public static final String DB_ID = "id";
    public static final String DB_NAME = "name";

    public MainDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" + DB_ID + " INTEGER PRIMARY KEY, " + DB_NAME + " NVARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(MainItem item) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_NAME, item.getName());
        contentValues.put(DB_ID, item.getId());

        database.insert(DB_TABLE_NAME, null, contentValues);

        database.close();
    }

    public long getNewID() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_ID + " DESC", null);

        cursor.moveToFirst();

        long id = 1;
        if (!cursor.isAfterLast()) {
            id = cursor.getLong(cursor.getColumnIndex(DB_ID)) + 1;
        }

        database.close();
        return id;
    }

    public ArrayList<MainItem> getData() {
        ArrayList<MainItem> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_ID, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(DB_ID));
            String name = cursor.getString(cursor.getColumnIndex(DB_NAME));
            MainItem item = new MainItem(id, name);
            list.add(item);
            cursor.moveToNext();
        }

        return list;

    }
}
