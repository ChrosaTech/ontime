package com.chrosatech.ontime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;


/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UniversalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UniversalFragment#newInstance} factory method to
 * create an instance of this fragment.
*/
public class UniversalFragment extends Fragment {

    private String day;

    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;
    private TextView fifth;
    private TextView sixth;
    private TextView room1;
    private TextView room2;
    private TextView room3;
    private TextView room4;
    private TextView room5;
    private TextView room6;
    private TableRow row1;
    private TableRow row2;
    private TableRow row3;
    private TableRow row4;
    private TableRow row5;
    private TableRow row6;
    private TableRow row_break;



    public UniversalFragment() {
        // Required empty public constructor
        day="NULL";
    }

    public UniversalFragment(String day) {
        this.day = day;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment, container, false);

        first = (TextView)view.findViewById(R.id.first);
        second = (TextView)view.findViewById(R.id.second);
        third = (TextView)view.findViewById(R.id.third);
        fourth = (TextView)view.findViewById(R.id.fourth);
        fifth = (TextView)view.findViewById(R.id.fifth);
        sixth = (TextView)view.findViewById(R.id.sixth);
        room1 = (TextView)view.findViewById(R.id.first_room);
        room2 = (TextView)view.findViewById(R.id.second_room);
        room3 = (TextView)view.findViewById(R.id.third_room);
        room4 = (TextView)view.findViewById(R.id.fourth_room);
        room5 = (TextView)view.findViewById(R.id.fifth_room);
        room6 = (TextView)view.findViewById(R.id.sixth_room);
        row1 = (TableRow)view.findViewById(R.id.row1);
        row2 = (TableRow)view.findViewById(R.id.row2);
        row3 = (TableRow)view.findViewById(R.id.row3);
        row4 = (TableRow)view.findViewById(R.id.row4);
        row5 = (TableRow)view.findViewById(R.id.row5);
        row6 = (TableRow)view.findViewById(R.id.row6);
        row_break = (TableRow)view.findViewById(R.id.row_break);
        fill(day);

