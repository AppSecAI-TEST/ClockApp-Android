package com.example.tony.myclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySurfaceView mySurfaceView = null;
    private FloatingActionButton faButton = null;
    private SharedPreferences alarmSettings;
   // private SharedPreference ringSettings;
    private TextView alarmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add main activity to activity list
        ActivityCollector.addActivity(this);


        setContentView(R.layout.layout_main);

        mySurfaceView = new MySurfaceView(this);
        RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.findViewById(R.id.main_layout);

        //Log.d("Layout", myLayout.toString());

        myLayout.addView(mySurfaceView);

        faButton = (FloatingActionButton) findViewById(R.id.fab);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        alarmText = (TextView)findViewById(R.id.alarm_text);
        alarmSettings = getSharedPreferences("Prefs", 0);
        int hourOfDay  = alarmSettings.getInt("hour", -1);
        int minute  = alarmSettings.getInt("minute", -1);

        if (hourOfDay == -1 || minute == -1) {
            alarmText.setText("No Alarm");

        } else {
            alarmText.setText("Alarm: " + hourOfDay + ":" + minute);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.update:
                alarmSettings = getSharedPreferences("Prefs", 0);
                int hourOfDay  = alarmSettings.getInt("hour", -1);
                int minute  = alarmSettings.getInt("minute", -1);

                if (hourOfDay == -1 || minute == -1) {
                    Toast.makeText(MainActivity.this, "No alarm needs to be updated!",
                            Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(this, AlarmUpdateActivity.class);
                    startActivity(intent);
                    return true;
                }
                break;
            case R.id.color:
                Intent colorIntent = new Intent(this,ColorSettingActivity.class);
                startActivity(colorIntent);
                break;

            case R.id.clear:
                alarmSettings = getSharedPreferences("Prefs", 0);
                SharedPreferences.Editor editor = alarmSettings.edit();
                editor.clear();
                editor.commit();
                alarmText.setText("No Alarm");
                Toast.makeText(this,"Alarm has been cleared", Toast.LENGTH_LONG).show();

                break;

            case R.id.exit:
                ActivityCollector.finishAll();
                finish();

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
