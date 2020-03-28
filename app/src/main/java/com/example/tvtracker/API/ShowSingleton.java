package com.example.tvtracker.API;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ShowSingleton {

    private static ShowSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private ShowSingleton(Context context){
        this.context = context;
    }

    public static ShowSingleton getInstance(Context context){
        if(instance==null){
            instance = new ShowSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

}
