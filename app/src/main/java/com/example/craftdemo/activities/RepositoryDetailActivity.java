package com.example.craftdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.craftdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.craftdemo.Util.getAllInfoFromJSON;

public class RepositoryDetailActivity extends AppCompatActivity {

    public final static String OBJECT_INFO = "OBJECT_INFO";
    public final static String OBJECT_TITLE = "OBJECT_TITLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject mJsonObject = null;
        if (!getIntent().hasExtra(OBJECT_INFO) || !getIntent().hasExtra(OBJECT_TITLE)) {
            finish();
        }

        try {
            mJsonObject = new JSONObject(getIntent().getStringExtra(OBJECT_INFO));
        } catch (JSONException e) {
            Log.e(RepositoryDetailActivity.class.getSimpleName(),
                    "There was an error retrieving the JSONObject from the calling class");
            finish();
        }

        setContentView(R.layout.repository_detail);
        //Initialize all the views
        ((TextView) findViewById(R.id.repository_name_tv)).setText(getIntent().getStringExtra(OBJECT_TITLE));

        LinearLayout layout = findViewById(R.id.repository_layout);
        try {
            List<Pair<String, Object>> allInfo = getAllInfoFromJSON(mJsonObject);
            for (final Pair<String, Object> pair : allInfo) {
                View view = getLayoutInflater().inflate(R.layout.property_item, null);
                ((TextView) view.findViewById(R.id.list_item_name)).setText(pair.first);
                TextView itemDescription = view.findViewById(R.id.list_item_description);
                if (!(pair.second instanceof JSONObject) && !(pair.second instanceof JSONArray)) {
                    itemDescription.setText(String.valueOf(pair.second));
                } else if (pair.second instanceof JSONObject) {
                    //TODO: Move to Strings file
                    itemDescription.setText("Click for more info");
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(v.getContext(), RepositoryDetailActivity.class);
                            i.putExtra(OBJECT_TITLE, pair.first);
                            i.putExtra(OBJECT_INFO, pair.second.toString());
                            v.getContext().startActivity(i);
                        }
                    });
                } else {
                    Log.e(RepositoryDetailActivity.class.getSimpleName(),
                            "JSON Array object or unexpected item found in JSON. Skipping:\n"
                                    + pair.second.toString());
                }
                layout.addView(view);
            }
        } catch (JSONException e) {
            Log.e(RepositoryDetailActivity.class.getSimpleName(),
                    "Error occurred getting and populating JSON property data.");
            finish();
        }


    }

}
