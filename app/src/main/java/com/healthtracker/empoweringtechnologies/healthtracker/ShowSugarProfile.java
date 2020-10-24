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

public class ShowSugarProfile extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sugar_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         /* Populate the medication data  */
        PopulateGlucoseFunctionData();
    }
    public void AddGlucoseFunction(View view)
    {
        Intent intent = new Intent(this, AddSugarData.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateGlucoseFunctionData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.SugarListView);

        Cursor cursor = dbHandler.FetchGlucoseFunctionInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_GLUCOSE,
                dbHandler.COLUMN_GLUCOSETESTDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.glucose,
                R.id.glucosedate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.sugarrow,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.SugarListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String glucoseVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_GLUCOSE));
                final String glucosetestdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_GLUCOSETESTDATE));

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowSugarProfile.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowSugarProfile.this,com.healthtracker.empoweringtechnologies.healthtracker.AddSugarData.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("glucoseVal", glucoseVal);
                        intent.putExtra("glucosetestdateVal", glucosetestdateVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateGlucoseFunctionData();
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

    public void AddSugarData(View view)
    {
        Intent intent = new Intent(this, AddSugarData.class);
        startActivity(intent);
    }


}
