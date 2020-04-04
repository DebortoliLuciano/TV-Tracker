package com.example.tvtracker.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvtracker.CustomShowAdapter;
import com.example.tvtracker.DBHandler;
import com.example.tvtracker.JavaBeans.Show;
import com.example.tvtracker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class watchListFragment extends Fragment {


    public watchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_watch_list, container, false);

        Context context = this.getContext();
        DBHandler db = new DBHandler(getContext());
        ArrayList<Show> shows = db.getWatchedShows("false");
        CustomShowAdapter adapter = new CustomShowAdapter(shows, context);
        RecyclerView recyclerView = view.findViewById(R.id.watchList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

}
