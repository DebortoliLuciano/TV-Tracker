package com.example.tvtracker.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvtracker.DBHandler;
import com.example.tvtracker.JavaBeans.Show;
import com.example.tvtracker.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchedListFragment extends Fragment {



    public WatchedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_watched_list, container, false);


        DBHandler db = new DBHandler(getContext());
        ArrayList<Show> shows = db.getWatchedShows("true");
        //TODO Add custom view addapter to this location


        return view;
    }

}
