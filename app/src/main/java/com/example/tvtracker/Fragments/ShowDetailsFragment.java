package com.example.tvtracker.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvtracker.JavaBeans.Show;
import com.example.tvtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDetailsFragment extends Fragment {

    Show show;
    public static final String SHOW = "Show";

    public ShowDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_details, container, false);

        final TextView title = view.findViewById(R.id.titleText);
        final TextView description = view.findViewById(R.id.descriptionText);
        final TextView moreInfo = view.findViewById(R.id.moreInfoText);
        final ImageView imageView = view.findViewById(R.id.posterImage);
        Button actionButton = view.findViewById(R.id.favoriteButton);

        if(getArguments() != null){
            show = getArguments().getParcelable(SHOW);
            if(show != null){
                title.setText(show.getTitle());
                description.setText(show.getSummary());
                //TODO add Picasso
                imageView.setImageResource(R.drawable.ic_menu_camera);
                moreInfo.setText("IMDB id: " + show.getImdbID() + "\n" + show.getDay() + "\n" + show.getTime());
            }
        }


        return view;
    }

}
