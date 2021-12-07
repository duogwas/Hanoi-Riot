package fithou.duogwas.hanoi_riot.Database;//
// Created by duogwas on 07/12/2021.
//


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HRDBHelper extends SQLiteOpenHelper {
    public HRDBHelper(Context context) {
        super(context, "QuanLyKhoHang.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table User(username TEXT primary key, password TEXT, phoneNumber TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists User");
    }

    //1. Bảng User
    public boolean insertUser (String username, String password, String phoneNumber){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("phoneNumber",phoneNumber);
        long result = MyDB.insert("user","hack",contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUser(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where username = ?",new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where phoneNumber = ?",new String[] {phoneNumber});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUserPassword (String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where username = ? and password = ?",new String[] {username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
