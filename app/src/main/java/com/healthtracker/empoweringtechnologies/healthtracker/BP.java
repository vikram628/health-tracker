package com.healthtracker.empoweringtechnologies.healthtracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.healthtracker.empoweringtechnologies.healthtracker.R.id.time4;

public class BP extends AppCompatActivity {

    public EditText systolic;
    public EditText diastolic;
    public EditText heartrate;
    public EditText notes;
    public EditText bpdate;
    public int idValue;
    public MyDBHandler dbHandler;

    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        systolic = (EditText) findViewById(R.id.txtSystolic);
        diastolic = (EditText) findViewById(R.id.txtDiastolic);
        heartrate = (EditText) findViewById(R.id.txtHeartRate);
        notes = (EditText) findViewById(R.id.txtNotes);
        bpdate = (EditText) findViewById(R.id.txtDate);

        dateFormatter = new SimpleDateFormat("MM /dd /yyyy", Locale.US);
        if (idValue > 0) {
            systolic.setText(myIntent.getStringExtra("systolicVal"));
            diastolic.setText(myIntent.getStringExtra("diastolicVal"));
            heartrate.setText(myIntent.getStringExtra("heartrateVal"));
            notes.setText(myIntent.getStringExtra("notesVal"));
            bpdate.setText(myIntent.getStringExtra("bpdateVal"));
        } else {
            bpdate.setText(dateFormatter.format(new Date()));
        }
        dbHandler = new MyDBHandler(this, null, null, 1);


        bpdate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v == bpdate) {
                    bpdate.setError(null);
                    showDialog(DATE_DIALOG_ID);
                }

                return false;
            }
        });
        /*
        bpdate.setInputType(InputType.TYPE_NULL);
        bpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(v == bpdate)
                    showDialog(DATE_DIALOG_ID);
            }
        });
        */
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
            bpdate.setText(date_selected);
        }
    };

    /* method to add or save activity data */
    public void RecordBP(View view) {

        BPData bpdata = new BPData(systolic.getText().toString(), diastolic.getText().toString(), heartrate.getText().toString(), notes.getText().toString(), bpdate.getText().toString());

        /* validations on user iput data */
        if (systolic.getText().toString().length() == 0) {
            systolic.setError("Systolic is required!");
            return;
        }
        if (diastolic.getText().toString().length() == 0) {
            diastolic.setError("Diastolic is required!");
            return;
        }
        if (heartrate.length() == 0) {
            heartrate.setError("Heartrate is required!");
            return;
        }
        if (bpdate.length() == 0) {
            bpdate.setError("Date is required!");
            return;
        }

        if (idValue > 0)
            dbHandler.UpdateBPData(bpdata, idValue);
        else
            dbHandler.SaveBPData(bpdata);
        clearAll();
    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void TrackBP(View view) {
        Intent intent = new Intent(this, ShowBPData.class);
        startActivity(intent);
    }

    /* method to clear the inout fields in the Save Activity page */
    public void ClearBPData(View view) {
        clearAll();
    }

    public void clearAll() {
        systolic.setText("");
        diastolic.setText("");
        heartrate.setText("");
        notes.setText("");
        bpdate.setText("");

    }
}
