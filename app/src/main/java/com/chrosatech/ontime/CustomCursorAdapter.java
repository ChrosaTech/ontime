package com.chrosatech.ontime;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by mayank on 25/12/15.
 */
class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
    // CursorAdapter will handle all the moveToFirst(), getCount() logic for you :)

    /*public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }*/

    public void bindView(View view, Context context, Cursor cursor) {

        // Get all the values
        // Use it however you need to

        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);
        TextView subject = (TextView) view.findViewById(R.id.subject);
        TextView room = (TextView) view.findViewById(R.id.room);

        startTime.setText(cursor.getString(1));
        endTime.setText(cursor.getString(2));
        subject.setText(cursor.getString(3));
        room.setText(cursor.getString(4));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate your view here.
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_cursor_adapter, parent, false);

        return view;
    }
}

