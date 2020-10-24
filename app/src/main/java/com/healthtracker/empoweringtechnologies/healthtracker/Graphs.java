package com.healthtracker.empoweringtechnologies.healthtracker;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
//import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Graphs extends AppCompatActivity {
    public MyDBHandler dbHandler;
    public String mCurrentPDFPath;
    public static int Write_permissions = 0;
    private static final String TAG = "Profile";

    public LatestHealthData lhd = new LatestHealthData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new MyDBHandler(this, null, null, 1);

    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoWeightGraph() {
        Intent intent = new Intent(this, WeightGraphs.class);
        startActivity(intent);
    }

    public void GoBPGraph() {
        Intent intent = new Intent(this, BPGraphs.class);
        startActivity(intent);
    }

    public void GoLipidGraph() {
        Intent intent = new Intent(this, LipidGraphs.class);
        startActivity(intent);
    }

    public void GoReport(View view) {
        Intent intent = new Intent(this, LatestHealthData.class);
        startActivity(intent);
    }

    public void EMailReport(View view) {
        SendMail();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.weightgraph:
                if (checked)
                    GoWeightGraph();
                break;
            case R.id.bpgraph:
                if (checked)
                    GoBPGraph();
                break;
            case R.id.lipidgraph:
                if (checked)
                    GoLipidGraph();
                break;
        }
    }

    public void SendMail() {
        try {
            CreateReport();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void CreateReport() throws IOException {
        File file = createPDFFile();
        //file.getParentFile().mkdirs();
        createPdf(file.getPath());
        String filepath = file.getPath();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"vikramrk3@gmail.com"});
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Health Report ");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Please see health report");
        //shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filepath));
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(shareIntent);
    }

    public void createPdf(String dest) throws IOException {
        Document document = new Document();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String reportRow;
        StringBuilder sb = new StringBuilder(10000);
        Cursor cursor;

        // step 2
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            // step 3
            document.open();
            // step 4
            sb.append("                 Health Report           ");
            sb.append("\n");
            sb.append("\n");


            //profile data
            cursor = dbHandler.FetchProfileData("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("Name: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_NAME)));
                sb.append("\n");
                sb.append("Gender: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_GENDER)));
                sb.append("\n");
                sb.append("Date of Birth: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_DOB)));
                sb.append("\n");

                /*
                Image img = Image.getInstance(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_PICTUREPATH)));
                document.add(img);

                */

                sb.append("\n");
                sb.append("\n");
            }

            //Weight
            cursor = dbHandler.FetchWeightInfo("1");
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                sb.append("Height: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_HEIGHT)));
                sb.append("\n");
                sb.append("Weight: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WEIGHT)));
                sb.append("\n");
                sb.append("WAIST: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WAIST)));
                sb.append("\n");
                sb.append("\n");
            }

            //bp
            cursor = dbHandler.FetchBPInfo("1");

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("Systolic: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)));
                sb.append("\n");
                sb.append("Diastolic: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_DIASTOLIC)));
                sb.append("\n");
                sb.append("Heart Rate: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_HEARTRATE)));
                sb.append("\n");
                sb.append("\n");
            }

            cursor = dbHandler.FetchLipidInfo("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("Total Cholesterol: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TOTALCHOLESTEROL)));
                sb.append("\n");
                sb.append("LDL: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_LDL)));
                sb.append("\n");
                sb.append("HDL: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_HDL)));
                sb.append("\n");
                sb.append("Triglycerides: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_Triglycerides)));
                sb.append("\n");
                sb.append("\n");
            }

            cursor = dbHandler.FetchGlucoseFunctionInfo("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("Glucose: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_GLUCOSE)));
                sb.append("\n");
                sb.append("\n");
            }

            cursor = dbHandler.FetchThyroidFunctionInfo("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("TSH: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TSH)));
                sb.append("\n");
                sb.append("FreeT4: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_FREET4)));
                sb.append("\n");
                sb.append("FreeT3: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_FREET3)));
                sb.append("\n");
                sb.append("\n");
            }

            cursor = dbHandler.FetchKidneyFunctionInfo("1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sb.append("GFR: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_GFR)));
                sb.append("\n");
                sb.append("Creatinine: ");
                sb.append(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_CREATININE)));
                sb.append("\n");
                sb.append("\n");
            }


            document.add(new Paragraph(sb.toString()));
            document.add(new Paragraph("End "));

        } catch (Exception ex) {
            ex.getMessage();
        }
        // step 5
        document.close();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            Write_permissions = 1;
        }
    }


    /* method to create the picture file location */
    public File createPDFFile() throws IOException {
        // Create PDF  file name
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String PDFFileName = "HealthReport" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File PDFFile = null;

        if (isStoragePermissionGranted() || (Write_permissions == 1)) {
            try {

                PDFFile = File.createTempFile(
                        PDFFileName,  /* prefix */
                        ".pdf",         /* suffix */
                        storageDir      /* directory */
                );
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPDFPath = "file:" + PDFFile.getAbsolutePath();

            } catch (Exception ex) {
                ex.getMessage();
            }
        }
        return PDFFile;
    }

    public void FindNearestHospitals(View view) {
        Intent intent = new Intent(this, NearestMedicalLocation.class);
        startActivity(intent);
    }

}
