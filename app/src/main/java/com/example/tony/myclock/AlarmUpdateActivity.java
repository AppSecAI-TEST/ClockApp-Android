package com.example.tony.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmUpdateActivity extends AppCompatActivity {

    private TextView ringText;
    private TimePicker timePicker = null;
    private EditText labelEditText;
    private PendingIntent pendingIntent;
    private SharedPreferences settings;
    private SharedPreferences ringSettings;
    private Button saveButton;
    private ToggleButton vibrateButton;
    private boolean vibratorOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        ActivityCollector.addActivity(this);

        ringText = (TextView)findViewById(R.id.update_ring_selected_tv);
        timePicker = (TimePicker)findViewById(R.id.update_timePicker);
        saveButton = (Button)findViewById(R.id.update_confirm_bt);
        labelEditText = (EditText)findViewById(R.id.update_alarm_et);
        vibrateButton = (ToggleButton)findViewById(R.id.update_switch_one);

        settings = getSharedPreferences("Prefs", 0);

        Log.d("SharedPreference",settings.toString());

        int hourOfDay  = settings.getInt("hour", 0);
        int minute  = settings.getInt("minute", 0);

        boolean vibrateStatus = settings.getBoolean("vibratorStatus",false);

        if (vibrateStatus){
            vibrateButton.setChecked(true);
        } else {
            vibrateButton.setChecked(false);
        }

        String labelStr = settings.getString("labelEditText","failure");
        labelEditText.setText(labelStr);


        //timePicker.setMinute(minute);
        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(minute);

        ringSettings = getSharedPreferences("RingPrefs", 0);
        int ringNum = ringSettings.getInt("ring",0);
        ringText.setText("ring"+ringNum);




        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("hour", hourOfDay);
                editor.putInt("minute", minute);
                editor.commit();

                Log.d("TimePicker_Setting", hourOfDay+":"+minute);

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = settings.edit();
                if (!labelEditText.getText().toString().isEmpty()) {
                    editor.putString("labelEditText",labelEditText.getText().toString());
                    editor.commit();
                }


                if (vibratorOn) {
                    editor.putBoolean("vibratorStatus", true);
                    editor.commit();
                    Log.d("vibrateButton","true");
                } else {
                    editor.putBoolean("vibratorStatus", false);
                    editor.commit();
                }


                boolean vibratorStatus = settings.getBoolean("vibratorStatus",false);


                int hourOfDay  = settings.getInt("hour", 0);
                int minute  = settings.getInt("minute", 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 00);
                calendar.set(Calendar.MILLISECOND, 00);

                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Intent mainIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(mainIntent);
                //finish();
            }
        });


        ToggleButton vibrateButton = (ToggleButton) findViewById(R.id.update_switch_one);
        ToggleButton deleteButton = (ToggleButton) findViewById(R.id.update_switch_two);

        vibrateButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //add events
                    vibratorOn = true;


                } else {
                    //add events
                    vibratorOn = false;
                    Log.d("virateButton","false");
                }
            }
        });


    }
}
