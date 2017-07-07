package com.example.tony.myclock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by Jingguo Jiang on 27/11/2016.
 */

public class TitleLayout extends LinearLayout {

        public TitleLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            LayoutInflater.from(context).inflate(R.layout.title, this);
        }

}
