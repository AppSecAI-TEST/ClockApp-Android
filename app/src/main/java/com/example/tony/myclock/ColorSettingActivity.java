package com.example.tony.myclock;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ColorSettingActivity extends AppCompatActivity {

    private SharedPreferences settings = null;
    private LinearLayout basicColorLayout;
    private LinearLayout rainbowColorLayout;
    private LinearLayout lightColorLayout;
    private LinearLayout threeColorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_setting);

        ActivityCollector.addActivity(this);

        settings = getSharedPreferences("Prefs", 0);

        basicColorLayout = (LinearLayout)findViewById(R.id.basic_color_layout);
        rainbowColorLayout = (LinearLayout)findViewById(R.id.rainbow_color_layout);
        lightColorLayout = (LinearLayout)findViewById(R.id.light_color_layout);
        threeColorLayout = (LinearLayout)findViewById(R.id.three_color_layout);

        basicColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("color", "basic color");
                editor.commit();

                Toast.makeText(ColorSettingActivity.this, "You choose basic color",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        rainbowColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("color", "rainbow color");
                editor.commit();

                Toast.makeText(ColorSettingActivity.this, "You choose rainbow color",Toast.LENGTH_LONG).show();

                finish();
            }
        });


        lightColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("color", "light color");
                editor.commit();

                Toast.makeText(ColorSettingActivity.this, "You choose light color",Toast.LENGTH_LONG).show();
                finish();
            }
        });


        threeColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("color", "three color");
                editor.commit();

                Toast.makeText(ColorSettingActivity.this, "You choose three color",Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }





}
