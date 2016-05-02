package com.chrosatech.ontime.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mayank on 27/3/16.
 */
public class CustomSpinnerArrayAdapter<T> extends ArrayAdapter<T> {
    public CustomSpinnerArrayAdapter(Context context, int resource,List<T> objects) {
        super(context, resource, objects);
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }
}
