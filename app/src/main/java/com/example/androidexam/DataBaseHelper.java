package com.example.androidexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table tbl_student(" +
                "   sno text(8) primary key," +
                "   sname text(20) not null," +
                "   syear integer not null," +
                "   gender text(1) not null," +
                "   major text(20) not null," +
                "   score integer(3) not null" +
                "   )";
        sqLiteDatabase.execSQL(sql);
        Log.d("TAG", "onCreate: 성공");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
