package com.omapslab.andromaps.weapon.slider.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omapslab.andromaps.R;
import com.omapslab.andromaps.weapon.slider.listener.AndroomapSliderAdapterListener;


/**
 * Created by omapslab on 6/10/17.
 */

@SuppressLint("ValidFragment")
public class AndroomapSliderFragment extends Fragment {

    Object o;
    int layout = R.layout.blank_slider, position = 0;
    private AndroomapSliderAdapterListener sliderAdapterListener;

    public static AndroomapSliderFragment init(Object o, int position, int layout, AndroomapSliderAdapterListener sliderAdapterListener) {
        AndroomapSliderFragment fragment;
        fragment = new AndroomapSliderFragment(o, layout, sliderAdapterListener);
        return fragment;
    }

    public AndroomapSliderFragment() {
    }

    public AndroomapSliderFragment(Object o, int layout, AndroomapSliderAdapterListener listener) {
        this.o = o;
        this.layout = layout;
        this.sliderAdapterListener = listener;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(layout, container, false);
        if (o != null) {
            sliderAdapterListener.onGenerateSlider(rootView, o, position);
        }
        return rootView;
    }
}
