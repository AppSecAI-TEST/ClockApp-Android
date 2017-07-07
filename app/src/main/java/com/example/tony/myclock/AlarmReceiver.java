package com.example.tony.myclock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private Vibrator vibrator;
    //private SharedPreference settings;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.d("Receiver", "alarm rings");

        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        //settings = (SharedPreference) context.getSharedPreferences("Prefs", 0);

        //Log.d("settings in recevier",settings.toString());

        boolean vibratorStatus = intent.getBooleanExtra("vibration",false);


        if (vibratorStatus){
            vibrator.vibrate(10000);
            Log.d("vibrate",Boolean.toString(true));
        } else {
            //vibrator.cancel();
            Log.d("vibrate",Boolean.toString(false));
        }


        Intent alarmIntent = new Intent(context,AlertDialogActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
