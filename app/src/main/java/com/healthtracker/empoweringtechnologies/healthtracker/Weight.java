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


public class Weight extends AppCompatActivity {

    public EditText weight;
    public EditText height;
    public EditText waist;
    public EditText weightdate;
    public int idValue;
    public MyDBHandler dbHandler;

    static final int DATE_DIALOG_ID = 0;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        weight = (EditText) findViewById(R.id.txtweight);
        height = (EditText) findViewById(R.id.txtheight);
        waist = (EditText) findViewById(R.id.txtwaist);
        weightdate = (EditText) findViewById(R.id.txtweightDate);

        dateFormatter = new SimpleDateFormat("MM /dd /yyyy", Locale.US);
        if (idValue > 0) {
            weight.setText(myIntent.getStringExtra("weightVal"));
            height.setText(myIntent.getStringExtra("heightVal"));
            waist.setText(myIntent.getStringExtra("waistVal"));
            weightdate.setText(myIntent.getStringExtra("weightdateVal"));
        }
        else
        {
            weightdate.setText(dateFormatter.format(new Date()));
        }
        dbHandler = new MyDBHandler(this,null,null,1);


        weightdate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == weightdate) {
                    weightdate.setError(null);
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
            weightdate.setText(date_selected);
        }
    };

    /* method to add or save activity data */
    public void RecordWeightData(View view) {

        WeightData weightdata = new WeightData(weight.getText().toString(), height.getText().toString(), waist.getText().toString(),weightdate.getText().toString());

        /* validations on user iput data */
        if (weight.getText().toString().length() == 0) {
            weight.setError("Weight is required!");
            return;
        }
        if (height.getText().toString().length() == 0) {
            height.setError("Height is required!");
            return;
        }
        if (waist.length() == 0) {
            waist.setError("Waist data is required!");
            return;
        }
        if (idValue > 0)
            dbHandler.UpdateWeightData(weightdata, idValue);
        else
            dbHandler.SaveWeightData(weightdata);
        clearAll();
    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void TrackWeight(View view)
    {
        Intent intent = new Intent(this, ShowWeightProfile.class);
        startActivity(intent);
    }


    public void TrackBP(View view)
    {
        Intent intent = new Intent(this, ShowBPData.class);
        startActivity(intent);
    }

    /* method to clear the inout fields in the Save Activity page */
    public void ClearWeightData(View view) {
        clearAll();
    }

    public void clearAll()
    {
        weight.setText("");
        height.setText("");
        waist.setText("");
        weightdate.setText("");
    }
}
