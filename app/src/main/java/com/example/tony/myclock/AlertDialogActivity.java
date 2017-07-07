package com.example.tony.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class AlertDialogActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    private SharedPreferences settings;
    TextView textView;
    private Button btnBack;
    private Button btnAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        ActivityCollector.addActivity(this);

        btnBack = (Button)findViewById(R.id.button2);
        btnAgain = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView3);


        settings = getSharedPreferences("Prefs", 0);
        String reminder = settings.getString("k","You have an alarm!!!");
        int ringNum = settings.getInt("ring", 0);

        textView.setText(reminder);


        Uri alert1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri alert2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Uri alert3 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mMediaPlayer = new MediaPlayer();
        if(ringNum == 0) {
            mMediaPlayer = MediaPlayer.create(this, alert1);
        }else if(ringNum == 1){
            mMediaPlayer = MediaPlayer.create(this, alert2);
        }else{
            mMediaPlayer = MediaPlayer.create(this, alert3);
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backMain);
                //mMediaPlayer.stop();
                mMediaPlayer.release();
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("hour");
                editor.remove("minute");
                editor.commit();

                finish();

            }
        });
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hourOfDay  = settings.getInt("hour", 0);
                int minute  = settings.getInt("minute", 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE, minute+10);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Intent intent1= new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent1);
                mMediaPlayer.release();

                int newMinute = minute+10;
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("hour", hourOfDay);
                editor.putInt("minute", newMinute);
                editor.commit();

                finish();
            }
        });

    }
}
