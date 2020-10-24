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

public class AddKidneyFunction extends AppCompatActivity {

    public EditText gfr;
    public EditText creatinine;
    public EditText kidneyfunctiondate;
    public int idValue;
    public MyDBHandler dbHandler;
    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kidney_function);

        gfr = (EditText) findViewById(R.id.txtgfr);
        creatinine = (EditText) findViewById(R.id.txtcreatinine);
        kidneyfunctiondate = (EditText) findViewById(R.id.txtkidneyfunctiondate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (idValue > 0) {
            gfr.setText(myIntent.getStringExtra("gfrVal"));
            creatinine.setText(myIntent.getStringExtra("creatinineVal"));
            kidneyfunctiondate.setText(myIntent.getStringExtra("kidneyfunctiondateVal"));
        }
        dbHandler = new com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler(this, null, null, 1);

        kidneyfunctiondate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v == kidneyfunctiondate) {
                    kidneyfunctiondate.setError(null);
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
            kidneyfunctiondate.setText(date_selected);
        }
    };

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void TrackKidneyFunction(View view) {
        Intent intent = new Intent(this, ShowKidneyFunction.class);
        startActivity(intent);
    }

    /* method to add or save activity data */
    public void RecordKidneyFunctionData(View view) {
        KidneyFunctionData kidneyfunctiondata = new KidneyFunctionData(gfr.getText().toString(), creatinine.getText().toString(), kidneyfunctiondate.getText().toString());
        /* validations on user iput data */
        if (gfr.getText().toString().length() == 0) {
            gfr.setError("GFR is required!");
            return;
        }


        /* Did we arrive here from an edit of an existing activity or a new activity data save?
           The iDVal will be greater than 0 if this is an edit of an existing activity data
         */

        if (idValue > 0)
            dbHandler.UpdateKidneyFunction(kidneyfunctiondata, idValue);
        else
            dbHandler.SaveKidneyFunctionData(kidneyfunctiondata);
        Clear();
    }

    public void Clear() {
        gfr.setText("");
        creatinine.setText("");
        kidneyfunctiondate.setText("");
    }

    public void ClearAll(View view) {
        Clear();
    }
}
