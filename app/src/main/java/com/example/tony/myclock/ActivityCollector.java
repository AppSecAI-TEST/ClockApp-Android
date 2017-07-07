package com.example.tony.myclock;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jingguo Jiang on 05/12/2016.
 */

/**
 *  ActivityCollector class is used to manage all activities
 *  in order to allow the app to exit randomly.
 *
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    // put activities into list
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // remove activity from the list
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // when exit app, destroy all activities
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }



}
