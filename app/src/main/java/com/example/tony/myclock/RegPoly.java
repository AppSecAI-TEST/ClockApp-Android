package com.example.tony.myclock;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Jingguo Jiang on 24/11/2016.
 */

public class RegPoly {
    private float x0, y0, r;
    private int n;
    private float x[], y[];

    private Canvas canvas = null;
    private Paint paint = null;

    private String[] clockNumbers = {"12","1","2","3","4","5","6","7","8","9","10","11"};
    private String num;

    public RegPoly(float x0, float y0, float r, int n, Canvas canvas, Paint paint) {
        this.x0 = x0;
        this.y0 = y0;
        this.r = r;
        this.n = n;
        this.canvas = canvas;
        this.paint = paint;

        x = new float[n]; y = new float[n];

        for(int i =0;i<n;i++) {
            x[i] = (float)(x0 + r * Math.cos(2*Math.PI*i/n));
            y[i] = (float)(y0 + r * Math.sin(2*Math.PI*i/n));
        }
    }

    public float getX(int i) {return x[i%n];}
    public float getY(int i) {return y[i%n];}


    public void drawRegPoly () {
        for(int i=0;i<n;i++) {
            canvas.drawLine(x[i], y[i], x[(i+1)%n], y[(i+1)%n], paint);
        }
    }

    public void drawPoints() {

        Log.d("TAG","draw Points");
        for(int i=0;i<n;i++) {
            canvas.drawCircle(x[i], y[i], 6, paint);
        }
    }

    public void drawRadius(int i) {
        canvas.drawLine(x0, y0, x[i%n], y[i%n], paint);

    }

    public void drawSecMarks() {

        for(int i=0;i<n;i++) {
            canvas.rotate(360 / 60, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2 - 360,
                    canvas.getWidth() / 2, canvas.getHeight() / 2 - 330, paint);
           // canvas.restore();
        }

    }

    public void drawClockNumber() {

        Rect textBounds = new Rect();

        canvas.save();
//      paint.setStrokeWidth(2);
        paint.setTextSize(50);

        int preX = canvas.getWidth() / 2;
        int preY = canvas.getHeight() / 2 - 370 - 3 ;// -radius- strokewidth

        int x,y;

        // cal the degree of canvas rotation each time
        int degree = 360 / clockNumbers.length;
        for(int i = 0; i < clockNumbers.length; i++){
            num = clockNumbers[i];
            paint.getTextBounds(num, 0, num.length(), textBounds);
            x = (int) (preX - paint.measureText(num) / 2);
            y = preY - textBounds.height();
            canvas.drawText(num, x, y, paint);
            canvas.rotate(degree, canvas.getWidth() / 2, canvas.getHeight() / 2);//rotate according to the center of circle
        }

        canvas.restore();
    }



}
