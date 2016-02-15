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

        final CardView cardView = (CardView) view.findViewById(R.id.cursor_adapter_cardview);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);
        TextView subject = (TextView) view.findViewById(R.id.subject);
        final TextView room = (TextView) view.findViewById(R.id.room);
        final TextView classType = (TextView) view.findViewById(R.id.class_type);
        final TextView teacherName = (TextView) view.findViewById(R.id.teacher_name);

        ViewHolder viewHolder = (ViewHolder)view.getTag();

        startTime.setText(cursor.getString(1));
        endTime.setText(cursor.getString(2));
        String type = "(" + cursor.getString(3) + ")";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String subjectForm = sharedPref.getString("example_appearance", "1");
        final String subjectFullForm = cursor.getString(4).trim();

        //To correctly display KenBurnsView
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (subjectFullForm.toLowerCase().equals("break")){
                    final KenBurnsView foodImage = (KenBurnsView) view.findViewById(R.id.cardview_bg_img_food);
                    foodImage.setVisibility(View.VISIBLE);
                    foodImage.setAdjustViewBounds(true);
                    if (cardView.getMeasuredHeight() != 0) {

                        foodImage.setMaxHeight(cardView.getMeasuredHeight());
                        Log.d("Ken", cardView.getMeasuredHeight() + "");
                    }else {
                        run();
                    }
                }
            }
        },1);



        if (subjectFullForm.toLowerCase().equals("break")){
            Log.d("Break","aknl");
            room.setVisibility(View.GONE);
            classType.setVisibility(View.GONE);
            teacherName.setVisibility(View.GONE);
        }

        if (subjectForm.equals("1")){
            subject.setText(subjectFullForm);
            classType.setText(type);
        }else {
            String subjectShortForm = "";
            if (subjectFullForm.contains(" ")) {
                StringTokenizer stringTokenizer = new StringTokenizer(subjectFullForm, " &");

                while (stringTokenizer.hasMoreTokens()) {
                    subjectShortForm = subjectShortForm + stringTokenizer.nextToken().toUpperCase().charAt(0) + ".";
                }

                subject.setText(subjectShortForm);

                String typeShort = type.substring(0,2) + String.valueOf(type.charAt(type.length()-1));
                classType.setText(typeShort);
            } else {
                subject.setText(subjectFullForm);
                classType.setText(type);
            }
        }

        //subject.setText(cursor.getString(3));
        room.setText(cursor.getString(5));
        teacherName.setText(cursor.getString(6));

        if (!viewHolder.isColored) {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            viewHolder.color = generator.getRandomColor();
            viewHolder.isColored = true;
        }
         final  TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(subject.getText().charAt(0)), viewHolder.color);
        imageView.setImageDrawable(drawable);

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
        view.setTag(viewHolder);

        return view;
    }

    //TODO try to implement with list instead of holder

    public class ViewHolder{
        int color;
        boolean isColored = false;
    }
}

