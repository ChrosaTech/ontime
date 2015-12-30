package com.chrosatech.ontime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    private ActionBar actionBar;
    // private AutoCompleteTextView autoCompleteTextView;
    // String sample1[]={"IT","Cse","Ece","EEE"};
    private Spinner branch, group, year;
    private Button btnSubmit;
    private SharedPreferences.Editor editor;
    private String firstLaunch = "firstLaunch";
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        branch = (Spinner) findViewById(R.id.branch);
        group = (Spinner) findViewById(R.id.group);
        year = (Spinner) findViewById(R.id.year);
       // btnSubmit = (Button) findViewById(R.id.btnSubmit);
        FloatingActionButton fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/OnTime.ttf");

        TextDrawable textDrawable = TextDrawable.builder()
                .beginConfig()
                .useFont(typeface)
                .endConfig()
                .buildRound(getString(R.string.arrow_forward), Color.TRANSPARENT);
        fabSubmit.setImageDrawable(textDrawable);
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
        addItemsOnSpinnerBranch();
        addItemsOnSpinnerBatch();
        addListnerOnSpinnerItemSelection();
        addItemsOnSpinnerYear();
        //btnSubmit.setOnClickListener(submitClick);
        fabSubmit.setOnClickListener(submitClick);
    }


    public void addItemsOnSpinnerBranch(){
        List<String> list=new ArrayList<String>();
        list.add("CSE");
        list.add("IT");
        list.add("ECE");
        list.add("EEE");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(dataApdapter);
    }

    public void addItemsOnSpinnerBatch(){
        List<String> list=new ArrayList<String>();
        list.add("P1");
        list.add("P2");
        list.add("P3");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(dataApdapter);
    }

    public void addItemsOnSpinnerYear(){
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

    private View.OnClickListener submitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int yearInt = getYear();
            String whereClause = "College = 'BVP' " +
                    "AND YEAR = " + yearInt +
                    " AND Branch = '"+String.valueOf(branch.getSelectedItem())+
                    "' AND Shift = 1 AND Tutorial = '2' AND Practical = '2'";
            // String whereClause = "College = 'BVP' AND YEAR = 3 AND Branch = 'IT' AND Shift = 1 AND Tutorial = '1' AND Practical = '2'";

            SelectionDatabase db = new SelectionDatabase(LaunchActivity.this);
            String id = db.getID(whereClause);
            //MainActivity.sharedpreferences = getPreferences(Context.MODE_PRIVATE);
            editor = MainActivity.sharedpreferences.edit();
            editor.putBoolean(firstLaunch, false);
            editor.putString("ID", id);
            editor.commit();

            /*Toast.makeText(LaunchActivity.this, "OnClickListner : " + "\nSpinner 1 : " + String.valueOf(branch.getSelectedItem()) +
                    "\nSpinner 2:" + String.valueOf(group.getSelectedItem()) + yearInt, Toast.LENGTH_SHORT).show();*/

            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    };

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

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}