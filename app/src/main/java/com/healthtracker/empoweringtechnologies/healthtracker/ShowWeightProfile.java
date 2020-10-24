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


public class ShowWeightProfile extends AppCompatActivity {
    private SimpleCursorAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weight_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Populate the medication data  */
        PopulateWeightData();
    }

    /* method to navigate to Reports page */
    private void PopulateWeightData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.WeightListView);

        Cursor cursor = dbHandler.FetchWeightInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_WEIGHT,
                dbHandler.COLUMN_HEIGHT,
                dbHandler.COLUMN_WAIST,
                dbHandler.COLUMN_WEIGHTDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.weight,
                R.id.height,
                R.id.waist,
                R.id.weightdate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.weightrow,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.WeightListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String weightVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_WEIGHT));
                final String heightVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_HEIGHT));
                final String waistVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_WAIST));
                final String weightdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_WEIGHTDATE));

                //final int positionToRemove = position;

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowWeightProfile.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowWeightProfile.this,com.healthtracker.empoweringtechnologies.healthtracker.Weight.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("weightVal", weightVal);
                        intent.putExtra("heightVal", heightVal);
                        intent.putExtra("waistVal", waistVal);
                        intent.putExtra("weightdateVal", weightdateVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteWeightItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateWeightData();
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

    public void AddWeightData(View view)
    {
        Intent intent = new Intent(this, Weight.class);
        startActivity(intent);
    }


    public void GoWeightGraphs(View view)
    {
        Intent intent = new Intent(this, WeightGraphs.class);
        startActivity(intent);
    }
}
