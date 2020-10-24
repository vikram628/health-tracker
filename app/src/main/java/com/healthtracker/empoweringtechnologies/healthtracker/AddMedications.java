package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.healthtracker.empoweringtechnologies.healthtracker.R;


public class AddMedications extends AppCompatActivity {

    public EditText medication;
    public EditText dosage;
    //public EditText frequency;
    public EditText time1;
    public EditText time2;
    public EditText time3;
    public EditText time4;
    public int idValue;
    public com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = getIntent(); // gets the previously created intent
        idValue = myIntent.getIntExtra("idValue", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medications);

        medication = (EditText) findViewById(R.id.editText_Medication);
        dosage = (EditText) findViewById(R.id.editText_Dosage);
        //frequency = (EditText) findViewById(R.id.editText_TimesADay);
        time1 = (EditText) findViewById(R.id.editText_Time1);
        time2 = (EditText) findViewById(R.id.editText_Time2);
        time3 = (EditText) findViewById(R.id.editText_Time3);
        time4 = (EditText) findViewById(R.id.editText_Time4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (idValue > 0) {
            medication.setText(myIntent.getStringExtra("medicationVal"));
            dosage.setText(myIntent.getStringExtra("dosageVal"));
            //frequency.setText(myIntent.getStringExtra("frequencyValue"));
            time1.setText(myIntent.getStringExtra("time1Val"));
            time2.setText(myIntent.getStringExtra("time2Val"));
            time3.setText(myIntent.getStringExtra("time3Val"));
            time4.setText(myIntent.getStringExtra("time4Val"));
        }
        dbHandler = new com.healthtracker.empoweringtechnologies.healthtracker.MyDBHandler(this, null, null, 1);

    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoMedications(View view) {
        Intent intent = new Intent(this, Medications.class);
        startActivity(intent);
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

    /* method to add or save activity data */
    public void RecordMedication(View view) {
        MedicationData medicationdata = new MedicationData(medication.getText().toString(), dosage.getText().toString(), time1.getText().toString(), time2.getText().toString(), time3.getText().toString(), time4.getText().toString());
        /* validations on user iput data */
        if (medication.getText().toString().length() == 0) {
            medication.setError("Medication is required!");
            return;
        }
        if (dosage.getText().toString().length() == 0) {
            dosage.setError("Dosage is required!");
            return;
        }

        if (time1.getText().toString().length() == 0) {
            time1.setError("Time time is required!");
            return;
        }


        if (idValue > 0)
            dbHandler.UpdateMedications(medicationdata, idValue);
        else
            dbHandler.SaveMedications(medicationdata);
        Clear();

        Intent intent = new Intent(this, MedicationNotifications.class);
        startActivity(intent);
    }

    public void Clear() {
        medication.setText("");
        dosage.setText("");
        //frequency.setText("");
        time1.setText("");
        time2.setText("");
        time3.setText("");
        time4.setText("");

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void ClearAll(View view) {
        Clear();
    }
}
