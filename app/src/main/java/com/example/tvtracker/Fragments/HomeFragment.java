package com.example.tvtracker.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.CustomShowAdapter;
import com.example.tvtracker.JavaBeans.Show;
import com.example.tvtracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //get context
        final Context context = this.getContext();

        //create an array list of shows
        final ArrayList<Show> shows = new ArrayList<>();
        //create the custom adapter
        final CustomShowAdapter adapter = new CustomShowAdapter(shows, context);
        //find the recycler view and set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.showList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        ImageView searchButton = view.findViewById(R.id.searchImage);
        final EditText searchBar = view.findViewById(R.id.searchBar);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shows.clear();
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://api.tvmaze.com/search/shows?q=" + searchBar.getText().toString();
                System.out.println(url);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject show = array.optJSONObject(i);
                                        if (show != null) {
                                            shows.add(new Show(show.getString("name"), show.getString("imdb"), show.getString("time"), "Saturday", "test", show.getString("summary")));//TODO add constuctor
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);

            }
        });


        return view;
    }

}
