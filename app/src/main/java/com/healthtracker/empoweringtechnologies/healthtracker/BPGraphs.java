package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import java.text.Format;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.Locale;

import static android.R.attr.format;


public class BPGraphs extends AppCompatActivity {

    public MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpgraphs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new MyDBHandler(this, null, null, 1);
        ShowSystolicGraph();
        showDiastolicGraph();
    }

    public void ShowSystolicGraph() {
        DateFormat format = new SimpleDateFormat("MM /dd /yyyy", Locale.US);
        GraphView graph = (GraphView) findViewById(R.id.Systolicgraph);
        Cursor cursor = dbHandler.FetchBPInfo("100");
        String DataPointString = null;
        Date bpdateitem = null;
        String[] horizontals = new String[cursor.getCount()];

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = 0;
            try {
                bpdateitem = format.parse(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_BPDATE)));
            } catch (Exception e) {

            }

            //DataPointString = "new DataPoint(" + bpdateitem + "," + cursor.getShort(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)) + ")";
            DataPointString = "new DataPoint(" + count + "," + cursor.getShort(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)) + ")";
            while (cursor.isAfterLast() == false) {
                DataPointString = DataPointString + ",";
                try {
                    bpdateitem = format.parse(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_BPDATE)));
                } catch (Exception e) {

                }
                //DataPointString = DataPointString + "new DataPoint(" + bpdateitem + "," + cursor.getShort(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)) + ")";
                DataPointString = DataPointString + "new DataPoint(" + count + "," + cursor.getShort(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC)) + ")";
                cursor.moveToNext();
                count++;
            }

            BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
            DataPoint[] values = new DataPoint[cursor.getCount()];


            DateFormat formatter = new SimpleDateFormat("MM /dd /yyyy");
            cursor.moveToFirst();
            count = 0;
            DateFormat df = new SimpleDateFormat("MM/dd/yy");

            for (int i = 0; i < cursor.getCount(); i++) {
                Integer xi = cursor.getInt(cursor.getColumnIndex(dbHandler.COLUMN_SYSTOLIC));
                try {
                    Date yi = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_BPDATE)));
                    horizontals[count] = df.format(yi);
                    ;
                    //DataPoint v = new DataPoint(yi, xi);
                    DataPoint v = new DataPoint(count, xi);
                    values[i] = v;
                } catch (Exception e) {
                }

                cursor.moveToNext();
                count++;
            }
            series = new BarGraphSeries<>(values);
            // styling series
            series.setTitle("Systolic");
            series.setColor(Color.GREEN);
            series.setSpacing(10);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });


            graph.addSeries(series);

            //graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
            graph.getGridLabelRenderer().setVerticalAxisTitle("Systloic");
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Timeline");

            if (count > 1) {
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(horizontals);
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            }

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }
    }

    public void showDiastolicGraph() {
        DateFormat format = new SimpleDateFormat("MM /dd /yyyy", Locale.US);
        GraphView graph = (GraphView) findViewById(R.id.Diastolicgraph);
        Cursor cursor = dbHandler.FetchBPInfo("100");
        String DataPointString = null;
        Date bpdateitem = null;
        String[] horizontals = new String[cursor.getCount()];

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = 0;
            try {
                bpdateitem = format.parse(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_BPDATE)));
            } catch (Exception e) {

            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
            DataPoint[] values = new DataPoint[cursor.getCount()];


            DateFormat formatter = new SimpleDateFormat("MM /dd /yyyy");
            cursor.moveToFirst();
            count = 0;
            DateFormat df = new SimpleDateFormat("MM/dd/yy");

            for (int i = 0; i < cursor.getCount(); i++) {
                Integer xi = cursor.getInt(cursor.getColumnIndex(dbHandler.COLUMN_DIASTOLIC));
                try {
                    Date yi = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_BPDATE)));
                    horizontals[count] = df.format(yi);
                    //DataPoint v = new DataPoint(yi, xi);
                    DataPoint v = new DataPoint(count, xi);
                    values[i] = v;
                } catch (Exception e) {
                }

                cursor.moveToNext();
                count++;
            }
            series = new BarGraphSeries<>(values);
            // styling series
            series.setTitle("Systolic");
            series.setColor(Color.GREEN);
            series.setSpacing(10);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            graph.addSeries(series);
            // set date label formatter
            //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

            //graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
            graph.getGridLabelRenderer().setVerticalAxisTitle("Diastolic");
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Timeline");

            if (count > 1) {
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(horizontals);
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            }

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }
    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}