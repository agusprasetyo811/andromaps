package com.omapslab.andromaps.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.omapslab.andromaps.util.URLImageParser;

/**
 * View Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class ViewHelper {

    private Context c;

    public ViewHelper(Context c) {
        this.c = c;
    }

    /**
     * @param gridView
     * @param columns
     */
    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns, int gridItemLength) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (gridItemLength % 2 == 0) ? totalHeight : totalHeight + 100;
        gridView.setLayoutParams(params);

    }

    /**
     * Hide Keyboard
     *
     * @param a
     */
    public void hideKeyboard(Activity a) {
        try {
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {}
    }

    /**
     * Hide Keyboard From window
     *
     * @param a
     */
    public void hideKeyboard(Activity a, View v) {
        try {
            final InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {}
    }

    /**
     * Show Keyboard
     *
     * @param a
     */
    public void showKeyboard(Activity a) {
        try {
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {}
    }

    /**
     * Is Keyboard shown
     *
     * @param a
     * @return
     */
    public boolean isKeyboardVisible(Activity a) {
        try {
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param htmlTextView
     * @param htmlData
     */
    public void setHtmlTextView(TextView htmlTextView, String htmlData) {
        URLImageParser p = new URLImageParser(htmlTextView, c);
        Spanned htmlSpan = Html.fromHtml(htmlData, p, null);
        htmlTextView.setText(htmlSpan);
    }

    /**
     * Set fullscreen
     *
     * @param a
     */
    public void setFullScreen(Activity a) {
        try {
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {

                setWindowFlag(a, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                a.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(a, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
                a.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } catch (Exception e) {}
    }

    /**
     * Set window flag
     * Thanks to @iwanz98 (gitlab)
     *
     * @param activity
     * @param bits
     * @param on
     */
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        try {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        } catch (Exception e) {}
    }

}
