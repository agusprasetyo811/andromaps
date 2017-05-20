package com.omapslab.andromaps.helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

/**
 * Activity Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class ActivityHelper {

    /**
     * ToActivity
     * @param a
     * @param activity
     */
    public void toActivity(Activity a, Class<?> activity) {
        Intent i = new Intent(a, activity);
        a.startActivity(i);
    }

    /**
     * ToActivity with Action
     * @param a
     * @param activity
     * @param listener
     */
    public void toActivity(Activity a, Class<?> activity, ToActivityListener listener) {
        Intent i = new Intent(a, activity);
        listener.setAction(i);
        a.startActivity(i);
    }

    /**
     * ToForceActivity
     * @param a
     * @param activity
     */
    public void toForceActivity(Activity a, Class<?> activity) {
        Intent i = new Intent(a, activity);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(i);
        a.finish();
    }

    /**
     * System Exit
     * @param a
     */
    public void systemExit(Activity a) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        a.startActivity(i);
    }

    /**
     * ToForceActivity with Action
     * @param a
     * @param activity
     * @param listener
     */
    public void toForceActivity(Activity a, Class<?> activity, ToActivityListener listener) {
        Intent i = new Intent(a, activity);
        listener.setAction(i);
        a.startActivity(i);
        a.finish();
    }

    public interface ToActivityListener {
        void setAction(Intent i);
    }

}
