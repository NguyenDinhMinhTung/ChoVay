package com.example.megas.chovay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GroupDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATA";
    public static final String DB_TABLE_NAME = "group_table";
    public static final String DB_GROUP_ID = "group_id";
    public static final String DB_GROUP_NAME = "group_name";

    public GroupDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" + DB_GROUP_ID + " INTEGER PRIMARY KEY, " + DB_GROUP_NAME + " NVARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void createTableIfNotExists() {
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + "(" + DB_GROUP_ID + " INTEGER PRIMARY KEY, " + DB_GROUP_NAME + " NVARCHAR(100))");
    }

    public void insert(GroupItem item) {
        createTableIfNotExists();

        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_GROUP_ID, item.getGroupID());
        contentValues.put(DB_GROUP_NAME, item.getGroupName());
        database.insert(DB_TABLE_NAME, null, contentValues);

        database.close();
    }

    public long getNewLocalID() {
        createTableIfNotExists();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_GROUP_ID + " DESC", null);

        cursor.moveToFirst();

        long id = 1;
        if (!cursor.isAfterLast()) {
            id = cursor.getLong(cursor.getColumnIndex(DB_GROUP_ID)) + 1;
        }

        database.close();
        return id;
    }

    public ArrayList<GroupItem> getData() {
        ArrayList<GroupItem> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long groupID = cursor.getLong(cursor.getColumnIndex(DB_GROUP_ID));
            String groupName = cursor.getString(cursor.getColumnIndex(DB_GROUP_NAME));
            list.add(new GroupItem(groupID, groupName));
            cursor.moveToNext();
        }

        return list;
    }

    public void delete(long id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(DB_TABLE_NAME, DB_GROUP_ID + " =?", new String[]{String.valueOf(id)});

        database.close();
    }
}
