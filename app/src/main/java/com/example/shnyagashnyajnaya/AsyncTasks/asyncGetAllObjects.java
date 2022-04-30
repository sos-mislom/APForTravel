package com.example.shnyagashnyajnaya.AsyncTasks;


import static com.example.shnyagashnyajnaya.MainActivity.otmapi;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.yandex.mapkit.geometry.Point;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class asyncGetAllObjects extends AsyncTask<String, Void, Map<String, ArrayList>> {

    @Override
    protected Map<String, ArrayList> doInBackground(String... parameter) {
        JSONArray jar = otmapi.GET_PLACES(parameter[0], parameter[1], parameter[2], parameter[3], parameter[4]);
        Map<String, ArrayList> map = new HashMap<>();
        for (int i = 0; i < jar.length(); i++) {
            ArrayList<String> content = new ArrayList<>();

        }
        return map;
    }



    @SuppressLint("ResourceType")
    @Override
    protected void onPostExecute(Map<String, ArrayList> result) {
        super.onPostExecute(result);

    }
}