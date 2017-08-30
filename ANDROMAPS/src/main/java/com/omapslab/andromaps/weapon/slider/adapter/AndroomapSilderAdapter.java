package com.omapslab.andromaps.weapon.slider.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.omapslab.andromaps.weapon.slider.fragment.AndroomapSliderFragment;
import com.omapslab.andromaps.weapon.slider.listener.AndroomapSliderAdapterListener;
import com.tobishiba.circularviewpager.library.BaseCircularViewPagerAdapter;

import java.util.List;

/**
 * Created by omapslab on 6/10/17.
 */

public class AndroomapSilderAdapter extends BaseCircularViewPagerAdapter<Object> {

    private int layout;
    private AndroomapSliderAdapterListener sliderAdapterListener;

    public AndroomapSilderAdapter(FragmentManager fragmentManager, List<Object> objects, int layout, AndroomapSliderAdapterListener sliderAdapterListener) {
        super(fragmentManager, objects);
        this.layout = layout;
        this.sliderAdapterListener = sliderAdapterListener;
    }



    @Override
    protected Fragment getFragmentForItem(Object o) {
        int position = getItemPosition(o);
        return AndroomapSliderFragment.init(o, position, layout, sliderAdapterListener);
    }
}
