package com.example.megas.chovay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MoneyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATA";
    public static final String DB_TABLE_NAME = "money";
    public static final String DB_MAIN_ID = "main_id";
    public static final String DB_LOCAL_ID = "local_id";
    public static final String DB_MONEY = "money";
    public static final String DB_NOTE = "note";
    public static final String DB_STATE = "state";

    public MoneyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_NAME + "(" + DB_LOCAL_ID + " INTEGER PRIMARY KEY, "
                + DB_MAIN_ID + " INTEGER, " + DB_MONEY + " INTEGER, " + DB_NOTE + " NVARCHAR(100), " + DB_STATE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void createTableIfNotExists() {
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + "(" + DB_LOCAL_ID + " INTEGER PRIMARY KEY, "
                + DB_MAIN_ID + " INTEGER, " + DB_MONEY + " INTEGER, " + DB_NOTE + " NVARCHAR(100), " + DB_STATE + " INTEGER)");
    }

    public void insert(MoneyItem item) {
        createTableIfNotExists();

        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_MONEY, item.getMoney());
        contentValues.put(DB_MAIN_ID, item.getMainID());
        contentValues.put(DB_NOTE, item.getNote());
        contentValues.put(DB_LOCAL_ID, item.getLocalID());
        contentValues.put(DB_STATE, item.getState());

        database.insert(DB_TABLE_NAME, null, contentValues);

        database.close();
    }

    public long getNewLocalID() {
        createTableIfNotExists();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " ORDER BY " + DB_LOCAL_ID + " DESC", null);

        cursor.moveToFirst();

        long id = 1;
        if (!cursor.isAfterLast()) {
            id = cursor.getLong(cursor.getColumnIndex(DB_LOCAL_ID)) + 1;
        }

        database.close();
        return id;
    }

    public long getMoney(long itemID) {
        createTableIfNotExists();

        long sum = 0;

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long id = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID));
            int state = cursor.getInt(cursor.getColumnIndex(DB_STATE));

            if (id == itemID && state == 1) {
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
            long mainID = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID));
            if (mainID == itemID) {
                long localID = cursor.getLong(cursor.getColumnIndex(DB_LOCAL_ID));
                long money = cursor.getLong(cursor.getColumnIndex(DB_MONEY));
                String note = cursor.getString(cursor.getColumnIndex(DB_NOTE));
                int state = cursor.getInt(cursor.getColumnIndex(DB_STATE));

                MoneyItem item = new MoneyItem(mainID, localID, money, note, state);
                list.add(item);
            }
            cursor.moveToNext();
        }

        return list;
    }

    public void setToPaid(long itemID) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            long mainID = cursor.getLong(cursor.getColumnIndex(DB_MAIN_ID));
            if (mainID == itemID) {
                long localID = cursor.getLong(cursor.getColumnIndex(DB_LOCAL_ID));
                long money = cursor.getLong(cursor.getColumnIndex(DB_MONEY));
                String note = cursor.getString(cursor.getColumnIndex(DB_NOTE));
                int state = 0;

                MoneyItem item = new MoneyItem(mainID, localID, money, note, state);
                deleteByLocalId(localID);
                insert(item);
            }
            cursor.moveToNext();
        }
    }

    public void deleteByMainId(long id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(DB_TABLE_NAME, DB_MAIN_ID + " =?", new String[]{String.valueOf(id)});

        database.close();
    }

    public void deleteByLocalId(long id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(DB_TABLE_NAME, DB_LOCAL_ID + " =?", new String[]{String.valueOf(id)});

        database.close();
    }
}
