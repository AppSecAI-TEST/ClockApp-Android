package com.example.tony.myclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by Jingguo Jiang on 24/11/2016.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private ClockThread clockthread = null;
    private SurfaceHolder surfaceHolder;
    private SharedPreferences settings;



    private Paint paint;

    private float length;
    private boolean hasSurface;


    public MySurfaceView(Context context) {
        super(context);

        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

    }

    public MySurfaceView(Context context,AttributeSet attrs) {
        super(context,attrs);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        setZOrderOnTop(true);
//         holder.setFormat(PixelFormat.TRANSLUCENT);
    }

    // create the clock drawing thread
    public void resume() {

        if (clockthread == null) {
            clockthread = new ClockThread(surfaceHolder);
            if (hasSurface) {
                clockthread.start();
                clockthread.running = true;
            }
        }

    }


    // Kill the clock drawing thread
    public void pause() {
        boolean retry = true;
        clockthread.running = false;

        while (retry) {
            try {
                clockthread.join();
                retry = false;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clockthread = null;
    }


    // create a surface
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        hasSurface = true;

        if(clockthread!=null) { //if has clock thread, start it
            clockthread.start();
        } else {
            resume(); // create a new thread, and start it
        }

        clockthread.running = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hasSurface = false;
        pause();
    }


    class ClockThread extends Thread {
        private SurfaceHolder holder;
        private boolean running;

        public ClockThread(SurfaceHolder holder) {
            this.holder = holder;
            running = true;
        }


        @Override
        public void run() {
            Log.d("TAG", "run method exe");
            settings = getContext().getSharedPreferences("Prefs", 0);
            String color = settings.getString("color","No Color Found");


            int hour, min, sec;
            while (running) {

                Canvas canvas = null;
                try {
                    synchronized (holder) {
                        canvas = holder.lockCanvas();// lock canvas
                        canvas.drawColor(Color.rgb(0, 129, 123));// set canvas background color

                        length = 450;

                        // set up all kinds of paints
                        paint = new Paint();
                        paint.setAntiAlias(true);
                        Paint hourPaint = new Paint(paint);
                        Paint circlePaint = new Paint(paint);
                        Paint secPaint = new Paint(paint);
                        Paint secHandPaint = new Paint(paint);
                        Paint minHandPaint = new Paint(paint);
                        Paint hourHandPaint = new Paint(paint);
                        Paint smallCirclePaint = new Paint(paint);


                        if (color.equals("basic color")){

                            int basicColor = Color.rgb(79,93,115);
                            hourPaint.setColor(basicColor);
                            circlePaint.setColor(basicColor);
                            secPaint.setColor(basicColor);
                            secHandPaint.setColor(basicColor);
                            minHandPaint.setColor(basicColor);
                            hourHandPaint.setColor(basicColor);
                            smallCirclePaint.setColor(basicColor);


                        } else if (color.equals("light color")){
                            hourPaint.setColor(Color.WHITE);
                            circlePaint.setColor(Color.rgb(92,174,164));
                            secPaint.setColor(Color.rgb(92,174,164));
                            secHandPaint.setColor(Color.rgb(254,174,14));
                            minHandPaint.setColor(Color.WHITE);
                            hourHandPaint.setColor(Color.WHITE);
                            smallCirclePaint.setColor(Color.WHITE);

                        } else if(color.equals("rainbow color")) {
                            hourPaint.setColor(Color.rgb(245,181,31));
                            circlePaint.setColor(Color.rgb(188,207,10));
                            secPaint.setColor(Color.rgb(245,176,208));
                            secHandPaint.setColor(Color.rgb(224,153,94));
                            minHandPaint.setColor(Color.rgb(81,26,109));
                            hourHandPaint.setColor(Color.rgb(0,137,200));
                            smallCirclePaint.setColor(Color.rgb(72,167,41));

                        } else if (color.equals("three color")){
                            hourPaint.setColor(Color.rgb(51,62,80));
                            circlePaint.setColor(Color.rgb(51,62,80));
                            secPaint.setColor(Color.rgb(255,140,118));
                            secHandPaint.setColor(Color.rgb(255,140,118));
                            minHandPaint.setColor(Color.rgb(255,140,118));
                            hourHandPaint.setColor(Color.rgb(255,224,122));
                            smallCirclePaint.setColor(Color.rgb(255,224,122));
                        }


                        //draw hour numbers
                        hourPaint.setStrokeWidth(5);

                        //RegPoly hourMarks = new RegPoly(getWidth() / 2, getHeight() / 2, length - 50, 12, canvas, hourPaint);
                       // hourMarks.drawPoints();

                        RegPoly hourNumbers = new RegPoly(getWidth() / 2, getHeight() / 2, length - 40, 12, canvas, hourPaint);
                        hourNumbers.drawClockNumber();


                        //draw big circle of the clock

                        circlePaint.setStyle(Paint.Style.STROKE);
                        circlePaint.setStrokeWidth(4);
                        canvas.drawCircle(getWidth() / 2, getHeight() / 2, length-80, circlePaint);


                        //draw secMarks
                        secPaint.setStrokeWidth(6);
                        RegPoly secMarks = new RegPoly(getWidth() / 2, getHeight() / 2, length, 60, canvas, secPaint);
                        secMarks.drawSecMarks();


                        //draw dynamic hands: sec, min, and hour
                        // delay 1 second
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        //get date
                        long time = System.currentTimeMillis();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(time);
                        hour = calendar.get(Calendar.HOUR_OF_DAY);
                        min = calendar.get(Calendar.MINUTE);
                        sec = calendar.get(Calendar.SECOND);
                        //day = calendar.get(Calendar.DATE);
                        //month = calendar.get(Calendar.MONTH);

                        Log.d("Time",Integer.toString(hour) + Integer.toString(min));


                        //draw second hand
                        secHandPaint.setStyle(Paint.Style.STROKE);
                        secHandPaint.setStrokeWidth(6);
                        RegPoly secHand   = new RegPoly(getWidth() / 2, getHeight() / 2, length-130, 60,canvas, secHandPaint);
                        secHand.drawRadius(sec+45);


                        //draw minute hand

                        minHandPaint.setStyle(Paint.Style.STROKE);
                        minHandPaint.setStrokeWidth(11);
                        RegPoly minHand  = new RegPoly(getWidth()/2, getHeight()/2, length-170, 60, canvas, minHandPaint );
                        minHand.drawRadius(min+45);

                        //draw hour hand

                        hourHandPaint.setStyle(Paint.Style.STROKE);

                        hourHandPaint.setStrokeWidth(13);
                        RegPoly hourHand  = new RegPoly( getWidth()/2, getHeight()/2, length-200, 60, canvas, hourHandPaint);
                        hourHand.drawRadius(hour * 5 + min / 12 + 45);


                        //draw small inner circle of the clock

                        smallCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

                        smallCirclePaint.setStrokeWidth(11);
                        canvas.drawCircle(getWidth() / 2, getHeight() / 2, length-435, smallCirclePaint);

                        //draw texts to show current time: (hour:min)
                        Paint textPaint = new Paint(paint);
                        textPaint.setColor(Color.WHITE);
                        textPaint.setTypeface(Typeface.create("Roboto", Typeface.NORMAL));
                        textPaint.setStrokeWidth(10);
                        textPaint.setTextSize(150);
                        canvas.drawText(Integer.toString(hour) + ":" + Integer.toString(min), 130,140, textPaint );
                        textPaint.setColor(Color.rgb(254,174,14));
                        textPaint.setTextSize(50);
                        int apm = calendar.get(Calendar.AM_PM);
                        String apmStr;
                        if (apm == 0) {
                            apmStr = "AM";
                        } else {
                            apmStr = "PM";
                        }
                        canvas.drawText(apmStr,500,140,textPaint);




                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);//unlock canvas
                    }
                }
            }

        }

    }
}
