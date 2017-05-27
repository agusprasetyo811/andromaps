package com.omapslab.andromaps.util;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by omapslab on 5/27/17.
 */

public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}
