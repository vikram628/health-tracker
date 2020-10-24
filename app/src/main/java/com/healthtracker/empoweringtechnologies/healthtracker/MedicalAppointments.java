package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MedicalAppointments extends AppCompatActivity {
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_appointments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         /* Populate the medication data  */
        PopulateAppointmentData();
    }
    public void AddAppointment(View view)
    {
        Intent intent = new Intent(this, AddAppointment.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateAppointmentData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.AppointmentListView);

        Cursor cursor = dbHandler.FetchAppointmentInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_APPOINTMENTDATE,
                dbHandler.COLUMN_CHECKUP,
                dbHandler.COLUMN_HOSPITAL
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.appointmentdate,
                R.id.checkup,
                R.id.hospital
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.appointments,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.AppointmentListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String appointmentdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_APPOINTMENTDATE));
                final String checkupVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_CHECKUP));
                final String hospitalVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_HOSPITAL));


                //final int positionToRemove = position;

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(MedicalAppointments.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MedicalAppointments.this,com.healthtracker.empoweringtechnologies.healthtracker.AddAppointment.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("appointmentdateVal", appointmentdateVal);
                        intent.putExtra("checkupVal", checkupVal);
                        intent.putExtra("hospitalVal", hospitalVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateAppointmentData();
                    }
                });
                dialog.show();

            }
        });
    }
    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
