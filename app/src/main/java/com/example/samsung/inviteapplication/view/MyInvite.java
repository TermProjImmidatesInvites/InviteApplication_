package com.example.samsung.inviteapplication.view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyInvite extends SQLiteOpenHelper {

    SQLiteDatabase database;
    SQLiteStatement statement;

    public MyInvite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String  sql) {
        database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, String msg, String usn, byte[] image)
    {
        database = getWritableDatabase();
        String sql = "INSERT INTO Invite VALUES (NULL, ?, ?, ?,?) ";

        statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,msg);
        statement.bindString(3,usn);
        statement.bindBlob(4,image);

        statement.executeInsert();
    }

    public void insertData(String name, String msg, String usn, int image)
    {
        database = getWritableDatabase();
        String sql = "INSERT INTO Invite VALUES (NULL, ?, ?, ?, ?, ?) ";

        statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,msg);
        statement.bindString(3,usn);
        statement.bindString(4," ");
        statement.bindLong(5,image);

        statement.executeInsert();
    }

    public void insertData(String name, String msg, String usn)
    {
        database = getWritableDatabase();
        String sql = "INSERT INTO Invite VALUES (NULL, ?, ?, ?) ";

        statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,msg);
        statement.bindString(3,usn);

        statement.executeInsert();
    }

    public Cursor getData(String sql)
    {
        database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    public void deleteRecord()
    {
        database= getWritableDatabase();
        String query = "DELETE FROM Invite";

        statement = database.compileStatement(query);
        statement.clearBindings();

        statement.execute();
        Toast.makeText(getApplicationContext(), "Data Deleted!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
