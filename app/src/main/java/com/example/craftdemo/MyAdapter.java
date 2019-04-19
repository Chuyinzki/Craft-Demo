package com.example.craftdemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.craftdemo.Constants.GITHUB_API_DESCRIPTION;
import static com.example.craftdemo.Constants.GITHUB_API_NAME;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<JSONObject> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public MyAdapter(JSONArray myDataset) {
        List<JSONObject> toAdd = new ArrayList<>();
        for(int i = 0; i < myDataset.length(); i++) {
            try {
                toAdd.add(myDataset.getJSONObject(i));
            } catch (JSONException e) {
                Log.d(MyAdapter.class.getSimpleName(),
                        "Encountered unexpected value in JSONArray.", e);
            }
        }
        mDataset = toAdd;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JSONObject obj = mDataset.get(position);
        String name = "Error";
        String description = "Error";
        try {
            name = obj.getString(GITHUB_API_NAME);
            description = obj.getString(GITHUB_API_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView)holder.linearLayout.findViewById(R.id.list_item_name)).setText(name);
        ((TextView)holder.linearLayout.findViewById(R.id.list_item_description)).setText(description);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
