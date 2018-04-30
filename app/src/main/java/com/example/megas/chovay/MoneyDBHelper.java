package com.example.megas.chovay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MoneyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATA2";
    public static final String DB_TABLE_NAME = "money";
    public static final String DB_ID = "id";
    public static final String DB_MONEY = "money";
    public static final String DB_NOTE = "note";

    public MoneyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" + DB_ID + " INTEGER, " + DB_MONEY + " INTEGER, " + DB_NOTE + " NVARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(MoneyItem item) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_MONEY, item.getMoney());
        contentValues.put(DB_ID, item.getId());
        contentValues.put(DB_NOTE, item.getNote());

        database.insert(DB_TABLE_NAME, null, contentValues);

        database.close();
    }

    public long getMoney(long itemID) {
        long sum = 0;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(DB_ID));
            if (id == itemID) {
                long money = cursor.getLong(cursor.getColumnIndex(DB_MONEY));
                sum += money;
            }
            cursor.moveToNext();
        }

        database.close();
        return sum;
    }

    public ArrayList<MoneyItem> getData(long itemID) {
        ArrayList<MoneyItem> list = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(DB_ID));
            if (id == itemID) {
                long money = cursor.getLong(cursor.getColumnIndex(DB_MONEY));
                String note = cursor.getString(cursor.getColumnIndex(DB_NOTE));
                MoneyItem item = new MoneyItem(id, money, note);
                list.add(item);
            }
            cursor.moveToNext();
        }

        return list;

    }
}
