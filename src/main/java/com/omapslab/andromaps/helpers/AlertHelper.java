package com.omapslab.andromaps.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Alert Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public class AlertHelper {

    /**
     * Toast Helper
     * @param a
     * @param m
     */
    public void toast(Activity a, String m) {
        Toast.makeText(a, m, Toast.LENGTH_SHORT).show();
    }

    /**
     * DialogNeutralOk Helper
     * @param a
     * @param t
     * @param m
     */
    public void dialogNeutralOK(Activity a, String t, String m) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle(t);
        alertDialogBuilder.setMessage(m).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * DialogNeutralOk Helper
     * @param a
     * @param t
     * @param m
     * @param listener
     */
    public void dialogNeutralOK(Activity a, String t, String m, final dialogNeutralListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle(t);
        alertDialogBuilder.setMessage(m).setCancelable(false).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onClicked();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * DialogConfirm Helper
     * @param a
     * @param t
     * @param m
     * @param listener
     */
    public void dialogConfirm(Activity a, String t, String m, final dialogConfirmListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
        alertDialogBuilder.setTitle(t);
        alertDialogBuilder.setMessage(m).setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onOkClick();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onCancelClick();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /**
     * Interface DialogConfirm
     */
    public interface dialogConfirmListener {
        void onOkClick();

        void onCancelClick();
    }

    /**
     * Interface DialogNeutral
     */
    public interface dialogNeutralListener {
        void onClicked();
    }


}
