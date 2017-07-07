package com.example.tony.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TextView ringText;
    private TimePicker timePicker = null;
    private EditText labelEditText;
    private PendingIntent pendingIntent;
    private SharedPreferences settings;
    private ImageButton okImageButton;
    private ImageButton backImageButton;
    private String label;
    private Vibrator vibrator;
    private boolean vibratorOn = false;

    AlarmManager alarmManager;
    int hour;
    int min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm);

        ringText = (TextView)findViewById(R.id.ring_selected_tv);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        okImageButton = (ImageButton)findViewById(R.id.ok_bt);
        labelEditText = (EditText)findViewById(R.id.alarm_et);
        backImageButton = (ImageButton)findViewById(R.id.cancel_bt);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //label = labelEditText.getText().toString();

        //Intent intent = getIntent();
        //String str = intent.getStringExtra("listRadioButtonName");
        //ringText.setText(str);

        settings = this.getSharedPreferences("Prefs", 0);

        Log.d("SharedPreference",settings.toString());

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

        okImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = settings.edit();
                label = labelEditText.getText().toString();
                if (!label.isEmpty()) {
                    editor.putString("labelEditText",label);
                    editor.commit();
                } else {
                    editor.putString("labelEditText","");
                    editor.commit();
                }

                // store the status of the vibrator
                if (vibratorOn) {
                    editor.putBoolean("vibratorStatus", true);
                    editor.commit();
                    Log.d("vibrateButton","true");
                } else {
                    editor.putBoolean("vibratorStatus", false);
                    editor.commit();
                }


                boolean vibratorStatus = settings.getBoolean("vibratorStatus",false);

                Log.d("vibratorStatus",Boolean.toString(vibratorStatus));


                int hourOfDay  = settings.getInt("hour", 0);
                int minute  = settings.getInt("minute", 0);


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 00);
                calendar.set(Calendar.MILLISECOND, 00);

                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                intent.putExtra("vibration",vibratorStatus);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                Intent mainIntent = new Intent(AlarmActivity.this,MainActivity.class);
                startActivity(mainIntent);
                //finish();

                Toast.makeText(AlarmActivity.this, "Alarm Set Successfully", Toast.LENGTH_LONG).show();
            }
        });



        ToggleButton vibrateButton = (ToggleButton) findViewById(R.id.switch_one);
        ToggleButton deleteButton = (ToggleButton) findViewById(R.id.switch_two);

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

        // start ringtone activity
        RelativeLayout ringLayout = (RelativeLayout) findViewById(R.id.ringtone_layout);
        ringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ringIntent = new Intent(AlarmActivity.this, RingtonePopupWindow.class);

                startActivityForResult(ringIntent,0);
            }
        });

        Log.i("Alarm Activity", (savedInstanceState != null)? "Y" : "N");

        if(savedInstanceState != null){
            label = savedInstanceState.getString("label");
            labelEditText.setText(label);
        }

    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);

        Log.i("AlarmActivity", "Save State");
        label = labelEditText.getText().toString();

        outState.putString("label",label);
    }

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        String str = data.getStringExtra("listRadioButtonName");
        ringText.setText(str);

    }
}
