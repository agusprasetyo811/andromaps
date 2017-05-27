package com.omapslab.andromaps.helpers;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
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
     * @param a
     */
    public void hideKeyboard(Activity a) {
        InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * Show Keyboard
     * @param a
     */
    public void showKeyboard(Activity a) {
        InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    /**
     *
     * @param htmlTextView
     * @param htmlData
     */
    public void setHtmlTextView(TextView htmlTextView, String htmlData) {
        URLImageParser p = new URLImageParser(htmlTextView, c);
        Spanned htmlSpan = Html.fromHtml(htmlData, p, null);
        htmlTextView.setText(htmlSpan);
    }

}
