package com.example.tvtracker.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.DBHandler;
import com.example.tvtracker.JavaBeans.Genre;
import com.example.tvtracker.JavaBeans.MainRow;
import com.example.tvtracker.JavaBeans.Network;
import com.example.tvtracker.JavaBeans.Show;
import com.example.tvtracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDetailsFragment extends Fragment {

    Show show;
    public static final String SHOW = "Show";

    int genreId;
    int networkId;
    String status;


    public ShowDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_show_details, container, false);

        final Context context = this.getContext();
        final TextView title = view.findViewById(R.id.titleText);
        final TextView description = view.findViewById(R.id.descriptionText);
        final TextView moreInfo = view.findViewById(R.id.moreInfoText);
        final ImageView imageView = view.findViewById(R.id.posterImage);
        Button actionButton = view.findViewById(R.id.favoriteButton);


        if(getArguments() != null){
            show = getArguments().getParcelable(SHOW);

            final DBHandler db = new DBHandler(context);
            final ArrayList<Show> shows = db.getAllShows();
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + show.getTitle();
            System.out.println(url);



            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                    genreId = db.getGenrebyName(response.getJSONArray("genres").getString(0)).getId();
                                    status = response.getString("status");
                                    //TODO Decide whether to keep this in
                                    //networkId = db.getNetworkByName("Fox").getId();

                                    moreInfo.setText(db.getGenre(genreId).getGenreName() +
                                            "\n" + db.getNetwork(1).getNetworkName() +
                                            "\n" + status +
                                            "\n" + "IMDB id: " + show.getImdbID() +
                                            "\n" + show.getDay() +
                                            "\n" + show.getTime());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getLocalizedMessage());
                }
            });
            queue.add(request);

            if(show != null){

                title.setText(show.getTitle());
                description.setText(show.getSummary());
                Picasso.get().load(show.getCover()).resize(210, 295).centerCrop().placeholder(R.drawable.ic_menu_camera).error(R.drawable.ic_contact_phone_black_24dp).into(imageView);

            }
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Show existingShow = db.getShowByName(show.getTitle().toString());

                        System.out.println(existingShow.getId());
                        System.out.println("Contained");
                    }catch (Exception e){
                        db.addShow(show);

                        db.addMainRow(new MainRow(db.getShowByName(show.getTitle()).getId(), genreId, status, 1));
                        System.out.println(db.getShowByName(show.getTitle()).getId());
                    }

                }
            });
        }




        return view;
    }

}
