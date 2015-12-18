package com.example.pruebasandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Jordi on 16/12/2015.
 */
public class Database extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "DBTest.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public Database(Context context, String path, int version) {
        super(context, path, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tabla1 (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200) NOT NULL, numero INTEGER)");

        db.execSQL("CREATE TABLE tabla2 (id INTEGER PRIMARY KEY AUTOINCREMENT, text VARCHAR(200) NOT NULL, tabla1 INTEGER NOT NULL, FOREIGN KEY(tabla1) REFERENCES tabla1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long insertTabla1(String name, int numero) {

        if(existTabla1(name)) return -2;

        long id=-1;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("numero", numero);
        id=db.insert("tabla1", null, values);

        db.close();

        return id;
    }

    public long insertTabla2(String text, int tabla1) {

        if(existTabla2(text)) return -2;

        long id=-1;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("text", text);
        values.put("tabla1", tabla1);
        id=db.insert("tabla2", null, values);

        db.close();

        return id;
    }

    public HashMap<String, Object> getTabla1(int id) {
        HashMap<String, Object> map = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("tabla1",null, "id=?", new String[]{id + ""}, null, null, null, null);

        if (cursor != null && cursor.moveToNext()) {
            map.put("id", cursor.getInt(0));
            map.put("name", cursor.getString(1));
            map.put("numero", cursor.getInt(2));
        }

        db.close();

        return map;
    }

    public HashMap<String, Object> getTabla2(int id) {
        HashMap<String, Object> map = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("tabla2",null, "id=?", new String[]{id + ""}, null, null, null, null);

        if (cursor != null && cursor.moveToNext()) {
            map.put("id", cursor.getInt(0));
            map.put("text", cursor.getString(1));
            map.put("tabla1", cursor.getInt(2));
        }

        db.close();

        return map;
    }

    public boolean existTabla1(String name)
    {
        if(name==null) throw new IllegalArgumentException("No se puede tener el parámetro Name como NULL");

        SQLiteDatabase db = getReadableDatabase();

        int count=db.query("tabla1",null, "name=?", new String[]{ name }, null, null, null, null).getCount();

        db.close();

        return count>0;
    }

    public boolean existTabla2(String text)
    {
        if(text==null) throw new IllegalArgumentException("No se puede tener el parámetro Name como NULL");

        SQLiteDatabase db = getReadableDatabase();

        int count=db.query("tabla2",null, "text=?", new String[]{ text }, null, null, null, null).getCount();

        db.close();

        return count>0;
    }

    public int getTabla1Count()
    {
        SQLiteDatabase db = getReadableDatabase();

        int count=db.query("tabla1",null, null, null, null, null, null, null).getCount();

        db.close();

        return count;
    }

}
