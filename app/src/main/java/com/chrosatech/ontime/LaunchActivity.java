package com.chrosatech.ontime;

import android.app.NotificationManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    private ActionBar actionBar;
    // private AutoCompleteTextView autoCompleteTextView;
    // String sample1[]={"IT","Cse","Ece","EEE"};
    private Spinner branch, group, year;
    private Button btnSubmit;
    private TextView ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        branch = (Spinner) findViewById(R.id.branch);
        group = (Spinner) findViewById(R.id.group);
        year = (Spinner) findViewById(R.id.year);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        /*actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.indigo)));
            actionBar.setElevation(0);

        }*/

        // ArrayAdapter<String> adap=null;
        // adap = new ArrayAdapter<String>(getApplicationContext(),R.layout.autocomplete,sample1);
        // autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        // autoCompleteTextView.setAdapter(adap);
        // autoCompleteTextView.setThreshold(0);


        //Spinner
        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addListnerOnSpinnerItemSelection();
        addListnerOnButton();
        addItemsOnSpinner3();

    }

    public void addItemsOnSpinner1(){
        List<String> list=new ArrayList<String>();
        list.add("CSE");
        list.add("IT");
        list.add("ECE");
        list.add("EEE");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(dataApdapter);
    }

    public void addItemsOnSpinner2(){
        List<String> list=new ArrayList<String>();
        list.add("P1");
        list.add("P2");
        list.add("P3");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(dataApdapter);
    }

    public void addItemsOnSpinner3(){
        List<String> list=new ArrayList<String>();
        list.add("First Year");
        list.add("Second Year");
        list.add("Third Year");
        list.add("Fourth Year");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataApdapter);
    }

    public void addListnerOnSpinnerItemSelection(){
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addListnerOnButton(){

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yearInt = getYear();
                String whereClause = "College = 'BVP' " +
                        "AND YEAR = " + yearInt +
                        " AND Branch = '"+String.valueOf(branch.getSelectedItem())+
                        "' AND Shift = 1 AND Tutorial = '2' AND Practical = '2'";
               // String whereClause = "College = 'BVP' AND YEAR = 3 AND Branch = 'IT' AND Shift = 1 AND Tutorial = '1' AND Practical = '2'";

                ID = (TextView) findViewById(R.id.idTest);
                SelectionDatabase db = new SelectionDatabase(LaunchActivity.this);
                Cursor cursor = db.getID(whereClause);
                String id = cursor.getString(0);
                ID.setText(id);

                Toast.makeText(LaunchActivity.this, "OnClickListner : " + "\nSpinner 1 : " + String.valueOf(branch.getSelectedItem()) +
                        "\nSpinner 2:" + String.valueOf(group.getSelectedItem()) + yearInt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getYear() {
        int yearInt;
        switch (String.valueOf(year.getSelectedItem())){
            case "First Year" : yearInt = 1;
                break;
            case "Second Year" : yearInt = 2;
                break;
            case "Third Year" : yearInt = 3;
                break;
            case "Fourth Year" : yearInt = 4;
                break;
            default: yearInt = 0;
        }
        return yearInt;
    }

}