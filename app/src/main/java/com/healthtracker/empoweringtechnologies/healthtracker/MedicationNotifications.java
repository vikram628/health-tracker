package com.healthtracker.empoweringtechnologies.healthtracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.PendingIntent;
import android.app.AlarmManager;

import android.app.Notification;
import android.content.Context;
import android.os.SystemClock;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MedicationNotifications extends AppCompatActivity {
    NotificationCompat.Builder notification;
    private static final int uniqueid = 12345; //random number
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    public Calendar calendar;
    java.text.SimpleDateFormat format;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_notifications);


        SetNotifications();

        Intent intent = new Intent(this, AddMedications.class);
        startActivity(intent);
    }

    private Date converttoDate(String Val) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr = "";
        Date newDate = null;

        int hours = Integer.parseInt(Val.substring(0, 2));
        int minutes = Integer.parseInt(Val.substring(3, 5));

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);

        datestr = year + "-" + month + "-" + day + " " + Val + ":00";
        try {
            newDate = format.parse(datestr);
        } catch (Exception ex) {
            Log.i("Error", ex.getMessage());
        }

        return newDate;
    }

    public void SetNotifications() {


        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Cursor cursor = null;
        try {
            cursor = dbHandler.FetchMedicationInfo();
        } catch (Exception ex) {
            Log.i("Error", ex.getMessage());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        long millseconds;

        cursor.moveToFirst();
        if (!(cursor.isNull(2))) {
            try {
                Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME1)));
                Date Date2 = new Date();
                millseconds = Date1.getTime() - Date2.getTime();
                if (millseconds > 0) {
                    scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                }
            } catch (Exception ex) {
                Log.i("Error", ex.getMessage());
            }
        }

        if (!(cursor.isNull(3))) {
            try {
                Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME2)));
                Date Date2 = new Date();
                millseconds = Date1.getTime() - Date2.getTime();
                if (millseconds > 0) {
                    scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                }
            } catch (Exception ex) {
                Log.i("Error", ex.getMessage());
            }
        }

        if (!(cursor.isNull(4))) {
            try {
                Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME3)));
                Date Date2 = new Date();
                millseconds = Date1.getTime() - Date2.getTime();
                if (millseconds > 0) {
                    scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                }
            } catch (Exception ex) {
                Log.i("Error", ex.getMessage());
            }
        }

        if (!(cursor.isNull(5))) {
            try {
                Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME4)));
                Date Date2 = new Date();
                millseconds = Date1.getTime() - Date2.getTime();
                if (millseconds > 0) {
                    scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                }
            } catch (Exception ex) {
                Log.i("Error", ex.getMessage());
            }
        }

        try {
            while (cursor.moveToNext()) {
                if (!(cursor.isNull(2))) {
                    try {
                        Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME1)));
                        Date Date2 = new Date();
                        millseconds = Date1.getTime() - Date2.getTime();
                        if (millseconds > 0) {
                            scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                        }
                    } catch (Exception ex) {
                        Log.i("Error", ex.getMessage());
                    }
                }

                if (!(cursor.isNull(3))) {
                    try {
                        Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME2)));
                        Date Date2 = new Date();
                        millseconds = Date1.getTime() - Date2.getTime();
                        if (millseconds > 0) {
                            scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                        }
                    } catch (Exception ex) {
                        Log.i("Error", ex.getMessage());
                    }
                }

                if (!(cursor.isNull(4))) {
                    try {
                        Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME3)));
                        Date Date2 = new Date();
                        millseconds = Date1.getTime() - Date2.getTime();
                        if (millseconds > 0) {
                            scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                        }
                    } catch (Exception ex) {
                        Log.i("Error", ex.getMessage());
                    }
                }

                if (!(cursor.isNull(5))) {
                    try {
                        Date Date1 = converttoDate(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_TIME4)));
                        Date Date2 = new Date();
                        millseconds = Date1.getTime() - Date2.getTime();
                        if (millseconds > 0) {
                            scheduleNotification(getNotification(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_MEDICATION))), millseconds);
                        }
                    } catch (Exception ex) {
                        Log.i("Error", ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            Log.i("Error", ex.getMessage());
        } finally {
            cursor.close();
        }

    }

    public void ScheduleAlarm() {
        // Set the alarm to start at approximately 2:00 p.m.
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 14);
        //modify this for alarms at desired frequencies

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Take medication:");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.heart);
        return builder.build();
    }

}
