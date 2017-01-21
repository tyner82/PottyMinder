package com.tynercontracting.pottyminder.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tynercontracting.pottyminder.R;

/**
 * Created by Chris on 1/17/2017.
 */

public class PottyLogChartFragment extends Fragment{

    public PottyLogChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pottylog_chart, container, false);
    }
}
