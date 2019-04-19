package com.example.craftdemo;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Util {

    public static Pair<String, String> getNameAndDescriptionFromJSON(JSONObject obj, String nameKey,
                                                                     String descKey) {
        String name = "Error";
        String description = "Error";
        try {
            name = obj.getString(nameKey);
            description = obj.getString(descKey);
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
