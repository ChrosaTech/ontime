package com.chrosatech.ontime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by mayank on 3/10/15.
 */
public class SelectionDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "selecttest.db";
    private static final int DATABASE_VERSION = 1;

    public SelectionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
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
