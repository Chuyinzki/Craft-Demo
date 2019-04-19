package com.example.craftdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    final String INTUIT_REPOS_URL = "https://api.github.com/users/intuit/repos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getNewData();

        //TODO: Disable rotation of app
        //TODO: Add pull down to refresh
        //TODO: Add logic to parse json into objects (Can stay as JSON objects if possible)
        //TODO: Add RecyclerView to display the repositories
        //TODO: Add Text to show if no objects were retrieved/or there was an error to try again.
        //TODO: Objects will all be gathered here and passed to other activities through intents for them to display.
    }

    private void getNewData() {
        //TODO: Show spinner and clear data/redraw to avoid clicking stale data
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, INTUIT_REPOS_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mAdapter = new MyAdapter(response);
                        recyclerView.setAdapter(mAdapter);
                        //TODO: Clear the spinner
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println();
                        // TODO: Clear the spinner and add text to ask user to try again. Consider toast as well.
                    }
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
