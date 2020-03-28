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
import com.android.volley.toolbox.JsonArrayRequest;
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

    public CustomShowAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //get context
        final Context context = this.getContext();

        //create days string variable


        //create an array list of shows
        final ArrayList<Show> shows = new ArrayList<>();
        //create the custom adapter
        //shows.add(new Show("test", "test", "test", "test", "test", "test"));
        adapter = new CustomShowAdapter(shows, context);

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
                String url = "https://api.tvmaze.com/search/shows?q=" + searchBar.getText().toString();
                System.out.println(url);



                //TODO pull down genre table
                //requst jsonArray named response
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

//                                try{
//                                    JSONObject object = response.getJSONObject("show");
//                                    shows.add(new Show(object.getString("title"), "test", "test", "test", "test", "test"));
//                                }catch(Exception e){
//                                    e.printStackTrace();
//                                }

                                String days="";

                                //loop through json array
                                    for (int i = 0; i < response.length(); i++) {
                                //assign the current object from array to JsonObject named show
                                        try {
                                            JSONObject showObject = response.getJSONObject(i);
                                            if (showObject != null) {
                                                JSONObject show = showObject.getJSONObject("show");
                                                JSONObject schedule = show.getJSONObject("schedule");


                                                for(int x = 0; x < schedule.getJSONArray("days").length(); x++){

                                                    days += schedule.getJSONArray("days").getString(x) + " ";
                                                }

                                                for(int y = 0; y < show.getJSONArray("genres").length(); y++){
                                                    //TODO check if genre is in table
                                                    //TODO if not add to table
                                                }

                                                String summary = android.text.Html.fromHtml(show.getString("summary")).toString();


                                                shows.add(new Show(show.getString("name"), show.getJSONObject("externals").getString("imdb"), show.getJSONObject("schedule").getString("time"), days, show.getJSONObject("image").getString("medium"), summary));

                                            }
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }





                                    }

                                    adapter.notifyDataSetChanged();


                                System.out.println(shows);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(request);

            }
        });


        return view;
    }

}
