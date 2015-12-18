package com.example.pruebasandroid.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Jordi on 16/12/2015.
 */
public class Provider extends ContentProvider {

    public static final String AUTHORITY="com.example.pruebasandroid.provider";
    public static final Uri MAIN_URL=Uri.parse("content://"+AUTHORITY);

//    public static final Uri CONTENT_URI = Uri.parse(MAIN_URL);


    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Database database;

    @Override
    public boolean onCreate() {
        uriMatcher.addURI(AUTHORITY,"tabla1",1);
        database=new Database(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db=database.getReadableDatabase();

        switch(uriMatcher.match(uri))
        {
            case 1:
                return db.query("tabla1",projection,selection,selectionArgs,null,null,sortOrder);

            default:
                throw new IllegalStateException("No se pudo resolver la URI");
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID=-1;
        SQLiteDatabase db=database.getWritableDatabase();
        switch(uriMatcher.match(uri))
        {
            case 1:
                rowID=db.insert("tabla1", null, values);
                break;

        }
        if(rowID>=0){
            Uri _uri = ContentUris.withAppendedId(MAIN_URL, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = database.getWritableDatabase();

        int rowsAffected=0;
        switch(uriMatcher.match(uri))
        {
            case 1:
                rowsAffected=db.delete("tabla1",selection,selectionArgs);
                break;

        }

        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();

        int rowsAffected=0;
        switch(uriMatcher.match(uri))
        {
            case 1:
                rowsAffected=db.update("tabla1",values,selection,selectionArgs);
                break;

        }

        return rowsAffected;
    }

}
