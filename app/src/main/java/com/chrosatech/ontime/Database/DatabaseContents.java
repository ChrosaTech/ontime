package com.chrosatech.ontime.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.chrosatech.ontime.Activities.MainActivity;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by mayank on 25/12/15.
 */
public class DatabaseContents extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "selecttest.db";
    //just update the Database version to update the database in both databaseContents.java and SelectionDatabase.java
    private static final int DATABASE_VERSION = 1;

    private static Context context1;

    public DatabaseContents(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context1 = context;
        setForcedUpgrade();
    }

    public Cursor getCursor(String day) {

        String id = MainActivity.sharedPreferences.getString("ID", "0");

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

        String id = MainActivity.sharedPreferences.getString("ID", "0");

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

    public Calendar getNextNotificationTime(){

        SharedPreferences sharedPreferences = context1.getSharedPreferences("OnTimePreferences",Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("ID", "0");

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"StartTime"};
        String sqlTables = "TimeTable";

        Calendar dateCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        boolean found = false;
        Calendar calendar = Calendar.getInstance();

        while (!found) {
            Date d = dateCalendar.getTime();
            String dayOfTheWeek = sdf.format(d);

            String whereClause = "ID = '" + id + "' AND UPPER(Day) = UPPER('" + dayOfTheWeek + "') ";

            qb.setTables(sqlTables);
            Cursor c = qb.query(db, sqlSelect, whereClause, null,
                    null, null, null);

            c.moveToFirst();

            Calendar currentCalendar = Calendar.getInstance();
            int hours = currentCalendar.get(Calendar.HOUR);
            int amPm = currentCalendar.get(Calendar.AM_PM);

            while (c.getPosition() < c.getCount()) {
                String time = c.getString(0).toLowerCase();
                if (time.endsWith("am")) {
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                    time = time.replace("am", "");
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                    time = time.replace("pm", "");
                }
                StringTokenizer tokenizer = new StringTokenizer(time, ":", false);
                String hour = tokenizer.nextToken();
                Log.d("Calender hour", hour);

                //Because 12 is at index 0
                int hrs = Integer.parseInt(hour);
                if (hrs == 12)
                    hrs = 0;
                calendar.set(Calendar.HOUR, hrs);
                String minutes = tokenizer.nextToken();
                Log.d("Calender minutes",minutes);
                calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
                calendar.set(Calendar.SECOND, 0);
                Log.d("Calender",calendar.getTime()+"  "+currentCalendar.getTime());

                if (calendar.getTimeInMillis() > currentCalendar.getTimeInMillis()) {
                    Log.d("Calender","Found");
                    found = true;
                    break;
                }

                c.moveToNext();
            }

            if (!found) {
                Log.d("Calender"," Not Found");
                dateCalendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.DATE, 1);
            }
        }
        Log.d("Calender",calendar+"");

        return calendar;
    }
}
