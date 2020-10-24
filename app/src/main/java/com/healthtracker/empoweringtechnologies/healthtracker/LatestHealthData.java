package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;



import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LatestHealthData extends AppCompatActivity {
    private SimpleCursorAdapter dataAdapter;
    private SimpleDateFormat dateFormatter;
    public static final String HealthReportsFolder = "HealthReports";
    public String mCurrentPDFPath;
    public static final String HealthReport = "report.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_health_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Populate the medication data  */
        PopulateLatestHealthData();
    }

    private void PopulateLatestHealthData() {
        PopulateLaestWeightData();
        PopulateLatestBPData();
        PopulateLatestLipidData();
    }


    private void PopulateLatestBPData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.BPListView);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Cursor cursor = dbHandler.FetchBPInfo("1");
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

    }

    private void PopulateLatestLipidData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.LipidListView);

        Cursor cursor = dbHandler.FetchLipidInfo("1");
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

    }

    /* method to navigate to Reports page */
    private void PopulateLaestWeightData() {
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        ListView lv = (ListView) findViewById(R.id.WeightListView);

        Cursor cursor = dbHandler.FetchWeightInfo("1");
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

            }
        });
    }

    public void SendMail(View view)
    {
        try {
            CreateReport();
        }
        catch(Exception e)
        {e.getMessage();}

    }

    public void SendMail()
    {
        try {
            CreateReport();
        }
        catch(Exception e)
        {e.getMessage();}

    }

    public void CreateReport() throws IOException {
        File file =  createPDFFile();
        //file.getParentFile().mkdirs();
        createPdf(file.getPath());
        String filepath = file.getPath();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "vikramrk3@gmail.com" });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Health Report " );
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Please see health report");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filepath));
        startActivity(shareIntent);
    }

    public void createPdf(String dest) throws IOException {
        Document document = new Document();
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String reportRow;
        StringBuilder sb = new StringBuilder(10000);

        // step 2
        try {
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4

            document.add(new Paragraph("Health Report ..."));
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES );

            /*

            Image img = Image.getInstance(storageDir + "/ProfilePicture.jpg");
            document.add(img);
            */

            Cursor cursor = dbHandler.FetchWeightInfo("1");
            cursor.moveToFirst();
            sb.append("Waist Profile");
            sb.append("\n");
            sb.append("Weight:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WEIGHT)));
            sb.append("\n");
            sb.append("Height:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WAIST)));
            sb.append("\n");
            sb.append("\n");

            cursor = dbHandler.FetchBPInfo("1");
            cursor.moveToFirst();
            sb.append("Blood Pressure Profile");
            sb.append("\n");
            sb.append("Systolic:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)));
            sb.append("\n");
            sb.append("Diastolic:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_DIASTOLIC)));
            sb.append("\n");
            sb.append("Heart Rate:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_HEARTRATE)));
            sb.append("\n");
            sb.append("\n");

            cursor = dbHandler.FetchLipidInfo("1");
            cursor.moveToFirst();
            sb.append("Lipid Profile");
            sb.append("\n");
            sb.append("Cholesterol:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TOTALCHOLESTEROL)));
            sb.append("\n");
            sb.append("HDL:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_HDL)));
            sb.append("\n");
            sb.append("LDL:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_LDL)));
            sb.append("\n");
            sb.append("Triglyucerides:");
            sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_Triglycerides)));
            sb.append("\n");
            sb.append("\n");


            document.add(new Paragraph(sb.toString()));
            document.add(new Paragraph("End "));

        }
        catch(Exception ex){
            ex.getMessage();
        }
        // step 5
        document.close();
    }

    /* method to create the picture file location */
    public File createPDFFile() throws IOException {
        // Create PDF  file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String PDFFileName = "HealthReport" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS );
        File PDFFile = null;
        try {

            PDFFile = File.createTempFile(
                    PDFFileName,  /* prefix */
                    ".pdf",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPDFPath = "file:" + PDFFile.getAbsolutePath();

        }
        catch (Exception ex) {
            ex.getMessage();
        }
        return PDFFile;
    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
