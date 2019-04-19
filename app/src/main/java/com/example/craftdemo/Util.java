package com.example.craftdemo;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.craftdemo.Constants.GITHUB_API_DESCRIPTION;
import static com.example.craftdemo.Constants.GITHUB_API_NAME;

public class Util {

    public static Pair<String, String> getNameAndDescriptionFromJSON(JSONObject obj) {
        String name = "Error";
        String description = "Error";
        try {
            name = obj.getString(GITHUB_API_NAME);
            description = obj.getString(GITHUB_API_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Pair<>(name, description);
    }

    public static List<Pair<String, Object>> getAllInfoFromJSON(JSONObject obj) throws JSONException {
        List<Pair<String, Object>> retInfo = new ArrayList<>();
        Iterator<String> iter = obj.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            retInfo.add(new Pair<>(key, obj.get(key)));
        }
        return retInfo;
    }
}
