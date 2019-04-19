package com.example.craftdemo.activities;

import android.content.Context;
import android.os.Bundle;

import com.example.craftdemo.MyAdapter;

import org.json.JSONArray;


public class MainActivity extends AbstractRecyclerActivity {

    public final static String OBJECT_URL_FOR_DATA = "OBJECT_URL_FOR_DATA";
    public final static String OBJECT_URL_FOR_KEY = "OBJECT_URL_FOR_KEY";
    public final static String OBJECT_URL_FOR_DESC = "OBJECT_URL_FOR_DESC";

    final String INTUIT_REPOS_URL = "https://api.github.com/users/intuit/repos";
    final String GITHUB_API_NAME = "name";
    final String GITHUB_API_DESCRIPTION = "description";

    String urlForData = null;
    String nameKey = null;
    String descKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlForData = getIntent().hasExtra(OBJECT_URL_FOR_DATA) ?
                getIntent().getStringExtra(OBJECT_URL_FOR_DATA) : INTUIT_REPOS_URL;
        nameKey = getIntent().hasExtra(OBJECT_URL_FOR_KEY) ?
                getIntent().getStringExtra(OBJECT_URL_FOR_KEY) : GITHUB_API_NAME;
        descKey = getIntent().hasExtra(OBJECT_URL_FOR_DESC) ?
                getIntent().getStringExtra(OBJECT_URL_FOR_DESC) : GITHUB_API_DESCRIPTION;

        getNewData(this, getDataURL());
    }

    @Override
    protected String getDataURL() {
        return urlForData;
    }

    @Override
    protected MyAdapter getAdapter(Context context, JSONArray myDataset) {
        return new MyAdapter(context, myDataset, nameKey, descKey);
    }
}
