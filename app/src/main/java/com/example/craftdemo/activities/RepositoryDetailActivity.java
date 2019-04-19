package com.example.craftdemo.activities;

import android.app.ProgressDialog;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;

import static com.example.craftdemo.Util.getAllInfoFromJSON;
import static com.example.craftdemo.activities.MainActivity.OBJECT_URL_FOR_DATA;
import static com.example.craftdemo.activities.MainActivity.OBJECT_URL_FOR_DESC;
import static com.example.craftdemo.activities.MainActivity.OBJECT_URL_FOR_KEY;

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
        setTitle(getIntent().getStringExtra(OBJECT_TITLE));

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

                final String urlEnding = "_url";
                final String githubAPIHost = "api.github.com";
                if (pair.first.endsWith(urlEnding)) {
                    String rawUrl = pair.second.toString();
                    final String url = rawUrl.contains("{") ? rawUrl.substring(0, rawUrl.indexOf('{')) : rawUrl;
                    try {
                        URI uri = new URI(url);
                        if(uri.getHost() == null || !uri.getHost().equals(githubAPIHost)) {
                            continue;
                        }
                    } catch (URISyntaxException e) {
                        Log.e(RepositoryDetailActivity.class.getSimpleName(), "Could not parse URI, skipping.");
                        continue;
                    }

                    View view2 = getLayoutInflater().inflate(R.layout.property_item, null);
                    ((TextView) view2.findViewById(R.id.list_item_name)).setText(
                            pair.first.substring(0, pair.first.indexOf(urlEnding)));
                    TextView itemDescription2 = view2.findViewById(R.id.list_item_description);
                    itemDescription2.setText("Click for more info");
                    view2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Intent i = new Intent(v.getContext(), MainActivity.class);
                            i.putExtra(OBJECT_URL_FOR_DATA, url);
                            i.putExtra(OBJECT_URL_FOR_KEY, pair.first.equals("issues_url") ? "title" : "id"); //"title" to make issues pretty
                            i.putExtra(OBJECT_URL_FOR_DESC, "url");
                            v.getContext().startActivity(i);
                        }
                    });
                    layout.addView(view2);
                }

            }
        } catch (JSONException e) {
            Log.e(RepositoryDetailActivity.class.getSimpleName(),
                    "Error occurred getting and populating JSON property data.");
            finish();
        }
    }
}
