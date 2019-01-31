package com.omapslab.andromaps.weapon.slider.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.omapslab.andromaps.R;
import com.omapslab.andromaps.weapon.slider.handler.AndroomapsSliderHandler;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * AndroomapsSlider Slider
 * Create custom Slider
 * Weapon from Androomaps
 * <p>
 * Created by omapslab on 6/10/17.
 * Thanks to
 * - https://github.com/TobiasBuchholz/CircularViewPager
 * - https://github.com/Trinea/android-auto-scroll-view-pager
 */

public class AndroomapsSlider extends AutoScrollViewPager {

    private TypedArray a;
    public static int DIRECTION_LEFT = LEFT;
    public static int DIRECTION_RIGHT = RIGHT;
    private int interval = 0, direction = 0, paddingCorner = 0;
    private boolean isAutoPlay = true;
    private ItemSliderClickListener listener;

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
        if (!sliderAutoPlay) {
            stopAutoScroll();
        } else {
            startAutoScroll();
        }
        addOnPageChangeListener(new AndroomapsSliderHandler(this));

        if (interval != 0) {
            setInterval(interval);
        } else {
            setInterval(sliderInterval);
        }

        if (direction != 0) {
            setDirection(interval);
        } else {
            setDirection(sliderDirection);
        }

        if (paddingCorner != 0) {
            setPadding(paddingCorner, 0, paddingCorner, 0);
        }

        if (isAutoPlay) {
            startAutoScroll();
        } else {
            stopAutoScroll();
        }

        setupClickListener();
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("ANDROMAP", "Clicked");
            if(listener != null) {
                Log.i("ANDROMAP", "Clicked On Listener");
                listener.onItemSliderClick(AndroomapsSlider.this, getAdapter(), getCurrentItem());
            }
            return true;
        }
    }

    private void setupClickListener() {
        final GestureDetector tapGestureDetector = new    GestureDetector(getContext(), new TapGestureListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }


    public void setSliderInterval(int interval) {
        this.interval = interval;
    }

    public void setSliderDirection(int direction) {
        this.direction = direction;
    }

    public void setAutoPlay() {
        this.isAutoPlay = true;
    }

    public void stopAutoPlay() {
        this.isAutoPlay = false;
    }

    public void setSliderPaddingCorner(int cornerPadding) {
        this.paddingCorner = cornerPadding;
    }

    public void setOnItemSliderClickListener(ItemSliderClickListener listener) {
        this.listener = listener;
    }

    public interface ItemSliderClickListener {
        void onItemSliderClick(AndroomapsSlider androomapsSlider, PagerAdapter adapter, int currentItem);
    }
}
