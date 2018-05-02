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
    public static final String DB_MAIN_ID = "main_id";
    public static final String DB_GROUP_ID = "group_id";
    public static final String DB_NAME = "name";

    public MainDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" + DB_MAIN_ID + " INTEGER PRIMARY KEY, " + DB_NAME + " NVARCHAR(100), " + DB_GROUP_ID + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void insert(MainItem item) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_NAME, item.getName());
        contentValues.put(DB_MAIN_ID, item.getId());
        contentValues.put(DB_GROUP_ID, item.getGroupID());
        database.insert(DB_TABLE_NAME, null, contentValues);

        database.close();
    }

    public long getNewID() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_MAIN_ID + " DESC", null);

        cursor.moveToFirst();

        long id = 1;
        if (!cursor.isAfterLast()) {
            id = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID)) + 1;
        }

        database.close();
        return id;
    }

    public ArrayList<MainItem> getDataByGroupID(long groupID) {
        ArrayList<MainItem> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_GROUP_ID + " = " + groupID, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long gID = cursor.getLong(cursor.getColumnIndex(DB_GROUP_ID));
                long id = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DB_NAME));

                MainItem item = new MainItem(id, name, gID);
                list.add(item);

            cursor.moveToNext();
        }

        database.close();
        return list;
    }

    public long findGroupIDByName(String name) {
        long result = 0;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_NAME + " = '" + name+"'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String nme = cursor.getString(cursor.getColumnIndex(DB_NAME));
            if (nme.compareTo(name) == 0) {
                result = cursor.getLong(cursor.getColumnIndex(DB_GROUP_ID));
            }

            cursor.moveToNext();
        }

        database.close();
        return result;
    }

    public ArrayList<MainItem> getData() {
        ArrayList<MainItem> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_MAIN_ID, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID));
            long groupID = cursor.getLong(cursor.getColumnIndex(DB_GROUP_ID));
            String name = cursor.getString(cursor.getColumnIndex(DB_NAME));
            MainItem item = new MainItem(id, name, groupID);
            list.add(item);
            cursor.moveToNext();
        }

        return list;
    }

    public void delete(long id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(DB_TABLE_NAME, DB_MAIN_ID + " =?", new String[]{String.valueOf(id)});

        database.close();
    }
}
