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


public class ShowLipidProfile extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lipid_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /* Populate the medication data  */
        PopulateLipidData();
    }
    public void AddLipidData(View view)
    {
        Intent intent = new Intent(this, Lipid.class);
        startActivity(intent);
    }

    /* method to navigate to Reports page */
    private void PopulateLipidData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.LipidListView);

        Cursor cursor = dbHandler.FetchLipidInfo("100");
        {

            // The desired columns to be bound
        }
        String[] columns = new String[]{
                //dbHandler.COLUMN_ROWID,
                dbHandler.COLUMN_TOTALCHOLESTEROL,
                dbHandler.COLUMN_LDL,
                dbHandler.COLUMN_HDL,
                dbHandler.COLUMN_Triglycerides,
                dbHandler.COLUMN_LIPIDDATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.totalcholesterol,
                R.id.ldl,
                R.id.hdl,
                R.id.triglycerides,
                R.id.lipiddate
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.lipidrow,
                cursor,
                columns,
                to,
                0);

        final ListView listView = (ListView) findViewById(R.id.LipidListView);
        final Cursor mycursor = cursor;
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) dataAdapter.getItemId(position);
                final String totalcholesterolVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_TOTALCHOLESTEROL));
                final String ldlVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_LDL));
                final String hdlVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_HDL));
                final String triglyceridesVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_Triglycerides));
                final String lipiddateVal = mycursor.getString(mycursor.getColumnIndex(dbHandler.COLUMN_LIPIDDATE));

                //final int positionToRemove = position;

                final ListView lv = (ListView)listView;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowLipidProfile.this);
                dialog.setTitle("Update");
                dialog.setMessage("Do you want to Edit or Delete?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Edit", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowLipidProfile.this,com.healthtracker.empoweringtechnologies.healthtracker.Lipid.class);
                        intent.putExtra("idValue", RoWIDEdit);
                        intent.putExtra("totalcholesterolVal", totalcholesterolVal);
                        intent.putExtra("ldlVal", ldlVal);
                        intent.putExtra("hdlVal", hdlVal);
                        intent.putExtra("triglyceridesVal", triglyceridesVal);
                        intent.putExtra("lipiddateVal", lipiddateVal);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.DeleteItem(RoWIDEdit);
                        dataAdapter.notifyDataSetChanged();
                        PopulateLipidData();
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

    public void GoLipidGraphs(View view)
    {
        Intent intent = new Intent(this, LipidGraphs.class);
        startActivity(intent);
    }

}
