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

public class ShowHealthGoals extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_health_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Populate the medication data  */
        PopulateHealthGoalsData();
    }

    public void AddHealthGoalData(View view)
    {
        Intent intent = new Intent(this, Goal.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateHealthGoalsData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.HealthGoalsListView);

        Cursor cursor = dbHandler.FetchHealthGoalInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_GOAL,
                dbHandler.COLUMN_TARGETDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.healthgoal,
                R.id.targetdate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.healthgoals,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.HealthGoalsListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String healthgoalVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_GOAL));
                final String targetdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TARGETDATE));

                //final int positionToRemove = position;

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowHealthGoals.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowHealthGoals.this,com.healthtracker.empoweringtechnologies.healthtracker.Goal.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("healthgoalVal", healthgoalVal);
                        intent.putExtra("targetdateVal", targetdateVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateHealthGoalsData();
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
