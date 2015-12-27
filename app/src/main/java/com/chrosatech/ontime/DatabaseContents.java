package com.chrosatech.ontime;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by mayank on 25/12/15.
 */
public class DatabaseContents extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "selecttest.db";
    //just update the Databse verion to update the database in both databaseContents.java and SelectionDatabase.java
    private static final int DATABASE_VERSION = 1;
    private SharedPreferences.Editor editor;


    public DatabaseContents(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getCursor(String day) {

        String id = MainActivity.sharedpreferences.getString("ID", "0");

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"rowid _id", "StartTime", "EndTime", "Lecture", "Room"};
        String sqlTables = "TimeTable";
        String whereClause = "ID = '" + id + "' AND UPPER(Day) = UPPER('" + day + "') ";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }
}
