package com.chrosatech.ontime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.LinkedHashSet;

/**
 * Created by mayank on 25/12/15.
 */
public class DatabaseContents extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "selecttest.db";
    //just update the Database version to update the database in both databaseContents.java and SelectionDatabase.java
    private static final int DATABASE_VERSION = 1;

    public DatabaseContents(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getCursor(String day) {

        String id = MainActivity.sharedpreferences.getString("ID", "0");

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"rowid _id", "StartTime", "EndTime", "ClassType", "Lecture", "Room", "Teacher"};
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
            daysHashed = new LinkedHashSet<>(c.getCount());
            daysHashed.add(c.getString(0).toUpperCase());

            while (c.moveToNext()){
                daysHashed.add(c.getString(0).toUpperCase());
            }
            days = daysHashed.toArray(new String[daysHashed.size()]);
        } else{
            days = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        }
        return days;
    }

    public String getID(String whereClause) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"ID"};
        String sqlTables = "College";
        //String whereClause = "College = 'BVP' AND YEAR = 1 AND Branch = 'IT' AND Shift = 1 AND Tutorial = '1' AND Practical = '2'";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        c.moveToFirst();
        if (c.getCount() == 0)
            return null;
        else
            return c.getString(0);
    }
}
