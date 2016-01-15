package com.chrosatech.ontime;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.StringTokenizer;

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

        final CardView cardView = (CardView) view.findViewById(R.id.cursor_adapter_cardview);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);
        TextView subject = (TextView) view.findViewById(R.id.subject);
        TextView room = (TextView) view.findViewById(R.id.room);
        TextView classType = (TextView) view.findViewById(R.id.class_type);
        TextView teacherName = (TextView) view.findViewById(R.id.teacher_name);



        startTime.setText(cursor.getString(1));
        endTime.setText(cursor.getString(2));
        String type = "(" + cursor.getString(3) + ")";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String subjectForm = sharedPref.getString("example_appearance", "1");
        String subjectFullForm = cursor.getString(4);

        if (subjectFullForm.equals("Break")){
            room.setVisibility(View.GONE);
            classType.setVisibility(View.GONE);
            teacherName.setVisibility(View.GONE);
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.transparent_white));

            final KenBurnsView foodImage=(KenBurnsView)view.findViewById(R.id.cardview_bg_img_food);
            foodImage.setVisibility(View.VISIBLE);
            foodImage.setAdjustViewBounds(true);
            foodImage.setMaxHeight(cardView.getMeasuredHeight());

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

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();
        final TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(subject.getText().charAt(0)), color1);
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

        return view;
    }
}

