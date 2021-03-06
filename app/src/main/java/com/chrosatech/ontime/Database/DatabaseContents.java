package com.chrosatech.ontime.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;

import com.chrosatech.ontime.Activities.MainActivity;
import com.chrosatech.ontime.Helper.Values;
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

//TODO remove this library if possible
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

        int id = MainActivity.sharedPreferences.getInt("ID", 0);
        int altID = MainActivity.sharedPreferences.getInt(Values.keyAltID, 0);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"rowid _id",  "StartTime", "EndTime", "ClassType", "Lecture", "Room", "Teacher"};
        String sqlTables = MainActivity.sharedPreferences.getString(Values.keyCollege, "");
        String whereClause = "ID = " + id + " AND (AlternateID = " + id + " OR AlternateID LIKE \'%" + altID + "%\') AND UPPER(Day) = UPPER(\'" + day + "\') ";

        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        cursor.moveToFirst();
        Log.d("selectedData", cursor.getCount() + "");
        return cursor;

    }

    public String[] getWorkingDaysOfWeek(){

        int id = MainActivity.sharedPreferences.getInt("ID", 0);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"Day"};
        String sqlTables = MainActivity.sharedPreferences.getString(Values.keyCollege, "");
        String whereClause = "ID = " + id;
        LinkedHashSet<String> daysHashed;
        String days[];

        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() != 0){
            daysHashed = new LinkedHashSet<>(cursor.getCount());
            daysHashed.add(cursor.getString(0).toUpperCase());

            while (cursor.moveToNext()){
                daysHashed.add(cursor.getString(0).toUpperCase());
            }
            days = daysHashed.toArray(new String[daysHashed.size()]);
        } else{
            days = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        }
        cursor.close();
        return days;
    }

    public Pair<int[], String> getID(String whereClause) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Values.keyID, Values.keyAltID, Values.keyCollege};
        String sqlTables = "College";
        //String whereClause = "College = 'BVP' AND YEAR = 1 AND Branch = 'IT' AND Shift = 1 AND Tutorial = '1' AND Practical = '2'";

        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db, sqlSelect, whereClause, null,
                null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return null;
        }
        else {
            int ids[] = {cursor.getInt(0), cursor.getInt(1)};
            String college = cursor.getString(2);
            return new Pair<>(ids, college);
        }
    }

    public Calendar getNextNotificationTime(){

        SharedPreferences sharedPreferences = context1.getSharedPreferences("OnTimePreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int id = sharedPreferences.getInt(Values.keyID, 0);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"StartTime", "ClassType", "Lecture", "Room"};
        String sqlTables = sharedPreferences.getString(Values.keyCollege, "");

        Calendar dateCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        boolean found = false;
        Calendar calendar = Calendar.getInstance();
        int altID = sharedPreferences.getInt(Values.keyAltID, 0);

        while (!found) {
            Date d = dateCalendar.getTime();
            String dayOfTheWeek = sdf.format(d);

            String whereClause = "ID = " + id + " AND (AlternateID = " + id + " OR AlternateID LIKE \'%" + altID + "%\') AND UPPER(Day) = UPPER(\'" + dayOfTheWeek + "\') ";

            qb.setTables(sqlTables);
            Cursor cursor = qb.query(db, sqlSelect, whereClause, null,
                    null, null, null);

            cursor.moveToFirst();

            Calendar currentCalendar = Calendar.getInstance();
            /*int hours = currentCalendar.get(Calendar.HOUR);
            int amPm = currentCalendar.get(Calendar.AM_PM);*/

            while (cursor.getPosition() < cursor.getCount()) {
                String time = cursor.getString(0).toLowerCase();
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

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context1);
                String timeBefore = sharedPref.getString(Values.keyNotificationBeforeTime, "5 mins");
                StringTokenizer stringTokenizer = new StringTokenizer(timeBefore);
                if (stringTokenizer.hasMoreTokens()) {
                    calendar.roll(Calendar.MINUTE, -(Integer.parseInt(stringTokenizer.nextToken())));
                }

                if (calendar.getTimeInMillis() > currentCalendar.getTimeInMillis()) {
                    Log.d("Calender","Found");
                    editor.putString("ClassType", cursor.getString(1));
                    editor.putString("Subject",cursor.getString(2));
                    editor.putString("Room", cursor.getString(3));
                    editor.apply();
                    found = true;
                    cursor.close();
                    break;
                }

                cursor.moveToNext();
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
