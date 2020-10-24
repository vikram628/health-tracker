package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Medications extends AppCompatActivity {
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Populate the medication data  */
        PopulateMedicationData();
    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AddMedication(View view)
    {
        Intent intent = new Intent(this, AddMedications.class);
        startActivity(intent);
    }


    /* method to navigate to Reports page */
    private void PopulateMedicationData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.MyListView);

        Cursor cursor = dbHandler.FetchMedicationInfo();
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_MEDICATION,
                dbHandler.COLUMN_DOSAGE,
                //dbHandler.COLUMN_FREQUENCY,
                dbHandler.COLUMN_TIME1,
                dbHandler.COLUMN_TIME2,
                dbHandler.COLUMN_TIME3,
                dbHandler.COLUMN_TIME4
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.medication,
                R.id.dosage,
                R.id.time1,
                R.id.time2,
                R.id.time3,
                R.id.time4
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.custom_row,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.MyListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String medicationVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_MEDICATION));
                final String dosageVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_DOSAGE));
                final String time1Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TIME1));
                final String time2Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TIME2));
                final String time3Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TIME3));
                final String time4Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TIME4));
                //final int positionToRemove = position;

                final ListView lv = (ListView) listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(Medications.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Medications.this,com.healthtracker.empoweringtechnologies.healthtracker.AddMedications.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("medicationVal", medicationVal);
                        intent.putExtra("dosageVal", dosageVal);
                        intent.putExtra("time1Val", time1Val);
                        intent.putExtra("time2Val", time2Val);
                        intent.putExtra("time3Val", time3Val);
                        intent.putExtra("time4Val", time4Val);

                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateMedicationData();
                    }
                });
                dialog.show();

            }
        });
    }
}

