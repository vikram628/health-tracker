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


public class Goal extends AppCompatActivity {
    public EditText healthgoal;
    public EditText targetdate;
    public int idValue;
    public MyDBHandler dbHandler;

    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        healthgoal = (EditText) findViewById(R.id.txtGoal);
        targetdate = (EditText) findViewById(R.id.txtTargetDate);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        if (idValue > 0) {
            healthgoal.setText(myIntent.getStringExtra("healthgoalVal"));
            targetdate.setText(myIntent.getStringExtra("targetdateVal"));
        }


        dbHandler = new MyDBHandler(this,null,null,1);


        targetdate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == targetdate) {
                    targetdate.setError(null);
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
                return new DatePickerDialog(this,mDateSetListener,  cyear, cmonth, cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(monthOfYear+1)+" /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            targetdate.setText(date_selected);
        }
    };

    /* method to add or save activity data */
    public void RecordGoal(View view) {


        HealthGoalData healthgoaldata = new HealthGoalData(healthgoal.getText().toString(), targetdate.getText().toString());

        /* validations on user iput data */
        if (healthgoal.getText().toString().length() == 0) {
            healthgoal.setError("Goal is required!");
            return;
        }
        if (targetdate.getText().toString().length() == 0) {
            targetdate.setError("Target date is required!");
            return;
        }

        if (idValue > 0)
            dbHandler.UpdateHealthGoalData(healthgoaldata, idValue);
        else
            dbHandler.SaveHealthGoalData(healthgoaldata);
        clearAll();
    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void TrackGoals(View view)
    {
        Intent intent = new Intent(this, ShowHealthGoals.class);
        startActivity(intent);
    }

    /* method to clear the inout fields in the Save Activity page */
    public void ClearGoalData(View view) {
        clearAll();
    }

    public void clearAll()
    {
        healthgoal.setText("");
        targetdate.setText("");
    }

}
