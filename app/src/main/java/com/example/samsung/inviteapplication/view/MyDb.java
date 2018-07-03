package com.example.samsung.inviteapplication.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nimra on 5/31/2018.
 */

public class MyDb extends SQLiteOpenHelper {
    public static  final String DBName = "MyDb";
    public static  final int DbVersion = 1;
    public MyDb(Context context) {
        super(context, DBName, null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userTable="Create TABLE User(username VARCHAR, pass VARCHAR, email VARCHAR);";
        db.execSQL(userTable);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
