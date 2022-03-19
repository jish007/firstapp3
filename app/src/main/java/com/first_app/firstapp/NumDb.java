package com.first_app.firstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class NumDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "emNUm.db";
    public static final String TABLE_NAME= "Num_table";
    public static final String COL_1= "Number1";
    public static final String COL_2= "Number2";

    public NumDb(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db1){
        db1.execSQL("create table " + TABLE_NAME + " (Number1 INTEGER, Number2 INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {
        db1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db1);
    }

    public Boolean insertNum(String num1, String num2){
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,num1);
        contentValues.put(COL_2,num2);
        long result = db1.insert(TABLE_NAME, null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getData(){

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor cursor = db1.rawQuery("select * from " + TABLE_NAME,null);

        return cursor;

    }
}
