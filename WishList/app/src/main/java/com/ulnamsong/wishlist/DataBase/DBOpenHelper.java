package com.ulnamsong.wishlist.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public class DBOpenHelper {

    private static final String DATABASE_NAME = "addressbook.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // Constructor
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Initial Call
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Databases.CreateDB._CREATE);
        }

        // DB Version Update
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+Databases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String title, int categoryIndex, int curValue, int maxValue, int importancyValue, String startDate, String recentDate) {
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.TITLE, title);
        values.put(Databases.CreateDB.CATEGORY_INDEX, categoryIndex);
        values.put(Databases.CreateDB.CUR_VALUE, curValue);
        values.put(Databases.CreateDB.MAX_VALUE, maxValue);
        values.put(Databases.CreateDB.IMPORTANT_VALUE, importancyValue);
        values.put(Databases.CreateDB.START_DATE, startDate);
        values.put(Databases.CreateDB.RECENT_DATE, recentDate);
        return mDB.insert(Databases.CreateDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean updateColumn(long id , String title, int categoryIndex, int curValue, int maxValue, int importancyValue, String startDate, String recentDate) {
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.TITLE, title);
        values.put(Databases.CreateDB.CATEGORY_INDEX, categoryIndex);
        values.put(Databases.CreateDB.CUR_VALUE, curValue);
        values.put(Databases.CreateDB.MAX_VALUE, maxValue);
        values.put(Databases.CreateDB.IMPORTANT_VALUE, importancyValue);
        values.put(Databases.CreateDB.START_DATE, startDate);
        values.put(Databases.CreateDB.RECENT_DATE, recentDate);
        return mDB.update(Databases.CreateDB._TABLENAME, values, "_id=" + id, null) > 0;
    }

    public void deleteAllColumn() {
        mDB.execSQL("delete from "+ Databases.CreateDB._TABLENAME);
    }

    // Delete ID
    public boolean deleteColumn(long id){
        return mDB.delete(Databases.CreateDB._TABLENAME, "_id=" + id, null) > 0;
    }

    // Delete title
    public boolean deleteColumn(String title){
        return mDB.delete(Databases.CreateDB._TABLENAME, "title=" + title, null) > 0;
    }

    // Select All
    public Cursor getAllColumns(){
        return mDB.query(Databases.CreateDB._TABLENAME, null, null, null, null, null, null);
    }

    // Get ID Column
    public Cursor getColumn(long id){
        Cursor c = mDB.query(Databases.CreateDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // Search Title (rawQuery)
    public Cursor getMatchTitle(String title) {
        Cursor c = mDB.rawQuery( "select * from address where title=" + "'" + title + "'" , null);
        return c;
    }
}
