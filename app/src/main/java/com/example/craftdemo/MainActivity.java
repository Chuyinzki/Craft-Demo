package com.example.craftdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Disable rotation of app
        //TODO: Add pull down to refresh
        //TODO: Add logic to parse json into objects (Can stay as JSON objects if possible)
        //TODO: Add RecyclerView to display the repositories
        //TODO: Add loading animation to avoid clicking stale objects
        //TODO: Add Text to show if no objects were retrieved/or there was an error to try again.
        //TODO: Objects will all be gathered here and passed to other activities through intents for them to display.
    }
}
