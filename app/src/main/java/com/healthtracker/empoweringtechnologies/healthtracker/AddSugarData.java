package com.healthtracker.empoweringtechnologies.healthtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSugarData extends AppCompatActivity {

    public EditText glucose;
    public EditText glucosedate;
    public int idValue;
    public MyDBHandler dbHandler;
    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sugar_data);
        glucose = (EditText) findViewById(R.id.txtglucose);
        glucosedate = (EditText) findViewById(R.id.txtglucosefunctiondate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dateFormatter = new SimpleDateFormat("MM /dd /yyyy", Locale.US);

        if (idValue > 0) {
            glucose.setText(myIntent.getStringExtra("glucoseVal"));
            glucosedate.setText(myIntent.getStringExtra("glucosetestdateVal"));
        } else {
            glucosedate.setText(dateFormatter.format(new Date()));
        }
        dbHandler = new com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler(this, null, null, 1);

        glucosedate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v == glucosedate) {
                    glucosedate.setError(null);
                    showDialog(DATE_DIALOG_ID);
                }

                return false;
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, cyear, cmonth, cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear + 1) + " /" + String.valueOf(dayOfMonth) + " /" + String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            glucosedate.setText(date_selected);
        }
    };

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoShowSugarData(View view) {
        Intent intent = new Intent(this, ShowSugarProfile.class);
        startActivity(intent);
    }


    public void TrackGlucoseFunction(View view) {
        Intent intent = new Intent(this, ShowSugarProfile.class);
        startActivity(intent);
    }

    /* method to add or save activity data */
    public void RecordGlucoseFunctionData(View view) {
        GlucoseData glucosedata = new GlucoseData(glucose.getText().toString(), glucosedate.getText().toString());
        /* validations on user iput data */
        if (glucose.getText().toString().length() == 0) {
            glucose.setError("Glucose is required!");
            return;
        }


        /* Did we arrive here from an edit of an existing activity or a new activity data save?
           The iDVal will be greater than 0 if this is an edit of an existing activity data
         */

        if (idValue > 0)
            dbHandler.UpdateGlucoseData(glucosedata, idValue);
        else
            dbHandler.SaveGlucoseData(glucosedata);
        Clear();
    }

    public void Clear() {
        glucose.setText("");
        glucosedate.setText("");
    }

    public void ClearAll(View view) {
        Clear();
    }
}
