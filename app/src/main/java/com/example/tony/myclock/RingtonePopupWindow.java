package com.example.tony.myclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RingtonePopupWindow extends Activity  {

    private ListView listView=null;
    private RadioButton listRadioButton = null;
    //private RadioButton itemRadioButton = null;
    private Button saveButton = null;
    private LinearLayout popLayout = null;
    private RingtoneAdapter ringAdapter = null;
    private List <Map<String, Object>> list = null;
    private MediaPlayer mMediaPlayer = null;
    private SharedPreferences settings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_popup_window);

        listView=(ListView)findViewById(R.id.ring);
        list = getData();
        ringAdapter = new RingtoneAdapter(list,this);

        listView.setAdapter(ringAdapter);
        //saveButton = (Button) findViewById(R.id.ring_confirm_bt);
        //popLayout = (LinearLayout)findViewById(R.id.popup_layout);

        /*popLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); */

        saveButton = (Button) findViewById(R.id.ring_confirm_bt);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RingtonePopupWindow.this,AlarmActivity.class);
                i.putExtra("listRadioButtonName",listRadioButton.getText());
                //startActivity(i);
                //startActivityForResult(i,0);
                //mMediaPlayer.stop();

                setResult(0,i);
                mMediaPlayer.release();

                finish();
            }
        });

    }

    // destroy this popup window when click on the area outside of the popup layout
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("title", "ring"+i);
            list.add(map);

        }
        return list;
    }


    // add a logic for single choice of a radio button
    // set click events
    int listIndex = -1;

    public void onClickRadioButton(View v) {
        View vMain = ((View) v.getParent());
        //Log.d("View", vMain.toString());

        // uncheck previous checked button.
        if (listRadioButton != null) listRadioButton.setChecked(false);

        // assign to the variable the new one
        listRadioButton = (RadioButton) v;

        // find if the new one is checked or not, and set "listIndex"
        if (listRadioButton.isChecked()) {
            listIndex = ((ViewGroup) vMain.getParent()).indexOfChild(vMain);
            Log.d("List",Integer.toString(listIndex));

            switch (listIndex){
                case 0:
                    settings = getSharedPreferences("RingPrefs", 0);
                    SharedPreferences.Editor editor0 = settings.edit();
                    editor0.putInt("ring", 0);
                    editor0.commit();

                    Uri alert1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer = MediaPlayer.create(RingtonePopupWindow.this, alert1);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMediaPlayer.stop();
                        }
                    }, 1000);//milliseconds


                    break;
                case 1:
                    settings = getSharedPreferences("RingPrefs", 0);
                    SharedPreferences.Editor editor1 = settings.edit();
                    editor1.putInt("ring", 1);
                    editor1.commit();

                    Uri alert2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer = MediaPlayer.create(RingtonePopupWindow.this, alert2);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMediaPlayer.stop();
                        }
                    }, 1000);//milliseconds

                    break;
                case 2:
                    settings = getSharedPreferences("RingPrefs", 0);
                    SharedPreferences.Editor editor3 = settings.edit();
                    editor3.putInt("ring", 2);
                    editor3.commit();

                    Uri alert3 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer = MediaPlayer.create(RingtonePopupWindow.this, alert3);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMediaPlayer.stop();
                        }
                    }, 1000);//milliseconds

                    break;

            }

        } else {
            listRadioButton = null;
            listIndex = -1;
        }

    }





}
