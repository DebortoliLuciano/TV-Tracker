package com.example.tvtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.Fragments.ShowDetailsFragment;
import com.example.tvtracker.JavaBeans.MainRow;
import com.example.tvtracker.JavaBeans.Show;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class CustomShowAdapter extends RecyclerView.Adapter<CustomShowAdapter.CustomViewHolder> {

    //private ArrayList<JSONObject> shows;
    private ArrayList<Show> shows;
    private Context context;

    private int genreId;
    private int networkId;
    private String status;
    private DBHandler db;


    public CustomShowAdapter(ArrayList<Show> shows, Context context){
        this.shows=shows;
        this.context = context;
        this.db = new DBHandler(context);

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {

        final Show show = shows.get(position);

        final Show watchListShow = db.getShowByName(show.getTitle());


        holder.title.setText(show.getTitle());
        holder.summary.setText(show.getSummary());
        if(watchListShow != null && watchListShow.getWatched().equals("false")){
            holder.star.setImageResource(R.drawable.ic_star_half_black_24dp);
        }else if(watchListShow != null && watchListShow.getWatched().equals("true")){
            holder.star.setImageResource(R.drawable.ic_star_black_24dp);
        }else {
            holder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add query to check if item clicked is in the watch list table
                //if it is then display this TODO
                if(watchListShow != null && watchListShow.getWatched().equals("false")) {
                    new AlertDialog.Builder(context)
                            .setTitle("Remove From Watch List")
                            .setMessage("Are you sure you want to remove " + show.getTitle() + " from your watch list?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int id = watchListShow.getId();
                                    db.deleteShow(id);
                                    holder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }else if (watchListShow != null && watchListShow.getWatched().equals("true")){
                    new AlertDialog.Builder(context)
                            .setTitle("Remove From Watched List")
                            .setMessage("Are you sure you want to remove " + show.getTitle() + " from your watched list?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int id = watchListShow.getId();
                                    db.deleteShow(id);
                                    holder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Add to Watch List")
                            .setMessage("Are you sure you want to add " + show.getTitle() + " to your watch list?")
                            .setIcon(android.R.drawable.ic_input_add)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    show.setWatched("false");
                                    db.addShow(show);
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
                                                        networkId = db.getNetworkByName(response.getJSONObject("network").getString("name")).getId();

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

                                    //add the main row to the main row table
                                    db.addMainRow(new MainRow(db.getShowByName(show.getTitle()).getId(), genreId, status, networkId));
                                    holder.star.setImageResource(R.drawable.ic_star_half_black_24dp);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

            }
        });


        Picasso.get().load(show.getCover()).resize(210, 295).centerCrop().placeholder(R.drawable.ic_menu_camera).error(R.drawable.ic_contact_phone_black_24dp).into(holder.posterImage);


    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        protected TextView title;
        protected TextView summary;
        protected ImageView posterImage;
        protected ImageView star;


        public CustomViewHolder(final View view){
            super(view);
            this.title = view.findViewById(R.id.titleText);
            this.summary = view.findViewById(R.id.moreInfoText);
            this.posterImage = view.findViewById(R.id.posterImage);
            this.star = view.findViewById(R.id.favoriteStar);


            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v){
            final Show watchListShow = db.getShowByName(shows.get(getAdapterPosition()).getTitle());

            Bundle extra = new Bundle();
            if(watchListShow!=null){
                extra.putParcelable(ShowDetailsFragment.SHOW, watchListShow);
            }else {
                extra.putParcelable(ShowDetailsFragment.SHOW, shows.get(getAdapterPosition()));
            }


            Navigation.findNavController(v).navigate(R.id.showDetails, extra);
        }

        @Override
        public boolean onLongClick(View v){

            //add query to check if item clicked is in the watch list table
            //if it is then display this TODO

            final Show watchListShow = db.getShowByName(shows.get(getAdapterPosition()).getTitle());
            final Show show = shows.get(getAdapterPosition());

            if(watchListShow != null && watchListShow.getWatched().equals("true")){
                new AlertDialog.Builder(context)
                        .setTitle("Remove From Watched List")
                        .setMessage("Are you sure you want to remove " + show.getTitle() + " from your watched list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = watchListShow.getId();
                                db.deleteShow(id);
                                star.setImageResource(R.drawable.ic_star_border_black_24dp);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }else if(watchListShow != null && watchListShow.getWatched().equals("false")){
                new AlertDialog.Builder(context)
                        .setTitle("Remove From Watch List")
                        .setMessage("Are you sure you want to remove " + watchListShow.getTitle() + " from your watch list?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = watchListShow.getId();
                                db.deleteShow(id);
                                star.setImageResource(R.drawable.ic_star_border_black_24dp);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Add To Watched List")
                                        .setMessage("Are you sure you want to add " + watchListShow.getTitle() + " to your watched list?(Removes from watch list)")
                                        .setIcon(android.R.drawable.ic_input_add)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                show.setWatched("true");
                                                db.updateShow(show);
                                                star.setImageResource(R.drawable.ic_star_black_24dp);
                                            }
                                        })
                                        .setNegativeButton("No", null).show();
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(context)
                        .setTitle("Add to Watch List")
                        .setMessage("Are you sure you want to add " + show.getTitle() + " to your watch list?")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                show.setWatched("false");
                                db.addShow(show);
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

                                                    networkId = db.getNetworkByName(response.getJSONObject("network").getString("name")).getId();

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

                                //add the main row to the main row table
                                db.addMainRow(new MainRow(db.getShowByName(show.getTitle()).getId(), genreId, status, networkId));
                                star.setImageResource(R.drawable.ic_star_half_black_24dp);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }


            return false;
        }


    }
}
