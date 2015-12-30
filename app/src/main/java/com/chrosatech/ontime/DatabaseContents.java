package com.chrosatech.ontime;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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

    public String[] getWorkingDaysOfWeek(){

        String id = MainActivity.sharedpreferences.getString("ID", "0");

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"Day"};
        String sqlTables = "TimeTable";
        String whereClause = "ID = '" + id +"'";
        LinkedHashSet<String> daysHashed;
        String days[];

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        c.moveToFirst();

        if (c.getCount() != 0){
            daysHashed = new LinkedHashSet<String>(c.getCount());
            int i = 0;
            daysHashed.add(c.getString(0).toUpperCase());

            while (c.moveToNext()){
                daysHashed.add(c.getString(0).toUpperCase());
            }
            days = daysHashed.toArray(new String[daysHashed.size()]);
            Log.d("daysHAs",daysHashed+"");
        } else{
            days = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        }
        Log.d("days", Arrays.toString(days) +"");
        return days;
    }
}
