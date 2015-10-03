package com.chrosatech.ontime;

import android.app.NotificationManager;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    private ActionBar actionBar;
    // private AutoCompleteTextView autoCompleteTextView;
    // String sample1[]={"IT","Cse","Ece","EEE"};
    private Spinner branch, group, year;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

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
        addItemsOnSpinner2();
        addListnerOnSpinnerItemSelection();
        addListnerOnButton();
        addItemsOnSpinner3();

    }

    public void addItemsOnSpinner2(){
        group=(Spinner) findViewById(R.id.group);
        List<String> list=new ArrayList<String>();
        list.add("P1");
        list.add("P2");
        list.add("P3");
        ArrayAdapter<String> dataApdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(dataApdapter);
    }

    public void addItemsOnSpinner3(){
        year=(Spinner) findViewById(R.id.year);
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
        branch= (Spinner) findViewById(R.id.branch);
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
        branch = (Spinner) findViewById(R.id.branch);
        group = (Spinner) findViewById(R.id.group);
        year = (Spinner) findViewById(R.id.year);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LaunchActivity.this, "OnClickListner : " + "\nSpinner 1 : " + String.valueOf(branch.getSelectedItem()) +
                        "\nSpinner 2:" + String.valueOf(group.getSelectedItem()) + String.valueOf(year.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //spinner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}