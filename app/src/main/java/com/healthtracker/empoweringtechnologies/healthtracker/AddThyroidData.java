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

public class AddThyroidData extends AppCompatActivity {


    public EditText tsh;
    public EditText freet4;
    public EditText freet3;
    public EditText thyroidfunctiondate;

    public int idValue;
    public MyDBHandler dbHandler;
    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thyroid_data);
        tsh = (EditText) findViewById(R.id.txttsh);
        freet4 = (EditText) findViewById(R.id.txtfreet4);
        freet3 = (EditText) findViewById(R.id.txtfreet3);
        thyroidfunctiondate = (EditText) findViewById(R.id.txtthyroidfunctiondate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dateFormatter = new SimpleDateFormat("MM /dd /yyyy", Locale.US);

        if (idValue > 0) {
            tsh.setText(myIntent.getStringExtra("tshVal"));
            freet4.setText(myIntent.getStringExtra("freet4Val"));
            freet3.setText(myIntent.getStringExtra("freet3Val"));
            thyroidfunctiondate.setText(myIntent.getStringExtra("thyroidtestdateVal"));
        } else {
            thyroidfunctiondate.setText(dateFormatter.format(new Date()));
        }
        dbHandler = new com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler(this, null, null, 1);

        thyroidfunctiondate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v == thyroidfunctiondate) {
                    thyroidfunctiondate.setError(null);
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
            thyroidfunctiondate.setText(date_selected);
        }
    };

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void GoShowThyroidData(View view) {
        Intent intent = new Intent(this, ShowThyroidProfile.class);
        startActivity(intent);
    }

    /* method to add or save activity data */
    public void RecordThyroidFunctionData(View view) {
        ThyroidData thyroiddata = new ThyroidData(tsh.getText().toString(), freet4.getText().toString(), freet3.getText().toString(), thyroidfunctiondate.getText().toString());
        /* validations on user iput data */
        if (tsh.getText().toString().length() == 0) {
            tsh.setError("TSH is required!");
            return;
        }


        /* Did we arrive here from an edit of an existing activity or a new activity data save?
           The iDVal will be greater than 0 if this is an edit of an existing activity data
         */

        if (idValue > 0)
            dbHandler.UpdateThyroidData(thyroiddata, idValue);
        else
            dbHandler.SaveThyroidData(thyroiddata);
        Clear();
    }

    public void Clear() {
        tsh.setText("");
        freet4.setText("");
        freet3.setText("");
        thyroidfunctiondate.setText("");
    }

    public void ClearAll(View view) {
        Clear();
    }

}
