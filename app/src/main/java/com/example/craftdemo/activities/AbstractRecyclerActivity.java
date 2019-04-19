package com.example.craftdemo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.craftdemo.MyAdapter;
import com.example.craftdemo.R;
import com.example.craftdemo.SingletonRequestQueue;

import org.json.JSONArray;

public abstract class AbstractRecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        refreshLayout = findViewById(R.id.swipe_to_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewData(refreshLayout.getContext(), getDataURL());
                refreshLayout.setRefreshing(false);
            }
        });

    }

    protected void getNewData(final Context context, String url) {
        recyclerView.setAdapter(null);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        recyclerView.setAdapter(getAdapter(context, response));
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(getAdapter(context, null));
                        progressDialog.dismiss();
                        Toast.makeText(context, "There was a problem getting the information. " +
                                "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    protected abstract String getDataURL();

    protected abstract MyAdapter getAdapter(Context context, JSONArray myDataset);
}
