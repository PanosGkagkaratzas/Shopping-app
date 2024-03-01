package com.example.bestbeforeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "basket.db";
    public static final String TABLE_NAME = "shopping_basket";
    public static final String COL1 = "ID";
    public static final String COL2 = "BARCODE";
    public static final String COL3 = "NAME";
    public static final String COL4 = "QUANTITY";
    public static final String COL5 = "CATEGORY";
    public static final String COL6 = "EXP_DATE";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT , " + " BARCODE TEXT , NAME TEXT , QUANTITY INTEGER , CATEGORY TEXT , EXP_DATE DATE )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String barcode, String name, Editable quantity, String category, String exp_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, barcode);
        contentValues.put(COL3, name);
        contentValues.put(COL4, String.valueOf(quantity));
        contentValues.put(COL5, category);
        contentValues.put(COL6, exp_date);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(sql, null);
        return data;
    }

    public Cursor showData(String sql2)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery(sql2, null);
        return data;
    }

    public boolean updateData(String id, String name, Editable quantity, String category, String exp_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL3, name);
        contentValues.put(COL4, String.valueOf(quantity));
        contentValues.put(COL5, category);
        contentValues.put(COL6, String.valueOf(exp_date));

        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});

    }
}
