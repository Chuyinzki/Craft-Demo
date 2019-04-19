package com.example.craftdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.craftdemo.activities.RepositoryDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.craftdemo.Util.getNameAndDescriptionFromJSON;
import static com.example.craftdemo.activities.RepositoryDetailActivity.OBJECT_INFO;
import static com.example.craftdemo.activities.RepositoryDetailActivity.OBJECT_TITLE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<JSONObject> mDataset;
    private Context context;
    private final String nameKey;
    private final String descKey;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View linearLayout;
        public MyViewHolder(View v) {
            super(v);
            linearLayout = v;
        }
    }

    public MyAdapter(Context context, JSONArray myDataset, String nameKey, String descKey) {
        this.nameKey = nameKey;
        this.descKey = descKey;
        List<JSONObject> toAdd = new ArrayList<>();
        if(myDataset != null) {
            for (int i = 0; i < myDataset.length(); i++) {
                try {
                    toAdd.add(myDataset.getJSONObject(i));
                } catch (JSONException e) {
                    Log.d(MyAdapter.class.getSimpleName(),
                            "Encountered unexpected value in JSONArray.", e);
                }
            }
        }
        mDataset = toAdd;
        this.context = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JSONObject obj = mDataset.get(position);
        final Pair<String,String> nameAndDesc = getNameAndDescriptionFromJSON(obj, nameKey, descKey);
        ((TextView)holder.linearLayout.findViewById(R.id.list_item_name)).setText(nameAndDesc.first);
        ((TextView)holder.linearLayout.findViewById(R.id.list_item_description)).setText(nameAndDesc.second);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RepositoryDetailActivity.class);
                i.putExtra(OBJECT_INFO, obj.toString());
                i.putExtra(OBJECT_TITLE, nameAndDesc.first);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
