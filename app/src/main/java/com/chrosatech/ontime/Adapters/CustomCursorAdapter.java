package com.chrosatech.ontime.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.StringTokenizer;

/**
 * Created by mayank on 25/12/15.
 */
public class CustomCursorAdapter extends CursorAdapter {

    /*public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }*/
    // CursorAdapter will handle all the moveToFirst(), getCount() logic for you :)

    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    public void bindView(final View view, Context context, Cursor cursor) {

        // Get all the values
        // Use it however you need to

        final ViewHolder holder = (ViewHolder)view.getTag();

        holder.startTime.setText(cursor.getString(1));
        holder.endTime.setText(cursor.getString(2));
        String type = "(" + cursor.getString(3) + ")";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String subjectForm = sharedPref.getString(Values.keyAppearance, "1");
        final String subjectFullForm = cursor.getString(4).trim();

        holder.foodImage.setVisibility(View.GONE        );
        //To correctly display KenBurnsView
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (subjectFullForm.toLowerCase().equals("break")){
                    holder.foodImage.setVisibility(View.VISIBLE);
                    holder.foodImage.setAdjustViewBounds(true);
                    if (holder.cardView.getMeasuredHeight() != 0) {

                        holder.foodImage.setMaxHeight(holder.cardView.getMeasuredHeight());
                        Log.d("Ken", holder.cardView.getMeasuredHeight() + "");
                    }else {
                        run();
                    }
                }
            }
        },1);



        if (subjectFullForm.toLowerCase().equals("break")){
            holder.room.setVisibility(View.GONE);
            holder.classType.setVisibility(View.GONE);
            holder.teacherName.setVisibility(View.GONE);
        }

        if (subjectForm.equals("1")){
            holder.subject.setText(subjectFullForm);
            holder.classType.setText(type);
        }else {
            String subjectShortForm = "";
            if (subjectFullForm.contains(" ")) {
                StringTokenizer stringTokenizer = new StringTokenizer(subjectFullForm, " &");

                while (stringTokenizer.hasMoreTokens()) {
                    subjectShortForm = subjectShortForm + stringTokenizer.nextToken().toUpperCase().charAt(0) + ".";
                }

                holder.subject.setText(subjectShortForm);

                String typeShort = type.substring(0,2) + String.valueOf(type.charAt(type.length()-1));
                holder.classType.setText(typeShort);
            } else {
                holder.subject.setText(subjectFullForm);
                holder.classType.setText(type);
            }
        }

        //subject.setText(cursor.getString(3));
        holder.room.setText(cursor.getString(5));
        holder.teacherName.setText(cursor.getString(6));

        if (!holder.isColored) {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            holder.color = generator.getRandomColor();
            holder.isColored = true;
        }
         final  TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(holder.subject.getText().charAt(0)), holder.color);
        holder.imageView.setImageDrawable(drawable);

        //to set image size to equal height and width

       /* ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int x;
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                x = imageView.getMeasuredWidth();
                imageView.setLayoutParams(new LinearLayout.LayoutParams(x, x));
                return true;
            }
        });*/

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate your view here.
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_cursor_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.cardView = (CardView) view.findViewById(R.id.cursor_adapter_cardview);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
        viewHolder.startTime = (TextView) view.findViewById(R.id.start_time);
        viewHolder.endTime = (TextView) view.findViewById(R.id.end_time);
        viewHolder.subject = (TextView) view.findViewById(R.id.subject);
        viewHolder.room = (TextView) view.findViewById(R.id.room);
        viewHolder.classType = (TextView) view.findViewById(R.id.class_type);
        viewHolder.teacherName = (TextView) view.findViewById(R.id.teacher_name);
        viewHolder.foodImage = (KenBurnsView) view.findViewById(R.id.cardview_bg_img_food);
        view.setTag(viewHolder);
        return view;
    }

    //TODO try to implement with list instead of holder

    private static class ViewHolder{
        int color;
        boolean isColored = false;
        private CardView cardView;
        private ImageView imageView;
        private TextView startTime;
        private TextView endTime;
        private TextView subject;
        private TextView room;
        private TextView classType;
        private TextView teacherName;
        private KenBurnsView foodImage;
    }
}

