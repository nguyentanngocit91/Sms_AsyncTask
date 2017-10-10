package com.example.asus.my_sms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QLSV";
    private static final String TAG = "SQLite";


    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_conact = "CREATE TABLE Contacts( id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(30) NOT NULL, phone CHAR(30) NOT NULL)";
        db.execSQL(sql_conact);

        String sql_message = "CREATE TABLE Message( id INTEGER PRIMARY KEY AUTOINCREMENT, phone CHAR(30) NOT NULL, message CHAR(50))";
        db.execSQL(sql_message);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Message");
        db.execSQL("DROP TABLE IF EXISTS Contacts");
        onCreate(db);
    }



    public void insert(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Contacts (name, phone) VALUES (\"Thắng\", \"0909090901\")");
        db.execSQL("INSERT INTO Contacts (name, phone) VALUES (\"Linh\", \"0909090902\")");
        db.execSQL("INSERT INTO Contacts (name, phone) VALUES (\"Tuấn\", \"0909090903\")");
        db.execSQL("INSERT INTO Contacts (name, phone) VALUES (\"Hải\", \"0909090904\")");
        db.execSQL("INSERT INTO Contacts (name, phone) VALUES (\"Hiếu\", \"0909090905\")");
    }


    public Cursor readMessage() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Message group by phone having max(id) order by id asc",null);
        return cursor;
    }

    public String readName(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String name_ex = phone;
        Cursor cursor = db.rawQuery("Select * from Contacts where phone = '" + phone + "'",null);
        if (cursor != null) {
            if (cursor.moveToFirst())  name_ex = cursor.getString(cursor.getColumnIndex("name"));
        }
        return name_ex;
    }

    public String readPhone(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String name_ex = phone;
        Cursor cursor = db.rawQuery("Select * from Contacts where phone = '" + phone + "'",null);
        if (cursor != null) {
            if (cursor.moveToFirst())  name_ex = cursor.getString(cursor.getColumnIndex("phone"));
        }
        return name_ex;
    }

    public String readPhoneToName(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String name_ex = phone;
        Cursor cursor = db.rawQuery("Select * from Contacts where name = '" + phone + "'",null);
        if (cursor != null) {
            if (cursor.moveToFirst())  name_ex = cursor.getString(cursor.getColumnIndex("phone"));
        }
        return name_ex;
    }

    public Cursor getMessage(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Message where phone = '" + phone + "' order by id desc",null);
        return cursor;
    }

    public String getContact(String phone) {
        String name = phone;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Contacts where phone ='"+phone+"' ",null);
        if (cursor != null) {
            if (cursor.moveToFirst())  name = cursor.getString(cursor.getColumnIndex("name"));
        }
        return name;
    }

    public void insert_message(String phone,String mes){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Message (phone, message) VALUES (\""+phone+"\", \""+mes+"\")");
        Log.i(TAG, "Đã chèn thêm "+"INSERT INTO Message (phone, message) VALUES (\""+phone+"\", \""+mes+"\")");
    }
}