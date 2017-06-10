package com.omapslab.andromaps.weapon.slider.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.omapslab.andromaps.R;
import com.omapslab.andromaps.weapon.slider.handler.AndroomapsSliderHandler;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * AndroomapsSlider Slider
 * Create custom Slider
 * Weapon from Androomaps
 * <p>
 * Created by omapslab on 6/10/17.
 * Thank to
 *  - https://github.com/TobiasBuchholz/CircularViewPager
 *  - https://github.com/Trinea/android-auto-scroll-view-pager
 */

public class AndroomapsSlider extends AutoScrollViewPager {

    private TypedArray a;
    public static int DIRECTION_LEFT = LEFT;
    public static int DIRECTION_RIGHT = RIGHT;

    public AndroomapsSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        a = context.obtainStyledAttributes(attrs, R.styleable.AndroomapsSlider);
        int sliderPaddingCorner = a.getInteger(R.styleable.AndroomapsSlider_sliderPaddingCorner, 0);
        setClipToPadding(false);
        setPadding(sliderPaddingCorner, 0, sliderPaddingCorner, 0);
        setPageMargin(20);
    }

    public void build() {
        boolean sliderAutoPlay = a.getBoolean(R.styleable.AndroomapsSlider_sliderAutoPlay, true);
        int sliderInterval = a.getInteger(R.styleable.AndroomapsSlider_sliderInterval, DEFAULT_INTERVAL);
        int sliderDirection = a.getInteger(R.styleable.AndroomapsSlider_sliderDirection, DIRECTION_RIGHT);
        setInterval(sliderInterval);
        setDirection(sliderDirection);
        if (!sliderAutoPlay) {
            stopAutoScroll();
        } else {
            startAutoScroll();
        }
        addOnPageChangeListener(new AndroomapsSliderHandler(this));
    }

    public void setSliderInterval(int interval) {
        setInterval(interval);
    }

    public void setSliderDirection(int direction) {
        setInterval(direction);
    }

    public void setAutoPlay() {
        startAutoScroll();
    }

    public void stopAutoPlay() {
        stopAutoScroll();
    }

    public void setSliderPaddingCorner(int cornerPadding) {
        setPadding(cornerPadding, 0, cornerPadding, 0);
    }


}
