package com.example.tvtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.tvtracker.Fragments.ShowDetailsFragment;

import org.json.JSONObject;

import java.util.ArrayList;

public class CustomShowAdapter extends RecyclerView.Adapter<CustomShowAdapter.CustomViewHolder> {

    //private ArrayList<JSONObject> shows;
    private ArrayList<Show> shows;
    private Context context;

    public CustomShowAdapter(ArrayList<Show> shows, Context context){
        this.shows=shows;
        this.context = context;
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

        holder.title.setText(show.getTitle);
        holder.summary.setText(show.getSummary);
        holder.posterImage.setImageResource();//TODO figure out how to pull image from api

    }

    @Override
    public int getItemCount() {
        return 0;
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

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add query to check if item clicked is in the watch list table
                    //if it is then display this TODO
                    new AlertDialog.Builder(context)
                            .setTitle("Remove From Watch List")
                            .setMessage("Are you sure you want to remove " + shows.get(getAdapterPosition()).getTitle() + " from your watch list?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Add REMOVE Query from watch list here TODO
                                    star.setImageResource(R.drawable.ic_star_border_black_24dp);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    //else add to the watch list and display a alert TODO
                }
            });
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v){
            Bundle extra = new Bundle();
            extra.putParcelable(ShowDetailsFragment.SHOW, shows.get(getAdapterPosition()));

            Navigation.findNavController(v).navigate(R.id.showDetails, extra);
        }

        @Override
        public boolean onLongClick(View v){

            //add query to check if item clicked is in the watch list table
            //if it is then display this TODO
            new AlertDialog.Builder(context)
                    .setTitle("Remove From Watch List")
                    .setMessage("Are you sure you want to remove " + shows.get(getAdapterPosition()).getTitle() + " from your watch list?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Add REMOVE Query from watch list here
                            star.setImageResource(R.drawable.ic_star_border_black_24dp);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            //else if it is in the watched list display another version
            //else do nothing TODO


            return false;
        }


    }
}
