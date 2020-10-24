package com.healthtracker.empoweringtechnologies.healthtracker;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.healthtracker.empoweringtechnologies.healthtracker.AddMedications;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void GoAppointments(View view)
    {
        Intent intent = new Intent(this, MedicalAppointments.class);
        startActivity(intent);
    }
    public void GoMedications(View view)
    {
        Intent intent = new Intent(this, Medications.class);
        startActivity(intent);
    }
    public void ShowGraphs(View view)
    {
        Intent intent = new Intent(this, com.healthtracker.empoweringtechnologies.healthtracker.Graphs.class);
        startActivity(intent);
    }

    public void TrackVitals(View view)
    {
        Intent intent = new Intent(this, HomePage.class);
        //Intent intent = new Intent(this, MedicationNotifications.class);
        startActivity(intent);
    }

    public void ShowProfile(View view)
    {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void GoHealthGoals(View view)
    {
        Intent intent = new Intent(this, ShowHealthGoals.class);
        startActivity(intent);
    }


    public void GoVital(View view)
    {
        Intent intent = new Intent(this, VitalsPage.class);
        startActivity(intent);
    }
}
