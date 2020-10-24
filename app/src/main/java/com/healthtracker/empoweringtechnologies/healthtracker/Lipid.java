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

public class Lipid extends AppCompatActivity {
    public EditText totalcholesterol;
    public EditText ldl;
    public EditText hdl;
    public EditText triglycerides;
    public EditText lipiddate;
    public MyDBHandler dbHandler;
    public int idValue;

    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalcholesterol = (EditText) findViewById(R.id.txttotalcholesterol);
        ldl = (EditText) findViewById(R.id.txtldl);
        hdl = (EditText) findViewById(R.id.txthdl);
        triglycerides = (EditText) findViewById(R.id.txttriglycerides);
        lipiddate = (EditText) findViewById(R.id.txtlipidDate);
        dateFormatter = new SimpleDateFormat("MM /dd /yyyy", Locale.US);

        if (idValue > 0) {
            totalcholesterol.setText(myIntent.getStringExtra("totalcholesterolVal"));
            ldl.setText(myIntent.getStringExtra("ldlVal"));
            hdl.setText(myIntent.getStringExtra("hdlVal"));
            triglycerides.setText(myIntent.getStringExtra("triglyceridesVal"));
            lipiddate.setText(myIntent.getStringExtra("lipiddateVal"));
        }
        else
        {
            lipiddate.setText(dateFormatter.format(new Date()));
        }
        dbHandler = new MyDBHandler(this,null,null,1);
        lipiddate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == lipiddate) {
                    lipiddate.setError(null);
                    showDialog(DATE_DIALOG_ID);
                }

                return false;
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear+1)+" /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            lipiddate.setText(date_selected);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,  cyear, cmonth, cday);
        }
        return null;
    }
    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /* method to add or save activity data */
    public void RecordLipdData(View view) {
        LipidData lipiddata = new LipidData(totalcholesterol.getText().toString(), ldl.getText().toString(), hdl.getText().toString(), triglycerides.getText().toString(),lipiddate.getText().toString());

        /* validations on user iput data */
        if (totalcholesterol.getText().toString().length() == 0) {
            totalcholesterol.setError("Chlesterol is required!");
            return;
        }
        if (ldl.getText().toString().length() == 0) {
            ldl.setError("LDL is required!");
            return;
        }
        if (hdl.length() == 0) {
            hdl.setError("HDL is required!");
            return;
        }

        if (triglycerides.length() == 0) {
            triglycerides.setError("Heartrate is required!");
            return;
        }

        if (lipiddate.length() == 0) {
            lipiddate.setError("Date is required!");
            return;
        }
        if (idValue > 0)
            dbHandler.UpdateLipidData(lipiddata, idValue);
        else
            dbHandler.SaveLipidData(lipiddata);

        clearAll();
    }

    public void TrackLipid(View view)
    {
        Intent intent = new Intent(this, ShowLipidProfile.class);
        startActivity(intent);
    }


    /* method to clear the inout fields in the Save Activity page */
    public void ClearLipidData(View view) {
        clearAll();
    }

    public void clearAll()
    {
        totalcholesterol.setText("");
        ldl.setText("");
        hdl.setText("");
        triglycerides.setText("");
        lipiddate.setText("");
    }
}
