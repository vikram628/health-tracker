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


public class AddAppointment extends AppCompatActivity {

    public EditText appointmentdate;
    public EditText checkup;
    public EditText hospital;
    public int idValue;
    public com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler dbHandler;
    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent();
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        appointmentdate = (EditText) findViewById(R.id.txtappoinntmentdate);
        checkup = (EditText) findViewById(R.id.txtcheckup);
        hospital = (EditText) findViewById(R.id.txthospital);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // if idValue > 0, then we arrived here from edit action on the appointments report
        // else it's a new record
        if (idValue > 0) {
            appointmentdate.setText(myIntent.getStringExtra("appointmentdateVal"));
            checkup.setText(myIntent.getStringExtra("checkupVal"));
            hospital.setText(myIntent.getStringExtra("hospitalVal"));
        }

        appointmentdate.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v == appointmentdate) {
                    appointmentdate.setError(null);
                    showDialog(DATE_DIALOG_ID);
                }

                return false;
            }
        });
        dbHandler = new com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler(this, null, null, 1);

    }

    // Handle 'Home Icon' selection
    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Handle 'Reports Icon' selection
    public void TrackAppointments(View view) {
        Intent intent = new Intent(this, MedicalAppointments.class);
        startActivity(intent);
    }

    // Get the date value  selected on calendar
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear + 1) + " /" + String.valueOf(dayOfMonth) + " /" + String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            appointmentdate.setText(date_selected);
        }
    };

    // Show calendar dialog
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

    /* method to add or save activity data */
    public void RecordAppointmentData(View view) {
        AppointmentData appointmentData = new AppointmentData(appointmentdate.getText().toString(), checkup.getText().toString(), hospital.getText().toString());
        /* validations on user iput data */
        if (appointmentdate.getText().toString().length() == 0) {
            appointmentdate.setError("Appointment Date is required!");
            return;
        }
        if (checkup.getText().toString().length() == 0) {
            checkup.setError("Appointment For is required!");
            return;
        }

        if (hospital.getText().toString().length() == 0) {
            hospital.setError("Hospital time is required!");
            return;
        }

        /* Did we arrive here from an edit of an existing activity or a new activity data save?
           The iDVal will be greater than 0 if this is an edit of an existing activity data
         */

        if (idValue > 0)
            dbHandler.UpdateAppointments(appointmentData, idValue);
        else
            dbHandler.SaveAppointmentData(appointmentData);
        Clear();
    }

    public void Clear() {
        appointmentdate.setText("");
        checkup.setText("");
        hospital.setText("");
    }

    public void ClearAll(View view) {
        Clear();
    }
}
