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

import java.text.SimpleDateFormat;
import java.util.Locale;


public class ShowBPData extends AppCompatActivity {
    private SimpleCursorAdapter dataAdapter;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bpdata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Populate the medication data  */
        PopulateBPData();
    }

    public void AddBPData(View view)
    {
        Intent intent = new Intent(this, BP.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateBPData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.BPListView);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Cursor cursor = dbHandler.FetchBPInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_SYSTOLIC,
                dbHandler.COLUMN_DIASTOLIC,
                dbHandler.COLUMN_HEARTRATE,
                dbHandler.COLUMN_BPDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.systolic,
                R.id.diastolic,
                R.id.heartrate,
                R.id.bpdate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.bprow,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.BPListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String systolicVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC));
                final String diastolicVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_DIASTOLIC));
                final String heartrateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_HEARTRATE));
                final String bpdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_BPDATE));

                //final int positionToRemove = position;

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowBPData.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowBPData.this,com.healthtracker.empoweringtechnologies.healthtracker.BP.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("systolicVal", systolicVal);
                        intent.putExtra("diastolicVal", diastolicVal);
                        intent.putExtra("heartrateVal", heartrateVal);
                        intent.putExtra("bpdateVal", bpdateVal);

                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteBPItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateBPData();
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

    public void GoBPGraph(View view)
    {
        Intent intent = new Intent(this, BPGraphs.class);
        startActivity(intent);
    }
}
