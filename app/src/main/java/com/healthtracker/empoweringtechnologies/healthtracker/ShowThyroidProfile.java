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

public class ShowThyroidProfile extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_thyroid_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         /* Populate the medication data  */
        PopulateThyroidFunctionData();
    }
    public void AddKidneyFunction(View view)
    {
        Intent intent = new Intent(this, AddThyroidData.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateThyroidFunctionData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.ThyroidListView);

        Cursor cursor = dbHandler.FetchThyroidFunctionInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_TSH,
                dbHandler.COLUMN_FREET4,
                dbHandler.COLUMN_FREET3,
                dbHandler.COLUMN_THYROIDTESTDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.tsh,
                R.id.freet4,
                R.id.freet3,
                R.id.thyroiddate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.thyroidrow,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.ThyroidListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String tshVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TSH));
                final String freet4Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_FREET4));
                final String freet3Val = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_FREET3));
                final String thyroidtestdateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_THYROIDTESTDATE));

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowThyroidProfile.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowThyroidProfile.this,com.healthtracker.empoweringtechnologies.healthtracker.AddThyroidData.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("tshVal", tshVal);
                        intent.putExtra("freet4Val", freet4Val);
                        intent.putExtra("freet3Val", freet3Val);
                        intent.putExtra("thyroidtestdateVal", thyroidtestdateVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateThyroidFunctionData();
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

    public void AddThyroidData(View view)
    {
        Intent intent = new Intent(this, AddThyroidData.class);
        startActivity(intent);
    }
}
