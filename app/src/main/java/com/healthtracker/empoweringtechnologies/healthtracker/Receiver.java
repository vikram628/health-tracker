package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Receiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
		/*Intent service1 = new Intent(context, MyAlarmService.class);
	     context.startService(service1);*/
        Log.i("App", "called receiver method");
        MedicationNotifications medicationnotifications = new MedicationNotifications();
        try{
           // medicationnotifications.SendNotification();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}