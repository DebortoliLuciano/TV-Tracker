package com.example.tvtracker.Fragments;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
 * @author Luciano DeBortoli
 */
public class ShowDetailsFragment extends Fragment {

    private Show show;
    public static final String SHOW = "Show";

    private int genreId;
    private int networkId;
    private String status;
    private DBHandler db;
    private ArrayList<Show> shows;


    public ShowDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_show_details, container, false);

        //find all elements in the layout
        final Context context = this.getContext();
        final TextView title = view.findViewById(R.id.titleText);
        final TextView description = view.findViewById(R.id.descriptionText);
        final TextView moreInfo = view.findViewById(R.id.moreInfoText);
        final TextView moreInfo2 = view.findViewById(R.id.moreInfoText2);
        final TextView moreInfo3 = view.findViewById(R.id.moreInfoText3);
        final TextView moreInfo4 = view.findViewById(R.id.moreInfoText4);
        final TextView moreInfo5 = view.findViewById(R.id.moreInfoText5);
        final ImageView imageView = view.findViewById(R.id.posterImage);
        final Button watchButton = view.findViewById(R.id.favoriteButton);
        final Button watchedButton = view.findViewById(R.id.watchedButton);
        final Button imdbButton = view.findViewById(R.id.imdbButton);


        //if a show is passed
        if(getArguments() != null){
            //unparse the object and asign it to show
            show = getArguments().getParcelable(SHOW);

            //get the db handler
            db = new DBHandler(context);

            //create a volley request
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + show.getTitle();
            System.out.println(url);

            //depending on the status of the show in the database change the text of the buttons
            if(show.getWatched().equals("false")){
                watchButton.setText("Remove from Watch List");
            }else if(show.getWatched().equals("true")){
                watchedButton.setText("Remove from Watched List");
            }else{
                watchButton.setText("Add to Watch List");
                watchedButton.setText("Add to Watched List");
            }


            //start a json object request
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //get the genre id, status and network id for the show
                                    genreId = db.getGenrebyName(response.getJSONArray("genres").getString(0)).getId();
                                    status = response.getString("status");

                                    networkId = db.getNetworkByName(response.getJSONObject("network").getString("name")).getId();

                                    //set the info into a text field

                                    moreInfo3.setText(status);
                                    moreInfo4.setText(show.getDay());
                                    moreInfo5.setText(show.getTime());
                                    moreInfo.setText(db.getGenre(genreId).getGenreName());
                                    moreInfo2.setText(db.getNetwork(networkId).getNetworkName());


                            } catch (Exception e) {
                                moreInfo3.setText(status);
                                moreInfo4.setText(show.getDay());
                                moreInfo5.setText(show.getTime());
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

            //if the show does not equal null
            if(show != null){

                //set info and image
                title.setText(show.getTitle());
                description.setText(show.getSummary());
                Picasso.get().load(show.getCover()).resize(310, 400).centerCrop().placeholder(R.drawable.ic_menu_camera).error(R.drawable.ic_contact_phone_black_24dp).into(imageView);

            }
            watchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //depending on the state of the show in the database use alert dialog boxes to change its status
                    if(show.getWatched().equals("false")){
                        new AlertDialog.Builder(context)
                                .setTitle("Remove From Watch List")
                                .setMessage("Are you sure you want to remove " + show.getTitle() + " from your watch list?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watchButton.setText("Add to Watch List");
                                        int id = db.getShowByName(show.getTitle()).getId();
                                        db.deleteShow(id);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else if(show.getWatched().equals("")) {
                        new AlertDialog.Builder(context)
                                .setTitle("Add To Watch List")
                                .setMessage("Are you sure you want to add " + show.getTitle() + " to your watch list?")
                                .setIcon(android.R.drawable.ic_input_add)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watchButton.setText("Remove from Watch List");
                                        //if it fails add the show to the show table
                                        show.setWatched("false");
                                        db.addShow(show);

                                        //add the main row to the main row table
                                        db.addMainRow(new MainRow(db.getShowByName(show.getTitle()).getId(), genreId, status, networkId));
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                }
            });

            //same as above but with buttons
            watchedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(show.getWatched().equals("true")){
                        new AlertDialog.Builder(context)
                                .setTitle("Remove From Watched List")
                                .setMessage("Are you sure you want to remove " + show.getTitle() + " from your watched list?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watchedButton.setText("Add to Watched List");
                                        int id = db.getShowByName(show.getTitle()).getId();
                                        db.deleteShow(id);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else if(show.getWatched().equals("")){
                        new AlertDialog.Builder(context)
                                .setTitle("Add To Watched List")
                                .setMessage("Are you sure you want to add " + show.getTitle() + " to your watched list?")
                                .setIcon(android.R.drawable.ic_input_add)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watchedButton.setText("Remove from Watched List");
                                        show.setWatched("true");
                                        db.addShow(show);

                                        db.addMainRow(new MainRow(db.getShowByName(show.getTitle()).getId(), genreId, status, networkId));
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }else if(show.getWatched().equals("false")){
                        new AlertDialog.Builder(context)
                                .setTitle("Add To Watched List")
                                .setMessage("Are you sure you want to add " + show.getTitle() + " to your watched list? (Will be removed from watch list)")
                                .setIcon(android.R.drawable.ic_input_add)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        watchButton.setText("Add to Watch List");
                                        show.setWatched("true");
                                        db.updateShow(show);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                }
            });

            //when the imdb button is clicked take the user to the imdb page
            if(show.getImdbID() != null){
                imdbButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, "http://imdb.com/title/"+show.getImdbID());
                        startActivity(intent);
                    }
                });
            }

        }




        return view;
    }

}
