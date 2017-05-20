package com.omapslab.andromaps.viewmodels;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * MVP Core
 * Concerned with setting up Calligraphy for custom fonts
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
