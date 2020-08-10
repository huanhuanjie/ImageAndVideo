package com.example.imageandvideo.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity容器
 */

public class ActivityCollector {
    private static List<Activity> listActivity = new ArrayList<>();

    public static void addActivity(Activity activity){
        listActivity.add(activity);
    }

    public static void removeActivity(Activity activity){
        listActivity.remove(activity);
    }

    public static void finish(){
        for (Activity activity: listActivity){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}