        return view;
    }

    private void fill(String title){

        if(title.equals("MONDAY")){
            first.setText(TimeTable.Monday[0][0]);
            second.setText(TimeTable.Monday[1][0]);
            third.setText(TimeTable.Monday[2][0]);
            fourth.setText(TimeTable.Monday[3][0]);
            fifth.setText(TimeTable.Monday[4][0]);
            sixth.setText(TimeTable.Monday[5][0]);
            room1.setText(TimeTable.Monday[0][1]);
            room2.setText(TimeTable.Monday[1][1]);
            room3.setText(TimeTable.Monday[2][1]);
            room4.setText(TimeTable.Monday[3][1]);
            room5.setText(TimeTable.Monday[4][1]);
            room6.setText(TimeTable.Monday[5][1]);

            if(TimeTable.Monday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Monday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Monday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Monday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Monday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Monday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);


        }

        if(title.equals("TUESDAY")){
            first.setText(TimeTable.Tuesday[0][0]);
            second.setText(TimeTable.Tuesday[1][0]);
            third.setText(TimeTable.Tuesday[2][0]);
            fourth.setText(TimeTable.Tuesday[3][0]);
            fifth.setText(TimeTable.Tuesday[4][0]);
            sixth.setText(TimeTable.Tuesday[5][0]);
            room1.setText(TimeTable.Tuesday[0][1]);
            room2.setText(TimeTable.Tuesday[1][1]);
            room3.setText(TimeTable.Tuesday[2][1]);
            room4.setText(TimeTable.Tuesday[3][1]);
            room5.setText(TimeTable.Tuesday[4][1]);
            room6.setText(TimeTable.Tuesday[5][1]);

            if(TimeTable.Tuesday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Tuesday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Tuesday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Tuesday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Tuesday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Tuesday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);
        }

        if(title.equals("WEDNESDAY")){
            first.setText(TimeTable.Wednesday[0][0]);
            second.setText(TimeTable.Wednesday[1][0]);
            third.setText(TimeTable.Wednesday[2][0]);
            fourth.setText(TimeTable.Wednesday[3][0]);
            fifth.setText(TimeTable.Wednesday[4][0]);
            sixth.setText(TimeTable.Wednesday[5][0]);
            room1.setText(TimeTable.Wednesday[0][1]);
            room2.setText(TimeTable.Wednesday[1][1]);
            room3.setText(TimeTable.Wednesday[2][1]);
            room4.setText(TimeTable.Wednesday[3][1]);
            room5.setText(TimeTable.Wednesday[4][1]);
            room6.setText(TimeTable.Wednesday[5][1]);

            if(TimeTable.Wednesday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Wednesday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Wednesday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Wednesday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Wednesday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Wednesday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);
        }

        if(title.equals("THURSDAY")){
            first.setText(TimeTable.Thursday[0][0]);
            second.setText(TimeTable.Thursday[1][0]);
            third.setText(TimeTable.Thursday[2][0]);
            fourth.setText(TimeTable.Thursday[3][0]);
            fifth.setText(TimeTable.Thursday[4][0]);
            sixth.setText(TimeTable.Thursday[5][0]);
            room1.setText(TimeTable.Thursday[0][1]);
            room2.setText(TimeTable.Thursday[1][1]);
            room3.setText(TimeTable.Thursday[2][1]);
            room4.setText(TimeTable.Thursday[3][1]);
            room5.setText(TimeTable.Thursday[4][1]);
            room6.setText(TimeTable.Thursday[5][1]);

            if(TimeTable.Thursday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Thursday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Thursday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Thursday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Thursday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Thursday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);
        }

        if(title.equals("FRIDAY")){
            first.setText(TimeTable.Friday[0][0]);
            second.setText(TimeTable.Friday[1][0]);
            third.setText(TimeTable.Friday[2][0]);
            fourth.setText(TimeTable.Friday[3][0]);
            fifth.setText(TimeTable.Friday[4][0]);
            sixth.setText(TimeTable.Friday[5][0]);
            room1.setText(TimeTable.Friday[0][1]);
            room2.setText(TimeTable.Friday[1][1]);
            room3.setText(TimeTable.Friday[2][1]);
            room4.setText(TimeTable.Friday[3][1]);
            room5.setText(TimeTable.Friday[4][1]);
            room6.setText(TimeTable.Friday[5][1]);

            if(TimeTable.Friday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Friday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Friday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Friday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Friday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Friday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);
        }

        if(title.equals("SATURDAY")){
            first.setText(TimeTable.Saturday[0][0]);
            second.setText(TimeTable.Saturday[1][0]);
            third.setText(TimeTable.Saturday[2][0]);
            fourth.setText(TimeTable.Saturday[3][0]);
            fifth.setText(TimeTable.Saturday[4][0]);
            sixth.setText(TimeTable.Saturday[5][0]);
            room1.setText(TimeTable.Saturday[0][1]);
            room2.setText(TimeTable.Saturday[1][1]);
            room3.setText(TimeTable.Saturday[2][1]);
            room4.setText(TimeTable.Saturday[3][1]);
            room5.setText(TimeTable.Saturday[4][1]);
            room6.setText(TimeTable.Saturday[5][1]);

            if(TimeTable.Saturday[0][0].equals("-NA-"))
                row1.setVisibility(View.GONE);
            if(TimeTable.Saturday[1][0].equals("-NA-"))
                row2.setVisibility(View.GONE);
            if(TimeTable.Saturday[2][0].equals("-NA-"))
                row3.setVisibility(View.GONE);
            if(TimeTable.Saturday[3][0].equals("-NA-"))
                row4.setVisibility(View.GONE);
            if(TimeTable.Saturday[4][0].equals("-NA-"))
                row5.setVisibility(View.GONE);
            if(TimeTable.Saturday[5][0].equals("-NA-"))
                row6.setVisibility(View.GONE);

            if(row5.getVisibility()== View.GONE && row6.getVisibility()== View.GONE)
                row_break.setVisibility(View.GONE);
        }


    }

}
